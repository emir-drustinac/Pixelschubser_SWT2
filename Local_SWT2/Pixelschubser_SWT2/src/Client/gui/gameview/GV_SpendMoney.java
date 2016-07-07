package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import Client.Client;
import Client.gui.GuiActionCard;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.ActionCardList;
import SharedData.GameData;
import SharedData.PlayerData;

/**
 * @author chris
 *
 */
public class GV_SpendMoney extends GameView {

	private static final long serialVersionUID = 1829294331352754452L;
	private JPanel moneyCardsPanel;
	private JPanel itemsToBuy;
	private JPanel icons;
	private JPanel itemsCount;
	private MouseListener itemsML;
	private MouseListener cardsML;
	private JLabel mercs;
	private JLabel buildings;
	private JLabel cards;
	private JLabel lblCost;
	private JLabel mercsCount;
	private JLabel buildingsCount;
	private JLabel cardsCount;
	private JButton btnKaufen;
	private int cost = 0;
	private int money = 0;
	private int nrOfMercs;
	private int nrOfFreeMercsCards = 0;
	private int nrOfFreeBuildingsCards = 0;
	private int mercsToBuy = 0;
	private int buildingsToBuy = 0;
	private int cardsToBuy = 0;
	private ActionCardList acl;
	private GuiActionCard gac;

