package SharedData;

import java.io.Serializable;

public class Mercenary implements Serializable {

	private static final long serialVersionUID = 3494795381040939978L;
	public final String playerID;
	public final PlayerData owner;
	private static int nextMercID = 1;
	private String target;
	public final String mercID;
	private boolean defendingProconsul;		// to separate defending merc from offending one on Proconsul
	
	public Mercenary(PlayerData player) {
		this.playerID = player.playerID;
		this.owner = player;
		target = "";
		mercID = "merc" + nextMercID++;
		defendingProconsul = false;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isDefendingProconsul() {
		return defendingProconsul;
	}

	public void setDefendingProconsul(boolean defendingProconsul) {
		this.defendingProconsul = defendingProconsul;
	}

	static int getNextMercID() {
		return nextMercID;
	}

	static void setNextMercID(int nextMercID) {
		Mercenary.nextMercID = nextMercID;
	}

	public void destroy() {
		owner.mercenaries.removeMercenary(this);
	}

}
