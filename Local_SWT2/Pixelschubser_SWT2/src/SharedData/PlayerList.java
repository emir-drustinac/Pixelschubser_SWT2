package SharedData;

import java.util.Vector;

public class PlayerList extends Vector<PlayerData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7608829705336577845L;

	/**
	 * add a new player to this PlayerList
	 * @param playerID
	 * @param name
	 */
	public void addPlayer(String playerID, String name) {
		PlayerData p = new PlayerData(playerID, name);
		this.add(p);
	}

	/**
	 * removes player with playerID from this PlayerList
	 * @param playerID
	 */
	public boolean removePlayer(String playerID) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).playerID.equals(playerID)) {
				this.remove(i);
				return true;
			}
		}
		return false;
	}

	public PlayerData get(String clientID) {
		for (PlayerData p : this) {
			if (p.playerID.equals(clientID)) {
				return p;
			}
		}
		return null;
	}

}
