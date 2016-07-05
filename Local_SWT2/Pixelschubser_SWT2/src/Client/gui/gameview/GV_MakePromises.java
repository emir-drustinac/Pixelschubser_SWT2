package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import Client.Client;
import Client.gui.GuiActionCard;
import Client.gui.PlayerInfos;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.GameData;
import SharedData.PlayerData;

/**
 * @author chris
 *
 */
public class GV_MakePromises extends GameView {
	
	private static final long serialVersionUID = -9160771029486484794L;
	private JPanel players;
	private JPanel playerList;
	private JPanel cards;
	private JLabel info;
	private JButton btn;
	private JScrollPane scrollPane;
	private ActionCard promisedCard;
	
	public GV_MakePromises() {
		setLayout(new BorderLayout());

		// text label
		info = new JLabel("");
		info.setHorizontalAlignment(JLabel.CENTER);
		info.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 10));
		this.add(info, BorderLayout.NORTH);

		// players grid
		playerList = new JPanel();
		players = new JPanel(new GridLayout(0, 1, 0, 15));
		playerList.add(players);
		// card grid
		cards = new JPanel();
		cards.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(null);
		add(scrollPane, BorderLayout.CENTER);
		
		// button to proceed in game
		btn = new JButton("weiter");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.sendMessageToServer("confirm:promise_cards");
			}
		});
		btn.setEnabled(false);
		btn.setVisible(false);
		this.add(btn, BorderLayout.SOUTH);
	}

	@Override
	public void updateGameData(GameData g) {
		System.out.println("@ " + this.getClass().getSimpleName() + ".updateGameData @");
		PlayerData player = g.players.get(myClientID());
		promisedCard = null;
		
		if (player.isProconsul) {
			// show players with their promisedCardCount
			info.setText("Versprechungen machen: zuerst eine Karte anklicken und dann einen Spieler");
			scrollPane.setViewportView(playerList);
			
			players.removeAll();
			for (PlayerData p : g.players) {
				if (!p.isProconsul) {
					int n = p.getNumberOfPromisedCards();
					JLabel lbl = new JLabel(p.name + " hat " + n + " Versprechen");
					lbl.setForeground(n > 0 ? Color.green.darker() : Color.red);
					lbl.setHorizontalAlignment(JLabel.CENTER);
					players.add(lbl);
				}
			}
			
			btn.setVisible(true);
			if (g.allPlayersHaveBeenPromised()) btn.setEnabled(true);

		} else {
			// show cards promised in this round
			info.setText("vom Proconsul versprochene Karten");
			scrollPane.setViewportView(cards);
			
			cards.removeAll();
			for (int i = 0; i < player.getNumberOfPromisedCards(); i++) {
				ActionCard a = player.getPromise(i);
				GuiActionCard ac = new GuiActionCard(a, false);
				cards.add(ac);
			}
			btn.setVisible(false);
		}
	}

	@Override
	public void activateView(GameData g) {
		updateGameData(g);
		if (g.getPlayer(myClientID()).isProconsul) {
			// enable all cards to give
			markCardTypes(null);
			//TODO Proconsul can use picklock card?
		} else {
			// can use picklock card
			EnumSet<CardType> types = EnumSet.noneOf(CardType.class);
			types.add(CardType.PICKLOCK);
			markCardTypes(types);
		}
	}

	@Override
	public void deactivateView() {
		//markCardTypes(null);
	}


	@Override
	public void ActionCardClicked(ActionCard a) {
		promisedCard = a;
	}

	@Override
	public void PlayerInfoClicked(PlayerInfos p) {
		if(promisedCard != null) {
			if (p.isProconsul()) {
				// TODO show message ? u may not promise yourself a card 
			} else {
				// tell server
				String playerID = p.getPlayerID();
				System.out.println("~~~~~" + playerID + " got promised card " + promisedCard.getType().toString());
				Client.sendMessageToServer("promise_card:" + promisedCard.getCardID() + ":" + playerID);
			}
		}
	}

}
