package Server;

import java.util.Random;

import javax.swing.JOptionPane;

import Client.gui.MenuWindow;
import Client.gui.MenuWindowLogic;
import Server.phase.*;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.GameData;
import SharedData.GameRules;
import SharedData.PhaseType;
import SharedData.PlayerList;

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
		/*
		// first player
		game.addPlayer("a1000000-0000-0000-000000000000", "Alpha");
		PlayerData p = game.players.lastElement();
		p.isGameLeader = true;
		p.numberOfBuildings = 1;
		p.numberOfMercenaries = 1;
	    
		p.isReady = true;
		// second player
		game.addPlayer("b2000000-0000-0000-000000000000", "Beta");
		game.makeProconsul("b2000000-0000-0000-000000000000");
		p = game.players.lastElement();
		p.numberOfBuildings = 1;
		p.numberOfMercenaries = 1;
		p.isReady = true;
		*/
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
		if(game.players.size() >= 5) {
			String msg = "Maximale Anzahl an Spielern(5) sind schon im Spiel! Bitte einem anderen Spiel beitreten.";
			JOptionPane.showMessageDialog(new MenuWindow(new MenuWindowLogic()), msg, "Beitreten nicht möglich", JOptionPane.WARNING_MESSAGE);
			return false;
		} else {
			game.addPlayer(playerID, name);
			com.sendGameDataToAllClients(game);
			return true;
		}
	}

	public boolean removePlayer(String playerID) {
		boolean b = game.removePlayer(playerID); 
		if (b) {
			com.sendGameDataToAllClients(game);
		}
		return b;
	}

	public boolean startGame() {
		if (game.phase == PhaseType.JoinGame) {
			// to start game we need:
			
			// all cards
			// TODO create all cards and put em on to discard pile
			createDeck();
			/*for (int i = 0; i < 3; i++) {
				ActionCard a = new ActionCard();
				game.discardCard(a);
			}*/
			//on first call to game.takeCard() all cards are mixed and put into deck
			
			// a random proconsul
			game.makeProconsul( game.players.get( (new Random()).nextInt(game.players.size()) ).playerID );
			
			return true;
		}
		return false;
	}
	
	/**
	 * creates new Deck with 45 randomly distributed cards
	 * @author Emir
	 * 
	 */
	public void createDeck() {
		
		createCardsInDeck(CardType.DENARI1000, 		12	); 
		createCardsInDeck(CardType.DENARI2000, 		11	);
		createCardsInDeck(CardType.DENARI3000, 		3	);
		createCardsInDeck(CardType.LION, 			1	);
		createCardsInDeck(CardType.JUGGLER, 		1	);
		createCardsInDeck(CardType.PICKLOCK, 		1	);
		createCardsInDeck(CardType.SPY, 			1	);
		createCardsInDeck(CardType.BRIBE, 			1	);
		createCardsInDeck(CardType.SURPRISEATTACK, 	1	);
		createCardsInDeck(CardType.PROPAGANDA, 		1	);
		createCardsInDeck(CardType.FREEBUILDING, 	1	);
		createCardsInDeck(CardType.ANNEXATION, 		1	);
		createCardsInDeck(CardType.GOLDENCHARIOT, 	1	);
		createCardsInDeck(CardType.GOLDENLION, 		1	);
		createCardsInDeck(CardType.ASSASSINATION, 	2	);
		createCardsInDeck(CardType.CATAPULT, 		2	);
		createCardsInDeck(CardType.SLAVEREVOLT, 	2	);
		createCardsInDeck(CardType.ABUSEOFPOWER, 	2	);
										
		//System.out.println("Deck size: " + game.getDeckSize());
	}
	private void createCardsInDeck(CardType type, int n) {
		Random rnd = new Random();
		for (int i = 0; i < n; i++) {
			ActionCard a = new ActionCard(type);
			int r = rnd.nextInt(game.getDeckSize() + 1);
			game.addCard2DeckAtIndex(a, r);
		}
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
		case DeclareWinner: return new Phase_DeclareWinner(this, com);
		case CardLimit: return new Phase_CardLimit(this, com);
		case GameOver: return new Phase_GameOver(this, com);
		default: return new Phase_DrawCards(this, com);
		}
	}
	
	public PlayerList getWinners() {  
		PlayerList w = (PlayerList)(game.players.clone());
		w.removeIf( p->(p.getNumberOfVictoryPoints() < GameRules.VICTORY_POINTS_NEEDED) );
		return w;
	}

	/**
	 * called on every start of new round
	 */
	public void nextRound() {
		// prepare next round
		game.round++;
	}

}
