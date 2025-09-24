package edu.concordia.dpis.fifo;

import java.io.IOException;

import edu.concordia.dpis.commons.Imessenger;
import edu.concordia.dpis.commons.Message;
import edu.concordia.dpis.commons.MessageTransformer;
import edu.concordia.dpis.commons.TimeoutException;
import edu.concordia.dpis.messenger.UDPClient;

public class FIFO implements Imessenger {

	private Imessenger messenger;

	private MulticastSender multicastSender;

	private static int sequenceNumber = 0;

	public static final FIFO INSTANCE = new FIFO(new UDPClient());

	private FIFO(Imessenger messenger) {
		this.messenger = messenger;
		this.multicastSender = new MulticastSender(3000, "230.0.0.1");
	}

	@Override
	public Message send(Message msg, int timeout) throws TimeoutException {
		if (msg.getSequenceNumber() == -1) {
			msg.setSequenceNumber(newSequenceNumber());
		}
		return messenger.send(msg, timeout);
	}

	private int newSequenceNumber() {
		return ++sequenceNumber;
	}

	public void multicast(Message msg) throws IOException {
		this.multicastSender.send(MessageTransformer.serializeMessage(msg));
	}
}