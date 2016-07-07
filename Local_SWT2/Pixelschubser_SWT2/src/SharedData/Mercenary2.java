package SharedData;

import java.util.concurrent.ThreadLocalRandom;

public class Mercenary2 extends Combatant{
	private int combatValue = 0;
	private boolean combatValueReset = true;
	
	public Mercenary2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCombatValue() {
		if(getOwner().isProconsul) return 1;
		if(combatValueReset){
			combatValue = 1 + ThreadLocalRandom.current().nextInt(6);
			combatValueReset = false;
		}
		return combatValue;
	}

	@Override
	public void setTarget(PlayerData target) {
		combatValueReset = true;
		super.setTarget(target);
	}
	
	public void reset(){
		combatValueReset = true;
	}

}
