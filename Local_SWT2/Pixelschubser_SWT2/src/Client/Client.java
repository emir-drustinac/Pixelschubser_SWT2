package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.UUID;

import Client.gui.Presentation;
import SharedData.GameData;
import SharedData.PlayerData;

public class Client {

	// generate or load playerID of this client from file
	private static final String playerID;
	static {
		String pid = null;
		File pf = new File(System.getProperty("user.home") + "\\.proconsul\\playerID");
		if (pf.exists() && pf.canRead() && pf.length() >= 8) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pf)));
				pid = br.readLine();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (pid == null) {
			// generate new playerID
			pid = UUID.randomUUID().toString();
			try {
				pf.getParentFile().mkdirs();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pf)));
				bw.write(pid);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		playerID = pid;
		System.out.println("playerID: " + playerID);
	}
	private static ClientGameLogic game;
	private static ClientCommunicator com;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Client().initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// test of GameWindow
		GameData g = new GameData();
		// first player
		g.addPlayer(playerID, "Alpha");
		PlayerData p = g.players.lastElement();
		p.isGameLeader = true;
		p.numberOfBuildings = 3;
		p.numberOfMercenaries = 3;
		// second player
		g.addPlayer("b2000000-0000-0000-000000000000", "Beta");
		g.makeProconsul("b2000000-0000-0000-000000000000");
		p = g.players.lastElement();
		p.numberOfBuildings = 1;
		p.numberOfMercenaries = 4;
		// third player
		g.addPlayer("c3", "Gamma");
		p = g.players.lastElement();
		p.numberOfBuildings = 2;
		p.numberOfMercenaries = 1;
		// fourth player
		g.addPlayer("d4", "Delta");
		p = g.players.lastElement();
		p.numberOfBuildings = 1;
		p.numberOfMercenaries = 2;
		// create game window
		Presentation.createGameWindow(g);
		// and show gamedata
		updateGameState(g);
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
	public static void updateGameState(GameData g) {
		Presentation.updateGameState(g);
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
