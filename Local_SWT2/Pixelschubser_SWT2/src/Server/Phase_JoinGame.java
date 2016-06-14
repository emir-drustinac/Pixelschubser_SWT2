package Server;

import SharedData.GameData;
import SharedData.PlayerData;

public class Phase_JoinGame extends Phase {

	public Phase_JoinGame(GameLogic logic, ServerCommunicator com) {
		super(logic, com);
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.print(" > " + this.getClass().getSimpleName() + " " + clientID + " " + message);
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
			}
			sendGameDataToAllClients();
		}
		if (message.startsWith("startGame")) {
			logic.startGame();
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
	public Phase getNextPhase() {
		//TODO return new Phase_DrawCards(logic, com);
		return null;
	}

}
