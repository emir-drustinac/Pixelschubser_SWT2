package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import Client.Client;
import Client.gui.MercenaryGUI;
import Client.gui.Presentation;
import Client.gui.WrapPanel;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PlayerData;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;

/**
 * @author chris
 *
 */
public class GV_Combat extends GameView {
	
	private static final long serialVersionUID = 2201834375872284434L;

	private ArrayList<String> playerNumberList;
	private JPanel[] attackingMercenaries;
	private JPanel[] defendingMercenaries;
	
	public GV_Combat() {
		// TODO build gui
		setLayout(new BorderLayout());
		
		playerNumberList = new ArrayList<>();
		
		// button to proceed in game
		JButton btn = new JButton("weiter");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.sendMessageToServer("confirm:combat");
			}
		});
		this.add(btn, BorderLayout.SOUTH);

//		class resizeListener extends ComponentAdapter {
//			public void componentResized(ComponentEvent e) {
//				System.out.println("!resized! ");
//				for(JPanel p : attackingMercenaries) {
//					p.revalidate();
//				}
//				for(JPanel p : defendingMercenaries) {
//					p.revalidate();
//				}
//			}
//		}
//		addComponentListener(new resizeListener());

	}

	@Override
	public void updateGameData(GameData g) {
		// TODO update gui
		
		// update mercenary overview
		int numPlayers = g.players.size();
		for (int i = 0; i < numPlayers; i++) {
			attackingMercenaries[i].removeAll();
			defendingMercenaries[i].removeAll();
		}
		if (g.combat != null) {
			g.combat.printCombat();
			int num = playerNumberList.indexOf(g.combat.defender.playerID);
			for (Mercenary m : g.combat.att_mercs.values()) {
				//String target = m.getTarget().isEmpty() ? g.combat.defender.playerID : m.getTarget();
				Color color = Presentation.getColorOfPlayer(m.playerID);
				// if merc is defending
				//boolean defending = m.isDefendingProconsul() || m.getTarget().isEmpty();
				MercenaryGUI gui = new MercenaryGUI(m.playerID, m.mercID, num, color, false, true);
				attackingMercenaries[num].add(gui);
			}
			for (Mercenary m : g.combat.def_mercs.values()) {
				Color color = Presentation.getColorOfPlayer(m.playerID);
				MercenaryGUI gui = new MercenaryGUI(m.playerID, m.mercID, num, color, true, true);
				defendingMercenaries[num].add(gui);
			}
		}
		
		// update usable cards
		EnumSet<CardType> types = ActionCard.noCardTypes;
		if (g.combat != null) {
			// defender may use cards
			if (g.combat.stage == 1 && g.combat.remaining_defenders.containsKey(myClientID())) {
				types.addAll(ActionCard.defendingCardTypes);
			}
			// attacker may use cards
			if (g.combat.stage == 2 && g.combat.remaining_attackers.containsKey(myClientID())) {
				types.addAll(ActionCard.attackingCardTypes);
			}
		}
		markCardTypes(types);
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
		
		//updateGameData(g);
	}

	@Override
	public void deactivateView() {
		// TODO clear things up
		// clear player list
		playerNumberList.clear();
		// clean merc overview
		for (int i = 0; i < attackingMercenaries.length; i++) {
			attackingMercenaries[i].removeAll();
			defendingMercenaries[i].removeAll();
		}
		markCardTypes(ActionCard.noCardTypes);
	}

}
