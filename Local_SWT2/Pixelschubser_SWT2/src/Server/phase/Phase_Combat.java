package Server.phase;

import java.util.HashMap;
import java.util.Vector;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.ActionCard;
import SharedData.Combat;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_Combat extends Phase {
	
	private HashMap<String, Combat> combats;
	private Vector<String> combatOrder;
	private Combat currentCombat;

	public Phase_Combat(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		
		GameData game = logic.getGameData();

		//TODO TEST fake commands
		int pro = 2;
		game.makeProconsul(game.players.get(pro).playerID);
		for (int i = 0; i < game.players.size(); i++) {
			PlayerData p = game.players.get(i);
			while(p.mercenaries.size() <= 3){
				p.mercenaries.add(new Mercenary(p));
			}
			if (i == pro) continue;
			int t = 0;
			for (Mercenary m : p.mercenaries) {
				t++;
				t++;
				t %= game.players.size();
				if (t==i)t++;
				m.setTarget(game.players.get( t ).playerID);
				if (i==1 && t==pro) m.setDefendingProconsul(true); 
			}
		}
		// /TEST fake commands
		
		// creation of combat objects and prepare all lists
		combats = new HashMap<>();
		for (PlayerData p : game.players) {
			for (Mercenary m : p.mercenaries) {
				String target = m.getTarget();
				boolean defending = m.isDefendingProconsul() || target.isEmpty();
				String combatOwner = target.isEmpty() ? p.playerID : target;
				Combat combat = combats.get(combatOwner);
				if (combat == null){
					combat = new Combat(game.getPlayer(combatOwner));
					combats.put(combatOwner, combat);
				}
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
		
		// if pro is not attacked, provide promises
		if (!combats.containsKey(proconsulID)) providePromises();
		
		logic.getGameData().combat = currentCombat;
		
	}
	
	private Combat getNextCombat() {
		while (combatOrder.size() > 0) {
			String pid = combatOrder.remove(0);
			if (combats.containsKey(pid)) {
				return combats.get(pid);
			}
		}
		return null;
	}

	private void providePromises() {
		GameData game = logic.getGameData();
		for (PlayerData p : game.players) {
			boolean attacked = currentCombat == null || !currentCombat.attackers.containsKey(p.playerID);
			// player has not attacked in currentCombat
			while (p.getNumberOfPromisedCards() > 0) {
				ActionCard a = p.getPromise(0);
				p.removePromise(a);
				if (attacked) {
					game.discardCard(a);
				} else {
					p.addCard(a);
				}
			}
		}
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
//		if (message.startsWith("MessageString:")) {
//			String name = message.split(":", 2)[1];
//			logic.addPlayer(clientID, name);
//		}
		
		// if a card is used, add it to the new activeCardList in GameData
		//ActionCard a = ...
		//logic.getGameData().activeCardList.addCard(a);
		
		// next phase
		//logic.nextPhase();
	}

	@Override
	public void ReceivedGameStateFromClient(String clientID, GameData g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void ReceivedPlayerDataFromClient(String clientID, PlayerData p) {
		// TODO Auto-generated method stub
	}

	@Override
	public PhaseType getNextPhaseType() {
		return PhaseType.SpendMoney;
	}

}
