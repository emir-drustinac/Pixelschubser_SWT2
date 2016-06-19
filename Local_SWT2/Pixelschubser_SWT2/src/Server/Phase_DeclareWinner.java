package Server;

import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;
import SharedData.PlayerList;

public class Phase_DeclareWinner extends Phase {

	private PlayerList winners;
	
	public Phase_DeclareWinner(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		winners = logic.getWinners();
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
		if(winners.size()==0)
			return PhaseType.CardLimit;
		else 
			return PhaseType.GameOver;
	}

}
