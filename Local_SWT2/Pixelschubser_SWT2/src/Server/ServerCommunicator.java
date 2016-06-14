package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import SharedData.GameData;
import SharedData.NetworkProtocol;
import SharedData.PlayerData;
import SharedData.NetworkProtocol.AuthenticationPacket;
import SharedData.SocketWorker;
import SharedData.SocketWorkerManager;

public class ServerCommunicator implements Runnable, SocketWorkerManager{

	private GameLogic game;
	private ServerSocket serverSocket;
	private Collection<ServerSocketWorker> socketWorkers;
	private Thread workerThread;
	
	public ServerCommunicator() throws IOException {
		this(NetworkProtocol.DEFAULT_PORT);
	}
	public ServerCommunicator(int port) throws IOException{
		serverSocket = new ServerSocket(port);
		socketWorkers = new LinkedList<ServerSocketWorker>();
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
			socketWorkers.add((ServerSocketWorker)s);
		}
		String playerID = ((ServerSocketWorker)s).getClientID();
		String name = ((ServerSocketWorker)s).getUsername();
		game.addPlayer(playerID, name);
	}

	public void sendGameDataToAllClients(GameData g) {
		g.phase = (int) (Math.random() * 1000000);
		System.out.println("Sending GameData: phase=" + g.phase + " numPlayers=" + g.players.size());
		for (PlayerData p : g.players) {
			System.out.println("  Player " + p.playerID + " : Name=" + p.name + " buildings=" + p.numberOfBuildings
					+ " mercenaries=" + p.numberOfMercenaries + " numCards=" + p.getNumberOfCards()
					+ " leader=" + p.isGameLeader + " proconsul=" + p.isProconsul + " ready=" + p.isReady);
		}
		synchronized (socketWorkers) {
			for(ServerSocketWorker sw:socketWorkers){
				try {
					System.out.println(" > to " + sw.getClientID());
					sw.sendGameData(g);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param m
	 */
	public void sendMessageToAllClients(String m) {
		synchronized (socketWorkers) {
			for(ServerSocketWorker sw:socketWorkers){
				try {
					sw.sendMessage(m);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param PID
	 * @param m
	 */
	public void sendMessageToClient(String PID, String m) {
		synchronized (socketWorkers) {
			for(ServerSocketWorker sw:socketWorkers){
				try {
					if (sw.getClientID().equals(PID)) sw.sendMessage(m);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param PID
	 * @param m
	 */
	public void receivedMessage(String PID, String m) {
		game.receivedMessage(PID, m);
	}
	
	public void receivedGameData(String clientID, GameData g) {
		game.receivedGameData(clientID, g);
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
	public void setGameLogic(GameLogic g) {
		game = g;
	}
}
