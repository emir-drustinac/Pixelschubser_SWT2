package SharedData;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import SharedData.ActionCard.CardType;

public class Combat implements Serializable {
	
	private static final long serialVersionUID = -4053919102770431105L;
	public int stage = 0;
	public HashMap<String, PlayerData> attackers = new HashMap<>();
	public HashMap<String, PlayerData> defenders = new HashMap<>();
	public HashMap<String, PlayerData> remaining_attackers = new HashMap<>();
	public HashMap<String, PlayerData> remaining_defenders = new HashMap<>();
	public final PlayerData defender;
	public HashMap<String, Mercenary> att_mercs = new HashMap<>();
	public HashMap<String, Mercenary> def_mercs = new HashMap<>();
	private Random rnd;
	
	// activeCards
	public boolean SLAVEREVOLT = false; // Alle verteidigenden Söldner würfeln eine 2
	public boolean BRIBE = false; // Ignoriere die Söldner des Proconsuls
	public boolean SURPRISEATTACK = false; // Ignoriere in diesem Kampf alle Gebäude des Verteidigers
	
	public Combat(PlayerData owner) {
		defender = owner;
		defenders.put(owner.playerID, owner);
		remaining_defenders.put(owner.playerID, owner);
	}
	
	public void addAttackingMercenary(Mercenary m) {
		if (stage != 0) return;
		if (!att_mercs.containsKey(m.mercID) && !def_mercs.containsKey(m.mercID)) {
			att_mercs.put(m.mercID, m);
			if (!attackers.containsKey(m.playerID)) attackers.put(m.playerID, m.owner);
			if (!remaining_attackers.containsKey(m.playerID)) remaining_attackers.put(m.playerID, m.owner);
		}
	}
	
	public void addDefendingMercenary(Mercenary m) {
		if (stage != 0) return;
		if (!att_mercs.containsKey(m.mercID) && !def_mercs.containsKey(m.mercID)) {
			def_mercs.put(m.mercID, m);
			if (!defenders.containsKey(m.playerID)) defenders.put(m.playerID, m.owner);
			if (!remaining_defenders.containsKey(m.playerID)) remaining_defenders.put(m.playerID, m.owner);
		}
	}
	
	public boolean destroyMercenary(Mercenary m) {
		if (removeMercenary(m)) {
			m.destroy();
			return true;
		}
		return false;
	}
	
	public boolean removeMercenary(Mercenary m) {
		if (m == null) return false;
		boolean retValue = false;
		if (att_mercs.containsKey(m.mercID)) {
			retValue = true;
			att_mercs.remove(m.mercID);
			boolean noMercsLeft = true;
			for (Mercenary merc : att_mercs.values()) {
				if (merc.playerID.equals(m.playerID)) {
					noMercsLeft = false;
				}
			}
			if (noMercsLeft) {
				remaining_attackers.remove(m.playerID);
			}
		}
		if (def_mercs.containsKey(m.mercID)) {
			retValue = true;
			def_mercs.remove(m.mercID);
			boolean noMercsLeft = true;
			for (Mercenary merc : def_mercs.values()) {
				if (merc.playerID.equals(m.playerID)) {
					noMercsLeft = false;
				}
			}
			if (noMercsLeft && !defender.playerID.equals(m.playerID)) {
				remaining_defenders.remove(m.playerID);
			}
		}
		return retValue;
	}
	
	public int getAttackValue() {
		int val = 0;
		for (Mercenary m : att_mercs.values()) {
			val += m.dice;
		}
		for (Card c : attackersCards) {
			if (c.type == CardType.CATAPULT) val += 3;
		}
		return val;
	}
	
	public int getDefendValue() {
		int val = 0;
		for (Mercenary m : def_mercs.values()) {
			if (!BRIBE || !defender.isProconsul || !defender.playerID.equals(m.playerID))
				val += m.dice;
		}
		// ignoriere die Gebäude des Verteidigers
		if (!SURPRISEATTACK) val += defender.numberOfBuildings;
		for (Card c : defendersCards) {
			if (c.type == CardType.CATAPULT) val += 3;
		}
		return val;
	}
	
	public void rollDices() {
		for (Mercenary m : att_mercs.values()) {
			m.dice = rollDice();
		}
		for (Mercenary m : def_mercs.values()) {
			if (SLAVEREVOLT) {
				m.dice = 2;
			} else {
				if (defender.isProconsul && m.playerID.equals(defender.playerID)) {
					m.dice = 1;
				} else {
					m.dice = rollDice();
				}
			}
		}
	}
	
	private int rollDice() {
		if (rnd == null) rnd = new Random();
		return rnd.nextInt(6) + 1;
	}

