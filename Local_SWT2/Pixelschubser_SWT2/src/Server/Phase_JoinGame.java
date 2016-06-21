package Server;

import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_JoinGame extends Phase {

	public Phase_JoinGame(ServerGameLogic logic, ServerCommunicator com) {
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
				// check if all players are ready => startgame
				if (p.isReady) {
					boolean allready = true;
					for (PlayerData p1 : logic.getGameData().players) {
						if (!p1.isReady) allready = false;
					}
					if (allready && logic.getGameData().players.size() > 2) {
						logic.startGame();
						logic.nextPhase();
					}
				}
			}
			sendGameDataToAllClients();
			// TODO: check if all ready, then next phase
			//logic.nextPhase();
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
		return PhaseType.DrawCards;
	}

}
