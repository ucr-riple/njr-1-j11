package com.dhana.broker.impl;

import com.dhana.broker.MessageProducer;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.impl.AbstractDestination;
import com.dhana.broker.impl.Destination;

public class MockMessageProducer implements MessageProducer {

	Destination destination;

	public MockMessageProducer(AbstractDestination destination) throws DestinationClosedException {
		destination.openPublisher();
		this.destination = destination;
	}

	public void close() {
		this.destination.closePublisher();
	}

}
