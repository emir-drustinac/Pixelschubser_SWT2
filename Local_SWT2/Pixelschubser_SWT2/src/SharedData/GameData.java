package SharedData;

import java.io.Serializable;
import java.util.*;

public class GameData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4237062929837830623L;
	public int phase;
	public Collection<PlayerList> players;
	private Collection<ActionCardList> deck;
	private Collection<ActionCardList> discardPile;

	/**
	 * 
	 * @param a
	 */
	public void discardCard(ActionCard a) {
		// TODO - implement GameData.discardCard
		throw new UnsupportedOperationException();
	}

	public ActionCard takeCard() {
		// TODO - implement GameData.takeCard
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param name
	 */
	public void addPlayer(String name) {
		// TODO - implement GameData.addPlayer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param playerID
	 */
	public void removePlayer(String playerID) {
		// TODO - implement GameData.removePlayer
		throw new UnsupportedOperationException();
	}

	public String getWinnerID() {
		// TODO - implement GameData.getWinnerID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param PlayerID
	 */
	public void makeProconsul(String PlayerID) {
		// TODO - implement GameData.makeProconsul
		throw new UnsupportedOperationException();
	}

}