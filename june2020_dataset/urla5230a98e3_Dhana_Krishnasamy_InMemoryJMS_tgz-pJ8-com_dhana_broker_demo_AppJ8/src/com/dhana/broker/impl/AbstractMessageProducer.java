package com.dhana.broker.impl;

import com.dhana.broker.MessageProducer;
import com.dhana.broker.exceptions.DestinationClosedException;

public abstract class AbstractMessageProducer implements MessageProducer {

	protected Destination destination;
	protected static long defaultTTL = 500;

	public AbstractMessageProducer(Destination destination) throws DestinationClosedException {
		if (destination == null)
			throw new AssertionError("Destination cannot be null");
		this.destination = destination;
		this.destination.openPublisher();
	}


	public void close() {
		this.destination.closePublisher();
	}

}
