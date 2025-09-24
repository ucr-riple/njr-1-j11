package com.dhana.broker.demo;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhana.broker.BrokerFactory;
import com.dhana.broker.Message;
import com.dhana.broker.QueueReceiver;
import com.dhana.broker.QueueSender;
import com.dhana.broker.TopicPublisher;
import com.dhana.broker.TopicSubscriber;
import com.dhana.broker.exceptions.BrokerError;
import com.dhana.broker.exceptions.DestinationClosedException;

/**
 * Hello world!
 * 
 */
public class App {
	static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		testTopic();
		testQueue();
	}

	private static void testTopic() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();

		logger.info("==========    Topic Test ============");
		int noPublishers = 8;
		int noSubscribers = 5;
		final String demoQueue1 = "demoTopic1";
		try {
			logger.info("Creating Publisher for same topic :" + demoQueue1
					+ ", no of senders:" + noPublishers);
			final TopicPublisher senders[] = new TopicPublisher[noPublishers];
			for (int i = 0; i < noPublishers; i++) {
				senders[i] = BrokerFactory.createPublisher(demoQueue1);
			}
			logger.info("Creating Subscriber for same topic :" + demoQueue1
					+ ", no of receivers:" + noSubscribers);
			final TopicSubscriber receivers[] = new TopicSubscriber[noSubscribers];
			for (int i = 0; i < noSubscribers; i++) {
				receivers[i] = BrokerFactory.createSubscriber(demoQueue1);
			}

			logger.info("Scheduling all Subscriber on same topic :"
					+ demoQueue1);
			final AtomicInteger messagesReceived = new AtomicInteger(0);
			final CountDownLatch allReady = new CountDownLatch(noSubscribers);
			for (int i = 0; i < noSubscribers; i++) {
				final int id = i;
				Callable<Void> callable = new Callable<Void>() {
					public Void call() throws InterruptedException,
							DestinationClosedException {
						allReady.countDown();
						while (!Thread.currentThread().isInterrupted()) {
							Message message = receivers[id].receive();
							logger.info("TopicSubscriber:" + id
									+ " received the message "
									+ message.getMessage() + " from queue:"
									+ demoQueue1 + ", MessageCount:"
									+ messagesReceived.incrementAndGet());
						}
						logger.info("Closing receiver:" + id);
						return null;
					}
				};
				executor.submit(callable);
			}
			allReady.await();
			logger.info("Scheduling TopicPublisher :" + demoQueue1);
			ArrayList<Future> futures = new ArrayList<Future>(noPublishers);
			for (int i = 0; i < noPublishers; i++) {
				final int id = i;
				Callable<Void> callable = new Callable<Void>() {
					public Void call() throws Exception {
						Message message = BrokerFactory.createMessage();
						message.setMessage("Queue Message:" + id);
						senders[id].publish(message);
						logger.info("TopicPublisher:" + id
								+ " sent the message " + message.getMessage()
								+ " on queue:" + demoQueue1);
						return null;
					}
				};
				futures.add(executor.submit(callable));
			}
			logger.info("Waiting for TopicPublisher to finish");
			for (Future future : futures) {
				future.get();
			}
			// Wait to make sure no duplicate deliveries
			Thread.sleep(100);
			logger.info("after TopicPublisher finished + 100ms, message sent count:"
					+ noPublishers
					+ " ReceivedCount: "
					+ messagesReceived.get()
					+ " No of subscribers: "
					+ noSubscribers);
			for (int i = 0; i < noSubscribers; i++) {
				receivers[i].close();
			}
			for (int i = 0; i < noPublishers; i++) {
				senders[i].close();
			}
			executor.shutdownNow();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void testQueue() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();

		logger.info("==========   InMemory JMS Broker Demo============");
		logger.info("==========    Queue Test ============");
		int noSenders = 8;
		int noReceivers = 5;
		final String demoQueue1 = "DemoQueue1";
		try {
			logger.info("Creating Queue Senders for same queue :" + demoQueue1
					+ ", no of senders:" + noSenders);
			final QueueSender senders[] = new QueueSender[noSenders];
			for (int i = 0; i < noSenders; i++) {
				senders[i] = BrokerFactory.createSender(demoQueue1);
			}
			logger.info("Creating Queue Receivers for same queue :"
					+ demoQueue1 + ", no of receivers:" + noReceivers);
			final QueueReceiver receivers[] = new QueueReceiver[noReceivers];
			for (int i = 0; i < noReceivers; i++) {
				receivers[i] = BrokerFactory.createReceiver(demoQueue1);
			}

			logger.info("Scheduling all receivers on same queue :" + demoQueue1);
			final AtomicInteger messagesReceived = new AtomicInteger(0);
			final CountDownLatch allReady = new CountDownLatch(noReceivers);
			for (int i = 0; i < noReceivers; i++) {
				final int id = i;
				Callable<Void> callable = new Callable<Void>() {
					public Void call() throws InterruptedException,
							DestinationClosedException {
						allReady.countDown();
						while (!Thread.currentThread().isInterrupted()) {
							Message message = receivers[id].receive();
							logger.info("QueueReciver:" + id
									+ " received the message "
									+ message.getMessage() + " from queue:"
									+ demoQueue1 + ", MessageCount:"
									+ messagesReceived.incrementAndGet());
						}
						logger.info("Closing receiver:" + id);
						return null;
					}
				};
				executor.submit(callable);
			}
			allReady.await();
			logger.info("Scheduling senders :" + demoQueue1);
			ArrayList<Future> futures = new ArrayList<Future>(noSenders);
			for (int i = 0; i < noSenders; i++) {
				final int id = i;
				Callable<Void> callable = new Callable<Void>() {
					public Void call() throws Exception {
						Message message = BrokerFactory.createMessage();
						message.setMessage("Queue Message:" + id);
						senders[id].sendMessage(message);
						logger.info("QueueSender:" + id + " sent the message "
								+ message.getMessage() + " on queue:"
								+ demoQueue1);
						return null;
					}
				};
				futures.add(executor.submit(callable));
			}
			logger.info("Waiting for senders to finish");
			for (Future future : futures) {
				future.get();
			}
			// Wait to make sure no duplicate deliveries
			Thread.sleep(100);
			logger.info("after senders finished + 100ms, message sent count:"
					+ noSenders + " ReceivedCount: " + messagesReceived.get());
			for (int i = 0; i < noReceivers; i++) {
				receivers[i].close();
			}
			for (int i = 0; i < noSenders; i++) {
				senders[i].close();
			}
			executor.shutdownNow();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
