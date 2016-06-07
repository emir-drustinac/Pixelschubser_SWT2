package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;

import SharedData.NetworkProtocol;
import SharedData.NetworkProtocol.AuthenticationPacket;
import SharedData.SocketWorker;
import SharedData.SocketWorkerManager;

public class ServerCommunicator implements Runnable, SocketWorkerManager{

	private ServerSocket serverSocket;
	private Collection<SocketWorker> socketWorkers;
	private Thread workerThread;
	
	public ServerCommunicator() throws IOException {
		this(NetworkProtocol.DEFAULT_PORT);
	}
	public ServerCommunicator(int port) throws IOException{
		serverSocket = new ServerSocket(port);
		socketWorkers = new LinkedList<SocketWorker>();
	}
	public void start(){
		workerThread = new Thread(this);
		workerThread.start();
	}
	public void shutdown(){
		workerThread.interrupt();
	}
	public void acceptConnection() {
		// TODO - implement ServerCommunicator.acceptConnection
		throw new UnsupportedOperationException();
	}
	
	public Boolean authenticateClient(AuthenticationPacket a){
		System.out.println("Connect from "+a.getClientID()+" "+a.getUsername());
		return true;
	}

	public void registerWorker(SocketWorker s){
		synchronized (socketWorkers) {
			socketWorkers.add(s);
		}
	}

	public void sendGameDataToAllClients() {
		// TODO - implement ServerCommunicator.sendGameDataToAllClients
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param m
	 */
	public void sendMessageToAllClients(String m) {
		// TODO - implement ServerCommunicator.sendMessageToAllClients
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param PID
	 * @param m
	 */
	public void sendMessageToClient(String PID, String m) {
		// TODO - implement ServerCommunicator.sendMessageToClient
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param PID
	 * @param m
	 */
	public void receivedMessage(String PID, String m) {
		// TODO - implement ServerCommunicator.receivedMessage
		throw new UnsupportedOperationException();
	}
	@Override
	public void run() {
		Socket connecting = null;
		while (!Thread.interrupted()){
			try{
				connecting = serverSocket.accept();
				new ServerSocketWorker(connecting, this).start();
			}catch (IOException e){
				try{
					connecting.close();
				}catch(NullPointerException|IOException ignored){
					// ignored
				}
				e.printStackTrace();
			}
		}
	}
}
