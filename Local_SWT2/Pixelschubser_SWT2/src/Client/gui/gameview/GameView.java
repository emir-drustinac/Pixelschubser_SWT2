package Client.gui.gameview;


import java.util.EnumSet;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Client;
import Client.gui.ActionCardException;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
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
	
	protected String myClientID() {
		return Client.getPlayerID();
	}
	

	protected void markCardTypes(EnumSet<CardType> types) {
		// TODO mark cards drawn in this round in deck bar
		
	}
	
	public static JLabel getIconLabel(String path) {
		java.net.URL imgUrl = GameView.class.getResource(path);
		ImageIcon icon = new ImageIcon(imgUrl);
		return new JLabel(icon);
	}
}
