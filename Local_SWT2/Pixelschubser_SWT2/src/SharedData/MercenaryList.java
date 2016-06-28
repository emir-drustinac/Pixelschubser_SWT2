package SharedData;

import java.util.Vector;

public class MercenaryList extends Vector<Mercenary> {
	

	private static final long serialVersionUID = -3648229233202812463L;

	public void addMercenary() {
		add(new Mercenary());
	}

	/**
	 * 
	 * @param mercenary
	 */
	public boolean removeMercenary(Mercenary mercenary) {
		return remove(mercenary);
	}
	
}
