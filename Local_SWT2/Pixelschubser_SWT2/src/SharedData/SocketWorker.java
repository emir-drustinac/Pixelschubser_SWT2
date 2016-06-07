package SharedData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

abstract public class SocketWorker implements Runnable{

	protected Socket socket;
	private Thread workerThread;
	protected String playerID;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected SocketWorkerManager parent;
	protected NetworkProtocol.AuthenticationPacket auth;

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
	public void sendMessage(String m) throws IOException{
		// TODO - implement ClientSocketWorker.sendMessage
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param g
	 */
	public abstract void receivedGameState(GameData g);

	protected abstract void negotiate(Socket s) throws IOException;
	
	
	@Override
	public void run() {
		try{
			negotiate(socket);
			while (!Thread.interrupted()){
				Object data = in.readObject();
				if(data instanceof GameData)
					receivedGameState((GameData)data);
			}
		}catch (IOException|ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	public void setAuth(NetworkProtocol.AuthenticationPacket auth) {
		this.auth = auth;
	}
}
