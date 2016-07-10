package Client.gui.gameview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EnumSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

import Client.Client;
import Client.gui.CardPanel;
import Client.gui.GuiActionCard;
import Client.gui.Presentation;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.ActionCardList;
import SharedData.GameData;
import SharedData.PlayerData;
import SharedData.PlayerList;

/**
 * @author chris
 *
 */
public class GV_SpendMoney extends GameView {

	private static final long serialVersionUID = 1829294331352754452L;
	private JPanel moneyCardsPanel;
	private MouseListener itemsML;
	private MouseListener cardsML;
	private JLabel mercs;
	private JLabel buildings;
	private JLabel cards;
	private JLabel lblCost;
	private JButton btnKaufen;
	private JButton btnWeiter;
	private int cost = 0;
	private int money = 0;
	private int nrOfMercs;
	private int nrOfFreeMercsCards = 0;
	private int nrOfFreeBuildingsCards = 0;
	private boolean hasFreeMercsCards = false;
	private boolean hasFreeBuildingCards = false;
	private int mercsToBuy = 0;
	private int buildingsToBuy = 0;
	private int cardsToBuy = 0;
	private CardPanel handCardsPanel;
	private boolean buyPressed = false;
	private boolean cardBought = false;
	private ActionCardList discardedCards;
	

	public GV_SpendMoney() {
		this.setLayout(new BorderLayout());
		
		// create MouseListeners:
		createItemsML();
//		createCardsML();
		discardedCards = new ActionCardList();
		
		JPanel nord = new JPanel(new FlowLayout());
		add(nord, BorderLayout.NORTH);
		
		JPanel grids = new JPanel(new GridLayout(3, 4, 80, 1));
		nord.add(grids);
		
		JLabel lblDenari1 = new JLabel();
		lblDenari1.setVerticalAlignment(JLabel.CENTER);
		lblDenari1.setText("2000 Denari");
		lblDenari1.setHorizontalAlignment(JLabel.CENTER);
		grids.add(lblDenari1);
		
		JLabel lblDenari2 = new JLabel();
		lblDenari2.setVerticalAlignment(JLabel.CENTER);
		lblDenari2.setText("4000 Denari");
		lblDenari2.setHorizontalAlignment(JLabel.CENTER);
		grids.add(lblDenari2);
		
		JLabel lblDenari3 = new JLabel();
		lblDenari3.setVerticalAlignment(JLabel.CENTER);
		lblDenari3.setText("1000 Denari");
		lblDenari3.setHorizontalAlignment(JLabel.CENTER);
		grids.add(lblDenari3);
		
		JLabel lblDummy = new JLabel();
		lblDummy.setVerticalAlignment(JLabel.CENTER);
		lblDummy.setHorizontalAlignment(JLabel.CENTER);
		grids.add(lblDummy);
		
		mercs = new JLabel(new ImageIcon(getClass().getResource("/images/mercenary_small_2.png")));
		mercs.setText("0");
		mercs.setVerticalTextPosition(JLabel.BOTTOM);
		mercs.setHorizontalTextPosition(JLabel.CENTER);
		mercs.setHorizontalAlignment(JLabel.CENTER);
		mercs.setVerticalAlignment(JLabel.CENTER);
		mercs.addMouseListener(itemsML);
		grids.add(mercs);
		
		buildings = new JLabel(new ImageIcon(getClass().getResource("/images/building_small_2.png")));
		buildings.setText("0");
		buildings.setVerticalTextPosition(JLabel.BOTTOM);
		buildings.setHorizontalTextPosition(JLabel.CENTER);
		buildings.setHorizontalAlignment(JLabel.CENTER);
		buildings.setVerticalAlignment(JLabel.CENTER);
		buildings.addMouseListener(itemsML);
		grids.add(buildings);
		
		cards = new JLabel(new ImageIcon(getClass().getResource("/images/cards_small_2.png")));
		cards.setText("0");
		cards.setVerticalTextPosition(JLabel.BOTTOM);
		cards.setHorizontalTextPosition(JLabel.CENTER);
		cards.setHorizontalAlignment(JLabel.CENTER);
		cards.setVerticalAlignment(JLabel.CENTER);
		cards.addMouseListener(itemsML);
		grids.add(cards);
		
		btnKaufen = new JButton("kaufen");
		btnKaufen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnKaufen.setEnabled(false);
				buyPressed = true;
				cardBought = cardsToBuy > 0 ? true : false;
				Client.sendMessageToServer("kaufen:" + cost + ":" + 
						mercsToBuy + ":" + buildingsToBuy + ":" + cardsToBuy);
				
				//reset number of buyed items, cost and money
				mercsToBuy = buildingsToBuy = cardsToBuy = cost = money = 0;
				mercs.setText("" + mercsToBuy);
				buildings.setText("" + buildingsToBuy);
				cards.setText("" + cardsToBuy);
				lblCost.setText("" + cost);
				
				moneyCardsPanel.removeAll();
				moneyCardsPanel.validate();
				moneyCardsPanel.repaint();
			}
		});
		JPanel btnKaufenPanel = new JPanel(new FlowLayout());
		btnKaufenPanel.add(btnKaufen);
		grids.add(btnKaufenPanel);
		
		JLabel lblDummy2 = new JLabel();
		lblDummy2.setHorizontalAlignment(JLabel.CENTER);
		grids.add(lblDummy2);
		
		JPanel lblCostPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lblCost = new JLabel(new ImageIcon(getClass().getResource("/images/dollar_coin_stack.png")));
		lblCost.setText("" + cost /*+ " Denari"*/);
		lblCost.setHorizontalAlignment(JLabel.CENTER);
		lblCostPanel.add(lblCost);
		grids.add(lblCostPanel);
		
		JLabel lblDummy3 = new JLabel();
		lblDummy3.setHorizontalAlignment(JLabel.CENTER);
		grids.add(lblDummy3);
		
		btnWeiter = new JButton("weiter");
		btnWeiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnWeiter.setEnabled(false);
				btnKaufen.setEnabled(false);
				
				moneyCardsPanel.removeAll();
				moneyCardsPanel.validate();
				moneyCardsPanel.repaint();
				Client.sendMessageToServer("weiter");
			}
		});
		JPanel btnWeiterPanel = new JPanel(new FlowLayout());
		btnWeiterPanel.add(btnWeiter);
		grids.add(btnWeiterPanel);


		JPanel middle = new JPanel(new BorderLayout());
		JLabel lblDeck = new JLabel("Abgelegte Karten:"); //Discarded Cards
		lblDeck.setHorizontalAlignment(JLabel.CENTER);
		middle.add(lblDeck, BorderLayout.NORTH);
		// JPanel with cards that can be used for buying
		moneyCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		middle.add(moneyCardsPanel, BorderLayout.CENTER);
