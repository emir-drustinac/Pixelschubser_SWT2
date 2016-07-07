package SharedData;

public class Bulding extends Combatant {

	@Override
	public int getCombatValue() {
		return 1;
	}
	@Override
	public boolean isDefender(){
		return true;
	}
	@Override
	public PlayerData getTarget(){
		return getOwner();
	}

}
