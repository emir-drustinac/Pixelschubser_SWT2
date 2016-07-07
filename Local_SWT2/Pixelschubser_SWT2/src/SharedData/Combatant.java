package SharedData;

public abstract class Combatant {
	
	private PlayerData owner;
	private PlayerData target;
	protected boolean defendingProconsul = false;
	public abstract int getCombatValue();
	
	public PlayerData getOwner() {
		return owner;
	}
	public void setOwner(PlayerData owner) {
		this.owner = owner;
	}
	public PlayerData getTarget() {
		return target;
	}
	public void setTarget(PlayerData target) {
		this.target = target;
	}
	
	public boolean isDefender(){
		return owner.equals(target)||isDefendingProconsul();
	}

	public boolean isDefendingProconsul() {
		return defendingProconsul;
	}

	public void setDefendingProconsul(boolean defendingProconsul) {
		this.defendingProconsul = defendingProconsul;
	}
	
}
