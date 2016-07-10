package Server.phase;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.Combat;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_Combat extends Phase {
	
	private HashMap<String, Combat> combats;
	private Vector<String> combatOrder;
	private Combat currentCombat;
	private PlayerData nextProconsul;
	private Vector<String> annexationList = new Vector<>();

	public Phase_Combat(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		
		GameData game = logic.getGameData();

		//TODO TEST fake commands
//		int pro = 2;
//		game.makeProconsul(game.players.get(pro).playerID);
//		for (int i = 0; i < game.players.size(); i++) {
//			PlayerData p = game.players.get(i);
//			while(p.mercenaries.size() <= 3){
//				p.mercenaries.add(new Mercenary(p));
//			}
//			if (i == pro) continue;
//			int t = 0;
//			for (Mercenary m : p.mercenaries) {
//				t++;
//				t++;
//				t %= game.players.size();
//				if (t==i)t++;
//				m.setTarget(game.players.get( t ).playerID);
//				if (i==1 && t==pro) m.setDefendingProconsul(true); 
//			}
//		}
		// /TEST fake commands
		
		// creation of combat objects and prepare all lists
		combats = new HashMap<>();
		for (PlayerData p : game.players) {
				combats.put(p.playerID, new Combat(p));
		}
		for (PlayerData p : game.players) {
			for (Mercenary m : p.mercenaries) {
				String target = m.getTarget();
				boolean defending = m.isDefendingProconsul() || target.isEmpty();
				String combatOwner = target.isEmpty() ? p.playerID : target;
				Combat combat = combats.get(combatOwner);
				if (defending) {
					combat.addDefendingMercenary(m);
				} else {
					combat.addAttackingMercenary(m);
				}
			}
		}
		
		// set combatOrder
		// first fight is pro fight
		// next fights are following neighbors
		final String proconsulID = game.getProconsulID();
		final int proconsulIndex = game.players.getPlayerIndex(proconsulID);
		combatOrder = new Vector<>();
		final int numPlayers = game.players.size();
		for (int i = 0; i < numPlayers; i++) {
			combatOrder.add(game.players.get( (proconsulIndex + i) % numPlayers ).playerID);
		}

		currentCombat = getNextCombat();
		//game.setAllPlayersReady(currentCombat != null);
		if (currentCombat != null) {
			// always!
			currentCombat.nextStage();
			if (currentCombat.remaining_attackers.size() == 0) {
				currentCombat.stage = 4;
				// if pro is not attacked, provide promises
				providePromises();
				game.setAllPlayersReady(false);
			} else {
				game.setAllPlayersReady(true);
				for (PlayerData p : currentCombat.remaining_defenders.values()) {
					p.isReady = false;
				}
			}
		}
		
		logic.getGameData().combat = currentCombat;
		
	}
	
	private Combat getNextCombat() {
		while (combatOrder.size() > 0) {
			String pid = combatOrder.remove(0);
			if (combats.containsKey(pid) && (
					combats.get(pid).defender.isProconsul || combats.get(pid).remaining_attackers.size() > 0
					)) {
				return combats.get(pid);
			}
		}
		return null;
	}

	private void providePromises() {
		GameData game = logic.getGameData();
		for (PlayerData p : game.players) {
			boolean attacked = /*currentCombat == null ||*/ !currentCombat.attackers.containsKey(p.playerID);
			// player has not attacked in currentCombat
			while (p.getNumberOfPromisedCards() > 0) {
				ActionCard a = p.getPromise(0);
				p.removePromise(a);
				if (attacked) {
					game.discardCard(a);
				} else {
					p.addCard(a);
					currentCombat.addLootedCard(a, p.playerID);
				}
			}
		}
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		GameData game = logic.getGameData();
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		if (message.startsWith("confirm:combat")) {
			game.getPlayer(clientID).isReady = true;
			
			// next phase
			if (game.allPlayersAreReady()) {
				if (currentCombat == null) {
					game.setAllPlayersReady(false);
					logic.nextPhase();
				} else {
					// next stage or next Combat
					if (currentCombat.stage < 4) {
						currentCombat.nextStage();
						// set players unready
						if (currentCombat.stage == 1) {
							for (PlayerData p : currentCombat.remaining_defenders.values()) {
								p.isReady = false;
							}
						}
						if (currentCombat.stage == 2) {
							for (PlayerData p : currentCombat.remaining_attackers.values()) {
								p.isReady = false;
							}
						}
						if (currentCombat.stage == 3) {
							// roll the dices
							currentCombat.rollDices();
							//FIXME do all players have to ack?
							currentCombat.defender.isReady = false;
						}
						if (currentCombat.stage == 4) {
							// game ended, loot/provide cards
							boolean attackersWon = currentCombat.attackersWon();
							if (currentCombat.isProconsulFight()) {
								// grant promises to not attacking players
								for (PlayerData p : game.players) {
									if (attackersWon || currentCombat.attackers.containsKey(p.playerID)) {
										while (p.getNumberOfPromisedCards() > 0) {
											game.discardCard(p.getPromise(0));
										}
									} else {
										// player has not attacked pro
										while (p.getNumberOfPromisedCards() > 0) {
											ActionCard a = p.getPromise(0);
											currentCombat.addLootedCard(a, p.playerID);
											p.addCard(a);
										}
									}
								}
							}
							
							// loot cards
							if (attackersWon) {
								Vector<PlayerData> looters = currentCombat.getLooters();
								if (currentCombat.isProconsulFight()) {
									// remember new proconsul
									nextProconsul = looters.firstElement();
								}
								// give buildings
								while (annexationList.size() > 0 && currentCombat.defender.numberOfBuildings > 0) {
									PlayerData p = game.getPlayer(annexationList.remove(0));
									p.numberOfBuildings++;
									currentCombat.defender.numberOfBuildings--;
									looters.remove(p);
								}
								annexationList.clear();
								// give cards
								Random rnd = new Random();
								while (looters.size() > 0 && currentCombat.defender.getNumberOfCards() > 0) {
									PlayerData p = looters.remove(0);
									ActionCard a = currentCombat.defender.getCard( rnd.nextInt(currentCombat.defender.getNumberOfCards()) );
									currentCombat.addLootedCard(a, p.playerID);
									p.addCard(a);
								}
							}
							
							//FIXME do all players have to ack?
							currentCombat.defender.isReady = false;
						}
					} else {
						// next combat
						currentCombat = getNextCombat();
						if (currentCombat != null) {
							currentCombat.nextStage();
							// set players ready
							for (PlayerData p : currentCombat.remaining_defenders.values()) {
								p.isReady = false;
							}
						} else {
							game.setAllPlayersReady(false);
						}
						game.combat = currentCombat;
						if (currentCombat == null && nextProconsul != null) {
							game.makeProconsul(nextProconsul.playerID);
						}
					}
					// tell all clients
					sendGameDataToAllClients();
				}
			}
		}
		
		//combat_usecard:cardID
		if (message.startsWith("combat_usecard:")) {
			// if a card is used, add it to Combat
			String[] s = message.split(":", 3);
			ActionCard a = game.getPlayer(clientID).getCardByID(s[1]);
			if (a == null) {
				com.sendMessageToClient(clientID, "Sie haben keine Karte mit der ID: " + s[1] + "!");
			} else {
				if (currentCombat != null) {
					// ASSASSINATION
					if (a.getType() == CardType.ASSASSINATION) {
						if (s.length >= 3) {
							Mercenary m = currentCombat.att_mercs.get(s[2]);
							if (m==null) m = currentCombat.def_mercs.get(s[2]);
							currentCombat.destroyMercenary(m);
						} else {
							com.sendMessageToClient(clientID, "Ungültiger Befehl: " + message + "!");
						}
					}
					if (a.getType() == CardType.SLAVEREVOLT) {
						currentCombat.SLAVEREVOLT = true;
					}
					if (a.getType() == CardType.BRIBE) {
						currentCombat.BRIBE = true;
					}
					if (a.getType() == CardType.LION) {
						// Schlägt alleine angreifende Söldner jedes Angreifers in die Flucht
						HashMap<String, Mercenary> single = new HashMap<>();
						for (Mercenary m : currentCombat.att_mercs.values()) {
							if (!single.containsKey(m.playerID)) {
								single.put(m.playerID, m);
							} else {
								single.put(m.playerID, null);
							}
						}
						// entferne Söldner die von einem Angreifer alleine angreifen
						for (Mercenary m : single.values()) {
							if (m != null) currentCombat.removeMercenary(m);
						}
					}
					if (a.getType() == CardType.SURPRISEATTACK) {
						currentCombat.SURPRISEATTACK = true;
					}
					if (a.getType() == CardType.ANNEXATION) {
						// Als Angreifer vor einem Kampf, wenn Verteidiger mehr Gebäude hat
						if (currentCombat.defender.numberOfBuildings > game.getPlayer(clientID).numberOfBuildings + annexationList.size()) {
							annexationList.add(clientID);
						}
					}
					
					// add card to used card list
					currentCombat.addCardUsed(a, clientID);
				}
				
				// discard ActionCard
				logic.getGameData().discardCard(a);

				// tell all clients
				sendGameDataToAllClients();
			}
		}
	}

	@Override
	public void ReceivedGameStateFromClient(String clientID, GameData g) {}

	@Override
	public void ReceivedPlayerDataFromClient(String clientID, PlayerData p) {}

	@Override
	public PhaseType getNextPhaseType() {
		return PhaseType.SpendMoney;
	}

}
