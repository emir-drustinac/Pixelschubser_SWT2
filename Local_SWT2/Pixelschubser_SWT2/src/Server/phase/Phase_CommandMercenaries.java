package Server.phase;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.ActionCard;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_CommandMercenaries extends Phase {

	public Phase_CommandMercenaries(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		for (PlayerData p : logic.getGameData().players) {
			// unready players but proconsul
			p.isReady = p.isProconsul;
			// reset all mercs
			for (Mercenary m : p.mercenaries) {
				m.setTarget("");
				m.setDefendingProconsul(false);
			}
		}
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		
		// PATTERN:		"merc_command:mercID:targetID:isAttacking
		
		GameData game = logic.getGameData();
		PlayerData p = game.getPlayer(clientID);
		if (message.startsWith("merc_command:")) {
			String[] params = message.split(":", 4);
			if (params.length < 4) {
				com.sendMessageToClient(clientID, "Ungültiger Söldner-Befehl: " + message + "!");
				return;
			}
			String mercID = params[1];
			String targetID = params[2];
			boolean attacking = Boolean.valueOf(params[3]);
			boolean targetIsProconsul = game.getPlayer(targetID).isProconsul;
			if (p.isProconsul) {
				com.sendMessageToClient(clientID, "Proconsul kann seine Söldner nicht befehligen: " + message + "!");
				return;
			}
			// switching to defend myself
			if (targetID.equals(clientID)) {
				targetID = "";
			}
			System.out.println("targetID " + targetID + " attacking " + attacking + " clientID " + clientID);
			for (Mercenary m : p.mercenaries) {
				if (targetIsProconsul && m.getTarget().equals(targetID)) {
					m.setDefendingProconsul(!attacking);
				}
				if (m.mercID.equals(mercID)) {
					m.setTarget(targetID);
					m.setDefendingProconsul(!attacking);
				}
			}
			// merc mercID not found
			//com.sendMessageToClient(clientID, "mercID nicht gefunden: " + message + "!");
			sendGameDataToAllClients();
		}
		
		if (message.startsWith("confirm:command_mercs")) {
			// next phase
			p.isReady = true;
			if (game.allPlayersAreReady()) {
				logic.nextPhase();
			} else {
				sendGameDataToAllClients();
			}
		}
		
		// use card
		if (message.startsWith("command_usecard:")) {
			// if a card is used
			String[] s = message.split(":", 2);
			ActionCard a = game.getPlayer(clientID).getCardByID(s[1]);
			if (a == null) {
				com.sendMessageToClient(clientID, "Sie haben keine Karte mit der ID: " + s[1] + "!");
			} else {
				// discard ActionCard
				logic.getGameData().discardCard(a);

				// tell all clients
				sendGameDataToAllClients();
			}
		}
	}

	@Override
	public void ReceivedGameStateFromClient(String clientID, GameData g) {}

	@Override
	public void ReceivedPlayerDataFromClient(String clientID, PlayerData p) {}

	@Override
	public PhaseType getNextPhaseType() {
		return PhaseType.Combat;
	}

}
