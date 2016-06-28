package Server.phase;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.ActionCard;
import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_MakePromises extends Phase {

	public Phase_MakePromises(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		PlayerData proconsul = logic.getGameData().getPlayer(clientID);
		if (proconsul != null && proconsul.isProconsul) {
			//promise_card:cardid:playerid
			if (message.startsWith("promise_card:")) {
				String[] s = message.split(":", 3);
				ActionCard a = proconsul.getCardByID(s[1]);
				PlayerData p = logic.getGameData().getPlayer(s[2]);
				if (a != null && p != null) {
					// check for enough cards to promise all players
					if (proconsul.getNumberOfCards() > logic.getGameData().numberOfPlayersWithPromisedCards()
						|| p.getNumberOfPromisedCards() == 0 ) {
						p.addCard(a);
						com.sendMessageToClient(clientID, "promise_ack:true:u promised a card!");
						com.sendPlayerDataToClient(p.playerID, p);
						com.sendPlayerDataToClient(clientID, p);
					} else {
						com.sendMessageToClient(clientID, "promise_ack:false:Warning: you would not have enough cards left!");
					}
				} else {
					com.sendMessageToClient(clientID, "promise_ack:false:ERROR: Card or Player not found!");
				}
			}

			// next phase
			//logic.nextPhase();
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
		return PhaseType.CommandMercenaries;
	}

}
