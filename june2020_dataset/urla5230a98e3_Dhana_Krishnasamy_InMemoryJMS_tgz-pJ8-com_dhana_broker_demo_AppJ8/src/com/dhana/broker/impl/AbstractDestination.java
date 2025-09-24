package com.dhana.broker.impl;

import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhana.broker.Message;
import com.dhana.broker.exceptions.DestinationClosedException;

public abstract class AbstractDestination extends Observable implements
		Destination {
	static Logger logger = LoggerFactory.getLogger(AbstractDestination.class);
	protected String name;
	protected java.util.Queue<Message> queue = new ConcurrentLinkedQueue<Message>();
	protected CopyOnWriteArrayList<AsyncMessageConsumer> subscribers = new CopyOnWriteArrayList<AsyncMessageConsumer>();
	protected volatile boolean isClosed = false;
	protected AtomicInteger publishersCount = new AtomicInteger(0);
	protected PoisonMessage poison = new PoisonMessage();
	
	public AbstractDestination(String name) {
		if (name == null)
			throw new AssertionError("name cannot be null");
		this.name = name;
	}

	public boolean put(Message message) throws DestinationClosedException {
		if (isClosed)
			throw new DestinationClosedException("Destination closed");
		if (message == null)
			throw new NullPointerException("Message cannot be null");
		boolean ret = queue.offer(message);
		logger.debug("Message Added:" + message.getMessage());
		return ret;
	}

	public void close() {
		logger.info("Shutting down Destination:" + name);
		isClosed = true;
		queue.offer(poison);
		queue.clear();
		deleteObservers();
	}

	public void addSubscriber(AsyncMessageConsumer subscriber) throws DestinationClosedException {
		if (isClosed)
			throw new DestinationClosedException("Destination closed");
		if (subscriber == null)
			throw new AssertionError("subscriber cannot be null");
		subscribers.add(subscriber);
	}

	public void removeSubscriber(AsyncMessageConsumer subscriber) {
		subscribers.remove(subscriber);
		notifyIfRequired();
	}

	public void openPublisher() throws DestinationClosedException {
		if (isClosed)
			throw new DestinationClosedException("Destination closed");
		this.publishersCount.incrementAndGet();
	}

	public void closePublisher() {
		this.publishersCount.decrementAndGet();
		notifyIfRequired();
	}

	// Let the broker know that all the clients have closed
	private void notifyIfRequired() {
		if (this.publishersCount.get() < 1 && subscribers.size() < 1) {
			setChanged();
			notifyObservers(this.name);
		}

	}

	public int getMessageCount() {
		return this.queue.size();
	}
}
