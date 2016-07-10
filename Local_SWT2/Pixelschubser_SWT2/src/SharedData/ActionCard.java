package SharedData;

import java.io.Serializable;
import java.util.EnumSet;

public class ActionCard implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -783616116266345006L;
	private static int nextCardID = 1;

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
	 * VictoryPointsCards
	 * 		# Golden Statue
	 * 		# Golden Chariot
	 * 
	 * NothingCard
	 * 		# Entertainer? Juggler? (Gauklertruppe)
	 * 
	 */

	public enum CardType {
		noCard,
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
		PROPAGANDA, //X1 - ? - ?
		FREEBUILDING, //X1 ? - ? - ?
		// Extra Point Cards
		GOLDENLION, //X1 Goldener Löwe - kann nicht ausgespielt werden - gibt einen extra Machtpunkt
		GOLDENCHARIOT, //X1 Goldener Wagen - kann nicht ausgespielt werden - gibt einen extra Machtpunkt
		// Junk Karte
		JUGGLER //X1 Gauklertruppe - kann nicht ausgespielt werden - bewirkt nichts 
		//************************TOTAL OF 45 ActionCards*****************************
	}
	
	public final static EnumSet<CardType> noCardTypes = EnumSet.of(
			CardType.noCard
			);
	
	public final static EnumSet<CardType> commandMercenariesCardTypes = EnumSet.of(
			CardType.SPY
			);
	
	public final static EnumSet<CardType> attackingCardTypes = EnumSet.of(
			CardType.ASSASSINATION,
			CardType.CATAPULT,
			CardType.SLAVEREVOLT,
			CardType.BRIBE, 
			CardType.LION,
			CardType.SURPRISEATTACK,
			CardType.ANNEXATION
			);

	public final static EnumSet<CardType> defendingCardTypes = EnumSet.of(
			CardType.ASSASSINATION,
			CardType.CATAPULT,
			CardType.SLAVEREVOLT,
			CardType.BRIBE,
			CardType.LION
			);

	public final static EnumSet<CardType> moneyCardTypes = EnumSet.of(
			CardType.DENARI1000,
			CardType.DENARI2000,
			CardType.DENARI3000
			);
	public final static EnumSet<CardType> moneySpendingCardTypes = EnumSet.of(
			CardType.DENARI1000,
			CardType.DENARI2000,
			CardType.DENARI3000,
			CardType.ABUSEOFPOWER,
			CardType.FREEBUILDING,
			CardType.PROPAGANDA
			);
	
	private final String cardID;
	private final CardType type;
	private ActionCardList currentCardList;
	public int drawnInRound = 0;
	
	public ActionCard(CardType type) {
		this.cardID = "card" + nextCardID++;
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
	
	public boolean usableDuringFight() {
		return usableByDefender() || usableForOffender();
//				type == CardType.ASSASSINATION ||
//				type == CardType.CATAPULT ||
//				type == CardType.SLAVEREVOLT ||
//				type == CardType.BRIBE ||
//				type == CardType.LION ||
//				type == CardType.ABUSEOFPOWER;
	}

	public boolean usableForOffender(){
		return attackingCardTypes.contains(type);
	}

	public boolean usableByDefender() {
		return defendingCardTypes.contains(type);
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
	private String getNameText() {
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
			case noCard: return "leere Karte";
		default:
			break;
		}
		return "unknown card";
	}

	public boolean isMoneyCard() {
		return moneyCardTypes.contains(type);
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
		case PICKLOCK: return "Nachdem alle Versprechungen gemacht wurden";
		case SPY: return "Nachdem alle ihre Söldner befehligt haben";
		case ASSASSINATION: return preFight;
		case CATAPULT: return preFight;
		case SLAVEREVOLT: return preFight;
		case BRIBE: return preFight;
		case LION: return preFight;
		case SURPRISEATTACK: return "Spiele als Angreifer vor einem Kampf";
		case ANNEXATION: return "Als Angreifer vor einem Kampf, wenn Verteidiger mehr Gebäude hat";
		case ABUSEOFPOWER: return "Spiele beim Geld ausgeben";
		case DENARI1000: return "";
		case DENARI2000: return "";
		case DENARI3000: return "";
		case PROPAGANDA: return "Spiele beim Geld ausgeben";
		case FREEBUILDING: return "Spiele beim Geld ausgeben";
		case GOLDENLION: return "";
		case GOLDENCHARIOT: return "";
		case JUGGLER: return "";
		case noCard: return "";
		}
		return "";
	}

	public String getActionConsequencesHTML() {
		return "<html>" + getActionConsequencesText() + "</html>";
	}
	public String getActionConsequencesText() {
		switch (type) {
		case PICKLOCK: return "Wähle eine Karte eines Spielers aus und nimm sie";
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
		case PROPAGANDA: return "Du erhältst einen Söldner gratis";
		case FREEBUILDING: return "Du erhältst ein Gebäude gratis";
		case GOLDENLION: return "1 Siegpunkt";
		case GOLDENCHARIOT: return "1 Siegpunkt";
		case JUGGLER: return "Keine Auswirkungen";
		case noCard: return "";
		}
		return "";
	}

	public String getImagePath() {
		switch (type) {
		case PICKLOCK: return "/images/actioncards/picklock.png";
		case SPY: return "/images/actioncards/spion.png";
		case ASSASSINATION: return "/images/actioncards/attentat.png";
		case CATAPULT: return "/images/actioncards/catapult.png";
		case SLAVEREVOLT: return "/images/actioncards/slaverage.png";
		case BRIBE: return "/images/actioncards/bribe.png";
		case LION: return "/images/actioncards/lion.png";
		case SURPRISEATTACK: return "/images/actioncards/surpriseattack.png";
		case ANNEXATION: return "/images/actioncards/annexion.png";
		case ABUSEOFPOWER: return "/images/actioncards/abuse_of_power.png";
		case DENARI1000: return "/images/actioncards/denari1000.png";
		case DENARI2000: return "/images/actioncards/denari2000.png";
		case DENARI3000: return "/images/actioncards/denari3000.png";
		case PROPAGANDA: return "/images/actioncards/propaganda.png";
		case FREEBUILDING: return "/images/actioncards/free_building.png";
		case GOLDENLION: return "/images/actioncards/golden_lion.png";
		case GOLDENCHARIOT: return "/images/actioncards/golden_wagon.png";
		case JUGGLER: return "/images/actioncards/juggler.png";
		case noCard: return "/images/actioncards/x.png";
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
			return commandMercenariesCardTypes.contains(type);
		case Combat:
			return usableDuringFight();
		case SpendMoney:
			return moneySpendingCardTypes.contains(type);
		case CardLimit:
		case DeclareWinner:
		case GameOver:
		default:
			return false;
		}
	}

	public boolean isValidTarget(PlayerData player, PlayerData target) {
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
		if (actionCardList != null) {
			// add to new list
			currentCardList = actionCardList;
			currentCardList.add(this);
		}
	}

	public String getCardID() {
		return cardID;
	}

}
