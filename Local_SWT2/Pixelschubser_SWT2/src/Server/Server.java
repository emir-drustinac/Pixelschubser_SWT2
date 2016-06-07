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

	private void initialize() {
		try {
			com = new ServerCommunicator(SharedData.NetworkProtocol.DEFAULT_PORT);
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
		// TODO - implement Server.loadGame
		throw new UnsupportedOperationException();
	}

	public void saveGame() {
		// TODO - implement Server.saveGame
		throw new UnsupportedOperationException();
	}

	public void quit() {
		// TODO - implement Server.quit
		throw new UnsupportedOperationException();
	}

}
