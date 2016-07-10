package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import Client.Client;
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
	JButton btnWeiter;
	
	public GV_DrawCards() {
		this.setLayout(new BorderLayout());
		
		// text label
		JLabel la = new JLabel("gezogene Karten:");
		la.setHorizontalAlignment(JLabel.CENTER);
		la.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
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
		btnWeiter = new JButton("weiter");
		btnWeiter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.sendMessageToServer("confirm:drawcards");
			}
		});
		this.add(btnWeiter, BorderLayout.SOUTH);
	}

	@Override
	public void updateGameData(GameData g) {
		// TODO update gui
		System.out.println("@ " + this.getClass().getSimpleName() + ".updateGameData @");
		// show cards drawn in this round
		EnumSet<CardType> types = EnumSet.noneOf(CardType.class);
		PlayerData p = g.players.get(myClientID());
		
		//visible only for the proconsul
		if(!p.isProconsul) {
			btnWeiter.setVisible(false);
		}
		
		cards.removeAll();
		for (int i = 0; i < p.getNumberOfCards(); i++) {
			ActionCard a = p.getCard(i);
			if (a.drawnInRound == g.round) {
				cards.add(new GuiActionCard(a, p.isProconsul));
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

}
