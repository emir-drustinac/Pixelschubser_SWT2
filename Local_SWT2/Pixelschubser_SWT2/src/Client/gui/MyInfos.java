package Client.gui;

import javax.swing.JPanel;

import SharedData.PlayerData;

public class MyInfos extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8590791567147096078L;
	
	private final String playerID;

	public MyInfos(String playerID) {
		this.playerID = playerID;
		// TODO
	}

	public void updateMyInfos(PlayerData p) {
		// TODO Auto-generated method stub
		
	}

	public String getPlayerID() {
		return playerID;
	}
}