//		this.add(middle, BorderLayout.CENTER);
		// moneyCardsPanel.setBorder(new EmptyBorder(1, 1, 20, 1));
		JScrollPane scrollPane = new JScrollPane(middle/* moneyCardsPanel */);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

//		this.add(nord, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);

	}

	private void createItemsML() {
		itemsML = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
//				if (btnKaufen.isEnabled()) {
					String nrS = ((JLabel) e.getSource()).getText();
					int nrI = Integer.parseInt(nrS);

					if (SwingUtilities.isLeftMouseButton(e)) {
						if (((JLabel) e.getSource()).equals(mercs)) {
							cost += 2000;
							if (nrOfFreeMercsCards > 0) {
								cost = cost >= 2000 ? (cost - 2000) : cost;
								nrOfFreeMercsCards--;
								mercsToBuy = nrI + 1;
								((JLabel) e.getSource()).setText("" + mercsToBuy);
							} else if (cost > money) {
								Presentation.getGameWindow().setMessage("Nicht genug Geld!", true);
								cost -= 2000;
							} else if (mercsToBuy + nrOfMercs > 3) {
								Presentation.getGameWindow().setMessage("Sie können maximal 4 Söldner haben!", true);
								cost -= 2000;
							} else {
								mercsToBuy = nrI + 1;
								((JLabel) e.getSource()).setText("" + mercsToBuy);
							}
						}

						if (((JLabel) e.getSource()).equals(buildings)) {
							cost += 4000;
							if (nrOfFreeBuildingsCards > 0) {
								cost = cost >= 4000 ? (cost - 4000) : cost;
								nrOfFreeBuildingsCards--;
								buildingsToBuy = nrI + 1;
								((JLabel) e.getSource()).setText("" + buildingsToBuy);
							} else if (cost > money) {
								Presentation.getGameWindow().setMessage("Nicht genug Geld!", true);
								cost -= 4000;
							} else {
								buildingsToBuy = nrI + 1;
								((JLabel) e.getSource()).setText("" + buildingsToBuy);
							}
						}

						if (((JLabel) e.getSource()).equals(cards)) {
							cost += 1000;
							if (cost > money) {
								Presentation.getGameWindow().setMessage("Nicht genug Geld!", true);
								cost -= 1000;
							} else if (cardsToBuy > 0) {
								Presentation.getGameWindow().setMessage("Sie können maximal 1 Karte in dieser Runde kaufen!", true);
								cost -= 1000;
							} else {
								if(!cardBought) {
									cardsToBuy = nrI + 1;
								} else {
									cost -= 1000;
									Presentation.getGameWindow().setMessage("Sie haben schon 1 Karte in dieser Runde gekauft!", true);
								}
								((JLabel) e.getSource()).setText("" + cardsToBuy);
							}
						}
						lblCost.setText("" + cost /*+ "  Denari"*/);
					}

					if (SwingUtilities.isRightMouseButton(e)) {
						if (((JLabel) e.getSource()).equals(mercs) && mercsToBuy > 0) {
							cost = cost >= 2000 ? (cost - 2000) : cost;
							if(hasFreeMercsCards) { 
								nrOfFreeMercsCards++;
							}
							((JLabel) e.getSource()).setText("" + --mercsToBuy);
						}
						else if (((JLabel) e.getSource()).equals(buildings) && buildingsToBuy > 0) {
							cost = cost >= 4000 ? (cost - 4000) : cost;
							if(hasFreeBuildingCards) {
								nrOfFreeBuildingsCards++;
							}
							((JLabel) e.getSource()).setText("" + --buildingsToBuy);
						}
						else if (((JLabel) e.getSource()).equals(cards) && cardsToBuy > 0) {
							cost = cost >= 1000 ? (cost - 1000) : cost;
							((JLabel) e.getSource()).setText("" + --cardsToBuy);
						}
						lblCost.setText("" + cost /*+ "  Denari"*/);
					}
				}