	public GV_SpendMoney() {
		this.setLayout(new BorderLayout());
		
		JPanel nord = new JPanel(new BorderLayout());
		JPanel itemsCosts = new JPanel(new FlowLayout(FlowLayout.CENTER, 68, 1));
		itemsCosts.add(new JLabel("2000 Denari"));
		itemsCosts.add(new JLabel("4000 Denari"));
		itemsCosts.add(new JLabel("1000 Denari"));
		itemsCosts.add(new JLabel("                              "));
		itemsCosts.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel totalCost = new JPanel(new FlowLayout(FlowLayout.CENTER));
		lblCost = getIconLabel("/images/dollar_coin_stack.png");
		lblCost.setText("" + cost + "  Denari");
		totalCost.add(lblCost);

		itemsToBuy = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 1));
		itemsToBuy.setBorder(new EmptyBorder(5, 5, 5, 5));
		nord.add(itemsCosts, BorderLayout.NORTH);
		nord.add(itemsToBuy, BorderLayout.CENTER);
		nord.add(totalCost, BorderLayout.SOUTH);
		add(nord/* itemsToBuy */, BorderLayout.NORTH);

		// create MouseListener:
		createItemsML();
		createCardsML();

		mercs = getIconLabel("/images/mercenary_small_2.png");
		mercs.setText("0");
		mercs.setToolTipText("2000 Denari");
		mercs.setVerticalAlignment(JLabel.CENTER);
		mercs.setHorizontalAlignment(JLabel.CENTER);
		mercs.setVerticalTextPosition(JLabel.BOTTOM);
		mercs.setHorizontalTextPosition(JLabel.CENTER);
		mercs.addMouseListener(itemsML);
		itemsToBuy.add(mercs);

		buildings = getIconLabel("/images/building_small_2.png");
		buildings.setText("0");
		buildings.setToolTipText("4000 Denari");
		buildings.setVerticalAlignment(JLabel.CENTER);
		buildings.setHorizontalAlignment(JLabel.CENTER);
		buildings.setVerticalTextPosition(JLabel.BOTTOM);
		buildings.setHorizontalTextPosition(JLabel.CENTER);
		buildings.addMouseListener(itemsML);
		itemsToBuy.add(buildings);

		cards = getIconLabel("/images/cards_small_2.png");
		cards.setText("0");
		cards.setToolTipText("1000 Denari");
		cards.setVerticalAlignment(JLabel.CENTER);
		cards.setHorizontalAlignment(JLabel.CENTER);
		cards.setVerticalTextPosition(JLabel.BOTTOM);
		cards.setHorizontalTextPosition(JLabel.CENTER);
		cards.addMouseListener(itemsML);
		itemsToBuy.add(cards);

		btnKaufen = new JButton("kaufen");
		btnKaufen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnKaufen.setEnabled(false);
				Client.sendMessageToServer(
						"confirm:spend_money:" + cost + ":" + mercsToBuy + ":" + buildingsToBuy + ":" + cardsToBuy);
			}
		});
		itemsToBuy.add(btnKaufen);

		JPanel middle = new JPanel(new BorderLayout());
		JLabel lblDeck = new JLabel("Karten auf dem Ablagestapel:");
		lblDeck.setHorizontalAlignment(JLabel.CENTER);
		middle.add(lblDeck, BorderLayout.NORTH);
		// JPanel with cards that can be used for buying
		moneyCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		middle.add(moneyCardsPanel, BorderLayout.CENTER);
		this.add(middle/* moneyCardsPanel */, BorderLayout.CENTER);
		// moneyCardsPanel.setBorder(new EmptyBorder(1, 1, 20, 1));
		JScrollPane scrollPane = new JScrollPane(middle/* moneyCardsPanel */);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.add(nord/* itemsToBuy */, BorderLayout.NORTH);
		add(scrollPane);

	}

	private void createItemsML() {
		Client.sendMessageToServer("phase_infos");
		itemsML = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (SwingUtilities.isLeftMouseButton(e)) {
					String nr = ((JLabel) e.getSource()).getText();

					if (((JLabel) e.getSource()).equals(mercs)) {
						cost += 2000;
						if(nrOfFreeMercsCards-- > 0 && (acl.contains(CardType.ABUSEOFPOWER) || acl.contains(CardType.PROPAGANDA))) {
							cost = cost >= 2000 ? (cost - 2000) : cost;
							mercsToBuy = Integer.parseInt(nr) + 1;
							((JLabel) e.getSource()).setText("" + mercsToBuy);
						}
						else if (cost > money) {
							Client.sendMessageToServer("buy:not_enough_money");
							cost -= 2000;
						} else if (mercsToBuy + nrOfMercs > 3) {
							Client.sendMessageToServer("buy:mercs_limit");
							cost -= 2000;
						} else {
							mercsToBuy = Integer.parseInt(nr) + 1;
							((JLabel) e.getSource()).setText("" + mercsToBuy);
						}
//						lblCost.setText("" + cost + "  Denari");
					}

					if (((JLabel) e.getSource()).equals(buildings)) {
						cost += 4000;
						if (nrOfFreeBuildingsCards-- > 0) {
							cost = cost >= 4000 ? (cost - 4000) : cost;
							buildingsToBuy = Integer.parseInt(nr) + 1;
							((JLabel) e.getSource()).setText("" + buildingsToBuy);
						} else if (cost > money) {
							Client.sendMessageToServer("buy:not_enough_money");
							cost -= 4000;
						} else {
							buildingsToBuy = Integer.parseInt(nr) + 1;
							((JLabel) e.getSource()).setText("" + buildingsToBuy);
						}
//						lblCost.setText("" + cost + "  Denari");
					}

					if (((JLabel) e.getSource()).equals(cards)) {
						cost += 1000;
						if (cost > money) {
							Client.sendMessageToServer("buy:not_enough_money");
							cost -= 1000;
						} else if (cardsToBuy > 0) {
							Client.sendMessageToServer("buy:card_limit");
							cost -= 1000;
						} else {
						//if(cost <= money && cardsToBuy < 1) {
							cardsToBuy = Integer.parseInt(nr) + 1;
							((JLabel) e.getSource()).setText("" + cardsToBuy);
						}
//						lblCost.setText("" + cost + "  Denari");
					}
					lblCost.setText("" + cost + "  Denari");
				}

				if (SwingUtilities.isRightMouseButton(e)) {
					if (((JLabel) e.getSource()).equals(mercs) && mercsToBuy > 0) {
						cost -= 2000;
						nrOfFreeMercsCards++;
						((JLabel) e.getSource()).setText("" + --mercsToBuy);
					}
					if (((JLabel) e.getSource()).equals(buildings) && buildingsToBuy > 0) {
						cost -= 4000;
						nrOfFreeBuildingsCards++;
						((JLabel) e.getSource()).setText("" + --buildingsToBuy);
					}
					if (((JLabel) e.getSource()).equals(cards) && cardsToBuy > 0) {
						cost -= 1000;
						((JLabel) e.getSource()).setText("" + --cardsToBuy);
					}
					lblCost.setText("" + cost + "  Denari");
				}
			}
		};
	}

	private void createCardsML() {
		acl = new ActionCardList();
		cardsML = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				GuiActionCard gac = ((GuiActionCard) e.getSource());

				// select
				if (SwingUtilities.isLeftMouseButton(e) && !acl.containsCardWithID(gac.getActionCard())) {
					gac.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLUE));
					acl.add(gac.getActionCard());
					System.out.println("~~~Addded card: " + acl.lastElement().getType());
					money += gac.getActionCard().moneyValue();
					System.out.println("~~~~MONEY: " + money);
					System.out.println("SIZE: " + acl.size());
					Client.sendMessageToServer("selectedCard:" + gac.getActionCard().getCardID());
				}
				// unselect
				if (SwingUtilities.isRightMouseButton(e) && acl.containsCardWithID(gac.getActionCard())) {
					gac.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
					acl.removeCard(gac.getActionCard());
					System.out.println("~~~Removed card: " + gac.getActionCard().getCardID());
					if (money != 0) {
						money -= gac.getActionCard().moneyValue();
						System.out.println("~~~~MONEY: " + money);
					}
					System.out.println("SIZE: " + acl.size());
					Client.sendMessageToServer("unselectedCard:" + gac.getActionCard().getCardID());
				}
			}
		};
	}

	@Override
	public void updateGameData(GameData g) {
		System.out.println("@ " + this.getClass().getSimpleName() + ".updateGameData @");

		// show cards which can be used for buying
		PlayerData pd = g.players.get(myClientID());
		nrOfMercs = pd.numberOfMercenaries();
		moneyCardsPanel.removeAll();
		boolean fMercs = false;
		boolean fBuildings = false;
		for (int i = 0; i < pd.getNumberOfCards(); i++) {
			ActionCard a = pd.getCard(i);
			fMercs = a.getType().equals(CardType.ABUSEOFPOWER) || a.getType().equals(CardType.PROPAGANDA);
			fBuildings = a.getType().equals(CardType.FREEBUILDING);
			if (a.isMoneyCard() || fMercs || fBuildings) {
				gac = new GuiActionCard(a, true);
				gac.addMouseListener(cardsML);
				moneyCardsPanel.add(gac);
			}
			if (fMercs) {
				nrOfFreeMercsCards++;
			}
			if (fBuildings) {
				nrOfFreeBuildingsCards++;
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

}
