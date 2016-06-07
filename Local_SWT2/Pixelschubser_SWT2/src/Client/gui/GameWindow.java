package Client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Client;
import SharedData.GameData;
import SharedData.PlayerData;

public class GameWindow extends JFrame {
	
	private static final String windowName = "Proconsul - Liber qui praesunt - Client v0.1";
	private static final int initialWidth = 1280;
	private static final int initialHeight = 960;

	private HashMap<String, PlayerInfos> playerPanels = new HashMap<String, PlayerInfos>();
	private MyInfos playerInfo;
	private GameInfos gameInfo;
	private JLabel message;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1661482076832146231L;

	public GameWindow(GameData gameData) {
		//JFrame f = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(windowName);
		setSize(initialWidth, initialHeight);
		setMinimumSize(new Dimension(600, 500));
		setLayout(new BorderLayout());
		
		// add playerInfos
		JPanel players = new JPanel(new GridLayout(1, 0, 10, 0));
		players.setBackground(Color.white);
		players.setBorder(BorderFactory.createLineBorder(Color.white, 10));
		for (PlayerData p : gameData.players) {
			// create PlayerInfo for other players
			if (!p.playerID.equals(Client.getPlayerID())) {
				// create playerInfos
				PlayerInfos pi = new PlayerInfos(p.playerID);
				// add to list
				playerPanels.put(p.playerID, pi);
				// add to panel
				players.add(pi);
			}
		}
		// add player infos to the frame
		add(players, BorderLayout.NORTH);
		
		// add myInfo
		for (PlayerData p : gameData.players) {
			// create myInfo for me
			if (p.playerID.equals(Client.getPlayerID())) {
				// create MyInfos
				MyInfos mi = new MyInfos(p.playerID);
				// add to list
				playerInfo = mi;
			}
		}
		add(playerInfo, BorderLayout.CENTER);
		
		// add gameInfo
		gameInfo = new GameInfos(gameData);
		add(gameInfo, BorderLayout.EAST);
		
		// add status message panel
		message = new JLabel();
		message.setText("initialized...");
		message.setVerticalAlignment(JLabel.TOP);
		message.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,10,10,10,Color.white), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		message.setPreferredSize(new Dimension(500, 50));
		add(message, BorderLayout.SOUTH);
		
		// center frame on screen
		setLocationRelativeTo(null);
		// show frame
		setVisible(true);
	}

	public void updateGameState(GameData g) {
		// update playerInfos
		for (PlayerData p : g.players) {
			if (playerPanels.containsKey(p.playerID)) {
				// update playerInfos
				playerPanels.get(p.playerID).updatePlayerInfos(p);
			}
		}
		
		// update myInfos
		for (PlayerData p : g.players) {
			if (p.playerID.equals(Client.getPlayerID())) {
				playerInfo.updateMyInfos(p);
			}
		}
		
		// update gameInfos
		gameInfo.updateGameInfos(g);
		
		// add messages to log
		// TODO
	}

}
