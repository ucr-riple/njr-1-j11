package com.dhana.broker.impl;

import java.util.concurrent.TimeoutException;

import com.dhana.broker.Message;
import com.dhana.broker.MessageConsumer;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.impl.AbstractDestination;
import com.dhana.broker.impl.AsyncMessageConsumer;
import com.dhana.broker.impl.Destination;

public class MockMessageConsumer implements MessageConsumer,AsyncMessageConsumer {

	Destination dest;

	public MockMessageConsumer(AbstractDestination destination) throws DestinationClosedException {
		this.dest = destination;
		this.dest.addSubscriber(this);
	}

	public Message receive() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	public Message receive(int timeOutMilliseconds)
			throws InterruptedException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() {
		this.dest.removeSubscriber(this);

	}

	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
