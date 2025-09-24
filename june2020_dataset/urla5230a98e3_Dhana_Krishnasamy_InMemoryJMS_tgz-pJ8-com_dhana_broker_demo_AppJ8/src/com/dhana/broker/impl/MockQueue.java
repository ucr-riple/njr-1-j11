package com.dhana.broker.impl;

import java.util.LinkedList;
import java.util.List;

import com.dhana.broker.Message;
import com.dhana.broker.impl.AsyncMessageConsumer;
import com.dhana.broker.impl.Queue;

public class MockQueue implements Queue {
	AsyncMessageConsumer receiver;

	LinkedList<Message> messages = new LinkedList<Message>();
	int publisherCount;

	public List<Message> getMessages() {
		return this.messages;
	}

	public boolean put(Message message) {
		messages.add(message);
		return true;
	}

	public void addSubscriber(AsyncMessageConsumer testSubscriber) {
		this.receiver = testSubscriber;

	}

	public void removeSubscriber(AsyncMessageConsumer subscriber) {
		if (this.receiver == subscriber)
			this.receiver = null;

	}

	public void close() {

	}

	public int getPublisherCount() {
		return publisherCount;
	}

	public void closePublisher() {

		--publisherCount;
	}

	public void openPublisher() {
		++publisherCount;

	}

}
