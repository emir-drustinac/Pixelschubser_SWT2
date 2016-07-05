package SharedData;

public enum PhaseType {
	JoinGame, // "Lobby", jeder geht ready -> spielstart
	DrawCards,
	MakePromises,
	CommandMercenaries,
	Combat,
	SpendMoney,
	DeclareWinner, // nachdem alle spieler geld ausgegeben haben, kann ein sieger bestimmt werden
	CardLimit, // karten wegschmeißen, wenn man zu viele hat
	GameOver;

	public String toStringDE() {
		if(this.equals(DrawCards)) return "Phase: Karten ziehen";
		if(this.equals(MakePromises)) return "Phase: Versprechungen machen";
		if(this.equals(CommandMercenaries)) return "Phase: Milizen befehligen";
		if(this.equals(Combat)) return "Phase: Kämpfe austragen";
		if(this.equals(SpendMoney)) return "Phase: Geld ausgeben";
		if(this.equals(CardLimit)) return "Phase: Handkartenlimit prüfen";
		else return " ";
	}
}
