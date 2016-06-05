package SharedData;

public class ActionCard {
	
	/*
	 * PromisesCard (After the Phase)
	 * 		# Picklock (Einbrecher)
	 * 
	 * CommandMercenariesCard (After the Phase)
	 * 		# Spy
	 * 
	 * Combatcards
	 * 		Defender
	 * 			# Assassination
	 * 			# Catapult
	 * 			# Slave Revolt
	 * 			# Bribe
	 * 			# Lion
	 * 
	 * 		Offender (incl. Defender Cards)
	 * 			# Surprise Attack
	 * 			# Annexation
	 * 
	 * 		Dice
	 * 			# Abuse of Power??? (Machtmissbrauch/Ablenkungsmanöver)
	 * 
	 * SpendMoneyCards
	 * 		MoneyCards
	 * 			# Denari1000
	 * 			# Denari2000
	 * 			# Denari3000
	 * 		FreeBuyCards
	 * 			# Propaganda (free Mercenary)
	 * 			# Free Buiding
	 * 
	 * VictoryPintsCards
	 * 		# Golden Statue
	 * 		# Golden Chariot
	 * 
	 * NothingCard
	 * 		# Entertainer? Juggler? (Gauklertruppe)
	 * 
	 */

	public enum CardType {
		// nach Phasen sortiert
		PICKLOCK, 
		SPY, 
		ASSASSINATION, CATAPULT, SLAVEREVOLT, BRIBE, LION,
		SURPRISEATTACK, ANNEXATION,
		ABUSEOFPOWER,
		DENARI1000, DENARI2000, DENARI3000,
		PROPAGANDA, FREEBUILDING,
		JUGGLER
	}
	private CardType type;
	
	/* 
	 * TODO: #### wenn die Karten shon sowieso ENUM sind, 
	 * vlt die ganzen Methoden streichen und Logic entscheidenlassen was mit welcher Karte tun?
	 * 
	 */
	
//	public boolean addsPoint() {
//		// TODO - implement ActionCard.addsPoint
//		throw new UnsupportedOperationException();
//	}
//
//	public boolean usableDuringFight() {
//		// TODO - implement ActionCard.usableDuringFight
//		throw new UnsupportedOperationException();
//	}
//	
//	public boolean usableForOffender(){
//		// TODO
//		return false;
//	}

	public CardType getType() {
		return this.type;
	}

//	public String getName() {
//		// TODO - implement ActionCard.getName
//		throw new UnsupportedOperationException();
//	}

//	public boolean isMoneyCard() {
//		return type == CardType.DENARI1000 || type == CardType.DENARI2000 || type == CardType.DENARI3000;
//	}
//
//	public int moneyValue() {
//		if (type == CardType.DENARI1000) return 1000;
//		if (type == CardType.DENARI2000) return 2000;
//		if (type == CardType.DENARI3000) return 3000;
//		return 0;
//	}

}