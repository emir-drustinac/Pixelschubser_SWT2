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
	 * 			# Free Building
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
		/* welche Phase? */
		PICKLOCK, // Einbrecher - darf wann gespielt werden? - bewirkt was?
		/* Kampfplanungsphase */
		SPY, // ? - ? - ?
		/* Kampfphase */
		// Offender
		ASSASSINATION, // ? - ? - ?
		CATAPULT, // ? - ? - ?
		SLAVEREVOLT, // ? - ? - ?
		BRIBE, // ? - ? - ?
		LION, // ? - ? - ?
		// Defender
		SURPRISEATTACK, // ? - ? - ?
		ANNEXATION, // ? - ? - ?
		// 
		ABUSEOFPOWER, // Machtmissbrauch - ? - ? 
		/* Geld ausgeben Phase */
		// Geldkarten
		DENARI1000, DENARI2000, DENARI3000,
		// 
		PROPAGANDA, // Propaganda - ? - ?
		FREEBUILDING, // ? - ? - ?
		// Extra Point Cards
		GOLDENLION, // Goldener Löwe - kann nicht ausgespielt werden - gibt einen extra Machtpunkt
		GOLDENCHARIOT, // Goldener Wagen - kann nicht ausgespielt werden - gibt einen extra Machtpunkt
		// Junk Karte
		JUGGLER // Gauklertruppe - kann nicht ausgespielt werden - bewirkt nichts 
	}
	private CardType type;
	
	/* 
	 * TODO: #### wenn die Karten shon sowieso ENUM sind, 
	 * vlt die ganzen Methoden streichen und Logic entscheiden lassen was mit welcher Karte tun?
	 * CFR: die Methoden unterstützen die Logic dabei zu entscheiden, welche Karte nutzbar ist
	 */
	
	public boolean addsPoint() {
		// TODO - implement ActionCard.addsPoint
		throw new UnsupportedOperationException();
	}

	public boolean usableDuringFight() {
		return 
				type == CardType.ASSASSINATION ||
				type == CardType.CATAPULT ||
				type == CardType.SLAVEREVOLT ||
				type == CardType.BRIBE ||
				type == CardType.LION ||
				type == CardType.SURPRISEATTACK ||
				type == CardType.ANNEXATION ||
				type == CardType.ABUSEOFPOWER;
	}

	public boolean usableForOffender(){
		return 
				type == CardType.ASSASSINATION ||
				type == CardType.CATAPULT ||
				type == CardType.SLAVEREVOLT ||
				type == CardType.BRIBE ||
				type == CardType.LION ||
				type == CardType.ABUSEOFPOWER;
	}

	public boolean usableByDefender() {
		return 
				type == CardType.ASSASSINATION ||
				type == CardType.CATAPULT ||
				type == CardType.SLAVEREVOLT ||
				type == CardType.BRIBE ||
				type == CardType.LION ||
				type == CardType.SURPRISEATTACK ||
				type == CardType.ANNEXATION ||
				type == CardType.ABUSEOFPOWER;
	}

	public CardType getType() {
		return this.type;
	}

	public String getName() {
		switch (type) {
		case PICKLOCK: return "Einbrecher";
		case SPY: return "Spion";
		case ASSASSINATION: return "Attentat";
		case CATAPULT: return "Katapult";
		case SLAVEREVOLT: return "Sklavenaufstand";
		case BRIBE: return "Schmiergeld";
		case LION: return "Löwe";
		case SURPRISEATTACK: return "Überraschungsangriff";
		case ANNEXATION: return "Annexion";
		case ABUSEOFPOWER: return "Machtmissbrauch";
		case DENARI1000: return "1000 Denari";
		case DENARI2000: return "2000 Denari";
		case DENARI3000: return "3000 Denari";
		case PROPAGANDA: return "Propaganda";
		case FREEBUILDING: return "Freies Gebäude";
		case GOLDENLION: return "Goldener Löwe";
		case GOLDENCHARIOT: return "Goldener Wagen";
		case JUGGLER: return "Gauklertruppe";
		}
		return "unknown card";
	}

	public boolean isMoneyCard() {
		return type == CardType.DENARI1000 || type == CardType.DENARI2000 || type == CardType.DENARI3000;
	}

	public int moneyValue() {
		if (type == CardType.DENARI1000) return 1000;
		if (type == CardType.DENARI2000) return 2000;
		if (type == CardType.DENARI3000) return 3000;
		return 0;
	}

}
