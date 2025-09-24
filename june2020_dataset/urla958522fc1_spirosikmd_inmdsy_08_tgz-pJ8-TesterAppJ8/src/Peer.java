import java.io.*;
import java.net.*;
import java.util.*;

public class Peer implements Runnable, ReadEventListener {

	private InetAddress group;
	private int port;
	private BufferedReader input;
	private PrintStream output;
	private Thread listener;
	private MulticastSocket socket;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	private DatagramPacket incomingPacket;
	private DatagramPacket outgoingPacket;
	private ObjectOutputStream outgoingObject;
	private ObjectInputStream incomingObject;
	private UUID peerID;

	public Peer(InetAddress group, int port) {
		this.peerID = UUID.randomUUID();
		this.group = group;
		this.port = port;
		initIO();
	}

	private void initIO() {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = System.out;
	}

	private void initNet() throws IOException {
		socket = new MulticastSocket(port);
		socket.setTimeToLive(5);
		socket.joinGroup(group);
	}

	private synchronized void handleIOException (IOException ex) {
		if (listener != null) {
			output.println(ex);
			if (listener != Thread.currentThread())
				listener.interrupt();
			listener = null;
			try {
				socket.leaveGroup(group);
			} catch (IOException ignored) {}
			socket.close();
		}
	}

	public synchronized void start() throws IOException {
		if (listener == null) {
			initNet();
			listener = new Thread(this);
			listener.start();
		}
	}

	public synchronized void stop() throws IOException {
		if (listener != null) {
			listener.interrupt();
			listener = null;
			try {
				socket.leaveGroup(group);
			} finally {
				socket.close();
			}
		}
	}

	public synchronized void handleReadEvent(ReadEvent e) {
		try {
			baos = new ByteArrayOutputStream();
			outgoingObject = new ObjectOutputStream(baos);
			String message = "This is a message from " + this.peerID;
			Message mes = new Message(this.peerID, message);
			outgoingObject.writeObject(mes);
			byte[] b = baos.toByteArray();
			outgoingPacket = new DatagramPacket(b, b.length, group, port);
			socket.send(outgoingPacket);
		} catch (IOException ex) {
			handleIOException(ex);
		}
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				byte[] b = new byte[65508]; 
				incomingPacket = new DatagramPacket(b, b.length);
				socket.receive(incomingPacket);
				bais = new ByteArrayInputStream(incomingPacket.getData());
				incomingObject = new ObjectInputStream(bais);
				Message messageObject = (Message) incomingObject.readObject();
				if (!messageObject.getID().equals(this.peerID)) {
					String message = messageObject.getMessage();
					output.println(message);
				}
			}
		} catch (IOException ex) {
			handleIOException(ex);
		} catch (ClassNotFoundException cnfex) {

		}
	}
}