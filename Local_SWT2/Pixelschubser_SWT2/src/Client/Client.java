package Client;

import java.io.IOException;

import SharedData.GameData;

public class Client {

	private static String playerID;
	private static ClientGameLogic game;
	private static ClientCommunicator com;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Client().initialize();
	}

	private void initialize() {
		//String ip = JOptionPane.showInputDialog("Server IP");
		//String username = JOptionPane.showInputDialog("Name");
		//String userpass = JOptionPane.showInputDialog("Password");
		com = new ClientCommunicator();
		com.setAuth("Nero", "test");
		try {
			com.connect();
			System.out.println("client up");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param Address
	 */
	public void connect(String Address) {
		// TODO - implement Client.connect
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param g
	 */
	public void updateGameState(GameData g) {
		// TODO - implement Client.updateGameState
		throw new UnsupportedOperationException();
	}

	public void quit() {
		// TODO - implement Client.quit
		throw new UnsupportedOperationException();
	}

	public static Object getGameLogic() {
		if (game == null) game = new ClientGameLogic();
		return game;
	}

	public static String getPlayerID() {
		return playerID;
	}
}