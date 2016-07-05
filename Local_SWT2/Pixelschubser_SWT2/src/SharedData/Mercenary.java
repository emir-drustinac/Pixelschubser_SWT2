package SharedData;

import java.io.Serializable;

public class Mercenary implements Serializable {

	private static final long serialVersionUID = 3494795381040939978L;
	private static int nextMercID = 1;
	private String target;
	public final String mercID;
	private boolean defendingProconsul;		// to separate defending merc from offending one on Proconsul
	
	public Mercenary() {
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

}
