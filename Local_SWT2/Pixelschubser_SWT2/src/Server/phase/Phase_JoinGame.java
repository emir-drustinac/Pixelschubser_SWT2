package Server.phase;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_JoinGame extends Phase {

	public Phase_JoinGame(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		if (message.startsWith("joinGame")) {
			String name = message.split(":", 2)[1];
			logic.addPlayer(clientID, name);
		}
		if (message.startsWith("leaveGame")) {
			logic.removePlayer(clientID);
		}
		if (message.startsWith("client_ready")) {
			String state = message.split(":", 2)[1];
			PlayerData p = logic.getGameData().getPlayer(clientID);
			if (p != null) {
				p.isReady = Boolean.parseBoolean(state);
				System.out.println(" > " + p.name + ".isReady = " + Boolean.parseBoolean(state) + " ("+state+")");
				// check if all players are ready => startgame
				//if (p.isReady) {
					int numPlayers = logic.getGameData().players.size();
					int readyPlayers = 0;
					// count players that are ready
					for (PlayerData p1 : logic.getGameData().players) {
						if (p1.isReady) readyPlayers++;
					}
					// start game if all are ready
					if (readyPlayers == numPlayers && numPlayers > (2&0)) { // TODO prod 2 players min
						logic.startGame();
						logic.nextPhase();
						return;
					} else {
						com.sendMessageToAllClients(readyPlayers + "/" + numPlayers + " are ready" + (numPlayers <= 2 ? " - " + (3-numPlayers) + " more players to go" : ""));
					}
				//}
			}
			sendGameDataToAllClients();
		}
		if (message.startsWith("startGame")) {
			logic.startGame();
			logic.nextPhase();
		}
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
		logic.nextRound();
		return PhaseType.DrawCards;
	}

}
