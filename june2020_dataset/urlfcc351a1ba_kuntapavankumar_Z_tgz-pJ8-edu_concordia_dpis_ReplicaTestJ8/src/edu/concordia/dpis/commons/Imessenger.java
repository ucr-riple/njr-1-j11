package edu.concordia.dpis.commons;

import edu.concordia.dpis.commons.Message;

// A messenger which can send a message and expects a reply within the given timeout
public interface Imessenger {
	// send the message
	public Message send(Message msg, int timeout) throws TimeoutException;

}
