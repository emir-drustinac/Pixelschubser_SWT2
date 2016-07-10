package Server.phase;

import java.util.Timer;
import java.util.TimerTask;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.ActionCard;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_DrawCards extends Phase {
	
	private static Timer timer;

	public Phase_DrawCards(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		
		//logic.getGameData().printDeck();
		
		// draw cards for every player
		GameData g = logic.getGameData();
		int numPlayers = g.players.size();
		// TODO: proconsul draws first?
		for (PlayerData p : g.players) {
			if (p.isProconsul) {
				int numCards = numPlayers + 2;
				drawCardsForPlayer(g, p, numCards);
			}
			//each player gets one mercenary at the beginning
			p.mercenaries.add(new Mercenary(p));
		}
		// TODO: other players draw in which order?
//		Vector<PlayerData> orderedPlayers = new Vector<>();
//		for ... {
//			orderedPlayers.add(p);
//		}
//		for (PlayerData p : orderedPlayers) {
		for (PlayerData p : g.players) {
			if (!p.isProconsul) {
				int numCards = g.round == 1 ? 2 : 1; // 2 on first round, 1 after
				drawCardsForPlayer(g, p, numCards);
			}
		}
		
		//logic.getGameData().printDeck();
		
		// new gamedata is automatically send to all clients
		// all players are not ready
		logic.getGameData().setAllPlayersReady(false);
	}

	/**
	 * @param g current GameData
	 * @param p player who gets the cards
	 * @param numCards number of cards drawn
	 */
	private void drawCardsForPlayer(GameData g, PlayerData p, int numCards) {
		for (int i = 0; i < numCards; i++) {
			ActionCard a = g.takeCard();
			if (a != null) {
				a.drawnInRound = g.round;
				p.addCard(a);
			}
		}
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");

		if (message.startsWith("confirm")) {
			logic.getGameData().getPlayer(clientID).isReady = true;
			// next phase
			if (logic.getGameData().allPlayersAreReady()) {
				logic.nextPhase();
			}
			// auto next after x Seconds
			if (timer == null) {
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						System.out.println("<Timer>");
						logic.nextPhase();
					}
				}, 1000);
			}
		}
	}

	@Override
	public void ReceivedGameStateFromClient(String clientID, GameData g) {
	}

	@Override
	public void ReceivedPlayerDataFromClient(String clientID, PlayerData p) {
	}

	@Override
	public PhaseType getNextPhaseType() {
		return PhaseType.MakePromises;
	}

}
