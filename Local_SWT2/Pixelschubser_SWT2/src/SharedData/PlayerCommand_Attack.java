package SharedData;

public class PlayerCommand_Attack extends PlayerCommand {

	/**
	 * 
	 */
	private final String playerID;
	private final String targetID;
	
	public PlayerCommand_Attack(PlayerData source, PlayerData target){
		playerID = source.playerID;
		this.targetID = target.playerID;
	}
	public String getPlayerID() {
		return playerID;
	}
	public String getTargetID() {
		return targetID;
	}
}
