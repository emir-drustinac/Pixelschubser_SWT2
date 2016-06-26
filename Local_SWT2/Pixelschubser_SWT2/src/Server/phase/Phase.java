package Server.phase;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.GameData;
import SharedData.PhaseType;
import SharedData.PlayerData;

public abstract class Phase {
	
	protected ServerGameLogic logic;
	protected ServerCommunicator com;
	
	public Phase(ServerGameLogic logic, ServerCommunicator com) {
		this.logic = logic;
		this.com = com;
	}
	
	protected void sendMessageToClient(String clientID, String message) {
		com.sendMessageToClient(clientID, message);
	}
	
	protected void sendMessageToAllClients(String message) {
		com.sendMessageToAllClients(message);
	}
	
	protected void sendGameDataToClient(String clientID) {
		//PlayerData p = logic.getGameData().players.get(clientID);
		com.sendGameDataToAllClients(logic.getGameData());
	}
	
	protected void sendGameDataToAllClients() {
		com.sendGameDataToAllClients(logic.getGameData());
	}
	
//	@SuppressWarnings("unused")
//	protected void sendPlayerDataToClient(String clientID) {
//		PlayerData p = logic.getGameData().players.get(clientID);
//		com.sendPlayerDataToClient(clientID, p);
//	}
//	
//	@SuppressWarnings("unused")
//	protected void sendPlayerDataToAllClients() {
//		for (PlayerData p : logic.getGameData().players) {
//			com.sendPlayerDataToClient(p.playerID, p);
//		}
//	}
	
	/**
	 * Phase received a Message from client
	 * @param clientID
	 * @param message
	 */
	public abstract void ReceivedMessageFromClient(String clientID, String message);
	/**
	 * Phase received a GameData object from client
	 * @param clientID
	 * @param g
	 */
	public abstract void ReceivedGameStateFromClient(String clientID, GameData g);
	/**
	 * Phase received a PlayerData object from Client
	 * @param clientID
	 * @param p
	 */
	public abstract void ReceivedPlayerDataFromClient(String clientID, PlayerData p);
	/**
	 * sets the next phase of game
	 * @return
	 */
	public abstract PhaseType getNextPhaseType();
		/**
	 * creates and returns an object of the next Phase dependent of current GameState
	 * @return
	 */
	public Phase getNextPhase() {
		logic.getGameData().phase = getNextPhaseType();
		return logic.getPhaseForCurrentGameState();
	}
}
