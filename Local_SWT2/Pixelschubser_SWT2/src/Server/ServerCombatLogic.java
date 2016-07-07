package Server;

import java.util.Hashtable;
import java.util.Vector;

import SharedData.*;

public class ServerCombatLogic {
	private static ServerCombatLogic instance = new ServerCombatLogic();
	private Vector<Combatant> combatantsUnsorted = new Vector<Combatant>();
	private Hashtable<String, Combat> targetID_combat = new Hashtable<String, Combat>(8);
	private Vector<PlayerCommand> commands = new Vector<PlayerCommand>();
	
	public ServerCombatLogic getInstance(){
		return instance;
	}
	
	public void reset(){
		combatantsUnsorted.clear();
		targetID_combat.clear();
		commands.clear();
	}
	
	public void processCommands(){
		for(PlayerCommand c:commands){
			if(c instanceof PlayerCommand_Attack){
				Mercenary2 m = new Mercenary2();
				//TODO: merc init
				combatantsUnsorted.add(m);
				commands.remove(c);
			}else if(c instanceof PlayerCommand_UseCard){
				
			}
		}
	}
	
	public void prepare(){
		processCommands();
		for(Combatant c:combatantsUnsorted){
			String targetID = c.getTarget().playerID;
			Combat combat = targetID_combat.get(targetID);
			if(combat == null){
				combat = new Combat();
				targetID_combat.put(targetID, combat);
				// add defender's buildings
				//TODO
			}
			
			
			
		}
		
	}
}
