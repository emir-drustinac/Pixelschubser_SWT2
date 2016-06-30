package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import Client.gui.PlayerInfos;
import SharedData.ActionCard;
import SharedData.GameData;
import SharedData.PlayerData;
import SharedData.ActionCard.CardType;

/**
 * @author chris
 *
 */
public class GV_MakePromises extends GameView {
	
	private static final long serialVersionUID = -9160771029486484794L;
	private JPanel cards;
	private JLabel info;
	private MouseListener cardMListener;
	private GuiActionCard guiCard;
	private ActionCard promisedCard;
	
	public GV_MakePromises() {
		setLayout(new BorderLayout());

		// text label
		info = new JLabel("Versprechungen machen: zuerst eine Karte anklicken und dann einen Spieler");
		info.setHorizontalAlignment(JLabel.CENTER);
		info.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		//this.add(la, BorderLayout.NORTH);

		// card grid
		cards = new JPanel();
		cards.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		this.add(cards, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane(cards);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);

		// button to proceed in game
		JButton btn = new JButton("weiter");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Client.sendMessageToServer("confirm:promise_cards");
			}
		});
		this.add(btn, BorderLayout.SOUTH);
		
		createCardsMouseListener(); 
	}

	@Override
	public void updateGameData(GameData g) {
		System.out.println("@ " + this.getClass().getSimpleName() + ".updateGameData @");
		// show cards drawn in this round
		EnumSet<CardType> types = EnumSet.noneOf(CardType.class);
		PlayerData p = g.players.get(myClientID());
		
		if (p.isProconsul) {
			this.add(info, BorderLayout.NORTH);

			cards.removeAll();
			for (int i = 0; i < p.getNumberOfCards(); i++) {
				ActionCard a = p.getCard(i);
				if (a.drawnInRound == g.round) {
					GuiActionCard ac = new GuiActionCard(a, false);
					ac.addMouseListener(cardMListener);
					cards.add(ac);
					// cards.add(new GuiActionCard(null, true));
					types.add(a.getType());
				}
			}
		} else {
			info.setText("Proconsul verspricht Karten...");
			this.add(info, BorderLayout.NORTH);
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
	public ActionCardException useActionCard(ActionCard c) {
		// TODO players uses an ActionCard
		return new ActionCardException();
	}

	//PlayerInfos
	@Override
	public void mouseClicked(MouseEvent e) {
		if(promisedCard != null) {
			String playerID = ((PlayerInfos)e.getSource()).getPlayerID();
			System.out.println("~~~~~" + playerID + "got promised card" + promisedCard.getType().toString());
			Client.sendMessageToServer("promised_card:" + promisedCard.getCardID() + ":" + playerID);
		}
	}

	private void createCardsMouseListener()	{
		cardMListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				guiCard = (GuiActionCard)e.getSource();
				guiCard.setBorder(BorderFactory.createLineBorder(Color.black, 3));
				ActionCard promisedCard = ((GuiActionCard)e.getSource()).getActionCard();
				Client.sendMessageToServer("promisedCard:" + promisedCard.getType().toString());
				System.out.println("~~~~~promised card: " + promisedCard.getType().toString());
			}
		};
	}
}
