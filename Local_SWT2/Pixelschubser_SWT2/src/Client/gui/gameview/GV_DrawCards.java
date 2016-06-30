package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EnumSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import Client.Client;
import Client.gui.ActionCardException;
import Client.gui.GuiActionCard;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.GameData;
import SharedData.PlayerData;

/**
 * @author chris
 *
 */
public class GV_DrawCards extends GameView {
	
	private static final long serialVersionUID = -4000887215527920683L;
	private final JPanel cards;
	
	public GV_DrawCards() {
		this.setLayout(new BorderLayout());
		
		// text label
		JLabel la = new JLabel("gezogene Karten");
		la.setHorizontalAlignment(JLabel.CENTER);
		la.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		this.add(la, BorderLayout.NORTH);
		
		// card grid
		cards = new JPanel();
		cards.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		this.add(cards, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(cards);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		
		// proconsul gets button to proceed in game
		JButton btn = new JButton("weiter");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.sendMessageToServer("confirm:drawcards");
			}
		});
		this.add(btn, BorderLayout.SOUTH);
	}

	@Override
	public void updateGameData(GameData g) {
		// TODO update gui
		System.out.println("@ " + this.getClass().getSimpleName() + ".updateGameData @");
		// show cards drawn in this round
		EnumSet<CardType> types = EnumSet.noneOf(CardType.class);
		PlayerData p = g.players.get(myClientID());
		cards.removeAll();
		for (int i = 0; i < p.getNumberOfCards(); i++) {
			ActionCard a = p.getCard(i);
			if (a.drawnInRound == g.round) {
				cards.add(new GuiActionCard(a, !p.isProconsul));
				//cards.add(new GuiActionCard(null, true));
				types.add(a.getType());
			}
		}
		
		// mark cards drawn in this round in deck bar
		markCardTypes(types);
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
