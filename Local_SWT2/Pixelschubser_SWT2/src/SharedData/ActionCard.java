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
	 * 			# Abuse of Power??? (Machtmissbrauch/Ablenkungsman�ver)
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
		/* welche Phase? - NACH VERPRECHUNGEN */
		PICKLOCK, //X1 Einbrecher - darf wann gespielt werden? - bewirkt was?
			 		// schaue dir die Handkarten eines Spielers und nimm dir eine davon
		/* Kampfplanungsphase */
		SPY, //X1 ? - ? - ?
		/* Kampfphase */
		// BOTH Defender AND Offender
		ASSASSINATION, //X2 ? - ? - ?
		CATAPULT, //X2 ? - ? - ?
		SLAVEREVOLT, //X2 ? - ? - ?
		BRIBE, //X1 ? - ? - ?
		LION, //X1 ? - ? - ?
		// ONLY AS Offender
		SURPRISEATTACK, //X1 ? - ? - ?
		ANNEXATION, //X1 ? - ? - ?
		// 
		ABUSEOFPOWER, //X2 Machtmissbrauch - ? - ? 
		/* Geld ausgeben Phase */
		// Geldkarten
		DENARI1000/*X12*/, DENARI2000/*X11*/, DENARI3000/*X3*/,
		// 
		PROPAGANDA, //X1 Propaganda - ? - ?
		FREEBUILDING, //X1 ? - ? - ?
		// Extra Point Cards
		GOLDENLION, //X1 Goldener L�we - kann nicht ausgespielt werden - gibt einen extra Machtpunkt
		GOLDENCHARIOT, //X1 Goldener Wagen - kann nicht ausgespielt werden - gibt einen extra Machtpunkt
		// Junk Karte
		JUGGLER //X1 Gauklertruppe - kann nicht ausgespielt werden - bewirkt nichts 
		//************************TOTAL OF 45 ActionCards*****************************
	}
	private CardType type;
	
	public ActionCard(CardType cardT) {
		
		this.type = cardT;
	}
	
	public ActionCard() {
		// Emir: macht das Sinn, Karte ohne Kartentyp?
	}
	
	/* 
	 * TODO: #### wenn die Karten shon sowieso ENUM sind, 
	 * vlt die ganzen Methoden streichen und Logic entscheiden lassen was mit welcher Karte tun?
	 * CFR: die Methoden unterst�tzen die Logic dabei zu entscheiden, welche Karte nutzbar ist
	 */
	

	public boolean addsFightPoints() {
		return 
				type == CardType.CATAPULT;
	}
	
	public boolean addsVictoryPoints() {
		return
				type == CardType.GOLDENCHARIOT ||
				type == CardType.GOLDENLION;			
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
			case LION: return "L�we";
			case SURPRISEATTACK: return "�berraschungsangriff";
			case ANNEXATION: return "Annexion";
			case ABUSEOFPOWER: return "Machtmissbrauch";
			case DENARI1000: return "1000 Denari";
			case DENARI2000: return "2000 Denari";
			case DENARI3000: return "3000 Denari";
			case PROPAGANDA: return "Propaganda";
			case FREEBUILDING: return "Freies Geb�ude";
			case GOLDENLION: return "Goldener L�we";
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

	public boolean isValidPhase(PhaseType pt) {
		switch(pt){
		case JoinGame:
		case DrawCards:
		case MakePromises:
			return false;
		case CommandMercenaries:
			return type == CardType.SPY;
		case Combat:
			return usableDuringFight();
		case SpendMoney:
			return isMoneyCard() || type==CardType.FREEBUILDING || type==CardType.PROPAGANDA;
		case CardLimit:
		case DeclareWinner:
		case GameOver:
		default:
			return false;
		}
	}

	public boolean isValidTarget(PlayerData player, PlayerData target) {
		// TODO Auto-generated method stub
		return false;
	}
}
