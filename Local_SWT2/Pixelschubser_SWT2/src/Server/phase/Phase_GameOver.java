package Server.phase;

import java.util.Timer;
import java.util.TimerTask;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;
import SharedData.PlayerList;
import SharedData.TopPlayer;

public class Phase_GameOver extends Phase {

	private Timer timer;

	public Phase_GameOver(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		
		//TODO Phase_GameOver (upd db, get top 10)
		// save current winner to db and load top 10 winners of all time
		PlayerList winners = logic.getGameData().getWinnerList();
		for (PlayerData p : winners) {
			// add players not in db
			String qry_add = "insert into TopPlayers (ID, Name) select ID, Name from (select (?) as ID, (?) as Name) n where not exists (select 1 from TopPlayers t where t.ID = n.ID)";

			// add points and money to player in db and increase number of games
			String qry_upd = "update TopPlayers set points += (?), money += (?), games += 1 where ID = (?)";
		}

		// load top 10 players desc by points, money, games
		String qry_top = "select top 10 * from TopPlayers order by points desc, money desc, games asc";
//		X rd = sql.getReader(qry);
		int nPlayers = winners.size(); //rd.numRows;
		TopPlayer[] list = new TopPlayer[nPlayers];
		int n = 0;
//		while (rd.next()) {
		for (PlayerData p : winners) {
//			list[n++] = new TopPlayer(rd["place"], rd["name"], rd["points"], rd["money"], rd["games"]);
			list[n++] = new TopPlayer(String.valueOf(n), p.name, String.valueOf(p.getNumberOfVictoryPoints()), 
					String.valueOf(p.getAmountOfMoney()), "1");
		}
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		// next phase
		if (message.startsWith("confirm:game_over")) {
			// auto next after x Seconds
			if (timer == null) {
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						System.out.println("<Timer>");
						logic.nextPhase();
					}
				}, 10000);
			}
		}
	}

	@Override
	public void ReceivedGameStateFromClient(String clientID, GameData g) {}

	@Override
	public void ReceivedPlayerDataFromClient(String clientID, PlayerData p) {}

	@Override
	public PhaseType getNextPhaseType() {
		return PhaseType.JoinGame;
	}

}
