package com.dhana.broker.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhana.broker.TopicSubscriber;
import com.dhana.broker.exceptions.DestinationClosedException;

public class TopicSubscriberImpl extends AbstractMessageConsumer implements
		TopicSubscriber {
	static Logger logger = LoggerFactory.getLogger(TopicSubscriberImpl.class);

	TopicSubscriberImpl(Topic topic) throws DestinationClosedException {
		super(topic);
	}

	TopicSubscriberImpl(Topic topic, int queueSize) throws DestinationClosedException {
		super(topic, queueSize);
	}

	TopicSubscriberImpl(Topic topic, int queueSize,
			MessageDiscardPolicy discardPolicy) throws DestinationClosedException {
		super(topic, queueSize, discardPolicy);

	}


	public Topic getTopic() { 
		return (Topic) this.destination;
	}

}
