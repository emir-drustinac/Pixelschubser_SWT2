package Client;

import Client.gui.Presentation;
import SharedData.*;

public class ClientGameLogic {

	private GameData game;
//	private ClientCommunicator com;
	
	
	public ClientGameLogic(ClientCommunicator com) {
//		this.com = com;
		com.setGameLogic(this);
	}
	/**
	 * 
	 * @param g
	 */
	public void receivedGameState(GameData g) {
		if(game == null){
			Presentation.createGameWindow(g);
		}
		game = g;
		Presentation.updateGameState(game);
	}

	public void receivedPlayerData(PlayerData p) {
		game.players.removePlayer(p.playerID);
		game.players.add(p);
		//Presentation.updatePlayerData(p);
		Presentation.updateGameState(game);
	}

	/**
	 * 
	 * @param m
	 */
	public void receivedMessage(String m) {
		Presentation.ReceivedMessage(m);
	}

	// metheods for each phase
	
	/* DRAW CARDS:
	 * 
	 * 00 - Display Cards 
	 * 01 - Click Draw Button 
	 * 02 - Update Cards View with new Card 
	 * 03 - Click EndPhase Button
	 * 
	 */
	
	/* MAKE PROMISES:
	 * 
	 * 00 - Display Cards 
	 * 01 - Click Card, Click again to deselect
	 * 02 - Click Player 
	 * 03 - Click EndPhase Button -> (check if all PLAYERS have at least 1 Card promised)
	 * 		#### PICKLOCK CARD
	*/
	
	/* COMMAND MERCENARIES:
	 * 
	 * 00 - Display Merc
	 * 01 - Click Merc
	 * 02 - Click Player to attack
	 * 		if player is YOUERSELF = defend
	 * 		if player is PROCONSUL = choose to ATTACK or DEFEND Prokonsul
	 * 03 - Click EndPhase Button -> (check if all MERCS have a TARGET )
	 * 		### SPY CARD
	 */
	
	/* - COMBAT DEFENDER CARDS
	 * 
	 * 00 - Displya Cards (Nonplayable Cards grayed out)
	 * 01 - Click Card to Select, Click again to deselect
	 * 		Add/Remove actionListener where needed
	 * 02 - Click YOURSELF or Merc
	 * 03 - Click EndPhase Button
	 */
	
	/* - COMBAT OFFENDER CARDS
	 * 
	 * 00 - Displya Cards (Nonplayable Cards grayed out)
	 * 01 - Click Card to Select, Click again to deselect
	 * 		Add/Remove actionListener where needed
	 * 02 - Click ENEMY PLAYER
	 * 03 - Click EndPhase Button 
	 */
	
	/* - COMBAT DICE
	 * 
	 * 00 - Display Def/Off MERC
	 * 01 - Click FIGHT Button
	 * 02 - Display Values
	 * 			### Click CARD re-Dice
	 * 			Display Values
	 * 03 - Display Winner
	 * 
	 */
	
	/* - COMBAT LOOT
	 * 
	 * 00 - Display LOOT button
	 * 00 - Click LOOT button
	 * 01 - Display looted Card
	 * 01 - Click EndPhase Button
	 */

	/* - SPEND MONEY
	 * 
	 * 00 - Display SHOP and CARDS
	 * ?????????????????????????????????
	 * xx - Click EndPhase button
	 */

	/* - CARD LIMIT
	 * 
	 * 00 - Dislpay all Cards
	 * 			Display DISPOSE button 
	 * 			Click Card
	 * 			Click Dispose Button
	 * 01 - Click EndPhase button
	 * 
	 */

}
