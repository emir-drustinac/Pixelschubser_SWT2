package Server;

import java.io.IOException;

public class Server {

	private Database db;
	private ServerCommunicator com;
	private GameLogic game;

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
			game = new GameLogic(com);
			game.initNewGame();
			
			com.start();
			System.out.println("server up");
			//System.in.read();
			//com.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadGame() {
		if (db == null) {
			db = new Database();
		}
		// TODO game.setGameData(db.loadGameData());
	}

	public void saveGame() {
		if (db == null) {
			db = new Database();
		}
		db.saveGameData(game.getGameData());
	}

	public void quit() {
		// TODO - implement Server.quit
		throw new UnsupportedOperationException();
	}

}
