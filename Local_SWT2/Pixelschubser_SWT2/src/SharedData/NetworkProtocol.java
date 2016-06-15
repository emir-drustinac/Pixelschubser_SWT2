package SharedData;

import java.nio.charset.StandardCharsets;

public class NetworkProtocol {
	public static final byte[] SERVER_HELLO = "Proconsul Server v0.1\r\n".getBytes(StandardCharsets.UTF_8);
	public static final byte[] CLIENT_HELLO = "Ave Caesar\r\n".getBytes(StandardCharsets.UTF_8);
	public static final byte[] LINE_BREAK = "\r\n".getBytes(StandardCharsets.US_ASCII);
	public static final int DEFAULT_PORT = 8736;
	public static final int CONNECT_TIMEOUT = 2500;
}
