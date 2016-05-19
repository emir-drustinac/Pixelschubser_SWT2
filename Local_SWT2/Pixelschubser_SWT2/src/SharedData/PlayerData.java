package SharedData;

import java.util.*;

public class PlayerData {

	public boolean isGameLeader;
	public String name;
	public String playerID;
	public boolean isProconsul;
	public int numberOfBuildings;
	public int numberOfMercenaries;
	private Collection<ActionCardList> promise;
	private Collection<ActionCardList> cards;

	public int getNumberOfCards() {
		// TODO - implement PlayerData.getNumberOfCards
		throw new UnsupportedOperationException();
	}

	public int getNumberOfPoints() {
		// TODO - implement PlayerData.getNumberOfPoints
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param i
	 */
	public ActionCard getCard(int i) {
		// TODO - implement PlayerData.getCard
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param a
	 */
	public void addCard(ActionCard a) {
		// TODO - implement PlayerData.addCard
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param a
	 */
	public void removeCard(ActionCard a) {
		// TODO - implement PlayerData.removeCard
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param i
	 */
	public ActionCard getPromise(int i) {
		// TODO - implement PlayerData.getPromise
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param a
	 */
	public void addPromise(ActionCard a) {
		// TODO - implement PlayerData.addPromise
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param a
	 */
	public void removePromise(ActionCard a) {
		// TODO - implement PlayerData.removePromise
		throw new UnsupportedOperationException();
	}

}