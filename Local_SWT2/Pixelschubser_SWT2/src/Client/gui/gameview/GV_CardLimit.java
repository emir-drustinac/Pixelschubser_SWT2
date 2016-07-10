package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import Client.Client;
import Client.gui.CardPanel;
import Client.gui.GuiActionCard;
import Client.gui.Presentation;
import SharedData.ActionCard;
import SharedData.GameData;
import SharedData.PlayerData;
import SharedData.ActionCard.CardType;

/**
 * @author chris
 *
 */
public class GV_CardLimit extends GameView {

	private static final long serialVersionUID = 8572614425351182480L;
	private JPanel pnlWeiter;
	private JPanel discardedCards;
	private CardPanel handCardsPanel;
	private JButton btnWeiter;
	private int nrOfCards = 0;

	public GV_CardLimit() {
		setLayout(new BorderLayout());
		
		// Panel with button to continue
		pnlWeiter = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnWeiter = new JButton("weiter");
		btnWeiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnWeiter.setEnabled(false);
				Client.sendMessageToServer("weiter");
			}
		});
		pnlWeiter.add(btnWeiter);
		add(pnlWeiter, BorderLayout.SOUTH);

		JPanel middle = new JPanel(new BorderLayout());
		JLabel lblDeck = new JLabel("Abgelegte Karten:"); // Discarded cards
		lblDeck.setHorizontalAlignment(JLabel.CENTER);
		middle.add(lblDeck, BorderLayout.NORTH);

		// Panel with discarded cards
		discardedCards = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		middle.add(discardedCards, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(middle/* moneyCardsPanel */);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(middle, BorderLayout.CENTER);
	}

	@Override
	public void updateGameData(GameData g) {
		System.out.println("@ " + this.getClass().getSimpleName() + ".updateGameData @");
		handCardsPanel = Presentation.getGameWindow().getCardPanel();
		markCardTypes(null);
		PlayerData pd = g.players.get(myClientID());
		nrOfCards = pd.getNumberOfCards();

		if (pd.getNumberOfCards() <= 4) {
			btnWeiter.setEnabled(true);
		} else {
			int diff = pd.getNumberOfCards() - 4;
			String msg = "Sie können maximal 4 Karten behalten, legen Sie noch " + 
						 diff + " Karte" + (diff > 1 ? "n ab" : " ab"); 
			Presentation.getGameWindow().setMessage(msg, false);
		}
	}

	@Override
	public void ActionCardClicked(ActionCard a) {
		GuiActionCard gac = new GuiActionCard(a, true);
		handCardsPanel = Presentation.getGameWindow().getCardPanel();

		if (nrOfCards > 4) {
			discardedCards.add(gac);
			discardedCards.validate();
			handCardsPanel.removeCard(a);
			handCardsPanel.validate();
			handCardsPanel.repaint();
			Client.sendMessageToServer("selectedCard:" + a.getCardID());
		}
	}

	@Override
	public void activateView(GameData g) {
		updateGameData(g);
		for (PlayerData pd : g.players) {
			if (pd.getNumberOfCards() > 4) {
				btnWeiter.setEnabled(false);
				Presentation.getGameWindow().setMessage("Sie können maximal 4 Karten behalten", false);
			} else {
				Presentation.getGameWindow().setMessage(" ", false);
			}
		}
	}

	@Override
	public void deactivateView() {
		// TODO clear things up
	}

}
