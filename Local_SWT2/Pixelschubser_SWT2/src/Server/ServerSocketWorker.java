package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import SharedData.GameData;
import SharedData.NetworkProtocol;
import SharedData.SocketWorker;
import SharedData.SocketWorkerManager;

public class ServerSocketWorker extends SocketWorker{

	public ServerSocketWorker(Socket s, SocketWorkerManager parent) throws IOException {
		super(s, parent);
	}
		
	public void sendGameData() {
		// TODO - implement ServerSocketWorker.sendGameData
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param m
	 */
	public void receivedMessage(String m) {
		// TODO - implement ServerSocketWorker.receivedMessage
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param m
	 */
	public void sendMessage(String m) {
		// TODO - implement ServerSocketWorker.sendMessage
		throw new UnsupportedOperationException();
	}

	@Override
	public void receivedGameState(GameData g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void negotiate(Socket s) throws IOException {
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
			NetworkProtocol.AuthenticationPacket auth = (NetworkProtocol.AuthenticationPacket)(in.readObject());
			if (!((ServerCommunicator) parent).authenticateClient(auth)){
				s.close();
			}else{
				parent.registerWorker(this);
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("Bad protocol or bad version.", e);
		}
	}

}
