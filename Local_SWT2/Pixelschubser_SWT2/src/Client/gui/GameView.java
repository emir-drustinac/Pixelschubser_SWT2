package Client.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SharedData.ActionCard;
import SharedData.GameData;

public abstract class GameView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1615315556855261053L;

	/**
	 * received new GameData g
	 */
	public abstract void updateGameData(GameData g);
	
	/**
	 * bringing View to front
	 */
	public abstract void activateView(GameData g);
	
	/**
	 * View is deactivated and not in front anymore
	 */
	public abstract void deactivateView();
	
	/**
	 * Player wants to use an ActionCard c
	 */
	public abstract ActionCardException useActionCard(ActionCard c);
	
	public static JLabel getIconLabel(String path) {
		java.net.URL imgUrl = GameView.class.getResource(path);
		ImageIcon icon = new ImageIcon(imgUrl);
		return new JLabel(icon);
	}
}
