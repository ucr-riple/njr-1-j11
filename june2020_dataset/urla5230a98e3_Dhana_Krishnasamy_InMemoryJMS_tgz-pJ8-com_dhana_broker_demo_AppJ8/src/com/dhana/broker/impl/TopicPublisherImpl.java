package com.dhana.broker.impl;

import com.dhana.broker.Message;
import com.dhana.broker.TopicPublisher;
import com.dhana.broker.exceptions.DestinationClosedException;

public class TopicPublisherImpl extends AbstractMessageProducer implements
		TopicPublisher {

	public TopicPublisherImpl(Topic topic) throws DestinationClosedException {
		super(topic);
	}

	public void publish(Message message) throws DestinationClosedException {
		if (message == null)
			throw new NullPointerException("Message is null");
		message.setExpiration(System.currentTimeMillis() + defaultTTL);
		this.destination.put(message);
	}

	public Topic getTopic() {
		return (Topic) this.destination;
	}
}
