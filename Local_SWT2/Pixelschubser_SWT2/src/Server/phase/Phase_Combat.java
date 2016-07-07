package Server.phase;

import java.util.HashMap;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.Combat;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_Combat extends Phase {
	
	private HashMap<String, Combat> combats;

	public Phase_Combat(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		
		// creation of combat objects and prepare all lists
		combats = new HashMap<>();
		GameData game = logic.getGameData();
		for (PlayerData p : game.players) {
			for (Mercenary m : p.mercenaries) {
				String target = m.getTarget();
				boolean defending = m.isDefendingProconsul() || target.isEmpty();
				String combatOwner = target.isEmpty() ? p.playerID : target;
				Combat combat = combats.get(combatOwner);
				if (combat == null){
					combat = new Combat(game.getPlayer(combatOwner));
					combats.put(combatOwner, combat);
				}
				if (defending) {
					combat.addDefendingMercenary(m);
				} else {
					combat.addAttackingMercenary(m);
				}
			}
		}
		
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
//		if (message.startsWith("MessageString:")) {
//			String name = message.split(":", 2)[1];
//			logic.addPlayer(clientID, name);
//		}
		
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
		return PhaseType.SpendMoney;
	}

}
