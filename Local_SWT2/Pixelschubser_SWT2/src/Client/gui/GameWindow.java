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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import SharedData.ActionCard.CardType;
import SharedData.GameData;
import SharedData.PlayerData;
import SharedData.PhaseType;

public class GameWindow extends JFrame {
	
	private static final String windowName = "Proconsul - Liber qui praesunt - Client v0.1";
	private static final int initialWidth = 880;
	private static final int initialHeight = 560;
	
	public static final Color freeSpaceColor = Color.white;

	private HashMap<String, PlayerInfos> playerInfos = new HashMap<String, PlayerInfos>();
	private JPanel players;
	private GameViews gameViews;
	private GameLog gameLog;
	private JLabel message;
	private JLabel lblPhase;
	private JPanel infosPanel;
	private CardPanel cardpanel;
	private JLabel handCards;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1661482076832146231L;

	public GameWindow(GameData gameData) {
		//JFrame f = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle(windowName);
		setSize(initialWidth, initialHeight);
		setMinimumSize(new Dimension(700, 750));
		setLayout(new BorderLayout());
		
		// add -myInfo- all GameViews
		gameViews = new GameViews(gameData);
		add(gameViews, BorderLayout.CENTER);
		
		// add playerInfos
		/*JPanel*/ players = new JPanel(new GridLayout(1, 0, 0, 0));
		players.setBackground(freeSpaceColor);
		players.setBorder(BorderFactory.createLineBorder(freeSpaceColor, 5));
		for (PlayerData p : gameData.players) {
			// create PlayerInfo for other players
			//if (!p.playerID.equals(Client.getPlayerID())) {
				// create playerInfos
				PlayerInfos pi = new PlayerInfos(p.playerID, p.name, gameViews);
				// add to list
				playerInfos.put(p.playerID, pi);
				// add to panel
				players.add(pi);
			//}
		}
		// add player infos to the frame
		add(players, BorderLayout.NORTH);
		
		// add gameInfo
		gameLog = new GameLog(gameData);
		//add(gameInfo, BorderLayout.EAST);
		gameLog.setVisible(false);

		// contain cardPanel and status panel in Grid(2,1)
		JPanel south = new JPanel(new BorderLayout());
		{
			//Label handCards
			handCards = new JLabel();
			handCards.setBorder(new EmptyBorder(5, 5, 5, 5));
			handCards.setHorizontalAlignment(JLabel.CENTER);
			//handCards.setVerticalTextPosition(JLabel.TOP);
			south.add(handCards, BorderLayout.NORTH);
			
			setBackground(Color.BLUE);
			cardpanel = new CardPanel(gameViews);
			cardpanel.setBackground(freeSpaceColor);
			//cardpanel.addMouseListener(gameViews);
			south.add(cardpanel, BorderLayout.CENTER);

			//Panel with Phase label on the top and message on the bottom
			infosPanel = new JPanel(new GridLayout(2, 0, 0, 0));
			
			lblPhase = new JLabel("");
			lblPhase.setForeground(Color.BLUE);
			lblPhase.setHorizontalAlignment(JLabel.CENTER);
			lblPhase.setBorder(new EmptyBorder(0, 0, 10, 0));
			infosPanel.add(lblPhase);
			
			message = new JLabel();
			//message.setText("initialisiert...");
			message.setVerticalAlignment(JLabel.BOTTOM);
			message.setHorizontalAlignment(JLabel.CENTER);
			message.setBorder(new EmptyBorder(5, 5, 5, 5));
			//message.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,10,10,10,freeSpaceColor), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
			infosPanel.add(message);
			south.add(infosPanel, BorderLayout.SOUTH);
		}
		south.setBorder(new MatteBorder(0, 10, 10, 10, Color.WHITE));
		add(south, BorderLayout.SOUTH);

		// center frame on screen
		setLocationRelativeTo(null);
		// show frame
		setVisible(true);
	}

	public void updateGameState(GameData g) {
		// update playerInfos
		// TODO remove playerInfos not existing in GameData?
		boolean b = g.phase != PhaseType.JoinGame && g.phase != (PhaseType.GameOver) && 
				g.phase != (PhaseType.DeclareWinner);  
		handCards.setText(b ? "Karten auf der Hand:" : " ");
		lblPhase.setText(g.phase.toStringDE());
		
		for (PlayerData p : g.players) {
			if (playerInfos.containsKey(p.playerID)) {
				// update playerInfos
				playerInfos.get(p.playerID).updatePlayerInfos(p);
			} else {
				// #######
				// create playerInfos
				PlayerInfos pi = new PlayerInfos(p.playerID, p.name, gameViews);
				// add to list
				playerInfos.put(p.playerID, pi);
				// add to panel
				players.add(pi);
			}
		}
		
		// update GameViews
		gameViews.updateGameData(g);
		
		// update cardPanel
		cardpanel.updateGameData(g);
		
		// update gameInfos
		gameLog.updateGameInfos(g);
		
		validate();
		// add messages to log
		// TODO message to log
	}

	public void ReceivedMessage(String m) {
		//gameInfo.
		//Warning message -> make text red
		if(m.endsWith("!")) {
			message.setForeground(Color.RED);
			message.setText(m);
		} else {
			message.setForeground(Color.BLACK);
			message.setText(m);
		}
		//gameViews.
	}

	public GameViews getGameViews() {
		return gameViews;
	}

	public CardPanel getCardPanel() {
		return cardpanel;
	}
	
	public PlayerInfos getPlayerInfo(String playerID) {
		return playerInfos.get(playerID);
	}

}
