package SharedData;

public enum PhaseType {
	JoinGame, // "Lobby", jeder geht ready -> spielstart
	DrawCards,
	MakePromises,
	CommandMercenaries,
	Combat,
	SpendMoney,
	CardLimit // karten wegschmei�en, wenn man zu viele hat
}
