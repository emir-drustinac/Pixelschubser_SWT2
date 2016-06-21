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

import javax.swing.JOptionPane;

import Client.gui.MenuWindow;
import Client.gui.MenuWindowLogic;
import Client.gui.Presentation;
import SharedData.*;
import SharedData.ActionCard.CardType;

public class Client {

	public static final Boolean DEBUG = true;

	// generate or load playerID of this client from file
	private static String playerName = "Nero";
	private static String password = "pw";
	private static /*final*/ String playerID = "id";
//	static {
//		String pid = null;
//		File pf = new File(System.getProperty("user.home") + "\\.proconsul\\playerID");
//		if (pf.exists() && pf.canRead() && pf.length() >= 8) {
//			try {
//				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pf)));
//				pid = br.readLine();
//				br.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (pid == null) {
//			// generate new playerID
//			pid = UUID.randomUUID().toString();
//			try {
//				pf.getParentFile().mkdirs();
//				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pf)));
//				bw.write(pid);
//				bw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		playerID = pid;
//		System.out.println("playerID: " + playerID);
//	}
	private static ClientGameLogic game;
	private static ClientCommunicator com;
	private static MenuWindowLogic logic;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Create Logic for Start Menu
		logic = new MenuWindowLogic();
		Presentation.createMenuWindow(logic);

		// test von CFR
		if (playerID.equals("82687bf1-df0c-4bb2-af1a-d84a492f7501")
				|| playerID.equals("2d15d67e-5857-47a5-98cb-9bb06d6ca102")) {
			playerName = "Christian";
			// // test of GameWindow
			// GameData g = new GameData();
			// // first player
			// g.addPlayer(playerID, "Alpha");
			// PlayerData p = g.players.lastElement();
			// p.isGameLeader = true;
			// p.numberOfBuildings = 3;
			// p.numberOfMercenaries = 3;
			// // second player
			// g.addPlayer("b2000000-0000-0000-000000000000", "Beta");
			// g.makeProconsul("b2000000-0000-0000-000000000000");
			// p = g.players.lastElement();
			// p.numberOfBuildings = 1;
			// p.numberOfMercenaries = 4;
			// // third player
			// g.addPlayer("c3", "Gamma");
			// p = g.players.lastElement();
			// p.numberOfBuildings = 2;
			// p.numberOfMercenaries = 1;
			// g.makeProconsul("c3");
			// // fourth player
			// g.addPlayer("d4", "Delta");
			// p = g.players.lastElement();
			// p.numberOfBuildings = 1;
			// p.numberOfMercenaries = 2;
			// // create game window
			// Presentation.createGameWindow(g);
			// // and show gamedata
			// g.makeProconsul(playerID);
			// g.players.get(0).addCard(null);
			// g.players.get(0).addCard(null);
			// g.players.get(0).addCard(null);
			// g.players.get(0).addCard(null);
			// g.players.get(0).addCard(null);
			// g.players.get(0).addCard(null);
			// g.players.get(0).addCard(null);
			// g.players.get(0).addCard(null);
			// g.players.get(0).addCard(null);
			// updateGameState(g);
		}

		// test von ABO
		if (playerID.equals("91fa3ab2-2280-4c90-b215-327b2a4b2be0")
				|| playerID.equals("2a861719-9a4c-4467-98b2-6bdaaba358a6")) {
			playerName = "Alexander";
			// test of MenuWindow

			// // Create Logic for Start Menu
			// logic = new MenuWindowLogic();
			// Presentation.createMenuWindow(logic);

		}

		// Test von Emir
		if (playerID.equals("c6debb40-db95-45f7-afd8-83c954e39a27")||
				playerID.equals("39ab2c11-82a0-4513-8827-0f0bd12a78ed")) {
			//playerName = "Emir";
			/*
			 * // first player g.addPlayer(playerID, "Emir"); PlayerData p =
			 * g.players.lastElement(); p.isGameLeader = true;
			 * p.numberOfBuildings = 3; p.numberOfMercenaries = 3; p.addCard(new
			 * ActionCard()); //p.addCard(new ActionCard()); //p.addCard(new
			 * ActionCard());
			 */
		}

		// hier auskommentieren um ohne menu zu starten

		// try {
		// initialize();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// Network test
		if (playerID.equals("bfd15200-f2a8-40a0-ad1d-9f73eae6f3c8")) {
			playerName = "Frank";
			try {
				// wait for the connection
				Thread.sleep(2500);
				com.sendMessage("Hello World");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void initialize() {
		// String ip = JOptionPane.showInputDialog("Server IP");
		// String username = JOptionPane.showInputDialog("Name");
		// String userpass = JOptionPane.showInputDialog("Password");
		com = new ClientCommunicator();
		game = new ClientGameLogic(com);
		com.setAuth(playerID, playerName, password);
		try {
			// TODO: remove {true ? "127.0.0.1" : } for operative use
			com.setServer(true ? "127.0.0.1" : JOptionPane.showInputDialog("Server IP"));
			com.connect();
			System.out.println("client up");
		} catch (IOException e) {
			if (DEBUG) {
				// Manually call the server
				System.err.println("Server not running, trying to start one.");
				new Server.Server().initialize();
				try {
					com.setServer("127.0.0.1");
					com.connect();
					System.out.println("client up");
				} catch (IOException ex) {
					System.err.println("Failed to start server.");
					ex.printStackTrace();
				}
			} else {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @param Address
	 */
	public static void connect(String Address, String playerName) {
		// TODO - implement Client.connect
		// throw new UnsupportedOperationException();
		
		playerID = playerName;
		
		com = new ClientCommunicator();
		game = new ClientGameLogic(com);
		com.setAuth(playerID, playerName, password);

		try {
			com.setServer(Address);
			com.connect();
			System.out.println("client up");
		} catch (IOException e) {
			System.err.println("Could not connect");
			e.printStackTrace();
		}

	}

	public static void create(String playerNameXXX) {
		new Server.Server().initialize();
		connect("127.0.0.1", playerNameXXX);
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
		// if (game == null) game = new ClientGameLogic();
		return game;
	}

	public static String getPlayerID() {
		return playerID;
	}

	public static void sendMessageToServer(String string) {
		com.sendMessage(string);
	}
}
