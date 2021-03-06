package SharedData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

abstract public class SocketWorker implements Runnable{

	protected Socket socket;
	private Thread workerThread;
	protected String playerID;

	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected SocketWorkerManager parent;
	protected AuthenticationPacket auth;
	
	public SocketWorker(Socket s, SocketWorkerManager parent) throws IOException{
		this.socket = s;
		this.parent = parent;
	}

	public void start(){
		workerThread = new Thread(this);
		workerThread.start();
	}
	public void shutdown(){
		workerThread.interrupt();
	}
	/**
	 * 
	 * @param m
	 */
	public void sendMessage(String m) throws IOException {
		if(out != null) {
			out.reset();
			out.writeObject(m);
		} else {
			System.out.println("Die Nachricht \"" + m + "\" konnte nicht verschickt werden!");
		}
	}

	/**
	 * 
	 * @param g
	 */
	public abstract void receivedGameState(GameData g);
	public abstract void receivedPlayerData(PlayerData p);
	public abstract void receivedMessage(String m);

	protected abstract void negotiate(Socket s) throws IOException;
	
	
	@Override
	public void run() {
		try{
			negotiate(socket);
			while (!Thread.interrupted()){
				Object data = in.readObject();
				if(data instanceof GameData)
					receivedGameState((GameData)data);
				if(data instanceof PlayerData)
					receivedPlayerData((PlayerData)data);
				if(data instanceof String)
					receivedMessage((String)data);
			}
		} catch (SocketException e) {
			// TODO: Connection is lost
			e.printStackTrace();
		} catch (IOException|ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	public void setAuth(AuthenticationPacket auth) {
		this.auth = auth;
	}
}
