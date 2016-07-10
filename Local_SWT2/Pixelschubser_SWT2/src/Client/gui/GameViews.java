package Client.gui;


import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Client.Client;
import Client.gui.gameview.GV_CardLimit;
import Client.gui.gameview.GV_Combat;
import Client.gui.gameview.GV_CommandMercenaries;
import Client.gui.gameview.GV_DeclareWinner;
import Client.gui.gameview.GV_DrawCards;
import Client.gui.gameview.GV_GameOver;
import Client.gui.gameview.GV_JoinGame;
import Client.gui.gameview.GV_MakePromises;
import Client.gui.gameview.GV_SpendMoney;
import Client.gui.gameview.GameView;
import SharedData.ActionCard;
import SharedData.GameData;
import SharedData.PhaseType;

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

	private HashMap<PhaseType, GameView> allGameViews = new HashMap<>();

	private PhaseType lastPhase;
	
	public GameViews(GameData gameData) {
		this.playerID = Client.getPlayerID();
		// create all gui elements
		setLayout(cardLayout);
		setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, GameWindow.freeSpaceColor));
		setMinimumSize(new Dimension(preferredWidth, preferredHeight));
		setSize(preferredWidth, preferredHeight);
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		
		// create all used GameViews and add it to contentPane and cardLayout
		currentView = addGameView(PhaseType.JoinGame, new GV_JoinGame());
		addGameView(PhaseType.DrawCards, new GV_DrawCards());
		addGameView(PhaseType.MakePromises, new GV_MakePromises());
		addGameView(PhaseType.CommandMercenaries, new GV_CommandMercenaries());
		addGameView(PhaseType.Combat, new GV_Combat());
		addGameView(PhaseType.SpendMoney, new GV_SpendMoney());
		addGameView(PhaseType.CardLimit, new GV_CardLimit());
		addGameView(PhaseType.DeclareWinner, new GV_DeclareWinner());
		addGameView(PhaseType.GameOver, new GV_GameOver());
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
		if (lastPhase != g.phase) {
			lastPhase = g.phase;
			if (allGameViews.containsKey(g.phase)) {
				currentView.deactivateView();
				currentView = allGameViews.get(g.phase);
				currentView.activateView(g);
				cardLayout.show(this, g.phase.toString());
			} else {
				System.out.println("ERROR: no GameView instance found GameViews.updateGameData for phase " + g.phase);
				//throw new Exception("no GameView class found!");
			}
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
//		System.out.println("ERROR: no GameView class defined in getGameViewClassNameForPhase for phase " + phase);
//		return GV_JoinGame.class.getSimpleName();
//		//throw new Exception("no GameView class found!");
//	}

	public String getPlayerID() {
		return playerID;
	}

	public void ActionCardClicked(ActionCard a) {
		currentView.ActionCardClicked(a);
	}
	
	public void PlayerInfoClicked(PlayerInfos p) {
		currentView.PlayerInfoClicked(p);
	}
}
