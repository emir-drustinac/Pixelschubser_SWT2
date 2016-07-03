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
			if(p.mercenaries.size() == 0){
				p.mercenaries.add(new Mercenary());
			}
		}
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		
		// PATTERN:		"merc_command:" + selectedMerc + ":" + targetID + ":" + isAtacking?
		// TODO
		
		if (message.startsWith("merc_command:")) {
			String name = message.split(":", 2)[1];
//			logic.addPlayer(clientID, name);
		}
		
		// next phase
		//logic.nextPhase();
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
		return PhaseType.Combat;
	}

}
