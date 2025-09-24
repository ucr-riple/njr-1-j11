package com.dhana.broker.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhana.broker.Message;
import com.dhana.broker.MessageConsumer;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.util.MessageExpiryUtil;

public abstract class AbstractMessageConsumer implements MessageConsumer,
		AsyncMessageConsumer {
	static Logger logger = LoggerFactory
			.getLogger(AbstractMessageConsumer.class);
	private static Message poison = new PoisonMessage();
	final LinkedBlockingQueue<Message> queue;
	protected Destination destination;
	private MessageDiscardPolicy discardPolicy;
	private static MessageDiscardPolicy defaultDiscardPolicy = MessageDiscardPolicy.DiscardLatest;
	private static int defaultQueueSize = 10;
	private Executor timedReciveExecutor = Executors.newCachedThreadPool();
	private MessageExpiryUtil expiryUtil;
	private volatile boolean isClosed = false;

	AbstractMessageConsumer(Destination destination)
			throws DestinationClosedException {
		this(destination, defaultQueueSize, defaultDiscardPolicy);
	}

	AbstractMessageConsumer(Destination destination, int queueSize)
			throws DestinationClosedException {
		this(destination, queueSize, defaultDiscardPolicy);
	}

	AbstractMessageConsumer(Destination destination, int queueSize,
			MessageDiscardPolicy discardPolicy)
			throws DestinationClosedException {
		if (destination == null)
			throw new AssertionError("Topic cannot be null");
		this.destination = destination;
		this.discardPolicy = discardPolicy;
		queue = new LinkedBlockingQueue<Message>(queueSize);
		expiryUtil = new MessageExpiryUtil(queue);
		destination.addSubscriber(this);
	}

	/** Topic callback **/
	public void onMessage(Message msg) {
		if (msg == null)
			throw new AssertionError("message cannot be null");
		boolean isSuccessful = queue.offer(msg);
		if (!isSuccessful) {
			if (discardPolicy == MessageDiscardPolicy.DiscardOldest) {
				// Until successful insert
				while (!queue.offer(msg)) {
					queue.poll();
				}
				expiryUtil.scheduleForExpiry(msg);
			}
		} else {
			expiryUtil.scheduleForExpiry(msg);
		}
	}

	/** Client methods **/
	public void close() {
		logger.info("Closing MessageConsumer");
		destination.removeSubscriber(this);
		queue.offer(poison);
		queue.clear();
		expiryUtil.cancel();
		isClosed = true;
	}

	public Message receive() throws InterruptedException,
			DestinationClosedException {
		if (isClosed)
			throw new DestinationClosedException("MessageConsumer closed");
		Message m;
		// Make sure expired message is not delivered
		while (true) {
			m = queue.take();
			if (m == poison) {
				logger.info("Close signal received");
				throw new DestinationClosedException("MessageConsumer closed");
			}
			if (!m.hasExpired())
				return m;
			else
				logger.debug("Message Expired:" + m);
		}
	}

	public Message receive(int timeOutMilliseconds)
			throws InterruptedException, DestinationClosedException {
		if (isClosed)
			throw new DestinationClosedException("MessageConsumer closed");
		FutureTask<Message> future = getReceiveFuture();
		timedReciveExecutor.execute(future);
		try {
			return future.get(timeOutMilliseconds, TimeUnit.MILLISECONDS);
		} catch (ExecutionException e) {
			if (e.getCause() instanceof DestinationClosedException)
				throw ((DestinationClosedException) e.getCause());

			throw new InterruptedException("Error while calling receive():"
					+ e.getCause());
		} catch (TimeoutException e) {
			logger.debug("Receive timedout in " + timeOutMilliseconds);
		} finally {
			future.cancel(true);
			if (logger.isDebugEnabled())
				logExecutorStatus();
		}

		return null;
	}

	private void logExecutorStatus() {
		if (timedReciveExecutor instanceof ThreadPoolExecutor) {
			ThreadPoolExecutor e = ((ThreadPoolExecutor) timedReciveExecutor);
			long count = e.getTaskCount() - e.getCompletedTaskCount();
			logger.debug("Number of pending tasks: " + count);
		}

	}

	private FutureTask<Message> getReceiveFuture() {
		Callable<Message> callable = new Callable<Message>() {
			public Message call() throws Exception {
				return receive();
			}
		};

		return new FutureTask<Message>(callable);
	}

}

class PoisonMessage implements Message {

	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setMessage(String message) {
		// TODO Auto-generated method stub

	}

	public void setExpiration(long timeStamp) {
		// TODO Auto-generated method stub

	}

	public long getExpiration() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public Message clone() {
		return null;
	}
}
