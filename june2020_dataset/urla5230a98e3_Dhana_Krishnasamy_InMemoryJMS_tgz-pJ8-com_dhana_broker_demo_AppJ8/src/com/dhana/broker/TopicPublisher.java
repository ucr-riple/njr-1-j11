package com.dhana.broker;

import com.dhana.broker.exceptions.DestinationClosedException;

public interface TopicPublisher extends MessageProducer {
	void publish(Message message) throws DestinationClosedException;
}
