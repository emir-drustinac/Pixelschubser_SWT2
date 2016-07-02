package Server.phase;

import java.util.Timer;
import java.util.TimerTask;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;
import SharedData.PlayerList;

public class Phase_DeclareWinner extends Phase {

	private PlayerList winners;
	private Timer timer;
	
	public Phase_DeclareWinner(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		winners = logic.getWinners();
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		// next phase
		if (message.startsWith("confirm:declare_winners")) {
			// auto next after x Seconds
			if (timer == null) {
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						System.out.println("<Timer>");
						logic.nextPhase();
					}
				}, 3000);
			}
		}
	}

	@Override
	public void ReceivedGameStateFromClient(String clientID, GameData g) {}

	@Override
	public void ReceivedPlayerDataFromClient(String clientID, PlayerData p) {}

	@Override
	public PhaseType getNextPhaseType() {
		if(winners.size()==0)
			return PhaseType.CardLimit;
		else 
			return PhaseType.GameOver;
	}

}