	public final Vector<PlayerData> getLooters() {
		// sortiere überbleibende Angreifer nach ihrem Kampfwert
		Vector<PlayerData> looters = new Vector<>();
		for (PlayerData p : remaining_attackers.values()) {
			looters.add(p);
		}
		HashMap<String, Integer> fightvalues = new HashMap<>();
		for (Mercenary m : att_mercs.values()) {
			if (!fightvalues.containsKey(m.playerID)) fightvalues.put(m.playerID, new Integer(0));
			Integer f = fightvalues.get(m.playerID) + m.dice;
			fightvalues.put(m.playerID, f);
		}
		looters.sort(new Comparator<PlayerData>() {

			@Override
			public int compare(PlayerData p1, PlayerData p2) {
				int k1 = fightvalues.get(p1.playerID);
				int k2 = fightvalues.get(p1.playerID);
				// bei gleichen Werten zufällige Sortierung (beide werfen Würfel)
				while (k1 == k2) {
					k1 = rollDice();
					k2 = rollDice();
				}
				if (k1 < k2) return -1;
				if (k1 > k2) return 1;
				return 0;
			}
		});
		return looters;
	}
	
	public final boolean isProconsulFight() {
		return defender.isProconsul;
	}
	
	public final boolean attackersWon() {
		return getDefendValue() < getAttackValue();
	}

	public void printCombat() {
		System.out.println("# Combat on ground of " + defender.name + " in stage " + stage);
		System.out.print("# attackers:");
		for (PlayerData p : attackers.values()) System.out.print(" '" + p.name + "'");
		System.out.print("\n# defenders:");
		for (PlayerData p : defenders.values()) System.out.print(" '" + p.name + "'");
		System.out.print("\n# remaining_attackers:");
		for (PlayerData p : remaining_attackers.values()) System.out.print(" '" + p.name + "'");
		System.out.print("\n# remaining_defenders:");
		for (PlayerData p : remaining_defenders.values()) System.out.print(" '" + p.name + "'");
		System.out.println("\n# attacking mercenaries:");
		for (Mercenary m : att_mercs.values()) System.out.println("# \t" + m.mercID + " owner:'" + m.owner.name + "' target:" + m.getTarget() + " dice:" + m.dice);
		System.out.println("# defending mercenaries:");
		for (Mercenary m : def_mercs.values()) System.out.println("# \t" + m.mercID + " owner:'" + m.owner.name + "' target:" + m.getTarget() + " dice:" + m.dice);
		System.out.println("# def value: " + getDefendValue());
		System.out.println("# att value: " + getAttackValue());
		System.out.println("# attackersCards:");
		for (Card c : attackersCards) System.out.println("# \towner:" + c.owner + " type:" + c.type);
		System.out.println("# defendersCards:");
		for (Card c : defendersCards) System.out.println("# \towner:" + c.owner + " type:" + c.type);
		System.out.println("# lootedCards:");
		for (Card c : lootedCards) System.out.println("# \towner:" + c.owner + " type:" + c.type);
		Vector<PlayerData> looters = getLooters();
		System.out.print("# possible loot order:");
		for (PlayerData p : looters) System.out.print(" '" + p.name + "'");
		System.out.println();
	}

	public void nextStage() {
		stage++;
		if (stage == 1) {
			// fight started
			// reset all dices to 0 (undice em ;)
			for (Mercenary m : att_mercs.values()) {
				m.dice = 0;
			}
			for (Mercenary m : def_mercs.values()) {
				m.dice = 0;
			}
			// defenders may use their cards
		}
		if (stage == 2) {
			// attackers may use their cards
		}
		if (stage == 3) {
			// rolling dices
			rollDices();
		}
		if (stage == 4) {
			// fight ended
			// loot or provide promises
		}
	}
	
	@SuppressWarnings("serial")
	public final class Card implements Serializable {
		public final String owner;
		public final CardType type;
		public Card(String owner, CardType type) {
			this.owner = owner;
			this.type = type;
		}
	}
	public final Vector<Card> attackersCards = new Vector<>();
	public final Vector<Card> defendersCards = new Vector<>();
	public boolean addCardUsed(ActionCard a, String owner) {
		if (remaining_attackers.containsKey(owner)) {
			attackersCards.add(new Card(owner, a.getType()));
			return true;
		}
		if (remaining_defenders.containsKey(owner)) {
			defendersCards.add(new Card(owner, a.getType()));
			return true;
		}
		return false;
	}
	public final Vector<Card> lootedCards = new Vector<>();
	public void addLootedCard(ActionCard a, String owner) {
			lootedCards.add(new Card(owner, a.getType()));
	}
}
