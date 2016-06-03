package Client;

import java.io.IOException;

import javax.swing.JOptionPane;

import SharedData.*;

public class Client {

	private String playerID;
	private ClientGameLogic game;
	private ClientCommunicator com;

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

}