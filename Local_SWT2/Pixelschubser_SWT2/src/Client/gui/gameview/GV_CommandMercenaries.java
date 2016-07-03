package Client.gui.gameview;


import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Client.Client;
import Client.gui.MercenaryGUI;
import Client.gui.PlayerInfos;
import SharedData.GameData;


/**
 * @author alex
 *
 */
public class GV_CommandMercenaries extends GameView implements MouseListener{
	
	private static final long serialVersionUID = 982113843262696068L;

	private ArrayList<MercenaryGUI> mercenaryList;
	private MercenaryGUI selectedMerc = null;
	private boolean initialized;
	
	GameData game;
	
	public GV_CommandMercenaries() {
		// build gui
		setLayout(new FlowLayout());
		mercenaryList = new ArrayList<>(4);
		initialized = false;
		// ### TEST MERC
//		MercenaryGUI m = new MercenaryGUI("aaa", 1);
//		m.addMouseListener(this);
//		add(m);
//		mercenaryList.add(m);
	}
	
	@Override
	public void updateGameData(GameData g) {
		game = g;
		
		// FIXME game data kommt 2 mal
		System.out.println("+++++++++++++++++++++++++++++++++++++++ ");
		System.out.println(" ----- " + g.getPlayer(myClientID()).name + " " + g.getPlayer(myClientID()).numberOfMercenaries);
		System.out.println(" ----- " + g.getPlayer(myClientID()).name + " " + g.getPlayer(myClientID()).mercenaries.size());
		
		if (!initialized) {
			initialized = true;
			
			
			for (int j = 0; j < g.players.size(); j++) {

				if (g.players.get(j).playerID.equals(myClientID())) {

					for (int i = 0; i < g.getPlayer(myClientID()).mercenaries.size(); i++) {
						MercenaryGUI m = null;
						if(g.getPlayer(myClientID()).isProconsul){
							m = new MercenaryGUI(g.getPlayer(myClientID()).name, 6);
						}else {
							m = new MercenaryGUI(g.getPlayer(myClientID()).name, j + 1);
						}
						
						m.addMouseListener(this);
						add(m);
						mercenaryList.add(m);
						System.out.println("xxxxxxx");
					}

					break;
				}
			}
			
			
			
			
			
		}
		
				
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
	public void PlayerInfoClicked(PlayerInfos p) {

		if (selectedMerc != null) {

			// if(!game.getPlayer(myClientID()).isProconsul){		// TODO comment that to test

			if (p.isProconsul()) {
				// target is proconsul
				// dialog box

				int answer = JOptionPane.showConfirmDialog(this, "Attack proconsul?");

				if (answer == JOptionPane.YES_OPTION) {

					// User clicked YES.
					for (int j = 0; j < game.players.size(); j++) {
						if (game.players.get(j).playerID.equals(p.getPlayerID())) {
							selectedMerc.setDice(j + 1);
							String targetID = p.getPlayerID();
							System.out.println("~~~~~" + targetID + " attacked by " + selectedMerc.getPlayerID());
							// attacking?
							Client.sendMessageToServer("merc_command:" + selectedMerc + ":" + targetID + ":" + true);
							break;
						}
					}
				} else if (answer == JOptionPane.NO_OPTION) {
					// User clicked NO.
					selectedMerc.setDice(6);
					String targetID = p.getPlayerID();
					System.out.println("~~~~~" + targetID + " defended by " + selectedMerc.getPlayerID());
					// attacking?
					Client.sendMessageToServer("merc_command:" + selectedMerc + ":" + targetID + ":" + false);
				}

				System.out.println("target is proconsul");
			} else {
				// target is NOT proconsul
				// set dice

				System.out.println(" target is NOT proconsul");

				for (int j = 0; j < game.players.size(); j++) {
					if (game.players.get(j).playerID.equals(p.getPlayerID())) {
						selectedMerc.setDice(j + 1);
						// System.out.println("attacking " + p.getPlayerID());
						////
						String targetID = p.getPlayerID();
						System.out.println("~~~~~" + targetID + " attacked by " + selectedMerc.getPlayerID());
						// attacking?
						Client.sendMessageToServer("merc_command:" + selectedMerc + ":" + targetID + ":" + true);
						break;
					}
				}

			}

			// } else {									// TODO comment that to test
			// // owner is proconsul
			// // nothing
			// System.out.println("owner is proconsul");
			// }

			// deselect merc at the end
			selectedMerc.setSelected(false);
			selectedMerc = null;
		}
	}

	// MOUSE LISTENER METHODS ##########################
	// MOUSE LISTENER METHODS ##########################
	// MOUSE LISTENER METHODS ##########################
	// MOUSE LISTENER METHODS ##########################
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// not needed
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// not needed
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//
		if(e.getSource() instanceof MercenaryGUI){
			
			if(selectedMerc == null){
				selectedMerc = (MercenaryGUI) e.getSource();
				selectedMerc.setSelected(true);
			}else{
				MercenaryGUI m  = (MercenaryGUI) e.getSource();
				if(selectedMerc.equals(m)){
					selectedMerc.setSelected(false);
					selectedMerc = null;
				}
				
			}
				
		} 
			
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// not needed
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// not needed
	}

}
