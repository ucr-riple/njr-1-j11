package com.dhana.broker.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhana.broker.Message;
import com.dhana.broker.exceptions.DestinationClosedException;

public class TopicImpl extends AbstractDestination implements Topic {
	static Logger logger = LoggerFactory.getLogger(TopicImpl.class);

	/**
	 * Distributor is single threaded as the subscriber is not doing much except
	 * inserting the message in a queue and some scheduling, so In my opinion single thread should
	 * perform well enough in single JVM (only few subscribers/threads). However
	 * if it proves to be bottleneck, multiple distribution threads should be
	 * introduced handing a set of subscribers per thread.
	 * **/
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Semaphore permits = new Semaphore(0);

	public TopicImpl(String name) {
		super(name);
		executor.execute(new Distributor());
	}

	public boolean put(Message message) throws DestinationClosedException {
		boolean ret = super.put(message);
		permits.release();
		logger.debug("Message Added:" + message.getMessage());
		return !ret;
	}

	class Distributor implements Runnable {

		public void run() {
			while (true) {
				try {
					logger.debug("Waiting for new message");
					permits.acquire();
					Message msg = queue.poll();
					if (msg == null) {
						logger.error("Received null message from the queue, programming error??? Ignoring the message");
						break;
					}
					if (msg == poison) {
						logger.info("Received close signal, closing queue:"
								+ name);
						break;
					}
					// Bottleneck prone - Intro multiple thread per set of subscribers
					for (AsyncMessageConsumer subscriber : subscribers) {
						subscriber.onMessage(msg.clone());
						logger.debug("Message sent to a client");
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}

		}

	}

	public void close() {
		super.close();
		executor.shutdownNow();
	}
}
