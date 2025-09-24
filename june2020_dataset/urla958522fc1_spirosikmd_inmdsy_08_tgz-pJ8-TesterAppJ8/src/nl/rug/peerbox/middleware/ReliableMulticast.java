package nl.rug.peerbox.middleware;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public class ReliableMulticast implements Multicast {

	private static final int HEARTBEAT_FREQUENCY = 1000 * 5; //ms (10 seconds)

	static Logger logger = Logger.getLogger(ReliableMulticast.class);

	private final int id = new Random().nextInt(Integer.MAX_VALUE);

	private final int port;
	private final InetAddress address;

	private Sender sender;
	private MulticastSocket socket;
	private Receiver receiver;
	private Listener listener;

	private ArrayList<MessageListener> observer = new ArrayList<MessageListener>();

	private final AtomicInteger messageCounter = new AtomicInteger(0);

	private RemoteHostManager hostManager;

	private ReliableMulticast(InetAddress address, int port,
			MulticastSocket socket) {
		this.port = port;
		this.address = address;
		this.socket = socket;
	}

	public static Multicast createPeer(String ip, int port) {
		try {

			InetAddress address = InetAddress.getByName(ip);

			MulticastSocket socket = new MulticastSocket(port);
			socket.setTimeToLive(5);
			socket.joinGroup(address);

			final ReliableMulticast group = new ReliableMulticast(address,
					port, socket);
			group.hostManager = new RemoteHostManager();
			group.sender = new Sender(group);
			group.receiver = new Receiver(group);
			group.listener = new Listener(group);
			
			group.sender.start();
			group.receiver.start();
			group.listener.start();
			
			
			Timer timer = new Timer("Heartbeat", true);
			timer.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					Announcement heartbeat = Announcement.heartbeat(group.getPeerId(), group.messageCounter.get(), new byte[0]);
					group.sendMessage(heartbeat);
				}
			}, 0, HEARTBEAT_FREQUENCY);

			return group;

		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}

	public void announce(byte[] payload) {

		if (payload.length > Announcement.MAX_PAYLOAD_SIZE) {
			logger.warn("Payload of message is too large");
			throw new RuntimeException("Payload too large");
		}
		Announcement outgoing = Announcement.send(id,
				messageCounter.incrementAndGet(), payload);
		sendMessage(outgoing);
	}

	void sendMessage(Announcement outgoing) {
		sender.pushMessage(outgoing);
	}

	void rdeliver(Announcement m) {
		logger.debug("Consumed: " + m.toString());
		sendMessage(m);
		notifyListener(m);
	}

	public MulticastSocket getSocket() {
		return socket;
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public int getPeerId() {
		return id;
	}

	public Sender getSender() {
		return sender;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	@Override
	public RemoteHostManager getHostManager() {
		return hostManager;
	}

	@Override
	public void shutdown() {
		logger.debug("Shutdown Multicast group");
		receiver.shutdown();
		sender.shutdown();

		try {
			socket.leaveGroup(address);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			socket.close();
		}

	}

	ExecutorService pool = Executors.newSingleThreadExecutor();

	private void notifyListener(final Announcement about) {
		if (about.getLength() > 0) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					for (MessageListener ml : observer) {
						ml.receivedMessage(about.getPayload(), about.getPeerID());
					}
				}
			});
		}
	}

	@Override
	public void addMessageListener(MessageListener ml) {
		observer.add(ml);
	}
}
