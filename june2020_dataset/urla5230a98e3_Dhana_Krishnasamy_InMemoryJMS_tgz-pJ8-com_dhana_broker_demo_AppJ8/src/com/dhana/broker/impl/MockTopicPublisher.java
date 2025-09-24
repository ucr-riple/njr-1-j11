package com.dhana.broker.impl;

import com.dhana.broker.Message;
import com.dhana.broker.TopicPublisher;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.impl.Topic;

class MockTopicPublisher implements TopicPublisher {

	Topic topic;

	public MockTopicPublisher(Topic t) throws DestinationClosedException {
		topic = t;
		this.topic.openPublisher();
	}

	public void publish(Message message) {

	}

	public void close() {
		this.topic.closePublisher();

	}

}