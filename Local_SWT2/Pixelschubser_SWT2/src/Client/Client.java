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

import Client.gui.Logic;
import Client.gui.Presentation;
import SharedData.*;

public class Client {
	
	public static final Boolean DEBUG = true;
	
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
	private static Logic logic;

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
		
		// test von CFR
		if (playerID.equals("82687bf1-df0c-4bb2-af1a-d84a492f7501X")) {
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
			g.makeProconsul("c3");
			// fourth player
			g.addPlayer("d4", "Delta");
			p = g.players.lastElement();
			p.numberOfBuildings = 1;
			p.numberOfMercenaries = 2;
			// create game window
			Presentation.createGameWindow(g);
			// and show gamedata
			g.makeProconsul(playerID);
			g.players.get(0).addCard(null);
			g.players.get(0).addCard(null);
			g.players.get(0).addCard(null);
			g.players.get(0).addCard(null);
			g.players.get(0).addCard(null);
			g.players.get(0).addCard(null);
			g.players.get(0).addCard(null);
			g.players.get(0).addCard(null);
			g.players.get(0).addCard(null);
			updateGameState(g);
		}

		// test von ABO
		if (playerID.equals("91fa3ab2-2280-4c90-b215-327b2a4b2be0")) {
			// test of MenuWindow
			
			// Create Logic for Start Menu
			logic =  new Logic();
			
			Presentation.createMenuWindow(logic);
		}
		
		//Test von Emir
				if (playerID.equals("c6debb40-db95-45f7-afd8-83c954e39a27")) {
					
					// test of MenuWindow
					GameData g = new GameData();
					
					// first player
					g.addPlayer(playerID, "Emir");
					PlayerData p = g.players.lastElement();
					p.isGameLeader = true;
					p.numberOfBuildings = 3;
					p.numberOfMercenaries = 3;
					p.addCard(new ActionCard());
					//p.addCard(new ActionCard());
					//p.addCard(new ActionCard());
					
					// second player
					g.addPlayer("ID0002", "Spieler2");
					g.makeProconsul("b2000000-0000-0000-000000000000");
					p = g.players.lastElement();
					p.numberOfBuildings = 1;
					p.numberOfMercenaries = 4;
					
					// third player
					g.addPlayer("ID0003", "Spieler3");
					p = g.players.lastElement();
					p.numberOfBuildings = 2;
					p.numberOfMercenaries = 1;
					g.makeProconsul("c3");
					p.addCard(new ActionCard());
					p.addCard(new ActionCard());
					p.addCard(new ActionCard());
					p.addCard(new ActionCard());
					p.addCard(new ActionCard());
					
					// fourth player
					g.addPlayer("ID0004", "Spieler4");
					p = g.players.lastElement();
					p.numberOfBuildings = 1;
					p.numberOfMercenaries = 2;
					
					// fifth player
					g.addPlayer("ID0005", "Spieler5");
					p = g.players.lastElement();
					p.numberOfBuildings = 1;
					p.numberOfMercenaries = 2;
					p.addCard(new ActionCard());
					
					// create game window
					Presentation.createGameWindow(g);
					g.players.elementAt(0).addCard(new ActionCard());
					g.players.elementAt(0).addCard(new ActionCard());
					g.players.elementAt(0).addCard(new ActionCard());
					updateGameState(g);
					
					/*
					// Create Logic for Start Menu
					logic =  new Logic();
					
					Presentation.createMenuWindow(logic);*/
				}
		
		// Network test
		if (playerID.equals("bfd15200-f2a8-40a0-ad1d-9f73eae6f3c8")) {
			try {
				// wait for the connection
				Thread.sleep(2500);
				com.sendMessage("Hello World");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
	
	private void initialize() {
		//String ip = JOptionPane.showInputDialog("Server IP");
		//String username = JOptionPane.showInputDialog("Name");
		//String userpass = JOptionPane.showInputDialog("Password");
		com = new ClientCommunicator();
		game = new ClientGameLogic(com);
		com.setAuth(playerID, "Nero", "test");
		try {
			com.setServer(JOptionPane.showInputDialog("Server IP"));
			com.connect();
			System.out.println("client up");
		} catch (IOException e) {
			if (DEBUG){
				// Manually call the server
				System.err.println("Server not running, trying to start one.");
				new Server.Server().initialize();
				try {
					com.connect();
					System.out.println("client up");
				} catch (IOException ex) {
					System.err.println("Failed to start server.");
					ex.printStackTrace();
				}
			}else{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		//if (game == null) game = new ClientGameLogic();
		return game;
	}

	public static String getPlayerID() {
		return playerID;
	}
}
