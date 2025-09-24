package com.dhana.broker.impl;

import com.dhana.broker.Message;
import com.dhana.broker.TopicSubscriber;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.impl.AsyncMessageConsumer;
import com.dhana.broker.impl.Topic;

class MockTopicSubscriber implements TopicSubscriber, AsyncMessageConsumer {

	Topic topic;

	public MockTopicSubscriber() {

	}

	public MockTopicSubscriber(Topic t) throws DestinationClosedException {
		this.topic = t;
		this.topic.addSubscriber(this);
	}

	public void onMessage(Message msg) {

	}

	public Message receive() {
		return null;
	}

	public Message receive(int timeOutMilliseconds) throws InterruptedException {
		return null;
	}

	public void close() {
		topic.removeSubscriber(this);

	}

}