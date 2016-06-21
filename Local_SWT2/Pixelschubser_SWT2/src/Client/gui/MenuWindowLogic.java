package Client.gui;

import java.util.regex.Pattern;

import Client.Client;

public class MenuWindowLogic {

	//private Client client = null;

	private static final Pattern IPPATTERN = Pattern
			.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public MenuWindowLogic() {

	}
	
//	public void setClient(Client client){
//		this.client = client;
//	}

	public static boolean validate(final String ip) {
		return IPPATTERN.matcher(ip).matches();
	}

	public boolean createGame(String playerName){
		
		if(!playerName.equals("")){
			// TODO: create + connect
			Client.create(playerName);
			return true;
		} else{
			return false;
		}
		
	}

	public boolean connectToGame(String ip, String playerName) {
		// test auf Fehlerangaben + fehelermeldung
		if (validate(ip) && !playerName.equals("")) {
			// TODO: connect
			Client.connect(ip, playerName);
			return true;
		} else {
			return false;
		}

	}

}
