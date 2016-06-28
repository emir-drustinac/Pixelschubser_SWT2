package Client.gui.gameview;

import java.awt.event.MouseEvent;

import Client.gui.ActionCardException;
import SharedData.ActionCard;
import SharedData.GameData;

/**
 * @author chris
 *
 */
public class GV_SpendMoney extends GameView {
	
	private static final long serialVersionUID = 1829294331352754452L;

	
	public GV_SpendMoney() {
		// TODO build gui
	}

	@Override
	public void updateGameData(GameData g) {
		// TODO update gui
	}

	@Override
	public void activateView(GameData g) {
		updateGameData(g);
	}

	@Override
	public void deactivateView() {
		// TODO clear things up
	}

	@Override
	public ActionCardException useActionCard(ActionCard c) {
		// TODO players uses an ActionCard
		return new ActionCardException();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO react on mouse clicks
		
	}

}
