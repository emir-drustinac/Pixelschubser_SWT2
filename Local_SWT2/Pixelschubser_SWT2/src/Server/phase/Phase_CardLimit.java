package Server.phase;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_CardLimit extends Phase {
	
	private int nrOfReadyPlayers = 0;

	public Phase_CardLimit(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		PlayerData pd = logic.getGameData().players.get(clientID);
		
		if (message.startsWith("selectedCard:")) {
			String card = message.split(":", 2)[1];
			pd.removeCard(pd.getCardByID(card));
			com.sendPlayerDataToClient(clientID, pd);
		}
		if (message.startsWith("weiter")) {
			nrOfReadyPlayers++;
			if(nrOfReadyPlayers == logic.getGameData().players.size()) {
				sendGameDataToAllClients();
				logic.nextPhase();
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
		return PhaseType.DrawCards;
	}

}
