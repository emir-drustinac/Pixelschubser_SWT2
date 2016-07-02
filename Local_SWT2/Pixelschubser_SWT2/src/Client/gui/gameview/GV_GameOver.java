package Client.gui.gameview;

import SharedData.GameData;

/**
 * @author chris
 *
 */
public class GV_GameOver extends GameView {
	
	private static final long serialVersionUID = 1829294331352754452L;

	
	public GV_GameOver() {
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
