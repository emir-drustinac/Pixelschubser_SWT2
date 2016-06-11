package Client.gui;

import SharedData.GameData;

public class Presentation {
	
	private static GameWindow gameWindow;
	private static MenuWindow menuWindow;
	
	public static void createMenuWindow(Logic logic) {
		menuWindow = new MenuWindow(logic);
		logic.setGUI(menuWindow); // unsauber aber was solls
	}

	public static void createGameWindow(GameData gameData) {
		// TODO: if gameWindow != null dispose old gameWindow
		gameWindow = new GameWindow(gameData);
	}

	/**
	 * 
	 * @param g
	 */
	public static void updateGameState(GameData g) {
		if (gameWindow != null) gameWindow.updateGameState(g);
	}

	/**
	 * 
	 * @param m
	 */
	public void showMessage(String m) {
		// TODO - implement Presentation.showMessage
		throw new UnsupportedOperationException();
	}

}
