package Client.gui;


import java.awt.Color;

import SharedData.GameData;

public class Presentation {
	
	private static GameWindow gameWindow;
	private static MenuWindow menuWindow;
	
	public static void createMenuWindow(MenuWindowLogic logic) {
		menuWindow = new MenuWindow(logic); //TODO
		//logic.setGUI(menuWindow); // unsauber aber was solls
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

//	public static void updatePlayerData(PlayerData p) {
//		if (gameWindow != null) gameWindow.updatePlayerData(p);
//	}

	/**
	 * 
	 * @param m
	 */
	public void showMessage(String m) {
		// TODO - implement Presentation.showMessage
		throw new UnsupportedOperationException();
	}

	public static void ReceivedMessage(String m) {
		if (gameWindow != null) gameWindow.ReceivedMessage(m);
		
	}
	
	public static GameWindow getGameWindow() {
		return gameWindow;
	}
	
	public static MenuWindow getMenuWindow() {
		return menuWindow;
	}

	/**
	 * get color of p's playerInfo background
	 * @param playerID
	 * @return Color
	 */
	public static Color getColorOfPlayer(String playerID) {
		if (gameWindow != null) {
			PlayerInfos p = gameWindow.getPlayerInfo(playerID);
			if (p != null) {
				return p.getBgColor();
			}
		}
		return Color.LIGHT_GRAY;
	}

}
