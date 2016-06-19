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
	GameOver
}
