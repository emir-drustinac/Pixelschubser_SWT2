package SharedData;

import java.io.Serializable;
import java.util.*;

public class GameData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4237062929837830623L;
	public int round = 0;
	public PhaseType phase = PhaseType.JoinGame;
	public final PlayerList players;
	private final ActionCardList deck;
	private final ActionCardList discardPile;
	public final ActionCardList activeCardList;
	public TopPlayer[] topPlayerList;
	public Combat combat;
	
	public GameData() {
		players = new PlayerList();
		deck = new ActionCardList();
		discardPile = new ActionCardList();
		activeCardList = new ActionCardList();
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
			for (int i = numCards; i > 0; i--) {
				int r = rnd.nextInt(i);
				deck.addCard( discardPile.remove(r) );
			} 
		}
		System.out.println("~~~~~Deck size: " + deck.size());
		System.out.println("~~~~~Discard Pile size: " + discardPile.size());
		// return first card on deck and remove it from deck 
		if (deck.size() == 0) System.out.println("Can't draw card from empty deck!");
		return deck.size() == 0 ? null : deck.remove(0);
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
	
	public boolean isValidMove(PlayerCommand c){
		if(c instanceof PlayerCommand_UseCard){
			PlayerData p = getPlayer(((PlayerCommand_UseCard) c).getPlayerID());
			ActionCard card = ((PlayerCommand_UseCard) c).getCard();
			PlayerData target = ((PlayerCommand_UseCard) c).getTargetID()==null ? null :getPlayer(((PlayerCommand_UseCard) c).getTargetID()); 
			if(!p.hasCard(card)) return false;
			if(!card.isValidPhase(phase)) return false;
			return card.isValidTarget(p, target);
		}
		return false;
	}

	public int getDeckSize() {
		return deck.size();
	}

	public int getDiscardPileSize() {
		return discardPile.size();
	}
	
	public PhaseType getPhase() {
		return phase;
	}

	public void printDeck() {
		System.out.println("Deck contains " + deck.size() + " cards:");
		for (ActionCard a : deck) {
			System.out.print(a.getType() + " ");
		}
		System.out.println("");
	}

	public boolean allPlayersAreReady() {
		for (PlayerData p : players) {
			if (!p.isReady) return false;
		}
		return true;
	}

	public void setAllPlayersReady(boolean ready) {
		for (PlayerData p : players) {
			p.isReady = ready;
		}
	}

	public int numberOfPlayersWithPromisedCards() {
		int n = 0;
		for (PlayerData p : players) {
			if (p.getNumberOfPromisedCards() > 0) n++;
		}
		return n;
	}

	public boolean allPlayersHaveBeenPromised() {
		return numberOfPlayersWithPromisedCards() == players.size() - 1;
	}

	public String getProconsulID() {
		for (PlayerData p : players) {
			if (p.isProconsul) return p.playerID;
		}
		return null;
	}

}
