package Client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EnumSet;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import Client.Client;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.GameData;
import SharedData.PlayerData;

public class CardPanel extends JScrollPane implements MouseMotionListener, MouseListener {
	
	private HashMap<String, GuiActionCard> cards;
	private JPanel cardPanel;
	private EnumSet<CardType> cardFilter;
	
	private static final long serialVersionUID = -1766680394953812193L;
	private final GameViews gameViews;
	private GuiActionCard markedCard;
	
	public CardPanel (GameViews gameViews) {
		this.gameViews = gameViews;
		setBorder(null);
		
		cards = new HashMap<>();
		cardFilter = EnumSet.noneOf(CardType.class);;
		cardPanel = new JPanel();
		cardPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
		//cardPanel.setMinimumSize(new Dimension(200, 135));
		//cardPanel.setPreferredSize(new Dimension(200, 135));
		//cardPanel.setBackground(Color.blue);
		this.setViewportView(cardPanel);
		// set cardPanel Borders and scrollbars
		//this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		//this.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
		
		// mouse listener
		this.addMouseMotionListener(this);
		
		// show every card
//		addCard(new ActionCard(CardType.ABUSEOFPOWER));
//		addCard(new ActionCard(CardType.ANNEXATION));
//		addCard(new ActionCard(CardType.ASSASSINATION));
//		addCard(new ActionCard(CardType.BRIBE));
//		addCard(new ActionCard(CardType.CATAPULT));
//		addCard(new ActionCard(CardType.DENARI1000));
//		addCard(new ActionCard(CardType.DENARI2000));
//		addCard(new ActionCard(CardType.DENARI3000));
//		addCard(new ActionCard(CardType.FREEBUILDING));
//		addCard(new ActionCard(CardType.GOLDENCHARIOT));
//		addCard(new ActionCard(CardType.GOLDENLION));
//		addCard(new ActionCard(CardType.JUGGLER));
//		addCard(new ActionCard(CardType.LION));
//		addCard(new ActionCard(CardType.PICKLOCK));
//		addCard(new ActionCard(CardType.PROPAGANDA));
//		addCard(new ActionCard(CardType.SLAVEREVOLT));
//		addCard(new ActionCard(CardType.SPY));
//		addCard(new ActionCard(CardType.SURPRISEATTACK));
	}
	
	
	private void addCard(ActionCard a) {
		if (a==null) return;
		if (!cards.containsKey(a.getCardID())) {
			GuiActionCard gac = new GuiActionCard(a, true);
			gac.addMouseListener(this);
			gac.addMouseMotionListener(this);
			cards.put(a.getCardID(), gac);
			markUnmarkCard(gac);
			cardPanel.add(gac);
			validate();
		}
	}
	
	public void removeCard(ActionCard a) {
		if (a==null) return;
		if (cards.containsKey(a.getCardID())) {
			cardPanel.remove(cards.remove(a.getCardID()));
		}
		validate();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = this.getMousePosition().x;
		int w = this.getWidth();
		float p = (float)x / w;
		int cardWidth = cardPanel.getWidth();
		int scrollTo = (int) ((cardWidth - w) * p);
		//System.out.println("* mouseMoved " + x + " panelWidth:" + this.getWidth() + " p:" + p + " cardsWidth:" + cardWidth
		//		+ " scrollTo: " + scrollTo);
		if (scrollTo >= 0) cardPanel.scrollRectToVisible(new Rectangle(scrollTo, 1, w, 1));
	}


	public void setCardFilter(EnumSet<CardType> typeSet) {
		cardFilter = typeSet;
		for (GuiActionCard a : cards.values()) {
			markUnmarkCard(a);
		}
	}


	/**
	 * @param g
	 */
	private void markUnmarkCard(GuiActionCard g) {
		if (cardFilter.isEmpty()) {
			g.unMark();
		} else {
			if( cardFilter.contains( g.getActionCard().getType())) {
				g.markWith(Color.green);
			} else {
				g.markWith(Color.gray);
			}
		}
	}

	public void clearCardFilter() {
		cardFilter.clear();
		for (GuiActionCard a : cards.values()) {
			a.unMark();
		}
	}

	public void updateGameData(GameData g) {
		// replace all cards by new ones - any better ideas?
		markedCard = null;
		cards.clear();
		cardPanel.removeAll();
		String myplayerID = Client.getPlayerID();
		PlayerData p = g.getPlayer(myplayerID);
		int num = p.getNumberOfCards();
		for (int i = 0; i < num; i++) {
			ActionCard a = p.getCard(i);
			addCard(a);
		}
		validate();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}


	@Override
	public void mousePressed(MouseEvent e) {
		// check if card may be clicked by cardFilter
		if (e.getSource() instanceof GuiActionCard) {
			GuiActionCard g = (GuiActionCard)e.getSource();
			ActionCard a = g.getActionCard();
			// mark / unmark card
			if (markedCard != null) {
				markUnmarkCard(markedCard);
			}
			// tell GameViews
			if ((cardFilter.isEmpty() || cardFilter.contains(a.getType())) && SwingUtilities.isLeftMouseButton(e)) {
				g.markWith(Color.blue);
				markedCard = g;
				gameViews.ActionCardClicked(a);
			}
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void mouseEntered(MouseEvent e) {}


	@Override
	public void mouseExited(MouseEvent e) {}
	
	// disabling all card types by calling markCardTypes(ActionCard.noCardTypes) in GameView
//	public void setDisableAllCards(boolean disableAllCards) {
//		this.disableAllCards = disableAllCards;
//	}
}
