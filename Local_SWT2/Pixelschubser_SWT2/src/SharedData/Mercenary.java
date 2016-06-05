package SharedData;

public class Mercenary {

	private String target;
	private boolean defendingProconsul;		// to separate defending merc from offending one on Proconsul
	
	public Mercenary() {
		// TODO Auto-generated constructor stub
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

}
