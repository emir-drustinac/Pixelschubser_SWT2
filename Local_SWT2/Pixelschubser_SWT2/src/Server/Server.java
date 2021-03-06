package Server;

import java.io.IOException;

public class Server {

	private Database db;
	private ServerCommunicator com;
	private ServerGameLogic gameLogic;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Server().initialize();
	}

	public void initialize() {
		try {
			com = new ServerCommunicator(SharedData.NetworkProtocol.DEFAULT_PORT);
			gameLogic = new ServerGameLogic(com);
			gameLogic.initNewGame();
			
			com.start();
			System.out.println("server up");
			//System.in.read();
			//com.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadGame() {
		if (db == null) {
			db = new Database();
		}
		gameLogic.setGameData(db.loadGameData());
	}

	public void saveGame() {
		if (db == null) {
			db = new Database();
		}
		db.saveGameData(gameLogic.getGameData());
	}

	public void quit() {
		// TODO - implement Server.quit
		throw new UnsupportedOperationException();
	}

}
