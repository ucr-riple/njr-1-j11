package com.dhana.broker.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import com.dhana.broker.Message;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.util.MessageExpiryUtil;

public class QueueImpl extends AbstractDestination implements Queue {
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Semaphore permits = new Semaphore(0);
	private MessageExpiryUtil expiryUtil;
	private static MessageDiscardPolicy defaultDiscardPolicy = MessageDiscardPolicy.DiscardLatest;
	private static int defaultQueueSize = 20;
	private MessageDiscardPolicy discardPolicy;

	public QueueImpl(String name) {
		this(name, defaultQueueSize, defaultDiscardPolicy);
	}

	public QueueImpl(String name, int queueSize) {
		this(name, queueSize, defaultDiscardPolicy);
	}

	public QueueImpl(String name, int queueSize,
			MessageDiscardPolicy discardPolicy) {
		super(name);
		queue = new LinkedBlockingQueue<Message>(queueSize);
		this.discardPolicy = discardPolicy;
		expiryUtil = new MessageExpiryUtil(queue);
		executor.execute(new Distributor());
	}

	public boolean put(Message message) throws DestinationClosedException {
		boolean isSuccessful = super.put(message);
		if (!isSuccessful) {
			if (discardPolicy == MessageDiscardPolicy.DiscardOldest) {
				// Until successful insert
				while (!queue.offer(message)) {
					queue.poll();
				}
				expiryUtil.scheduleForExpiry(message);
			}
		} else {
			expiryUtil.scheduleForExpiry(message);
		}
		permits.release();
		logger.debug("Message Added to the queue:" + message.getMessage());
		return true;
	}

	class Distributor implements Runnable {

		public void run() {
			while (true) {
				try {
					logger.debug("Waiting for new message");
					permits.acquire();
					Message msg = queue.peek();
					if (msg == null) {
						// May be a new subscriber was added
						continue;
					}
					if (msg == poison) {
						logger.info("Received close signal, closing queue:"
								+ name);
						break;
					}
					if (!msg.hasExpired() && !subscribers.isEmpty()) {
						AsyncMessageConsumer subscriber = subscribers.get(0);
						subscriber.onMessage(msg.clone());
						logger.debug("Message sent to a client");
						queue.remove(msg);
					}
				} catch (InterruptedException e) {
					logger.debug("Interrupted exception");
					Thread.currentThread().interrupt();
					break;
				}
			}

		}

	}

	public void addSubscriber(AsyncMessageConsumer subscriber)
			throws DestinationClosedException {
		super.addSubscriber(subscriber);
		if (!queue.isEmpty())
			permits.release(queue.size());
	}

	public void close() {
		super.close();
		
		expiryUtil.cancel();
		executor.shutdownNow();
	}
}
