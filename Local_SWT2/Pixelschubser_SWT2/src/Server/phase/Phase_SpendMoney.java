package Server.phase;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.ActionCard;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_SpendMoney extends Phase {
	private int nrOfReadyPlayers = 0;
	private int mercsCount, buildingsCount, cardsCount;
	private PlayerData pd;
	private boolean got5VictoryPoint = false;
	
	public Phase_SpendMoney(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		
		for(PlayerData pd : logic.getGameData().players) {
			if(pd.numberOfMercenaries() == 0) {
				pd.mercenaries.add(new Mercenary(pd));
			}
		}
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		pd = logic.getGameData().players.get(clientID);
		String msgToSend = "";
		
		if(message.startsWith("selectedCard:")) {
			String[] card = message.split(":", 2);
			ActionCard ac = pd.getCardByID(card[1]);
			pd.removeCard(ac);

			sendGameDataToAllClients();
		}
		
		if (message.startsWith("kaufen:")) {
			//add buyed items to this player game data
			String[] addItems = message.split(":", 5);
			mercsCount = Integer.parseInt(addItems[2]);
			while(mercsCount-- > 0) {
				pd.mercenaries.add(new Mercenary(pd));
			}
			buildingsCount = Integer.parseInt(addItems[3]);
			if(buildingsCount > 0) {
				pd.numberOfBuildings += buildingsCount;
			}
			cardsCount = Integer.parseInt(addItems[4]);
			if(cardsCount > 0) {
				ActionCard newAC = logic.getGameData().takeCard();
				pd.addCard(newAC);
				cardsCount--;
				com.sendPlayerDataToClient(clientID, pd);
			}

			sendGameDataToAllClients();
		}
		
		if (message.startsWith("weiter")) {
			nrOfReadyPlayers++;
			System.out.println("#####nrOfReadyPlayers: " + nrOfReadyPlayers);
			
			if(pd.getNumberOfVictoryPoints() >= 5) {
				got5VictoryPoint = true;
			}
			if(nrOfReadyPlayers == logic.getGameData().players.size()) {
				sendGameDataToAllClients();
				logic.nextPhase();
			}
		}
		
	}

	@Override
	public void ReceivedGameStateFromClient(String clientID, GameData g) {}

	@Override
	public void ReceivedPlayerDataFromClient(String clientID, PlayerData p) {}

	@Override
	public PhaseType getNextPhaseType() {
		return got5VictoryPoint ? PhaseType.DeclareWinner : PhaseType.CardLimit;
	}

}
