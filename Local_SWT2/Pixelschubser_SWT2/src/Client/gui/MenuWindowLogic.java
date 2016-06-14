package Client.gui;

import java.util.regex.Pattern;

public class MenuWindowLogic implements Observable{
	
	private Observer observer = null;	// wird durch GUi registriert
	private View currentView = View.START;
	
	private MenuWindow gui = null;
	
	private static final Pattern IPPATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	public MenuWindowLogic() {
		
	}
	
	public void setGUI(MenuWindow gui){
		this.gui = gui;
	}
	
	public View getCurrentView(){
		return currentView;
	}
	
	public static boolean validate(final String ip) {
	    return IPPATTERN.matcher(ip).matches();
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
//		System.out.println("Connect to a Game LOGIC");
//		System.out.println("ip: " + ip);
//		System.out.println("player name: " + playername);
		
		// TODO test auf Fehlerangaben + fehelermeldung
		if(validate(ip) && !playername.equals("")){
			System.out.println(".---------------------");
			System.out.println("IP: OK");
			System.out.println("Name: OK");
			// TODO: oder connectingVEIW vor LOBBY 
			currentView = View.LOBBY;
			notifyObservers();
		} else {
			//TODO: fehlermeldung in GUI
			gui.error();			
			
		}
		
		
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
