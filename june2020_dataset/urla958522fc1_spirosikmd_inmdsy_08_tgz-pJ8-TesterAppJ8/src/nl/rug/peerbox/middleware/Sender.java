package nl.rug.peerbox.middleware;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.apache.log4j.Logger;

final class Sender {

	private final Queue<Announcement> sentMessagesList = new ConcurrentLinkedQueue<Announcement>();
	private final BlockingQueue<Announcement> waitingForSendQueue = new ArrayBlockingQueue<Announcement>(
			1024);
	private final TimedSemaphore semaphore = new TimedSemaphore(50,
			TimeUnit.MILLISECONDS, 1);

	private static final Logger logger = Logger.getLogger(Sender.class);

	private final ReliableMulticast group;
	private Thread thread;
	private volatile boolean alive = true;

	Sender(ReliableMulticast group) {
		this.group = group;
	}

	void start() {
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (alive) {
					try {
						// limit bandwidth
						semaphore.acquire();
						// get message from queue, if no message in queue wait
						Announcement message = waitingForSendQueue.take();

						ReliableMulticast.logger.debug("Send: " + message);

						byte[] data = message.toByte();
						DatagramPacket outgoingPacket = new DatagramPacket(
								data, data.length, group.getAddress(),
								group.getPort());

						group.getSocket().send(outgoingPacket);
						if (message.getCommand() == Announcement.MESSAGE) {
							if (!sentMessagesList.contains(message)) {
								sentMessagesList.add(message);
							}
						}
					} catch (IOException e) {
						alive = false;
						logger.error(e);
					} catch (InterruptedException e) {
						logger.debug("Sender interrupted");
					}
				}
				logger.debug("Sender stopped");
			}
		});
		thread.setDaemon(true);
		thread.setName("Sender");
		thread.start();
	}

	void shutdown() {
		logger.debug("Stop Sender Thread");
		alive = false;
		thread.interrupt();
	}

	void resendMessage(int messageID, int peerID) {

		for (Announcement stored : sentMessagesList) {
			if (stored.getMessageID() == messageID) {
				if (stored.getPeerID() == peerID) {
					pushMessage(stored);
					return;
				}
			}
		}
	}

	void pushMessage(Announcement toBeDeliverd) {
		try {
			if (!waitingForSendQueue.contains(toBeDeliverd)) {
				waitingForSendQueue.put(toBeDeliverd);
			} else {
				ReliableMulticast.logger
						.debug("Discarded duplicate message in send queue");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}