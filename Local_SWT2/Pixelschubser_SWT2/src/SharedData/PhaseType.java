package SharedData;

public enum PhaseType {
	JoinGame, // "Lobby", jeder geht ready -> spielstart
	DrawCards,
	MakePromises,
	CommandMercenaries,
	Combat,
	SpendMoney,
	CardLimit // karten wegschmeißen, wenn man zu viele hat
}
