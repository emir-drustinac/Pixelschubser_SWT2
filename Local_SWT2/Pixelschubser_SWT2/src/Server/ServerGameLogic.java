package Server;

import java.util.Random;

import SharedData.*;

public class ServerGameLogic {

	private GameData game;
	private ServerCommunicator com;
	private Phase currentPhase;
	
	public ServerGameLogic(ServerCommunicator com) {
		this.com = com;
		com.setGameLogic(this);
	}

	public void nextPhase() {
		currentPhase = currentPhase.getNextPhase();
		com.sendGameDataToAllClients(game);
	}

	public void initNewGame() {
		game = new GameData();
		currentPhase = new Phase_JoinGame(this, com);
		// TODO start with empty player list as every player will send "joinGame" message
		// first player
		game.addPlayer("a1000000-0000-0000-000000000000", "Alpha");
		PlayerData p = game.players.lastElement();
		p.isGameLeader = true;
		p.numberOfBuildings = 3;
		p.numberOfMercenaries = 3;
		p.isReady = true;
		// second player
		game.addPlayer("b2000000-0000-0000-000000000000", "Beta");
		game.makeProconsul("b2000000-0000-0000-000000000000");
		p = game.players.lastElement();
		p.numberOfBuildings = 1;
		p.numberOfMercenaries = 4;
		p.isReady = true;
	}

	/**
	 * 
	 * @param PID
	 * @param m
	 */
	public void receivedMessage(String PID, String m) {
		currentPhase.ReceivedMessageFromClient(PID, m);
		System.out.println("Message from " + PID + " : " + m);
	}

	public void receivedGameData(String clientID, GameData g) {
		currentPhase.ReceivedGameStateFromClient(clientID, g);
		System.out.println("GameData from " + clientID);
	}

	public boolean addPlayer(String playerID, String name) {
		game.addPlayer(playerID, name);
		com.sendGameDataToAllClients(game);
		return true;
	}

	public boolean removePlayer(String playerID) {
		boolean b = game.removePlayer(playerID); 
		if (b) {
			com.sendGameDataToAllClients(game);
		}
		return b;
	}

	public boolean startGame() {
		if (game.phase == null) {
			// to start game we need:
			
			// all cards
			// TODO create all cards and put em on to discard pile
			for (int i = 0; i < 3; i++) {
				ActionCard a = new ActionCard();
				game.discardCard(a);
			}
			//on first call to game.takeCard() all cards are mixed and put into deck
			
			// a random proconsul
			game.makeProconsul( game.players.get( (new Random()).nextInt(game.players.size()) ).playerID );
			
			return true;
		}
		return false;
	}

	// cards are mixed on takeCard when deck is empty
	//public void mixCards() {
	//	throw new UnsupportedOperationException();
	//}

	public GameData getGameData() {
		return game;
	}
	
	/**
	 * returns the Phase class instance for the current game phase
	 * @return
	 */
	public Phase getPhaseForCurrentGameState() {
		switch (game.phase) {
		case JoinGame: return new Phase_JoinGame(this, com);
		case DrawCards: return new Phase_DrawCards(this, com);
		case MakePromises: return new Phase_MakePromises(this, com);
		case CommandMercenaries: return new Phase_CommandMercenaries(this, com);
		case Combat: return new Phase_Combat(this, com);
		case SpendMoney: return new Phase_SpendMoney(this, com);
		case CardLimit: return new Phase_CardLimit(this, com);
		default: return new Phase_DrawCards(this, com);
		}
	}

}
