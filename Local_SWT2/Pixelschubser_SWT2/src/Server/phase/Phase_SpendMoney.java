package Server.phase;

import Server.ServerCommunicator;
import Server.ServerGameLogic;
import SharedData.ActionCard;
import SharedData.ActionCard.CardType;
import SharedData.ActionCardList;
import SharedData.GameData;
import SharedData.Mercenary;
import SharedData.PhaseType;
import SharedData.PlayerData;

public class Phase_SpendMoney extends Phase {
	private int nrOfReadyPlayers = 0;
	private int mercsCount, buildingsCount, cardsCount;
	private String cards = "";
	private ActionCardList acl;
	private PlayerData pd;
	
	public Phase_SpendMoney(ServerGameLogic logic, ServerCommunicator com) {
		super(logic, com);
		System.out.println("# " + this.getClass().getSimpleName() + " entered");
		acl = new ActionCardList();
	}

	@Override
	public void ReceivedMessageFromClient(String clientID, String message) {
		System.out.println("# " + this.getClass().getSimpleName() + " " + clientID + " " + message + " #");
		pd = logic.getGameData().players.get(clientID);
		String msgToSend = "";
		
//		************
//		pd.mercenaries.add(new Mercenary());
//		pd.addCard(new ActionCard(CardType.ABUSEOFPOWER));
//		pd.addCard(new ActionCard(CardType.ABUSEOFPOWER));
//		pd.addCard(new ActionCard(CardType.FREEBUILDING));
//		pd.addCard(new ActionCard(CardType.PROPAGANDA));
//		pd.addCard(new ActionCard(CardType.DENARI3000));
//		pd.addCard(new ActionCard(CardType.DENARI2000));

//		com.sendGameDataToAllClients(logic.getGameData());
//		************
		if(message.contains("phase_infos")) {
			msgToSend = /*"Zuerst Geldkarte(n) anklicken und dann das, was Sie kaufen wollen; "
					+ */"linker Mouseclick = Anzahl erhöhen, rechter = Anzahl verringern.";
			com.sendMessageToClient(clientID, msgToSend);
		}
		if (message.contains("buy:not_enough_money")) {
			com.sendMessageToClient(clientID, "Nicht genug Geld!");
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
			com.sendMessageToClient(clientID, msgToSend);
			String[] card = message.split(":", 2);
			ActionCard ac = new ActionCard(pd.getCardByID(card[1]).getType());
			acl.add(ac);
			cards += card[1] + ",";
			System.out.println("cards>" + cards);
		}
		if(message.contains("unselectedCard:")) {
			String[] card = message.split(":", 2);
			ActionCard ac = new ActionCard(pd.getCardByID(card[1]).getType());
			acl.removeCard(ac);
			cards = cards.replace(card[1] + ",", "");
			System.out.println("cards>" + cards);
			
		}
		if (message.contains("confirm:spend_money")) {
			nrOfReadyPlayers++;
			if(nrOfReadyPlayers == logic.getGameData().players.size()) {
				int cardsCnt = pd.getNumberOfCards()-1;
				while(cardsCnt-- > 0) {
					System.out.println("Karten VOR dem Kauf: " + pd.getCard(cardsCnt).getType());
				}
			
				//remove cards after buying items
				for(int i = 0; i < acl.size(); i++) {
					pd.removeCard(acl.getCard(0));
				}
				//add buyed items to this player game data
				String[] addItems = message.split(":", 6);
				mercsCount = Integer.parseInt(addItems[3]);
				while(mercsCount-- > 0) {
					pd.mercenaries.add(new Mercenary());
				}
				buildingsCount = Integer.parseInt(addItems[4]);
				if(buildingsCount > 0) {
					pd.numberOfBuildings += buildingsCount;
				}
				cardsCount = Integer.parseInt(addItems[5]);
				while(cardsCount-- > 0) {
					pd.addCard(logic.getGameData().takeCard());
				}
				
				cardsCnt = pd.getNumberOfCards()-1;
				while(cardsCnt-- > 0) {
					System.out.println("Karten NACH dem Kauf: " + pd.getCard(cardsCnt).getType());
					com.sendPlayerDataToClient(clientID, pd);
				}
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
