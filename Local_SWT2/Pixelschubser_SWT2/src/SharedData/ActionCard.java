package SharedData;

import java.io.Serializable;

public class ActionCard implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -783616116266345006L;

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
		GOLDENLION, //X1 Goldener Lï¿½we - kann nicht ausgespielt werden - gibt einen extra Machtpunkt
		GOLDENCHARIOT, //X1 Goldener Wagen - kann nicht ausgespielt werden - gibt einen extra Machtpunkt
		// Junk Karte
		JUGGLER //X1 Gauklertruppe - kann nicht ausgespielt werden - bewirkt nichts 
		//************************TOTAL OF 45 ActionCards*****************************
	}

	private final CardType type;
	private ActionCardList currentCardList;
	public int drawnInRound = 0;
	
	public ActionCard(CardType type) {
		this.type = type;
	}
	
	/* 
	 * TODO: #### wenn die Karten shon sowieso ENUM sind, 
	 * vlt die ganzen Methoden streichen und Logic entscheiden lassen was mit welcher Karte tun?
	 * CFR: die Methoden unterstützen die Logic dabei zu entscheiden, welche Karte nutzbar ist
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
	
	// Alex: ist damit DICE Phase gemeint? 
	// Weil sonst redundant viele Karten die es schon in usableForOffender/usableByDefender schon gibt
	public boolean usableDuringFight() {
		return 
//				type == CardType.ASSASSINATION ||
//				type == CardType.CATAPULT ||
//				type == CardType.SLAVEREVOLT ||
//				type == CardType.BRIBE ||
//				type == CardType.LION ||
				type == CardType.ABUSEOFPOWER;
	}

	public boolean usableForOffender(){
		return 
				type == CardType.ASSASSINATION ||
				type == CardType.CATAPULT ||
				type == CardType.SLAVEREVOLT ||
				type == CardType.BRIBE ||
				type == CardType.LION ||
				type == CardType.SURPRISEATTACK ||
				type == CardType.ANNEXATION;
//				type == CardType.ABUSEOFPOWER;
	}

	public boolean usableByDefender() {
		return 
				type == CardType.ASSASSINATION ||
				type == CardType.CATAPULT ||
				type == CardType.SLAVEREVOLT ||
				type == CardType.BRIBE ||
				type == CardType.LION;
				//type == CardType.ABUSEOFPOWER;
	}

	public CardType getType() {
		return this.type;
	}

	public String getNameHTML(boolean centered, boolean wrapped) {
		String name = getNameText();
		name = name.replace("-", wrapped ? "<br>" : "");
		if (wrapped) name = name.replace(" ", "<br>");
		return "<html>" + (centered ? "<center>" : "")
				+ name + (centered ? "</center>" : "") + "</html>";
	}
	public String getName() {
		return getNameText().replace("-", "");
	}
	public String getNameText() {
		switch (type) {
			case PICKLOCK: return "Einbrecher";
			case SPY: return "Spion";
			case ASSASSINATION: return "Attentat";
			case CATAPULT: return "Katapult";
			case SLAVEREVOLT: return "Sklaven-aufstand";
			case BRIBE: return "Schmiergeld";
			case LION: return "Löwe";
			case SURPRISEATTACK: return "Überraschungs-angriff";
			case ANNEXATION: return "Annexion";
			case ABUSEOFPOWER: return "Macht-missbrauch";
			case DENARI1000: return "Tribut";
			case DENARI2000: return "Tribut";
			case DENARI3000: return "Tribut";
			case PROPAGANDA: return "Propaganda";
			case FREEBUILDING: return "Freies Gebäude";
			case GOLDENLION: return "Goldener Löwe";
			case GOLDENCHARIOT: return "Goldener Wagen";
			case JUGGLER: return "Gaukler-truppe";
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

	public String getTimeUsableHTML() {
		return "<html>" + getTimeUsableText() + "</html>";
	}
	public String getTimeUsableText() {
		String preFight = "Spiele vor einem Kampf";
		switch (type) {
		case PICKLOCK: return "Spiele unmittelbar, nachdem der Präsident seine Versprechungen gemacht hat";
		case SPY: return "Spiele unmittelbar, nachdem alle Spieler ihre Söldner befehligt haben";
		case ASSASSINATION: return preFight;
		case CATAPULT: return preFight;
		case SLAVEREVOLT: return preFight;
		case BRIBE: return preFight;
		case LION: return preFight;
		case SURPRISEATTACK: return "Spiele als Angreifer vor einem Kampf";
		case ANNEXATION: return "Spiele als Angreifer vor einem Kampf, wenn der Verteidiger mehr Gebäude hat als du";
		case ABUSEOFPOWER: return "Spiele beim Geld ausgeben";
		case DENARI1000: return "";
		case DENARI2000: return "";
		case DENARI3000: return "";
		case PROPAGANDA: return "Spiele beim Geld ausgeben";
		case FREEBUILDING: return ":Freies Gebäude";
		case GOLDENLION: return "";
		case GOLDENCHARIOT: return "";
		case JUGGLER: return "";
		}
		return "";
	}

	public String getActionConsequencesHTML() {
		return "<html>" + getActionConsequencesText() + "</html>";
	}
	public String getActionConsequencesText() {
		switch (type) {
		case PICKLOCK: return "Schau dir die Handkarten eines Spielers deiner Wahl an und nimm eine davon auf deine Hand";
		case SPY: return "Schau dir die Söldner aller Mitspieler an, bevor du deine eigenen befehligst";
		case ASSASSINATION: return "Zerstöre 1 gegnerischen Söldner deiner Wahl";
		case CATAPULT: return "Dein Kampfwert erhöht sich um +3";
		case SLAVEREVOLT: return "Alle verteidigenden Söldner würfeln eine 2";
		case BRIBE: return "Ignoriere die Söldner des Proconsuls";
		case LION: return "Schlägt alleine angreifende Söldner jedes Angreifers in die Flucht";
		case SURPRISEATTACK: return "Ignoriere in diesem Kampf alle Gebäude des Verteidigers";
		case ANNEXATION: return "Ist dein Angriff erfolgreich, übernimm 1 Gebäude des Verteidigers und verzichte auf seine Handkarte";
		case ABUSEOFPOWER: return "Du erhältst einen Söldner gratis";
		case DENARI1000: return "1000 Denari";
		case DENARI2000: return "2000 Denari";
		case DENARI3000: return "3000 Denari";
		case PROPAGANDA: return "Du erhältst ein Gebäude gratis";
		case FREEBUILDING: return ">Freies Gebäude";
		case GOLDENLION: return "1 Siegpunkt";
		case GOLDENCHARIOT: return "1 Siegpunkt";
		case JUGGLER: return "Keine Auswirkungen";
		}
		return "";
	}

	public String getImagePath() {
		switch (type) {
		case PICKLOCK: return "";
		case SPY: return "";
		case ASSASSINATION: return "";
		case CATAPULT: return "";
		case SLAVEREVOLT: return "";
		case BRIBE: return "";
		case LION: return "";
		case SURPRISEATTACK: return "";
		case ANNEXATION: return "/images/actioncards/annexion.png";
		case ABUSEOFPOWER: return "";
		case DENARI1000: return "/images/building.png";
		case DENARI2000: return "/images/building.png";
		case DENARI3000: return "/images/building.png";
		case PROPAGANDA: return "";
		case FREEBUILDING: return "";
		case GOLDENLION: return "";
		case GOLDENCHARIOT: return "";
		case JUGGLER: return "";
		}
		return "/images/card.png";
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

	public boolean removeFromCardList() {
		// take card in "hand"
		if (currentCardList == null) return false;
		return currentCardList.remove(this);
	}

	public void putInCardList(ActionCardList actionCardList) {
		// remove from currentList
		removeFromCardList();
		// add to new list
		currentCardList = actionCardList;
		currentCardList.add(this);
	}

}
