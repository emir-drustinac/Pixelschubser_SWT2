package SharedData;

import java.util.Vector;

public class ActionCardList extends Vector<ActionCard> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8854831794429897717L;

	/**
	 * 
	 * @param c
	 */
	public boolean removeCard(ActionCard c) {
		if (c == null) return false;
		return c.removeFromCardList() | remove(c);
	}

	/**
	 * adds the given card to this ActionCardList
	 * @param c
	 */
	public void addCard(ActionCard c) {
		if (c != null) c.putInCardList(this);
	}

	/**
	 * returns the card with index i or null if there is not card with that index
	 * @param i
	 */
	public ActionCard getCard(int i) {
		return i < 0 || i >= this.size() ? null : this.get(i);
	}

}
