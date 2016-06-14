package Client;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import SharedData.*;

public class ClientCommunicator implements SocketWorkerManager{
	
	private ClientGameLogic game;
	private ClientSocketWorker socketWorker;
	private InetSocketAddress serverAddress;
	private int timeout = NetworkProtocol.CONNECT_TIMEOUT;
	private NetworkProtocol.AuthenticationPacket auth;
	
	public void setAuth(String id, String name, String pass){
		auth = new NetworkProtocol.AuthenticationPacket(id, name, pass);
	}
	public void setServer(String address){
		serverAddress = new InetSocketAddress(address, NetworkProtocol.DEFAULT_PORT);
	}
	public void setServer(String address, int port){
		serverAddress = new InetSocketAddress(address, port);
	}
	public void setServer(InetAddress address){
		serverAddress = new InetSocketAddress(address, NetworkProtocol.DEFAULT_PORT);
	}
	public void setServer(InetAddress address, int port){
		serverAddress = new InetSocketAddress(address, port);
	}
	/**
	 * 
	 * @param address
	 */
	public void connect() throws IOException{
		if(serverAddress == null)
		{
			serverAddress = new InetSocketAddress(Inet6Address.getLoopbackAddress(), NetworkProtocol.DEFAULT_PORT);
		}		
		socketWorker = new ClientSocketWorker(new Socket(), this);
		socketWorker.setAuth(auth);
		socketWorker.connect(serverAddress, timeout);
	}
	@Override
	public void registerWorker(SocketWorker s) {
		//TODO: how to signal ready?
	}
	public void setGameLogic(ClientGameLogic g){
		game = g;
	}

	/**
	 * 
	 * @param g
	 */
	public void receivedGameState(GameData g) {
		System.out.println("Received GameData");
		game.receivedGameState(g);
	}

	/**
	 * 
	 * @param m
	 */
	public void receivedMessage(String m) {
		System.out.println("Received message: " + m);
		game.receivedMessage(m);
	}

	/**
	 * 
	 * @param m
	 * @throws IOException 
	 */
	public void sendMessage(String m) {
		try {
			socketWorker.sendMessage(m);
		} catch (IOException e) {
			// TODO sending Message failed, so cache message and try to send it later!
			e.printStackTrace();
		}
	}

}
