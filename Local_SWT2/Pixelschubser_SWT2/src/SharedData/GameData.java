package SharedData;

import java.io.Serializable;
import java.util.*;

public class GameData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4237062929837830623L;
	public int random = 0;
	public PhaseType phase;
	public PlayerList players;
	private ActionCardList deck;
	private ActionCardList discardPile;
	
	public GameData() {
		players = new PlayerList();
		deck = new ActionCardList();
		discardPile = new ActionCardList();
	}

	/**
	 * 
	 * @param a
	 */
	public void discardCard(ActionCard a) {
		discardPile.addCard(a);
		// TODO: automatically remove card from its current ActionCardList by letting the card know on which list it is?
	}

	public ActionCard takeCard() {
		// if deck is empty, fill it with mixed discardPile
		if (deck.size() == 0) {
			System.out.println("Cards were mixed.");
			int numCards = discardPile.size();
			Random rnd = new Random();
			for (int i = numCards; i > 0; i++) {
				int r = rnd.nextInt(i);
				deck.addCard( discardPile.remove(r) );
			} 
		}
		// return first card on deck and remove it from deck 
		return deck.remove(0);
	}
	
	public void addCard2Deck(ActionCard a) {
		if(a != null) {
			deck.addCard(a);
		}
	}
	
	public void addCard2DeckAtIndex(ActionCard a, int index) {
		if(a != null) {
			deck.add(index, a);
		}
	}

	/**
	 * adds a new player to the playerList
	 * @param playerID
	 * @param name
	 */
	public void addPlayer(String playerID, String name) {
		players.addPlayer(playerID, name);
	}

	/**
	 * removes the player with the given playerID from the list of players
	 * @param playerID
	 */
	public boolean removePlayer(String playerID) {
		return players.removePlayer(playerID);
	}

	public PlayerList getWinnerList() {
		// gather list of players with the highest number of points and the highest amount of money (cards)
		int maxPoints = 0;
		int maxMoney = 0;
		PlayerList winners = new PlayerList();
		
		for (PlayerData player : players) {
			int points = player.getNumberOfVictoryPoints();
			int money = player.getAmountOfMoney();
			// new top dog
			if (points > maxPoints || (points == maxPoints && money > maxMoney)) {
				winners.clear();
				winners.add(player);
				maxPoints = points;
				maxMoney = money;
			} else
			// same points and same money, so another winner
			if (points == maxPoints && money == maxMoney) {
				winners.add(player);
			}
			// else looser
			
		}
		return winners;
	}

	/**
	 * 
	 * @param playerID
	 */
	public void makeProconsul(String playerID) {
		for(PlayerData p : players) {
			p.isProconsul = p.playerID.equals(playerID);
		}
	}

	public PlayerData getPlayer(String playerID) {
		for(PlayerData p : players) {
			if(p.playerID.equals(playerID))
				return p;
		}
		return null;
	}

	public int getDeckSize() {
		return deck.size();
	}

	public int getDiscardPileSize() {
		return discardPile.size();
	}

}
