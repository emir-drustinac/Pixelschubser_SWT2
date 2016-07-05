package Server.phase;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
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
			// TODO TESTING
			if(p.mercenaries.size() <= 3){
				p.mercenaries.add(new Mercenary());
				p.mercenaries.add(new Mercenary());
				p.mercenaries.add(new Mercenary());
			}
			// /TESTING
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
				com.sendMessageToClient(clientID, "ERROR: malformed merc_command: " + message);
				return;
			}
			String mercID = params[1];
			String targetID = params[2];
			boolean attacking = Boolean.valueOf(params[3]);
			boolean targetIsProconsul = game.getPlayer(targetID).isProconsul;
			if (p.isProconsul) {
				com.sendMessageToClient(clientID, "ERROR: proconsul cannot command his mercenaries: " + message);
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
			//com.sendMessageToClient(clientID, "ERROR: mercID not found: " + message);
			com.sendGameDataToAllClients(game);
		}
		
		if (message.startsWith("confirm:command_mercs")) {
			// next phase
			p.isReady = true;
			if (game.allPlayersAreReady()) {
				logic.nextPhase();
			} else {
				com.sendGameDataToAllClients(game);
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
