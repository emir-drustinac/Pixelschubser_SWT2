package Client.gui.gameview;

import SharedData.GameData;

/**
 * @author chris
 *
 */
public class GV_Combat extends GameView {
	
	private static final long serialVersionUID = 2201834375872284434L;

	
	public GV_Combat() {
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

}
