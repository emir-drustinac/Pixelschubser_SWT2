package Client.gui;

public class Logic implements Observable{
	
	private Observer observer = null;	// wird durch GUi registriert
	private View currentView = View.START;
	
	public Logic() {
		
	}
	
	public View getCurrentView(){
		return currentView;
	}
	
	// Views
	public void joinView(){
		System.out.println("Join Game LOGIC");
		currentView = View.JOIN;
		notifyObservers();
	}
	
	public void createView(){
		System.out.println("CreateView LOGIC");
		currentView = View.CREATE;
		notifyObservers();
	}
	
	public void createGame(String gameName, String playerName){
		System.out.println("Create Game LOGIC");
		System.out.println("game name: " + gameName);
		System.out.println("player name: " + playerName);
		
		// TODO test auf Fehlerangaben + fehelermeldung
		
		currentView = View.LOBBY;	// hier anders machen, gameWindow
		notifyObservers();
	}
	
	public void connectToGame(String ip, String playername){
		System.out.println("Connect to a Game LOGIC");
		System.out.println("ip: " + ip);
		System.out.println("player name: " + playername);
		// TODO test auf Fehlerangaben + fehelermeldung
		
		currentView = View.LOBBY;
		notifyObservers();
	}
	
	public void back(){
		System.out.println("Back to JoinCreate LOGIC");
		currentView = View.START;
		notifyObservers();
	}
	
	
	
	
	// --- Observable methods ---------------------------
	@Override
	public void registerObserver(Observer observer) {
		this.observer = observer;
	}

	@Override
	public void notifyObservers() {
		observer.update();
	}

	@Override
	public void removeObserver() {
		this.observer = null;
	}
	
}
