package SharedData;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class NetworkProtocol {
	public static final byte[] SERVER_HELLO = "Proconsul Server v0.1\r\n".getBytes(StandardCharsets.UTF_8);
	public static final byte[] CLIENT_HELLO = "Ave Caesar\r\n".getBytes(StandardCharsets.UTF_8);
	public static final byte[] LINE_BREAK = "\r\n".getBytes(StandardCharsets.US_ASCII);
	public static final int DEFAULT_PORT = 8736;
	public static final int CONNECT_TIMEOUT = 2500;
	
	public static class AuthenticationPacket implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 5455107748231623523L;
		public String username;
		public String userpass;
		public AuthenticationPacket(String name, String pass){
			username = name;
			userpass = pass;
		}
	}
}
