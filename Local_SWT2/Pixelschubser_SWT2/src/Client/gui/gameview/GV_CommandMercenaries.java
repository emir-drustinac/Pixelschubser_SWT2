package Client.gui.gameview;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Client.Client;
import Client.gui.MercenaryGUI;
import Client.gui.PlayerInfos;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PlayerData;
import SharedData.ActionCard.CardType;


/**
 * @author alex
 *
 */
public class GV_CommandMercenaries extends GameView implements MouseListener{
	
	private static final long serialVersionUID = 982113843262696068L;

	private ArrayList<MercenaryGUI> mercenaryList;
	private MercenaryGUI selectedMerc = null;
	private boolean initialized;
	
	private JPanel myMercenaries;
	private JPanel otherMercenaries;
	
	GameData game;
	
	public GV_CommandMercenaries() {
		// build gui
		setLayout(new BorderLayout());
		
		myMercenaries = new JPanel(new FlowLayout());
		otherMercenaries = new JPanel(new FlowLayout());
		
		add(otherMercenaries, BorderLayout.NORTH);
		add(myMercenaries, BorderLayout.CENTER);
		
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
		System.out.println(" ----- " + g.getPlayer(myClientID()).name + " " + g.getPlayer(myClientID()).numberOfMercenaries());
		System.out.println(" ----- " + g.getPlayer(myClientID()).name + " " + g.getPlayer(myClientID()).mercenaries.size());

		if (!initialized) {
			initialized = true;

			PlayerData me = g.getPlayer(myClientID());
			int j = getNumberOfPlayer(myClientID());

			for (Mercenary merc : me.mercenaries) {
				MercenaryGUI m = null;
				if (me.isProconsul) {
					m = new MercenaryGUI(me.name, merc.mercID, 6);
				} else {
					m = new MercenaryGUI(me.name, merc.mercID, j + 1);
				}

				m.addMouseListener(this);
				myMercenaries.add(m);
				mercenaryList.add(m);
				System.out.println("xxxxxxx");
			}
		}

		// if have active card SPY show other mercenaries
		if (true) {
			otherMercenaries.removeAll();
			for (PlayerData p : game.players) {
				if (!p.isProconsul && !p.playerID.equals(myClientID())) {
					for (Mercenary m : p.mercenaries) {
						int num = m.isDefendingProconsul() ? 6 : getNumberOfPlayer(m.getTarget().isEmpty() ? p.playerID : m.getTarget()) + 1;
						Color color = Color.blue; // TODO get color of playerInfo background
						// TODO how to decide if merc is attacking or defending?
						otherMercenaries.add(new MercenaryGUI(p.playerID, m.mercID, num, color, true));
					}
				}
			}
		}
				
	}

	private int getNumberOfPlayer(String target) {
		for (int j = 0; j < game.players.size(); j++) {
			if (game.players.get(j).playerID.equals(target)) {
				return j;
			}
		}
		return 0;
	}

	@Override
	public void activateView(GameData g) {
		updateGameData(g);
		EnumSet<CardType> types = EnumSet.noneOf(CardType.class);
		types.add(CardType.SPY);
		markCardTypes(types);
	}

	@Override
	public void deactivateView() {
		// TODO clear things up
	}
	
	@Override
	public void PlayerInfoClicked(PlayerInfos p) {

		if (selectedMerc != null) {

			if (p.isProconsul()) {
				// target is proconsul
				// dialog box

				int answer = JOptionPane.showConfirmDialog(this, "Proconsul angreifen?", "Proconsul Optionen", JOptionPane.YES_NO_OPTION);

				if (answer == JOptionPane.YES_OPTION) {

					// User clicked YES.
					for (int j = 0; j < game.players.size(); j++) {
						if (game.players.get(j).playerID.equals(p.getPlayerID())) {
							selectedMerc.setDice(j + 1);
							String targetID = p.getPlayerID();
							System.out.println("~~~~~ " + targetID + " attacked by " + selectedMerc.getPlayerID());
							// attacking?
							Client.sendMessageToServer("merc_command:" + selectedMerc.getMercID() + ":" + targetID + ":" + true);
							break;
						}
					}
				} else if (answer == JOptionPane.NO_OPTION) {
					// User clicked NO.
					selectedMerc.setDice(6);
					String targetID = p.getPlayerID();
					System.out.println("~~~~~ " + targetID + " defended by " + selectedMerc.getPlayerID());
					// attacking?
					Client.sendMessageToServer("merc_command:" + selectedMerc.getMercID() + ":" + targetID + ":" + false);
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
						System.out.println("~~~~~ " + targetID + " attacked by " + selectedMerc.getPlayerID());
						// attacking?
						Client.sendMessageToServer("merc_command:" + selectedMerc.getMercID() + ":" + targetID + ":" + true);
						break;
					}
				}

			}

			// deselect merc at the end
			selectedMerc.setSelected(false);
			selectedMerc = null;

		}

	}

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
		// if(!game.getPlayer(myClientID()).isProconsul){		// TODO comment that to test
			
			if(e.getSource() instanceof MercenaryGUI){
				MercenaryGUI m  = (MercenaryGUI) e.getSource();
				
				if(selectedMerc == null){
					selectedMerc = m;
					selectedMerc.setSelected(true);
				}else{
					if(selectedMerc.equals(m)){
						selectedMerc.setSelected(false);
						selectedMerc = null;
					}
				}
			} 
		// } else {									// TODO comment that to test
		// // owner is proconsul
		// // nothing
		// System.out.println("owner is proconsul");
		// }
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
