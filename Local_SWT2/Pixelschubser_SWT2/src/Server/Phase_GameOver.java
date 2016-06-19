package Server;

import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_GameOver extends Phase {

	public Phase_GameOver(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return PhaseType.JoinGame;
	}

}
