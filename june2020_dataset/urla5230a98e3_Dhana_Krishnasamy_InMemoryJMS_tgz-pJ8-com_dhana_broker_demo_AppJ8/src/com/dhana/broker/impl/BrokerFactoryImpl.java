package com.dhana.broker.impl;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhana.broker.Message;
import com.dhana.broker.QueueReceiver;
import com.dhana.broker.QueueSender;
import com.dhana.broker.TopicPublisher;
import com.dhana.broker.TopicSubscriber;
import com.dhana.broker.exceptions.BrokerError;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.impl.BrokerFactoryImpl.DestinationType;

public class BrokerFactoryImpl {

	enum DestinationType {
		TOPIC, QUEUE
	}

	static final int noOfLocks = 16;
	static Logger logger = LoggerFactory.getLogger(BrokerFactoryImpl.class);
	static ConcurrentHashMap<String, FutureTask<Topic>> topicCache = new ConcurrentHashMap<String, FutureTask<Topic>>();
	static ReadWriteLock[] topicLocks = new ReadWriteLock[noOfLocks]; 
	static CacheCleaner<Topic> topicCleaner = new CacheCleaner<Topic>(
			topicCache, DestinationType.TOPIC);

	static ConcurrentHashMap<String, FutureTask<Queue>> queueCache = new ConcurrentHashMap<String, FutureTask<Queue>>();
	static ReadWriteLock[] queueLocks = new ReadWriteLock[noOfLocks];
	static CacheCleaner<Queue> queueCleaner = new CacheCleaner<Queue>(
			queueCache, DestinationType.QUEUE);

	/*
	 * static { for (int i = 0; i < noOfLocks; i++) locks[i] = new
	 * ReentrantReadWriteLock(); }
	 */

	public void  BrokerFactoryImpl1() {
		this.queueCache.clear();;
	}
	public static void init() {
		for (int i = 0; i < noOfLocks; i++) {
			topicLocks[i] = new ReentrantReadWriteLock();
			queueLocks[i] = new ReentrantReadWriteLock();
		}
	}

	public static void close() {
		topicCache.clear();
		queueCache.clear();
	}

	public static TopicPublisher createPublisher(final String topicName)
			throws InterruptedException, BrokerError {
		try {
			return new TopicPublisherImpl(getTopicFor(topicName));
		} catch (ExecutionException e) {
			logger.error("Error while creating Topic", e);
			throw new InterruptedException(
					"Error while creating TopicPublisher:" + e.getCause());
		} catch (DestinationClosedException e) {
			// Oops this should never happen, concurrency issue?
			logger.error("Found a closed topic in the cache, cache corrupted",
					e);
			throw new BrokerError("Found cache corruption while creating topic");
		}

	}

	public static TopicSubscriber createSubscriber(String topicName)
			throws InterruptedException, BrokerError {
		try {
			return new TopicSubscriberImpl(getTopicFor(topicName));
		} catch (ExecutionException e) {
			logger.error("Error while creating Topic", e);
			throw new InterruptedException(
					"Error while creating TopicPublisher:" + e.getCause());
		} catch (DestinationClosedException e) {
			// Oops this should never happen, concurrency issue?
			logger.error("Found a closed topic in the cache, cache corrupted",
					e);
			throw new BrokerError("Found cache corruption while creating topic");
		}
	}

	public static Message createMessage() {
		return new MessageImpl();
	}

	public static QueueSender createSender(String queueName)
			throws InterruptedException, BrokerError {
		try {
			return new QueueSenderImpl(getQueueFor(queueName));
		} catch (ExecutionException e) {
			logger.error("Error while creating queue", e);
			throw new InterruptedException("Error while creating QueueSender:"
					+ e.getCause());
		} catch (DestinationClosedException e) {
			// Oops this should never happen, concurrency issue?
			logger.error("Found a closed topic in the cache, cache corrupted",
					e);
			throw new BrokerError("Found cache corruption while creating queue");
		}

	}

