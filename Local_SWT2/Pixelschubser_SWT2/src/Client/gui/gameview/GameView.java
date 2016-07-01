package Client.gui.gameview;


import java.util.EnumSet;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Client;
import Client.gui.PlayerInfos;
import Client.gui.Presentation;
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
	
	protected String myClientID() {
		return Client.getPlayerID();
	}
	

	protected void markCardTypes(EnumSet<CardType> types) {
		// mark cards usable in this phase in deck bar
		if (types == null) {
			Presentation.getGameWindow().getCardPanel().clearCardFilter();
		} else {
			Presentation.getGameWindow().getCardPanel().setCardFilter(types);
		}
	}

	/**
	 * Player wants to use an ActionCard
	 * param a ActionCard used
	 */
	public void ActionCardClicked(ActionCard a) {}

	public void PlayerInfoClicked(PlayerInfos p) {}
	
	public static JLabel getIconLabel(String path) {
		java.net.URL imgUrl = GameView.class.getResource(path);
		ImageIcon icon = new ImageIcon(imgUrl);
		return new JLabel(icon);
	}
}
