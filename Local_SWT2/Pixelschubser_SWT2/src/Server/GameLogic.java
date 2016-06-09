package Server;

import SharedData.*;

public class GameLogic {

	private GameData game;
	private ServerCommunicator com;
	
	public GameLogic(ServerCommunicator com) {
		this.com = com;
		com.setGameLogic(this);
	}

	public void nextPhase() {
		// TODO - implement GameLogic.nextPhase
		throw new UnsupportedOperationException();
	}

	public void initNewGame() {
		game = new GameData();
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
	public void sendMessageToClient(String PID, String m) {
		// TODO - implement GameLogic.sendMessageToClient
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param PID
	 * @param m
	 */
	public void receivedMessage(String PID, String m) {
		// TODO - implement GameLogic.receivedMessage
		throw new UnsupportedOperationException();
	}

	public boolean addPlayer(String playerID, String name) {
		game.addPlayer(playerID, name);
		com.sendGameDataToAllClients(game);
		return true;
	}

	public void removePlayer() {
		// TODO - implement GameLogic.removePlayer
		throw new UnsupportedOperationException();
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
