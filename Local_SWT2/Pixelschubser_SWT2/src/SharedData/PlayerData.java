package SharedData;

import java.io.Serializable;

public class PlayerData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5933512311142328024L;
	public boolean isGameLeader = false;
	public String name;
	public final String playerID;
	public boolean isProconsul = false;
	public int numberOfBuildings;
	public int numberOfMercenaries;
	private int combatPoints;
	private ActionCardList promises;
	private ActionCardList cards;
	public boolean isReady = false;
	 

	public PlayerData(String playerID, String name) {
		this.name = name;
		this.playerID = playerID;
		promises = new ActionCardList();
		cards = new ActionCardList();
		combatPoints = 0;
	}

	/**
	 * returns the number cards this player has on hand
	 */
	public int getNumberOfCards() {
		return cards.size();
	}

	/**
	 * returns the number of points this player has including the extra points from cards if known
	 */
	public int getNumberOfVictoryPoints() {
		// return number of buildings
		// plus points on extra point cards
		int points = 0;
		for (ActionCard c : cards) {
			if (c.addsVictoryPoints()) points++;
		}
		return numberOfBuildings + points;
	}

	/**
	 * returns the card with the given index i from the list of cards on players hand
	 * @param i
	 */
	public ActionCard getCard(int i) {
		return cards.getCard(i);
	}

	/**
	 * adds the given card to the list of cards on players hand
	 * @param a
	 */
	public void addCard(ActionCard a) {
		cards.addCard(a);
	}

	/**
	 * removes the given card from the list of cards on players hand
	 * @param a
	 */
	public void removeCard(ActionCard a) {
		cards.removeCard(a);
	}

	/**
	 * checks if the player holds a card 
	 * @param a
	 */
	public boolean hasCard(ActionCard a) {
		return cards.contains(a);
	}

	/**
	 * returns the promised card with index i
	 * @param i
	 */
	public ActionCard getPromise(int i) {
		return promises.getCard(i);
	}

	/**
	 * add a card to the list of promised cards
	 * @param a
	 */
	public void addPromise(ActionCard a) {
		promises.addCard(a);
	}

	/**
	 * remove the given card from the list of promises
	 * @param a
	 */
	public void removePromise(ActionCard a) {
		promises.removeCard(a);
	}
	
	/**
	 * returns the total amount of money the player has on all of his cards
	 */
	public int getAmountOfMoney() {
		int money = 0;
		for (ActionCard c : cards) {
			if (c.isMoneyCard()) money += c.moneyValue();
		}
		return money;
	}
	
	public int getCombatPoints() {
		return combatPoints;
	}

	public void addCombatPoints(int combatPoints) {
		this.combatPoints += combatPoints;
	}

}
