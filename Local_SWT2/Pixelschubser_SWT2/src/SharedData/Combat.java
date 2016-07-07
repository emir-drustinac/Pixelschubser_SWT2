package SharedData;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class Combat {
	
	private class Merc {
		private String owner;
		private int dice = 0;
		private Merc(String playerID) {owner = playerID;}
	}
	
	public int stage = 0;
	public HashMap<String, PlayerData> attackers = new HashMap<>();
	public HashMap<String, PlayerData> defenders = new HashMap<>();
	public HashMap<String, PlayerData> remaining_attackers = new HashMap<>();
	public HashMap<String, PlayerData> remaining_defenders = new HashMap<>();
	public final PlayerData defender;
	public HashMap<String, Merc> att_mercs = new HashMap<>();
	public HashMap<String, Merc> def_mercs = new HashMap<>();
	private Random rnd;
	
	// activeCards
	public boolean SLAVEREVOLT = false; // Alle verteidigenden S�ldner w�rfeln eine 2
	public boolean BRIBE = false; // Ignoriere die S�ldner des Proconsuls
	public boolean SURPRISEATTACK = false; // Ignoriere in diesem Kampf alle Geb�ude des Verteidigers
	
	public Combat(PlayerData owner) {
		defender = owner;
	}
	
	public void addAttackingMercenary(Mercenary m) {
		if (stage != 0) return;
		if (!att_mercs.containsKey(m.mercID) && !def_mercs.containsKey(m.mercID)) {
			att_mercs.put(m.mercID, new Merc(m.playerID));
			if (!attackers.containsKey(m.playerID)) attackers.put(m.playerID, m.owner);
		}
	}
	
	public void addDefendingMercenary(Mercenary m) {
		if (stage != 0) return;
		if (!att_mercs.containsKey(m.mercID) && !def_mercs.containsKey(m.mercID)) {
			def_mercs.put(m.mercID, new Merc(m.playerID));
			if (!defenders.containsKey(m.playerID)) defenders.put(m.playerID, m.owner);
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
		boolean retValue = false;
		if (att_mercs.containsKey(m.mercID)) {
			retValue = true;
			att_mercs.remove(m.mercID);
			boolean noMercsLeft = true;
			for (Merc merc : att_mercs.values()) {
				if (merc.owner.equals(m.playerID)) {
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
			for (Merc merc : def_mercs.values()) {
				if (merc.owner.equals(m.playerID)) {
					noMercsLeft = false;
				}
			}
			if (noMercsLeft) {
				remaining_defenders.remove(m.playerID);
			}
		}
		return retValue;
	}
	
	public int getAttackValue() {
		int val = 0;
		for (Merc m : att_mercs.values()) {
			val += m.dice;
		}
		return val;
	}
	
	public int getDefendValue() {
		int val = 0;
		for (Merc m : def_mercs.values()) {
			if (!BRIBE || !defender.isProconsul || !defender.playerID.equals(m.owner))
				val += m.dice;
		}
		// ignoriere die Geb�ude des Verteidigers
		if (!SURPRISEATTACK) val += defender.numberOfBuildings;
		return val;
	}
	
	public void rollDices() {
		for (Merc m : att_mercs.values()) {
			m.dice = rollDice();
		}
		for (Merc m : def_mercs.values()) {
			if (SLAVEREVOLT) {
				m.dice = 2;
			} else {
				if (defender.isProconsul && m.owner.equals(defender.playerID)) {
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
		// sortiere �berbleibende Angreifer nach ihrem Kampfwert
		Vector<PlayerData> looters = new Vector<>();
		for (PlayerData p : remaining_attackers.values()) {
			looters.add(p);
		}
		HashMap<String, Integer> fightvalues = new HashMap<>();
		for (Merc m : att_mercs.values()) {
			if (!fightvalues.containsKey(m.owner)) fightvalues.put(m.owner, new Integer(0));
			Integer f = fightvalues.get(m.owner) + m.dice;
			fightvalues.put(m.owner, f);
		}
		looters.sort(new Comparator<PlayerData>() {

			@Override
			public int compare(PlayerData p1, PlayerData p2) {
				int k1 = fightvalues.get(p1.playerID);
				int k2 = fightvalues.get(p1.playerID);
				// bei gleichen Werten zuf�llige Sortierung (beide werfen W�rfel)
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
}
