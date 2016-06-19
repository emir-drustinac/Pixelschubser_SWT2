package SharedData;

public class PlayerCommand_UseCard extends PlayerCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3699602772666838809L;
	
	private final String playerID;
	private final ActionCard card;
	private final String targetID;
	
	public PlayerCommand_UseCard(PlayerData source, ActionCard card){
		playerID = source.playerID;
		this.card = card;
		targetID = null;
	}
	public PlayerCommand_UseCard(PlayerData source, PlayerData target, ActionCard card){
		playerID = source.playerID;
		this.card = card;
		this.targetID = target.playerID;
	}
	public String getPlayerID() {
		return playerID;
	}
	public ActionCard getCard() {
		return card;
	}
	public String getTargetID() {
		return targetID;
	}
}
