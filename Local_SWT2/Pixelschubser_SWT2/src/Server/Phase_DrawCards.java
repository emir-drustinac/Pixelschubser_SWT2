package Server;

import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_DrawCards extends Phase {

	public Phase_DrawCards(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.print(" > " + this.getClass().getSimpleName() + " " + clientID + " " + message);
		if (message.startsWith("MessageString:")) {
			String name = message.split(":", 2)[1];
			logic.addPlayer(clientID, name);
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
		return PhaseType.MakePromises;
	}

}
