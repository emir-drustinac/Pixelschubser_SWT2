package SharedData;

public class ActionCard {

	public enum CardType {
		Money1000, Money2000, Money3000
		// TODO: add other types
	}
	private CardType type;

	public boolean addsPoint() {
		// TODO - implement ActionCard.addsPoint
		throw new UnsupportedOperationException();
	}

	public boolean usableDuringFight() {
		// TODO - implement ActionCard.usableDuringFight
		throw new UnsupportedOperationException();
	}

	public CardType getType() {
		return this.type;
	}

	public String getName() {
		// TODO - implement ActionCard.getName
		throw new UnsupportedOperationException();
	}

	public boolean isMoneyCard() {
		return type == CardType.Money1000 || type == CardType.Money2000 || type == CardType.Money3000;
	}

	public int moneyValue() {
		if (type == CardType.Money1000) return 1000;
		if (type == CardType.Money2000) return 2000;
		if (type == CardType.Money3000) return 3000;
		return 0;
	}

}