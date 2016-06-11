package Client.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import SharedData.ActionCard;
import SharedData.GameData;

public interface GameView {
	/**
	 * received new GameData g
	 */
	public void updateGameData(GameData g);
	
	/**
	 * bringing View to front
	 */
	public void activateView(GameData g);
	
	/**
	 * View is deactivated and not in front anymore
	 */
	public void deactivateView();
	
	/**
	 * Player wants to use an ActionCard c
	 */
	public ActionCardException useActionCard(ActionCard c);
	
	public static JLabel getIconLabel(String path) {
		java.net.URL imgUrl = GameView.class.getResource(path);
		ImageIcon icon = new ImageIcon(imgUrl);
		return new JLabel(icon);
	}
}
