package Client.gui;


import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Client.Client;
import Client.gui.gameview.GV_CardLimit;
import Client.gui.gameview.GV_Combat;
import Client.gui.gameview.GV_CommandMercenaries;
import Client.gui.gameview.GV_DrawCards;
import Client.gui.gameview.GV_JoinGame;
import Client.gui.gameview.GV_MakePromises;
import Client.gui.gameview.GV_SpendMoney;
import Client.gui.gameview.GameView;
import SharedData.GameData;
import SharedData.PhaseType;

public class GameViews extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8590791567147096078L;
	
	private final String playerID;
	
	private static final int preferredWidth = 250;
	private static final int preferredHeight = 300;
	
	private GameView currentView;
	private CardLayout cardLayout = new CardLayout();

	private HashMap<PhaseType, GameView> allGameViews = new HashMap<>();
	
//	private JLabel buildings;

	public GameViews(GameData gameData) {
		this.playerID = Client.getPlayerID();
		// create all gui elements
		setLayout(cardLayout);
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
		currentView = addGameView(PhaseType.JoinGame, new GV_JoinGame());
		addGameView(PhaseType.DrawCards, new GV_DrawCards());
		addGameView(PhaseType.MakePromises, new GV_MakePromises());
		addGameView(PhaseType.CommandMercenaries, new GV_CommandMercenaries());
		addGameView(PhaseType.Combat, new GV_Combat());
		addGameView(PhaseType.SpendMoney, new GV_SpendMoney());
		addGameView(PhaseType.CardLimit, new GV_CardLimit());
	}

	/**
	 * @param phase 
	 * @return
	 */
	private GameView addGameView(PhaseType phase, GameView view) {
		//String className = view.getClass().getSimpleName();
		allGameViews.put(phase, view);
		add(view, phase.toString());
		return view;
	}

	public void updateGameData(GameData g) {
		// translate g.phase => GameView
		//String className = getGameViewClassNameForPhase(g.phase);
		// if view changed
		//if (!className.equals(currentView.getClass().getSimpleName()) && allGameViews.containsKey(className)) {
		if (allGameViews.containsKey(g.phase)) {
			currentView.deactivateView();
			currentView = allGameViews.get(g.phase);
			currentView.activateView(g);
			cardLayout.show(this, g.phase.toString());
		} else {
			System.out.println("ERROR: no GameView class defined in getGameViewClassNameForPhase for phase " + g.phase);
			//throw new Exception("no GameView class found!");
		}
		// update the current view
		currentView.updateGameData(g);
	}

//	private String getGameViewClassNameForPhase(PhaseType phase) {
//		if (phase == PhaseType.JoinGame) return GV_JoinGame.class.getSimpleName();
//		if (phase == PhaseType.DrawCards) return GV_DrawCards.class.getSimpleName();
//		if (phase == PhaseType.MakePromises) return GV_MakePromises.class.getSimpleName();
//		if (phase == PhaseType.JoinGame) return GV_JoinGame.class.getSimpleName();
//		if (phase == PhaseType.JoinGame) return GV_JoinGame.class.getSimpleName();
//		if (phase == PhaseType.JoinGame) return GV_JoinGame.class.getSimpleName();
//		if (phase == PhaseType.JoinGame) return GV_JoinGame.class.getSimpleName();
//		// TODO add more GameViews
//		System.out.println("ERROR: no GameView class defined in getGameViewClassNameForPhase for phase " + phase);
//		return GV_JoinGame.class.getSimpleName();
//		//throw new Exception("no GameView class found!");
//	}

	public String getPlayerID() {
		return playerID;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		currentView.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
