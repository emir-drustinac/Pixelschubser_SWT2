package SharedData;

public class PlayerData {

	public boolean isGameLeader;
	public String name;
	public final String playerID;
	public boolean isProconsul;
	public int numberOfBuildings;
	public int numberOfMercenaries;
	private ActionCardList promises;
	private ActionCardList cards;

	public PlayerData(String playerID, String name) {
		this.name = name;
		this.playerID = playerID;
		promises = new ActionCardList();
		cards = new ActionCardList();
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
	public int getNumberOfPoints() {
		// return number of buildings
		// plus points on extra point cards
		int points = 0;
		for (ActionCard c : cards) {
			if (c.addsPoint()) points++;
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

}