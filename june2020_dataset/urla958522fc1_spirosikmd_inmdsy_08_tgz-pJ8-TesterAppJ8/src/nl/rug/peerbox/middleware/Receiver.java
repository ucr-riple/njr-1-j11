package nl.rug.peerbox.middleware;

import java.net.DatagramPacket;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

final class Receiver {

	private static final int RECEIVE_QUEUE_SIZE = 1024;

	private final static Logger logger = Logger.getLogger(Receiver.class);

	private final Queue<Announcement> holdbackQueue = new ConcurrentLinkedQueue<Announcement>();
	private final BlockingQueue<DatagramPacket> receivedDataQueue = new ArrayBlockingQueue<DatagramPacket>(
			RECEIVE_QUEUE_SIZE);

	private ReliableMulticast group;
	private volatile boolean alive = true;
	private Thread thread;
	private final Timer missedTimer;

	public Receiver(ReliableMulticast group) {
		this.group = group;
		missedTimer = new Timer(true);
	}

	void start() {
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (alive) {
					try {
						DatagramPacket data = receivedDataQueue.take();
						processMessage(data.getData());
					} catch (InterruptedException e) {
						logger.debug("Receiver interrupted");
					}
				}
				logger.debug("Receiver stopped");
			}

		});
		thread.setDaemon(true);
		thread.setName("Receiver");
		thread.start();
	}

	void shutdown() {
		logger.debug("Stop Receiver Thread");
		alive = false;
		thread.interrupt();
	}

	void pushDataPacket(DatagramPacket dp) {
		if (!receivedDataQueue.offer(dp)) {
			logger.equals("Incoming Blockingqueue is full");
		}
	}

	private void processMessage(byte[] bytes) {
		Announcement m = null;
		try {
			m = Announcement.fromByte(bytes);
			receiveMessage(m);
		} catch (ChecksumFailedException e) {
			logger.warn("Checksum failed");
		}
	}

	private void receiveMessage(Announcement m) {

		switch (m.getCommand()) {
		case Announcement.MESSAGE:

			handleMessage(m);
			break;

		case Announcement.NACK:
			logger.debug(m.toString());
			group.getSender().resendMessage(m.getMessageID(), m.getPeerID());
			break;
		case Announcement.HEARTBEAT:
			if (m.getPeerID() == group.getPeerId())
				return;
			RemoteHost p = group.getHostManager().getRemoteHost(m.getPeerID());
			if (p == null) {
				p = detectedRemoteHost(m);
			}
			p.heartbeated();
			break;
		case Announcement.ACK:
			if (m.getPeerID() != group.getPeerId()) {
				logger.debug("Acked: " + m.toString());
			}
		}
	}

	private void handleMessage(Announcement m) {
		if (m.getPeerID() == group.getPeerId())
			return;

		RemoteHost p = group.getHostManager().getRemoteHost(m.getPeerID());
		if (p == null) {
			p = detectedRemoteHost(m);
		}

		int r = p.getReceivedMessageID();
		int s = m.getMessageID();
		if (s > p.getSeenMessageID()) {
			p.setSeenMessageID(s);
		}

		if (s == r + 1) {
			logger.debug("Received: " + m.toString());

			p.setReceivedMessageID(++r);
			group.rdeliver(m);

			Announcement stored = findMessageInHoldbackQueue(p.getHostID(),
					s + 1);
			if (stored != null) {
				holdbackQueue.remove(stored);
				receiveMessage(stored);
			}

		} else if (s > r + 1) {
			logger.debug("Received: " + m.toString());
			logger.debug("Missed message " + (r + 1) + " detected from group "
					+ m.getPeerID());
			holdbackQueue.add(m);
			for (int missedID = r + 1; missedID < p.getSeenMessageID(); missedID++) {
				if (findMessageInHoldbackQueue(p.getHostID(), missedID) == null) {
					// TODO associate miss message with timer, if timer is over
					// and h.messageid < missedID retry else message has been
					// received
					sendMiss(m.getPeerID(), missedID);
					missedTimer.schedule(new MissTimerTask(p, missedID), 5000);
				}
			}
		} else if (s <= r) {
			logger.debug("Discarded duplicate: " + m.toString());
		}
	}

	private RemoteHost detectedRemoteHost(Announcement m) {
		RemoteHost p;
		p = new RemoteHost();
		p.setHostID(m.getPeerID());
		if (m.getCommand() != Announcement.MESSAGE) {
			p.setSeenMessageID(m.getMessageID());
			p.setReceivedMessageID(m.getMessageID());
		} else {
			p.setSeenMessageID(m.getMessageID() - 1);
			p.setReceivedMessageID(m.getMessageID() - 1);
		}
		p.heartbeated();
		group.getHostManager().addRemoteHost(m.getPeerID(), p);
		return p;
	}

	void sendMiss(int peer, int message_id) {
		Announcement miss = Announcement.nack(peer, message_id);
		group.sendMessage(miss);
	}

	private Announcement findMessageInHoldbackQueue(int host, int messageID) {
		for (Announcement m : holdbackQueue) {
			if (m.getPeerID() == host && m.getMessageID() == messageID) {
				return m;
			}
		}
		return null;
	}

	private class MissTimerTask extends TimerTask {

		private static final int REREQUEST_TIMER = 5000;
		private final RemoteHost host;
		private final int missedID;

		public MissTimerTask(final RemoteHost h, final int missedID) {
			this.host = h;
			this.missedID = missedID;
		}

		@Override
		public void run() {
			if (host.getReceivedMessageID() < missedID) {
				logger.debug("Re-request message " + missedID + " from " + host.getHostID() + " r="+host.getReceivedMessageID());
				sendMiss(host.getHostID(), missedID);
				missedTimer.schedule(new MissTimerTask(host, missedID), REREQUEST_TIMER);
			}
		}
	}
}