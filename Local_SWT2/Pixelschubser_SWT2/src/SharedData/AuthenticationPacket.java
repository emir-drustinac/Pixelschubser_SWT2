package SharedData;

import java.io.Serializable;

public class AuthenticationPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5455107748231623523L;
	private final String clientID;
	private final String username;
	private final String password;
	public AuthenticationPacket(String clientID, String username, String pass){
		this.clientID = clientID;
		this.username = username;
		this.password = pass;
	}
	public String getClientID() {
		return clientID;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}