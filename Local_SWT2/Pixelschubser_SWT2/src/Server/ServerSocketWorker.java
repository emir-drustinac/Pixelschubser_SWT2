package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import SharedData.AuthenticationPacket;
import SharedData.GameData;
import SharedData.NetworkProtocol;
import SharedData.SocketWorker;
import SharedData.SocketWorkerManager;

public class ServerSocketWorker extends SocketWorker{

	public ServerSocketWorker(Socket s, SocketWorkerManager parent) throws IOException {
		super(s, parent);
	}
		
	public void sendGameData(GameData g) throws IOException {
		out.reset();
		out.writeObject(g);
		out.flush();
	}

	/**
	 * 
	 * @param m
	 */
	public void receivedMessage(String m) {
		((ServerCommunicator) parent).receivedMessage(getClientID(), m);
		((ServerCommunicator) parent).sendMessageToAllClients(auth.getUsername()+": "+m);
	}

	@Override
	public void receivedGameState(GameData g) {
		((ServerCommunicator) parent).receivedGameData(getClientID(), g);
	}
	
	public String getClientID(){
		if(auth == null)
			return null;
		return auth.getClientID();
	}

	public String getUsername(){
		if(auth == null)
			return null;
		return auth.getUsername();
	}

	@Override
	protected void negotiate(Socket s) throws IOException {
		System.out.println("################# negotiate() - Server ###################");
		InputStream inRaw = s.getInputStream();
		OutputStream outRaw = s.getOutputStream();
		outRaw.write(NetworkProtocol.SERVER_HELLO);
		byte[] buf = new byte[NetworkProtocol.CLIENT_HELLO.length];
		inRaw.read(buf, 0, NetworkProtocol.CLIENT_HELLO.length);
		if (!Arrays.equals(buf, NetworkProtocol.CLIENT_HELLO)){
			s.close();
			throw new IOException("Bad protocol or bad version.");
		}
		outRaw.write(NetworkProtocol.LINE_BREAK);

		out = new ObjectOutputStream(outRaw);
		in = new ObjectInputStream(inRaw);
		try {
			AuthenticationPacket auth = (AuthenticationPacket)(in.readObject());
			if (!((ServerCommunicator) parent).authenticateClient(auth)){
				s.close();
			}else{
				this.auth = auth;
				parent.registerWorker(this);
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("Bad protocol or bad version.", e);
		}
	}

}