	public static QueueReceiver createReceiver(String queueName)
			throws InterruptedException, BrokerError {
		try {
			return new QueueReceiverImpl(getQueueFor(queueName));
		} catch (ExecutionException e) {
			logger.error("Error while creating queue", e);
			throw new InterruptedException(
					"Error while creating QueueReceiver:" + e.getCause());
		} catch (DestinationClosedException e) {
			// Oops this should never happen, concurrency issue?
			logger.error("Found a closed topic in the cache, cache corrupted",
					e);
			throw new BrokerError("Found cache corruption while creating queue");
		}
	}

	private static Topic getTopicFor(String topicName)
			throws InterruptedException, ExecutionException {
		final ReadWriteLock lock = getTopicLock(topicName);
		lock.readLock().lock();
		try {
			FutureTask<Topic> f = topicCache.get(topicName);
			if (f == null) {
				FutureTask<Topic> future = getNewTopicFuture(topicName);
				f = topicCache.putIfAbsent(topicName, future);
				if (f == null) {
					f = future;
					future.run();
				}
			}
			Topic topic = f.get();
			return topic;
		} finally {
			lock.readLock().unlock();
		}
	}

	private static Queue getQueueFor(String queueName)
			throws InterruptedException, ExecutionException {
		final ReadWriteLock lock = getTopicLock(queueName);
		lock.readLock().lock();
		try {
			FutureTask<Queue> f = queueCache.get(queueName);
			if (f == null) {
				FutureTask<Queue> future = getNewQueueFuture(queueName);
				f = queueCache.putIfAbsent(queueName, future);
				if (f == null) {
					f = future;
					future.run();
				}
			}
			Queue topic = f.get();
			return topic;
		} finally {
			lock.readLock().unlock();
		}
	}

	private static FutureTask<Topic> getNewTopicFuture(final String topicName) {
		Callable<Topic> callable = new Callable<Topic>() {

			public Topic call() throws Exception {
				TopicImpl t = new TopicImpl(topicName);
				t.addObserver(topicCleaner);
				return t;
			}
		};
		return new FutureTask<Topic>(callable);
	}

	private static FutureTask<Queue> getNewQueueFuture(final String queueName) {
		Callable<Queue> callable = new Callable<Queue>() {

			public Queue call() throws Exception {
				QueueImpl t = new QueueImpl(queueName);
				t.addObserver(queueCleaner);
				return t;
			}
		};
		return new FutureTask<Queue>(callable);
	}

	static ReadWriteLock getLockFor(String destName, DestinationType type) {
		if (type == DestinationType.TOPIC)
			return getTopicLock(destName);
		else
			return getQueueLock(destName);
	}

	private static ReadWriteLock getTopicLock(String topicName) {
		return topicLocks[Math.abs(topicName.hashCode() % noOfLocks)];
	}

	private static ReadWriteLock getQueueLock(String queueName) {

		return queueLocks[Math.abs(queueName.hashCode() % noOfLocks)];
	}
}

class CacheCleaner<E extends Destination> implements Observer {
	Map<String, FutureTask<E>> cache;
	DestinationType destinationType;

	CacheCleaner(Map<String, FutureTask<E>> cache,
			DestinationType destinationType) {
		this.cache = cache;
		this.destinationType = destinationType;
	}

	public void update(Observable o, Object topicName) {
		final ReadWriteLock lock = BrokerFactoryImpl.getLockFor((String) topicName,
				this.destinationType);
		FutureTask<E> f;
		lock.writeLock().lock();
		try {
			f = cache.remove(topicName);
		} finally {
			lock.writeLock().unlock();
		}
		if (f != null) {
			try {
				Destination t = f.get();
				t.close();
				f.cancel(true);
			} catch (Throwable e) {
				BrokerFactoryImpl.logger.error(
						"Error while cleaning the lock cache.", e);
				throw new RuntimeException(
						"Error while cleaning the lock cache.", e);
			}
		}
	}

}
