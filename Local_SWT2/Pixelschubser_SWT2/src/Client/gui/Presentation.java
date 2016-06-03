package Client.gui;

import SharedData.GameData;

public class Presentation {
	
	private static GameWindow gameWindow;
	
	public void createMenuWindow() {
		// TODO - implement Presentation.createMenuWindow
		throw new UnsupportedOperationException();
	}

	public void createGameWindow(GameData gameData) {
		gameWindow = new GameWindow(gameData);
	}

	/**
	 * 
	 * @param g
	 */
	public void updateGameState(GameData g) {
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