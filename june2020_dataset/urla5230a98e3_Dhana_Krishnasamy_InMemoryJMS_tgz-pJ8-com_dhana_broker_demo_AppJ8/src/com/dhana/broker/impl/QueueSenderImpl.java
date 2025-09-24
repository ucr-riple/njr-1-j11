package com.dhana.broker.impl;

import com.dhana.broker.Message;
import com.dhana.broker.QueueSender;
import com.dhana.broker.exceptions.DestinationClosedException;

public class QueueSenderImpl extends AbstractMessageProducer implements
		QueueSender {

	public QueueSenderImpl(Queue destination) throws DestinationClosedException {
		super(destination);
	}

	public void sendMessage(Message message) throws DestinationClosedException {
		if (message == null)
			throw new NullPointerException("Message is null");
		message.setExpiration(System.currentTimeMillis() + defaultTTL);
		this.destination.put(message);
	}

	public Queue getQueue() {
		
		return (Queue) this.destination;
	}
}
