package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.JOptionPane;

import Client.gui.MenuWindow;
import Client.gui.MenuWindowLogic;
import SharedData.*;

public class ClientSocketWorker extends SocketWorker{
	
	public ClientSocketWorker(Socket s, SocketWorkerManager parent) throws IOException {
		super(s, parent);
	}
	
	public void connect(InetSocketAddress address, int timeout) /*throws IOException*/{
		try {
			socket.connect(address, timeout);
		} catch (IOException e) {
			String message = "Der Server läuft nicht! Bitte einen anderen Server eingeben.";
			JOptionPane.showMessageDialog(new MenuWindow(new MenuWindowLogic()), message, "Falscher Server", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		start();
	}

	@Override
	public void receivedMessage(String m) {
		((ClientCommunicator) parent).receivedMessage(m);
	}
	/**
	 * 
	 * @param g
	 */
	public void receivedGameState(GameData g) {
		((ClientCommunicator) parent).receivedGameState(g);
	}
	@Override
	protected void negotiate(Socket s) throws IOException {
		InputStream inRaw = s.getInputStream();
		OutputStream outRaw = s.getOutputStream();
		byte[] buf = new byte[NetworkProtocol.SERVER_HELLO.length];
		inRaw.read(buf, 0, NetworkProtocol.SERVER_HELLO.length);
		if (!Arrays.equals(buf, NetworkProtocol.SERVER_HELLO)){
			s.close();
			throw new IOException("Bad protocol or bad version.");
		}
		outRaw.write(NetworkProtocol.CLIENT_HELLO);
		inRaw.read(buf, 0, NetworkProtocol.LINE_BREAK.length);
		
		in = new ObjectInputStream(inRaw);
		out = new ObjectOutputStream(outRaw);
		System.out.println("################# negotiate() - Client ###################");
		out.writeObject(auth);
		negotiated = true;
	}
}