//			}
		};
	}
	/* discarded cards dont need this listener any more
	private void createCardsML() {
		cardsML = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				gac = ((GuiActionCard) e.getSource());

				// select
				if (SwingUtilities.isLeftMouseButton(e) && !discardedCards.containsCardWithID(gac.getActionCard())) {
					gac.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLUE));
					discardedCards.add(gac.getActionCard());
					System.out.println("~~~Addded card: " + discardedCards.lastElement().getType());
					money += gac.getActionCard().moneyValue();
					System.out.println("~~~~MONEY: " + money);
					System.out.println("SIZE: " + discardedCards.size());
					Client.sendMessageToServer("selectedCard:" + gac.getActionCard().getCardID());
				}
				// unselect
				if (SwingUtilities.isRightMouseButton(e) && discardedCards.containsCardWithID(gac.getActionCard())) {
					gac.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
					discardedCards.removeCard(gac.getActionCard());
					System.out.println("~~~Removed card: " + gac.getActionCard().getCardID());
					if (money != 0) {
						money -= gac.getActionCard().moneyValue();
						System.out.println("~~~~MONEY: " + money);
					}
					System.out.println("SIZE: " + discardedCards.size());
					Client.sendMessageToServer("unselectedCard:" + gac.getActionCard().getCardID());
				}
			}
		};
	}*/

	@Override
	public void updateGameData(GameData g) {
		System.out.println("@ " + this.getClass().getSimpleName() + ".updateGameData @");
		handCardsPanel = Presentation.getGameWindow().getCardPanel();
		
		// show cards which can be used for buying
		EnumSet<CardType> types = EnumSet.noneOf(CardType.class);
		PlayerData pd = g.players.get(myClientID());
		nrOfMercs = pd.numberOfMercenaries();
		
		boolean fMercs = false;
		boolean fBuildings = false;
		for (int i = 0; i < pd.getNumberOfCards(); i++) {
			ActionCard a = pd.getCard(i);
			fMercs = a.getType().equals(CardType.ABUSEOFPOWER) || a.getType().equals(CardType.PROPAGANDA);
			fBuildings = a.getType().equals(CardType.FREEBUILDING);
			if (a.isMoneyCard() || fMercs || fBuildings) {
				types.add(a.getType());
			} //else if(buyPressed && a.isMoneyCard()) {
//				btnKaufen.setEnabled(true);
//			}
		}
		// mark cards in the hand which can be used to buy items
		if(!types.isEmpty()) {
			markCardTypes(types);
		}
	}
	
	@Override
	public void ActionCardClicked(ActionCard a) {
		boolean fMercs, fBuildings;
		GuiActionCard gac = new GuiActionCard(a, true);
		handCardsPanel = Presentation.getGameWindow().getCardPanel();
		
		moneyCardsPanel.add(gac);
		discardedCards.add(a);
		moneyCardsPanel.validate();
		handCardsPanel.removeCard(a);
		handCardsPanel.validate();
		handCardsPanel.repaint();			
		Client.sendMessageToServer("selectedCard:" + a.getCardID());
		
		fMercs = a.getType().equals(CardType.ABUSEOFPOWER) || a.getType().equals(CardType.PROPAGANDA);
		fBuildings = a.getType().equals(CardType.FREEBUILDING);
		if (fMercs) {
			hasFreeMercsCards = true;
			nrOfFreeMercsCards++;
			System.out.println("~~~~(" + Client.getPlayerName() + ") Free mercs: " + nrOfFreeMercsCards);
		} else if (fBuildings) {
			hasFreeBuildingCards = true;
			nrOfFreeBuildingsCards++;
			System.out.println("~~~~(" + Client.getPlayerName() + ") Free buildings: " + nrOfFreeBuildingsCards);
		} else {
			money += a.moneyValue();
			btnKaufen.setEnabled(true);
		}
		System.out.println("~~~~(" + Client.getPlayerName() + ") MONEY: " + money);
		
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
