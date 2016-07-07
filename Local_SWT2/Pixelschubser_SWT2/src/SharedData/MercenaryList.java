package SharedData;

import java.util.Vector;

public class MercenaryList extends Vector<Mercenary> {
	

	private static final long serialVersionUID = -3648229233202812463L;
	private PlayerData owner;
	
	public MercenaryList(PlayerData player) {
		this.owner = player;
	}
	
	public void addMercenary() {
		add(new Mercenary(owner));
	}

	/**
	 * 
	 * @param mercenary
	 */
	public boolean removeMercenary(Mercenary mercenary) {
		return remove(mercenary);
	}
	
}
