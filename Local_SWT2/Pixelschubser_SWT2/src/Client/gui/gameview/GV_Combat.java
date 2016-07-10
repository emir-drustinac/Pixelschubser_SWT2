package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Client.Client;
import Client.gui.GuiActionCard;
import Client.gui.MercenaryGUI;
import Client.gui.Presentation;
import Client.gui.WrapPanel;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PlayerData;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.Combat;
import SharedData.Combat.Card;

/**
 * @author chris
 *
 */
public class GV_Combat extends GameView {
	
	private static final long serialVersionUID = 2201834375872284434L;

	private ArrayList<String> playerNumberList;
	private JPanel[] attackingMercenaries;
	private JPanel[] defendingMercenaries;
	private final JButton btnNext;
	private final JLabel lblTitle;
	private final JLabel lblDefenderSummary;
	private final JLabel lblAttackerSummary;
	private final JLabel lblResult;
	private final JPanel cardPanel;
	private final JPanel pnlCards;

	private ActionCard chooseAssassinatedMerc;

	private final MouseListener mouseListener;

	private Combat combat;
	
	public GV_Combat() {
		// build gui
		setLayout(new BorderLayout());
		
		playerNumberList = new ArrayList<>();
		
		// center consists of three labels and a panel for cards got
		JPanel center = new JPanel(new BorderLayout());
		lblTitle = new JLabel("stage");
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		center.add(lblTitle, BorderLayout.NORTH);
		JPanel grid = new JPanel(new GridLayout(1, 4));
		center.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createMatteBorder(10, 0, 0, 0, Color.white),
						BorderFactory.createEmptyBorder(10, 10, 10, 10))
				);
		{
			// first label for game summary for defenders
			lblDefenderSummary = new JLabel("Verteidungswerte:");
			lblDefenderSummary.setFont(lblDefenderSummary.getFont().deriveFont(Font.PLAIN));
			lblDefenderSummary.setHorizontalAlignment(JLabel.CENTER);
			lblDefenderSummary.setVerticalAlignment(JLabel.TOP);
			grid.add(lblDefenderSummary);
			
			// second label for game summary for defenders
			lblAttackerSummary = new JLabel("Angriffswerte:");
			lblAttackerSummary.setFont(lblAttackerSummary.getFont().deriveFont(Font.PLAIN));
			lblAttackerSummary.setHorizontalAlignment(JLabel.CENTER);
			lblAttackerSummary.setVerticalAlignment(JLabel.TOP);
			grid.add(lblAttackerSummary);
	
			// third label for result and looter
			lblResult = new JLabel("Ergebnisse:");
			lblResult.setFont(lblResult.getFont().deriveFont(Font.PLAIN));
			lblResult.setHorizontalAlignment(JLabel.CENTER);
			lblResult.setVerticalAlignment(JLabel.TOP);
			grid.add(lblResult);
			
			// rightmost panel for actionCards this player got
			cardPanel = new JPanel(new BorderLayout());
			cardPanel.add(new JLabel("erlangte Karten:", JLabel.CENTER), BorderLayout.NORTH);
			pnlCards = new JPanel();
			JScrollPane sp = new JScrollPane(pnlCards);
			sp.setBorder(null);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			cardPanel.add(sp, BorderLayout.CENTER);
			grid.add(cardPanel);
		}
		center.add(grid, BorderLayout.CENTER);
		add(center, BorderLayout.CENTER);
		
		// mouseListener
		mouseListener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getSource() instanceof MercenaryGUI) {
					MercenaryGUI g = (MercenaryGUI) e.getSource();
					MercenaryClicked(g.getPlayerID(), g.getMercID());
				}
			}
			
		};
		
		// button to proceed in game
		btnNext = new JButton("weiter");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.sendMessageToServer("confirm:combat");
			}
		});
		this.add(btnNext, BorderLayout.SOUTH);

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
		combat = g.combat;
		if (g.combat != null) {
			g.combat.printCombat();
			int num = playerNumberList.indexOf(g.combat.defender.playerID);
			for (Mercenary m : g.combat.att_mercs.values()) {
				//String target = m.getTarget().isEmpty() ? g.combat.defender.playerID : m.getTarget();
				Color color = Presentation.getColorOfPlayer(m.playerID);
				// if merc is defending
				//boolean defending = m.isDefendingProconsul() || m.getTarget().isEmpty();
				MercenaryGUI gui = new MercenaryGUI(m.playerID, m.mercID, m.dice, color, false, true);
				if (g.combat.stage == 3) gui.animateDie();
				if (g.combat.stage == 4) gui.showDie();
				gui.addMouseListener(mouseListener);
				attackingMercenaries[num].add(gui);
			}
			for (Mercenary m : g.combat.def_mercs.values()) {
				Color color = Presentation.getColorOfPlayer(m.playerID);
				MercenaryGUI gui = new MercenaryGUI(m.playerID, m.mercID, m.dice, color, true, true);
				if (g.combat.stage == 3) gui.animateDie();
				if (g.combat.stage == 4) gui.showDie();
				gui.addMouseListener(mouseListener);
				defendingMercenaries[num].add(gui);
			}
		}
		
		// update labels
		if (g.combat != null) {
			
			// title
			String text = "";
			switch (g.combat.stage) {
			case 1: text = "Verteidiger wählen ihre Karten"; break;
			case 2: text = "Angreifer wählen ihre Karten"; break;
			case 3: text = "Die Würfel rollen"; break;
			case 4: text = "Kampfergebnisse"; break;
			}
			if (chooseAssassinatedMerc != null ) text = "Wähle einen Söldner aus!";
			lblTitle.setText(text);

			// defenders
			lblDefenderSummary.setVisible(true);
			text = "";
			int defValue =  g.combat.getDefendValue();
			text += "Verteidungswert: " + defValue + "<br>";
			
			for (Card c : g.combat.defendersCards) {
				text += g.getPlayer(c.owner).name + " benutzt " + c.type + "<br>";
			}
			if (g.combat.defendersCards.size() > 0) text += "<br><b>Gesamt: " + defValue + "</b>";
			lblDefenderSummary.setText("<html><b>Verteidiger</b><br><br>" + text + "</html>");
			
			// attackers
			lblAttackerSummary.setVisible(g.combat.stage >= 2);
			text = "";
			int attValue =  g.combat.getAttackValue();
			text += "Angriffswert: " + attValue + "<br>";
			
			for (Card c : g.combat.attackersCards) {
				text += g.getPlayer(c.owner).name + " benutzt " + c.type + "<br>";
			}
			if (g.combat.attackersCards.size() > 0) text += "<br><b>Gesamt: " + attValue + "</b>";
			lblAttackerSummary.setText("<html><b>Angreifer</b><br><br>" + text + "</html>");
			
			// results
			lblResult.setVisible(g.combat.stage >= 3);
			//text = "Verteidiger: " + defValue + "<br>";
			//text += "Angreifer: " + attValue + "<br><br>";
			text = defValue + (defValue >= attValue ? " größer gleich " : " kleiner ") + attValue + "<br><br>";
			text += "<b>" + (attValue > defValue ? "Angreifer" : "Verteidiger") + " gewinnen!</b>";
			lblResult.setText("<html><b>Ergebnis</b><br><br>" + text + "</html>");
		} else {
			// all combats done or no combats at all
			lblDefenderSummary.setVisible(false);
			lblAttackerSummary.setVisible(true);
			lblResult.setVisible(false);
			cardPanel.setVisible(false);
			
			lblAttackerSummary.setText("<html><br><br><br>Alle Kämpfe sind geschlagen!<br><br><br>Der neue Proconsul ist <b>" + g.getPlayer(g.getProconsulID()).name + "</b></html>");
		}
		
		// update looted cards
		if (g.combat != null) {
			cardPanel.setVisible(g.combat.stage >= 4);
			pnlCards.removeAll();
			for (Card c : g.combat.lootedCards) {
				if (c.owner.equals(myClientID())) {
					pnlCards.add(new GuiActionCard(new ActionCard(c.type), true));
				}
			}
		}
		
		// update usable cards
		// update btnNext visibility
		boolean btnNextActive = false;
		EnumSet<CardType> types = ActionCard.noCardTypes;
		if (g.combat != null) {
			// defender may use cards
			if (g.combat.stage == 1 && g.combat.remaining_defenders.containsKey(myClientID())) {
				types = ActionCard.defendingCardTypes;
				btnNextActive = true;
			}
			// attacker may use cards
			if (g.combat.stage == 2 && g.combat.remaining_attackers.containsKey(myClientID())) {
				types = ActionCard.attackingCardTypes;
				btnNextActive = true;
			}
			// roll dices
			if (g.combat.stage == 3) {
				btnNextActive = true;
			}
			// loot or promises
			if (g.combat.stage == 4) {
				btnNextActive = true;
			}
		}
		markCardTypes(types);
		btnNext.setVisible(btnNextActive);
	}
	
	@Override
	public void ActionCardClicked(ActionCard a) {
		if (a.getType() == CardType.ASSASSINATION) {
			chooseAssassinatedMerc = a;
		} else {
			chooseAssassinatedMerc = null;
			Client.sendMessageToServer("combat_usecard:" + a.getCardID());
		}
	}
	
	protected void MercenaryClicked(String playerID, String mercID) {
		if (combat != null && chooseAssassinatedMerc != null) {
			boolean iAmDefender = combat.defenders.containsKey(myClientID());
			boolean mercIsDefender = combat.defenders.containsKey(playerID);
			if (iAmDefender != mercIsDefender) {
				Client.sendMessageToServer("combat_usecard:" + chooseAssassinatedMerc.getCardID() + ":" + mercID);
			}
		}
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
		markCardTypes(ActionCard.noCardTypes);
	}

	@Override
	public void deactivateView() {
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
