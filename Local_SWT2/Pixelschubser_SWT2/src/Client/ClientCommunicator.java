package Client;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import SharedData.*;

public class ClientCommunicator implements SocketWorkerManager{

	private ClientSocketWorker socketWorker;
	private InetSocketAddress serverAddress;
	private int timeout = NetworkProtocol.CONNECT_TIMEOUT;
	private String userName = "";
	private String userPass = "";
	
	public void setAuth(String name, String pass){
		userName = name;
		userPass = pass;
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
		socketWorker.setAuth(userName, userPass);
		socketWorker.connect(serverAddress, timeout);
	}
	@Override
	public void registerWorker(SocketWorker s) {
		//TODO: how to signal ready?
	}

	/**
	 * 
	 * @param g
	 */
	public void receivedGameState(GameData g) {
		// TODO - implement ClientCommunicator.receivedGameState
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param m
	 */
	public void receivedMessage(String m) {
		// TODO - implement ClientCommunicator.receivedMessage
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param m
	 */
	public void sendMessage(String m) {
		// TODO - implement ClientCommunicator.sendMessage
		throw new UnsupportedOperationException();
	}

}
