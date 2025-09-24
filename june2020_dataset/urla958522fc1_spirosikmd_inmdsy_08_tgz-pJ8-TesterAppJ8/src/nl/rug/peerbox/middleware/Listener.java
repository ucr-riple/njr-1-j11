package nl.rug.peerbox.middleware;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

import org.apache.log4j.Logger;

final class Listener {

	private static final Logger logger = Logger.getLogger(Listener.class);
	
	private final ReliableMulticast group;

	Listener(ReliableMulticast group) {
		this.group = group;
	}

	void start() {
		
		Thread listenerThread = new Thread(new Runnable() {

			public void run() {
				try {

					logger.info("Start listening for incoming packages");
					while (!Thread.interrupted()) {
						DatagramPacket incoming = new DatagramPacket(
								new byte[Announcement.MAX_MESSAGE_SIZE],
								Announcement.MAX_MESSAGE_SIZE);
						group.getSocket().receive(incoming);
						group.getReceiver().pushDataPacket(incoming);
					}

				} catch (SocketException se) {
					// shutdown listener, but actually nothing to do
					logger.debug("Stopped listening for incoming packages");
				} catch (IOException ioe) {
					logger.error(ioe);
				}
				logger.debug("Listener stopped");
			}
		});
		listenerThread.setDaemon(true);
		listenerThread.setName("Listener");
		listenerThread.start();
	}

}