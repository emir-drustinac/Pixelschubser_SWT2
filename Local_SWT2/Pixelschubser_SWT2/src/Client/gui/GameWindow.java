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

import SharedData.GameData;
import SharedData.PlayerData;

public class GameWindow extends JFrame {
	
	private static final String windowName = "Proconsul - Liber qui praesunt - Client v0.1";
	private static final int initialWidth = 880;
	private static final int initialHeight = 560;
	
	public static final Color freeSpaceColor = Color.white;

	private HashMap<String, PlayerInfos> playerInfos = new HashMap<String, PlayerInfos>();
	private JPanel players;
	private GameViews gameViews;
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
		/*JPanel*/ players = new JPanel(new GridLayout(1, 0, 0, 0));
		players.setBackground(freeSpaceColor);
		players.setBorder(BorderFactory.createLineBorder(freeSpaceColor, 5));
		for (PlayerData p : gameData.players) {
			// create PlayerInfo for other players
			//if (!p.playerID.equals(Client.getPlayerID())) {
				// create playerInfos
				PlayerInfos pi = new PlayerInfos(p.playerID, p.name);
				// add to list
				playerInfos.put(p.playerID, pi);
				// add to panel
				players.add(pi);
			//}
		}
		// add player infos to the frame
		add(players, BorderLayout.NORTH);
		
		// add -myInfo- all GameViews
		gameViews = new GameViews(gameData);
		add(gameViews, BorderLayout.CENTER);
		
		// add gameInfo
		gameInfo = new GameInfos(gameData);
		add(gameInfo, BorderLayout.EAST);
		
		// add status message panel
		message = new JLabel();
		message.setText("initialized...");
		message.setVerticalAlignment(JLabel.TOP);
		message.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,10,10,10,freeSpaceColor), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		message.setPreferredSize(new Dimension(500, 50));
		add(message, BorderLayout.SOUTH);
		
		// center frame on screen
		setLocationRelativeTo(null);
		// show frame
		setVisible(true);
	}

	public void updateGameState(GameData g) {
		// update playerInfos
		// TODO remove playerInfos not existing in GameData?
		for (PlayerData p : g.players) {
			if (playerInfos.containsKey(p.playerID)) {
				// update playerInfos
				playerInfos.get(p.playerID).updatePlayerInfos(p);
			} else {
				// #######
				// create playerInfos
				PlayerInfos pi = new PlayerInfos(p.playerID, p.name);
				// add to list
				playerInfos.put(p.playerID, pi);
				// add to panel
				players.add(pi);
			}
		}
		
		// update GameViews
		gameViews.updateGameData(g);
		
		// update gameInfos
		gameInfo.updateGameInfos(g);
		
		//repaint();
		// add messages to log
		// TODO
	}

	public void ReceivedMessage(String m) {
		//gameInfo.
		message.setText(m);
		//gameViews.
	}

}
