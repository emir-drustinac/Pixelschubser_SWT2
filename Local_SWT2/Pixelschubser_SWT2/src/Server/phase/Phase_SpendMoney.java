package Server.phase;

import Client.Client;
import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.ActionCardList;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PhaseType;
import SharedData.PlayerData;
import SharedData.PlayerList;

public class Phase_SpendMoney extends Phase {
	private int nrOfReadyPlayers = 0;
	private int mercsCount, buildingsCount, cardsCount;
	private String cards = "";
	private ActionCardList discardedCards;
	private PlayerData pd;
	private PlayerList waitingPlayers;
	
	public Phase_SpendMoney(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		discardedCards = new ActionCardList();
		waitingPlayers = logic.getGameData().players;
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		pd = logic.getGameData().players.get(clientID);
		String msgToSend = "";
		
		if(message.contains("waiting_for_proconsul")) {
			msgToSend = "Warten bis der Proconsul mit dem Kaufen fertig ist...";
			com.sendMessageToClient(clientID, msgToSend);
		}
		if(message.contains("can_i_play_now")) {
			if(clientID.equals(waitingPlayers.get(0))) {
				msgToSend = "Sie sind jetzt dran";
			} else {
				msgToSend = "Warten bis der Spieler " + waitingPlayers.get(0) + " mit dem Kaufen fertig ist...";
			}
			com.sendMessageToClient(clientID, msgToSend);
		}
		if(message.contains("phase_infos")) {
			msgToSend = "linker Mouseclick auf ein Item = Anzahl erhöhen, rechter Mouseclick = Anzahl verringern.";
			com.sendMessageToClient(clientID, msgToSend);
		}
		if (message.contains("buy:not_enough_money")) {
			com.sendMessageToClient(clientID, "Nicht genug Geld!");
////			************
//			pd.mercenaries.add(new Mercenary());
//			pd.addCard(new ActionCard(CardType.ABUSEOFPOWER));
//			pd.addCard(new ActionCard(CardType.ABUSEOFPOWER));
//			pd.addCard(new ActionCard(CardType.FREEBUILDING));
//			pd.addCard(new ActionCard(CardType.PROPAGANDA));
//			pd.addCard(new ActionCard(CardType.DENARI3000));
//			pd.addCard(new ActionCard(CardType.DENARI2000));
//			pd.addCard(new ActionCard(CardType.DENARI1000));
//			
//			com.sendPlayerDataToClient(clientID, pd);
////			************
		}
		if (message.contains("buy:nothing")) {
			com.sendMessageToClient(clientID, "Nichts zum Kaufen ausgewählt!");
		}
		if (message.contains("buy:card_limit")) {
			com.sendMessageToClient(clientID, "Sie können maximal 1 Karte in dieser Runde kaufen!");
		}
		if (message.contains("buy:mercs_limit")) {
			com.sendMessageToClient(clientID, "Sie können maximal 4 Söldner haben!");
		}
		if(message.contains("selectedCard:")) {
			String[] card = message.split(":", 2);
			ActionCard ac = new ActionCard(pd.getCardByID(card[1]).getType());
			discardedCards.add(ac);
			System.out.println("##### Discarded cards size: " + discardedCards.size());
			cards += card[1] + ",";
			System.out.println("cards: " + cards);
//			pd.removeCard(ac);
//			com.sendGameDataToAllClients(logic.getGameData());
		}
		if(message.contains("unselectedCard:")) {
			String[] card = message.split(":", 2);
			ActionCard ac = new ActionCard(pd.getCardByID(card[1]).getType());
			discardedCards.removeCard(ac);
			cards = cards.replace(card[1] + ",", "");
			System.out.println("cards: " + cards);
			
		}
		if (message.contains("kaufen:")) {
			int cardsCnt = pd.getNumberOfCards()-1;
			System.out.print("Handkarten VOR dem Kauf: "); 
			while(cardsCnt-- > 0) {
				System.out.print(pd.getCard(cardsCnt).getType() + ", " + (cardsCnt == 0 ? "\n" : ""));
			}
			
			//remove cards after buying items
			for(int i = 0; i < discardedCards.size(); i++) {
				pd.removeCard(discardedCards.getCard(0));
			}
			discardedCards.removeAllElements();
			System.out.println("##### Discarded cards size: " + discardedCards.size());
			com.sendGameDataToAllClients(logic.getGameData());
			
			//add buyed items to this player game data
			String[] addItems = message.split(":", 5);
			mercsCount = Integer.parseInt(addItems[2]);
			while(mercsCount-- > 0) {
				pd.mercenaries.add(new Mercenary(pd));
			}
			buildingsCount = Integer.parseInt(addItems[3]);
			if(buildingsCount > 0) {
				pd.numberOfBuildings += buildingsCount;
			}
			cardsCount = Integer.parseInt(addItems[4]);
			if(cardsCount-- > 0) {
				ActionCard newAC = logic.getGameData().takeCard();
				pd.addCard(newAC);
//				com.sendPlayerDataToClient(clientID, pd);
				com.sendGameDataToAllClients(logic.getGameData());
			}
			
			cardsCnt = pd.getNumberOfCards()-1;
			System.out.print("Handkarten NACH dem Kauf: "); 
			while(cardsCnt-- > 0) {
				System.out.print(pd.getCard(cardsCnt).getType() + ", " + (cardsCnt == 0 ? "\n" : ""));
			}
			com.sendPlayerDataToClient(clientID, pd);
			
		}
		if (message.contains("weiter")) {
			nrOfReadyPlayers++;
			System.out.println("~~~~nrOfReadyPlayers: " + nrOfReadyPlayers);
//			String[] nr = message.split(":", 2);
//			if(nr[1].equals("0")) {
//				com.sendMessageToAllClients("");
//			}
			waitingPlayers.removePlayer(clientID);
			com.sendGameDataToAllClients(logic.getGameData());
			if(nrOfReadyPlayers == logic.getGameData().players.size()) {
				com.sendGameDataToAllClients(logic.getGameData());
				logic.nextPhase();
			}
		}
		
	}

	@Override
	public void ReceivedGameStateFromClient(String clientID, GameData g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void ReceivedPlayerDataFromClient(String clientID, PlayerData p) {
		// TODO Auto-generated method stub
	}

	@Override
	public PhaseType getNextPhaseType() {
		return PhaseType.CardLimit;//Emir
	}

}
