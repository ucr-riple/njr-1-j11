package com.dhana.broker;

import com.dhana.broker.exceptions.DestinationClosedException;

public interface QueueSender extends MessageProducer {
	void sendMessage(Message message) throws DestinationClosedException;
}
