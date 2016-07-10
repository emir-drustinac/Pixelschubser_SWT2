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
		GameData game = logic.getGameData();
		PlayerData proconsul = game.getPlayer(clientID);
		int numberOfPlayersWithoutPromises = game.players.size() - game.numberOfPlayersWithPromisedCards() - 1;
		if (proconsul != null && proconsul.isProconsul) {
			//promise_card:cardid:playerid
			if (message.startsWith("promise_card:")) {
				String[] s = message.split(":", 3);
				ActionCard actioncard = proconsul.getCardByID(s[1]);
				PlayerData player = game.getPlayer(s[2]);
				if (actioncard != null && player != null) {
					// check for enough cards to promise all players
					if (proconsul.getNumberOfCards() > numberOfPlayersWithoutPromises
						|| player.getNumberOfPromisedCards() == 0 ) {
						player.addPromise(actioncard);
						com.sendMessageToClient(clientID, "u promised a card to " + player.name);
						//com.sendPlayerDataToClient(player.playerID, player);
						//com.sendPlayerDataToClient(clientID, player);
						sendGameDataToAllClients();
					} else {
						com.sendMessageToClient(clientID, "Warning: you would not have enough cards left to promise all players!");
					}
				} else {
					com.sendMessageToClient(clientID, "ERROR: Card " + s[1] + " or Player " + s[2] + " not found!");
					sendGameDataToAllClients();
				}
			}

			// next phase
			if (message.startsWith("confirm:promise_cards")) {
				// check if all players have promises
				if (game.numberOfPlayersWithPromisedCards() == game.players.size() - 1) {
					logic.nextPhase();
				} else {
					com.sendMessageToClient(clientID, "ERROR: u have not promised cards to all players yet!");
				}
			}
		}
	}

	@Override
	public void ReceivedGameStateFromClient(String clientID, GameData g) {}

	@Override
	public void ReceivedPlayerDataFromClient(String clientID, PlayerData p) {}

	@Override
	public PhaseType getNextPhaseType() {
		return PhaseType.CommandMercenaries;
	}

}
