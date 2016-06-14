package Server;

import SharedData.*;

public class GameLogic {

	private GameData game;
	private ServerCommunicator com;
	private Phase phase;
	
	public GameLogic(ServerCommunicator com) {
		this.com = com;
		com.setGameLogic(this);
	}

	public void nextPhase() {
		phase = phase.getNextPhase();
	}

	public void initNewGame() {
		game = new GameData();
		// TODO start with empty player list as every player will send "joinGame" message
		// first player
		game.addPlayer("a1000000-0000-0000-000000000000", "Alpha");
		PlayerData p = game.players.lastElement();
		p.isGameLeader = true;
		p.numberOfBuildings = 3;
		p.numberOfMercenaries = 3;
		// second player
		game.addPlayer("b2000000-0000-0000-000000000000", "Beta");
		game.makeProconsul("b2000000-0000-0000-000000000000");
		p = game.players.lastElement();
		p.numberOfBuildings = 1;
		p.numberOfMercenaries = 4;
	}

	/**
	 * 
	 * @param PID
	 * @param m
	 */
	public void receivedMessage(String PID, String m) {
		phase.ReceivedMessageFromClient(PID, m);
		System.out.println("Message from " + PID + " : " + m);
	}

	public void receivedGameData(String clientID, GameData g) {
		phase.ReceivedGameStateFromClient(clientID, g);
		System.out.println("GameData from " + clientID);
	}

	public boolean addPlayer(String playerID, String name) {
		game.addPlayer(playerID, name);
		com.sendGameDataToAllClients(game);
		return true;
	}

	public boolean removePlayer(String playerID) {
		boolean b = game.removePlayer(playerID); 
		if (b) {
			com.sendGameDataToAllClients(game);
		}
		return b;
	}

	public boolean startGame() {
		// TODO - implement GameLogic.startGame
		throw new UnsupportedOperationException();
	}

	public void mixCards() {
		// TODO - implement GameLogic.mixCards
		throw new UnsupportedOperationException();
	}

	public GameData getGameData() {
		return game;
	}

}
