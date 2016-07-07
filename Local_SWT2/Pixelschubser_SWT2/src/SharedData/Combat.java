package SharedData;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

public class Combat {
	private Collection<Combatant> combatants = new Vector<Combatant>();
	private Hashtable<String, Integer> playerID_contributions = new Hashtable<String, Integer>(8);
	private boolean resolved = false;
	private int attackTotal = 0;
	private int defenseTotal = 0;
	
	private void resolve(){
		if (resolved) return;
		for(Combatant c: combatants){
			if(c.isDefender()){
				defenseTotal += c.getCombatValue();
			}else{
				String k = c.getOwner().playerID;
				int v = (playerID_contributions.get(k)!=null)?playerID_contributions.get(k):0;
				playerID_contributions.put(c.getOwner().playerID, v + c.getCombatValue());
				attackTotal += c.getCombatValue();
			}
		}
		resolved = true;
	}
	
	
	public boolean attackerWins(){
		if(!resolved) resolve();
		return attackTotal > defenseTotal;
	}
	
	public boolean isResolved(){
		return resolved;
	}
	
	public void addCombatant (Combatant c){
		if(resolved) throw new RuntimeException("Invalid game state: Combat already resolved.");
		combatants.add(c);
	}
	public void removeCombatant (Combatant c){
		if(resolved) throw new RuntimeException("Invalid game state: Combat already resolved.");
		combatants.remove(c);
	}
	
}
