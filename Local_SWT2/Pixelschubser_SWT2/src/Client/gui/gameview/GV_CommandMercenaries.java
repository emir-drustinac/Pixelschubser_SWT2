package Client.gui.gameview;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Client.Client;
import Client.gui.MercenaryGUI;
import Client.gui.PlayerInfos;
import Client.gui.Presentation;
import Client.gui.WrapPanel;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PlayerData;


/**
 * @author alex
 *
 */
public class GV_CommandMercenaries extends GameView implements MouseListener{
	
	private static final long serialVersionUID = 982113843262696068L;
	
	private MercenaryGUI selectedMerc = null;
	private ArrayList<String> playerNumberList;
	private HashMap<String, MercenaryGUI> myGUIMercs;
	
	private JPanel myMercenaries;
	private JPanel[] attackingMercenaries;
	private JPanel[] defendingMercenaries;
	private final JButton btn;
	
	private boolean iAmProconsul = false;
	private boolean haveSpyCardActive = false;
	
	public GV_CommandMercenaries() {
		// build gui
		setLayout(new BorderLayout());
		
		myMercenaries = new JPanel(new GridBagLayout());
		//myMercenaries.setBackground(Color.orange);
		add(myMercenaries, BorderLayout.CENTER);
		
		playerNumberList = new ArrayList<>();
		myGUIMercs = new HashMap<>();
		
		// button to proceed in game
		btn = new JButton("weiter");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.sendMessageToServer("confirm:command_mercs");
				btn.setEnabled(false);
			}
		});
		this.add(btn, BorderLayout.SOUTH);
	}
	
	@Override
	public void updateGameData(GameData g) {

		PlayerData me = g.getPlayer(myClientID());
		iAmProconsul = me.isProconsul;
		
		// update mercenary overview
		int numPlayers = g.players.size();
		for (int i = 0; i < numPlayers; i++) {
			attackingMercenaries[i].removeAll();
			defendingMercenaries[i].removeAll();
		}
		for (PlayerData p : g.players) {
			if (p.isProconsul || p.playerID.equals(myClientID()) || haveSpyCardActive) {
				for (Mercenary m : p.mercenaries) {
					//int num = m.isDefendingProconsul() ? 6 : getNumberOfPlayer(m.getTarget().isEmpty() ? p.playerID : m.getTarget()) + 1;
					int num = getNumberOfPlayer(m.getTarget().isEmpty() ? p.playerID : m.getTarget());
					Color color = Presentation.getColorOfPlayer(p.playerID);
					// if merc is defending
					boolean defending = m.isDefendingProconsul() || m.getTarget().isEmpty() || m.getTarget().equals(p.playerID);
					MercenaryGUI gui = new MercenaryGUI(p.playerID, m.mercID, num, color, defending, true);
					if (defending) {
						defendingMercenaries[num].add(gui);
					} else {
						attackingMercenaries[num].add(gui);
					}
				}
			}
		}
		
		// update my mercs colors and icons
		for (Mercenary m : me.mercenaries) {
			MercenaryGUI gui = myGUIMercs.get(m.mercID);
			if (gui != null) gui.updateVisuals(m);
		}
		
		validate();
	}

	private int getNumberOfPlayer(String playerID) {
		return playerNumberList.indexOf(playerID);
	}

	@Override
	public void activateView(GameData g) {
		
		// fill playerNumberList
		for (PlayerData p : g.players) {
			playerNumberList.add(p.playerID);
		}
		
		// build gui for merc overview
		if (attackingMercenaries == null) {
			int numPlayers = g.players.size();
			attackingMercenaries = new JPanel[numPlayers];
			defendingMercenaries = new JPanel[numPlayers];
			JPanel otherMercenaries = new JPanel(new GridLayout(1, numPlayers));
			for (int i = 0; i < numPlayers; i++) {
				JPanel box = new JPanel();
				box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
				attackingMercenaries[i] = new WrapPanel();
				box.add(attackingMercenaries[i]);
				defendingMercenaries[i] = new WrapPanel();
				box.add(defendingMercenaries[i]);
				otherMercenaries.add(box);
			}
			add(otherMercenaries, BorderLayout.NORTH);
		}
		
		// build gui for my mercs to command them
		PlayerData me = g.getPlayer(myClientID());
		Color myColor = Presentation.getColorOfPlayer(myClientID());
//		int j = getNumberOfPlayer(myClientID());

		for (Mercenary merc : me.mercenaries) {
//			MercenaryGUI m = null;
//			if (me.isProconsul) {
//				m = new MercenaryGUI(me.name, merc.mercID, 6);
//			} else {
//				m = new MercenaryGUI(me.name, merc.mercID, j + 1);
//			}
			MercenaryGUI m  = new MercenaryGUI(me.playerID, merc.mercID, 0, myColor, true, false);
			myGUIMercs.put(merc.mercID, m);
			m.addMouseListener(this);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridheight = 50;
			gbc.gridwidth = 50;
			gbc.insets = new Insets(15, 15, 15, 15);
			myMercenaries.add(m, gbc);
		}
		
		updateGameData(g);
		
		markCardTypes(ActionCard.commandMercenariesCardTypes);
		
		btn.setEnabled(true);
	}

	@Override
	public void deactivateView() {
		haveSpyCardActive = false;
		// clear player list
		playerNumberList.clear();
		// clean my mercs
		myMercenaries.removeAll();
		// clean merc overview
		for (int i = 0; i < attackingMercenaries.length; i++) {
			attackingMercenaries[i].removeAll();
			defendingMercenaries[i].removeAll();
		}
		markCardTypes(ActionCard.noCardTypes);
	}
	
	@Override
	public void ActionCardClicked(ActionCard a) {
		if (a.getType() == CardType.SPY) 
			haveSpyCardActive = true;
		Client.sendMessageToServer("command_usecard:" + a.getCardID());
	}
	
	@Override
	public void PlayerInfoClicked(PlayerInfos p) {

		if (selectedMerc != null) {
			
			String targetID = p.getPlayerID();
			boolean attacking = true;
			if (p.isProconsul()) {
				// target is proconsul
				// dialog box
				int answer = JOptionPane.showConfirmDialog(this, "Proconsul angreifen?", "Proconsul Optionen", JOptionPane.YES_NO_OPTION);
				attacking = (answer == JOptionPane.YES_OPTION);
			}
			Client.sendMessageToServer("merc_command:" + selectedMerc.getMercID() + ":" + targetID + ":" + attacking);

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
		if(!iAmProconsul){

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
