package Client.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Client;
import Client.gui.gameview.GV_JoinGame;
import SharedData.GameData;

public class GameViews extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8590791567147096078L;
	
	private final String playerID;
	
	private static final int preferredWidth = 250;
	private static final int preferredHeight = 300;
	
	private GameView currentView;
	private CardLayout cardLayout = new CardLayout();

	private HashMap<String, GameView> allGameViews = new HashMap<>();
	
//	private JLabel buildings;

	public GameViews(GameData gameData) {
		this.playerID = Client.getPlayerID();
		// create all gui elements
		setLayout(new CardLayout());
		setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, GameWindow.freeSpaceColor));
		setMinimumSize(new Dimension(preferredWidth, preferredHeight));
		setSize(preferredWidth, preferredHeight);
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		
//		// east for mercenaries
//		JPanel mercs = new JPanel();
//		mercs.setLayout(new GridLayout(0, 1));
//		mercs.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
//		mercs.setBackground(bgColor);
//		mercenaries = new JLabel[MAX_NUMBER_OF_MERCENARIES];
//		for (int i = 0; i < MAX_NUMBER_OF_MERCENARIES; i++) {
//			JLabel label;
//			label = getIconLabel("/images/mercenary.png");
//			label.setVerticalAlignment(JLabel.CENTER);
//			mercenaries[i] = label;
//			mercs.add(label);
//		}
//		add(mercs, BorderLayout.WEST);
		
		// center for buildings
//		buildings = getIconLabel("/images/building.png");
//		// label is centered
//		buildings.setVerticalAlignment(JLabel.CENTER);
//		buildings.setHorizontalAlignment(JLabel.CENTER);
//		// with text above icon
//		buildings.setVerticalTextPosition(JLabel.TOP);
//		buildings.setHorizontalTextPosition(JLabel.CENTER);
//		//buildings.setText("-");
//		add(buildings, BorderLayout.CENTER);
		
//		// south for visual card list
//		cards = new JPanel();
//		cards.setBackground(bgColor);
////		for (int i = 0; i < 7; i++) {
////			JLabel card = getIconLabel("/images/card.png");
////			cards.add(card);
////		}
//		add(cards, BorderLayout.SOUTH);
//		
//		//east for proconsul icon (valign top with 10 pixel padding)
//		proconsul = getIconLabel("/images/proconsul.png");
//		proconsul.setVerticalAlignment(JLabel.TOP);
//		proconsul.setHorizontalAlignment(JLabel.CENTER);
//		proconsul.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
//		proconsul.setVisible(false);
//		add(proconsul, BorderLayout.EAST);
		
		// create all used GameViews and add it to contentPane and cardLayout
		currentView = addGameView(new GV_JoinGame());
//		addGameView(new GV_DrawCards());
//		addGameView(new GV_MakePromises());
//		addGameView(new GV_CommandMercenaries());
//		addGameView(new GV_Combat());
//		addGameView(new GV_SpendMoney());
//		addGameView(new GV_CardLimit());
	}

/**
 * @return
 */
private GameView addGameView(GameView view) {
	String className = view.getClass().getSimpleName();
	allGameViews.put(className, view);
	add(view, className);
	return view;
}

	/**
	 * @return
	 */
	private JLabel getIconLabel(String path) {
		java.net.URL imgUrl = getClass().getResource(path);
		ImageIcon icon = new ImageIcon(imgUrl);
		return new JLabel(icon);
	}

	public void updateGameData(GameData g) {
		// translate g.phase => GameView
		String className = getGameViewClassNameForPhase(g.phase);
		// if view changed
		if (!className.equals(currentView.getClass().getSimpleName())) {
			currentView.deactivateView();
			currentView = allGameViews.get(className);
			currentView.activateView(g);
			cardLayout.show(this, className);
		}
		// update the current view
		currentView.updateGameData(g);
	}

	private String getGameViewClassNameForPhase(int phase) {
		// TODO Auto-generated method stub
		// if phase == x return GV_JoinGame.class.getSimpleName();
		return GV_JoinGame.class.getSimpleName();
	}

	public String getPlayerID() {
		return playerID;
	}
}
