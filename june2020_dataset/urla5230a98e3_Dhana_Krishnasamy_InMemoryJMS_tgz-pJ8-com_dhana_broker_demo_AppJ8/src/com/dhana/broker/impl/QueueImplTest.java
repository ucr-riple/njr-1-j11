package com.dhana.broker.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dhana.broker.Message;
import com.dhana.broker.MockMessage;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.impl.AsyncMessageConsumer;
import com.dhana.broker.impl.QueueImpl;

public class QueueImplTest extends AbstractDestinationTest {

	@Override
	@Before
	public void setup() {
		this.topicName = "destination";
		this.destination = new QueueImpl(topicName);
	}

	@Override
	@After
	public void tearDown() {
		this.destination.close();
	}

	@Override
	@Test
	public void testPutMultipleMessages() {
		try {
			int noOfMessages = 5;
			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage("TestMessage");
				destination.put(msg);
			}
			// Get count on ConcurrentQueue is not accurate, So ignore count
			// checking
		} catch (Throwable e) {
			fail("Error while putting message in topic" + e.getMessage());
		}

	}

	@Test
	public void testGetMessageOneSubscriber() {
		try {
			final String message = "TestMessage-testGetMessage_OneSubscriber";
			final CountDownLatch gate = new CountDownLatch(1);
			final AtomicInteger msgCount = new AtomicInteger(0);
			final Message expected = new MockMessage(message);
			AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
				@Override
				public void onMessage(Message received) {

					assertNotNull("Received Null Message", received);
					assertEquals("Message values are not equal", message,
							received.getMessage());
					msgCount.incrementAndGet();
					gate.countDown();
				}
			};
			destination.addSubscriber(testSubscriber);
			destination.put(expected);
			boolean messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertTrue("Did not receive message in 100ms", messageReceived);
			assertEquals("Unexpected number of messages received", 1,
					msgCount.get());
		} catch (InterruptedException e) {
			fail("Wait InterruptedException " + e.getMessage());
		} catch (DestinationClosedException e) {
			fail("DestinationClosedException" + e.getMessage());
		}
	}

	@Test
	public void testPutGetMessagesInOrder() {
		try {
			int noOfMessages = 5;
			final CountDownLatch gate = new CountDownLatch(noOfMessages);
			final ArrayList<Message> list = new ArrayList<Message>(noOfMessages);
			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage("TestMessage");
				list.add(i, msg);
			}
			AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
				int cnt = 0;

				@Override
				public void onMessage(Message received) {

					assertNotNull("Received Null Message", received);
					assertEquals("Message values are not equal", list.get(cnt),
							received);
					cnt++;
					gate.countDown();

				}
			};
			destination.addSubscriber(testSubscriber);
			for (Message message : list) {
				destination.put(message);
			}
			boolean messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertTrue("Did not receive message in 100ms", messageReceived);
		} catch (Throwable e) {
			fail("Error while putting message in topic" + e.getMessage());
		}

	}

	@Test
	public void testGetMessageNoUnexpectedMessages() {
		try {
			int noOfMessages = 2;
			final String message = "TestMessage-testGetMessage_OneSubscriber";
			final CountDownLatch gate = new CountDownLatch(1);
			final AtomicInteger msgCount = new AtomicInteger(0);
			final Message expected = new MockMessage(message);
			AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
				@Override
				public void onMessage(Message received) {

					assertNotNull("Received Null Message", received);
					assertEquals("Message values are not equal", message,
							received.getMessage());
					msgCount.incrementAndGet();
					gate.countDown();
				}
			};
			destination.addSubscriber(testSubscriber);
			for (int i = 0; i < noOfMessages; i++)
				destination.put(expected);
			boolean messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertTrue("Did not receive message in 100ms", messageReceived);
			Callable<Integer> wait = new Callable<Integer>() {

				public Integer call() throws Exception {
					Thread.sleep(500);
					return msgCount.get();
				}
			};
			FutureTask<Integer> future = new FutureTask<Integer>(wait);
			future.run();
			assertEquals("Unexpected number of messages received",
					noOfMessages, future.get().intValue());
		} catch (InterruptedException e) {
			fail("Wait InterruptedException " + e.getMessage());
		} catch (ExecutionException e) {
			fail("Wait ExecutionException " + e.getMessage());
			e.printStackTrace();
		} catch (DestinationClosedException e) {
			fail("DestinationClosedException" + e.getMessage());
		}
	}

	@Test
	public void testGetMessageMultipleSubscribers() {
		try {
			int noOfSubscribers = 2;
			final AtomicInteger msgCount = new AtomicInteger(0);
			final String message = "TestMessage-testGetMessage_MultipleSubscribers";
			final CountDownLatch gate = new CountDownLatch(noOfSubscribers);
			final Message expected = new MockMessage(message);
			for (int i = 0; i < noOfSubscribers; i++) {
				AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
					@Override
					public void onMessage(Message received) {

						assertNotNull("Received Null Message", received);
						assertEquals("Message values are not equal", message,
								received.getMessage());

						msgCount.incrementAndGet();
						gate.countDown();
					}
				};
				destination.addSubscriber(testSubscriber);
			}

			destination.put(expected);
			boolean messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertFalse("Message was delivered to all the subscribers",
					messageReceived);
			assertEquals("Unexpected number of messages received", 1,
					msgCount.get());
		} catch (InterruptedException e) {
			fail("Wait InterruptedException" + e.getMessage());
		} catch (DestinationClosedException e) {
			fail("DestinationClosedException" + e.getMessage());
		}

	}

	@Test
	public void testMultipleMessagesMultipleSubscribers() {
		int noOfSubscribers = 2;
		int noOfMessages = 5;
		final HashMap<String, AtomicInteger> messages = new HashMap<String, AtomicInteger>();
		final AtomicInteger msgCount = new AtomicInteger(0);
		try {

			final String message = "TestMessage-testGetMessage_MultipleSubscribers";
			final CountDownLatch gate = new CountDownLatch(noOfSubscribers
					* noOfMessages);
			for (int i = 0; i < noOfSubscribers; i++) {
				AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
					@Override
					public void onMessage(Message received) {

						assertNotNull("Received Null Message", received);
						msgCount.incrementAndGet();
						gate.countDown();
					}
				};
				destination.addSubscriber(testSubscriber);
			}

			for (int i = 0; i < noOfMessages; i++) {
				messages.put(message + i, new AtomicInteger(0));
			}
			for (String m : messages.keySet()) {
				Message msg = new MockMessage(m);
				destination.put(msg);
			}
			gate.await(500, TimeUnit.MILLISECONDS);
			// Message could be delivered to same of different consumers
			// assertFalse("Did not receive message in 500ms", messageReceived);
			assertEquals("Unexpected number of messages received",
					noOfMessages, msgCount.get());

		} catch (InterruptedException e) {
			fail("Wait InterruptedException" + e.getMessage());
		} catch (DestinationClosedException e) {
			fail("DestinationClosedException" + e.getMessage());
		}

	}

	@Test
	public void testNoMessageForUnsubscribed() {
		try {
			String message = "TestMessage-testNoMessageForUnsubscribed";
			final CountDownLatch gate = new CountDownLatch(1);
			final Message expected = new MockMessage(message);
			AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
				@Override
				public void onMessage(Message received) {
					gate.countDown();
					fail("Message delivered to unsubscribed client");
				}
			};
			destination.addSubscriber(testSubscriber);
			destination.removeSubscriber(testSubscriber);
			destination.put(expected);
			boolean messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertFalse("Message delivered to unsubscribed client",
					messageReceived);
		} catch (InterruptedException e) {
			fail("Wait InterruptedException " + e.getMessage());
		} catch (DestinationClosedException e) {
			fail("DestinationClosedException" + e.getMessage());
		}

	}

	@Test
	public void testMessageForReSubscribed() {
		try {
			String message = "TestMessage-testNoMessageForUnsubscribed";
			final CountDownLatch gate = new CountDownLatch(1);
			final Message expected = new MockMessage(message);
			AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
				@Override
				public void onMessage(Message received) {			
					gate.countDown();
				}
			};
			destination.openPublisher();
			destination.addSubscriber(testSubscriber);
			destination.removeSubscriber(testSubscriber);
			destination.put(expected);
			boolean messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertFalse("Message delivered to unsubscribed client",
					messageReceived);
			destination.addSubscriber(testSubscriber);
			messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertTrue("Message was not delivered to resubscribed client",
					messageReceived);
		} catch (InterruptedException e) {
			fail("Wait InterruptedException " + e.getMessage());
		} catch (DestinationClosedException e) {
			fail("DestinationClosedException" + e.getMessage());
		}

	}

	@Test
	public void testMultipleMessageForReSubscribed() {
		try {
			int noOfMessages = 4;
			String message = "TestMessage-testNoMessageForUnsubscribed";
			final Semaphore gate = new Semaphore((noOfMessages - 1) * -1);
			final Message expected = new MockMessage(message);
			AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
				@Override
				public void onMessage(Message received) {
					gate.release();
				}
			};
			destination.openPublisher();
			destination.addSubscriber(testSubscriber);
			destination.removeSubscriber(testSubscriber);
			destination.put(expected);
			boolean messageReceived = gate.tryAcquire(100,
					TimeUnit.MILLISECONDS);
			assertFalse("Message delivered to unsubscribed client",
					messageReceived);
			destination.put(expected);
			destination.addSubscriber(testSubscriber);
			destination.put(expected);
			destination.put(expected);
			messageReceived = gate.tryAcquire(100, TimeUnit.MILLISECONDS);
			assertTrue("Message was not delivered to resubscribed client:"
					+ gate.availablePermits(), messageReceived);
		} catch (InterruptedException e) {
			fail("Wait InterruptedException " + e.getMessage());
		} catch (DestinationClosedException e) {
			fail("DestinationClosedException" + e.getMessage());
		}

	}

	@Test(expected = DestinationClosedException.class)
	public void testNoMessageAfterCloseWithSubscribers()
			throws DestinationClosedException {
		final String message = "TestMessage-testNoMessageAfterClose";
		final CountDownLatch gate = new CountDownLatch(1);
		final Message expected = new MockMessage(message);
		AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
			public void onMessage(Message received) {

				assertNotNull("Received Null Message", received);
				assertEquals("Message values are not equal", message,
						received.getMessage());
				gate.countDown();
			}
		};
		try {
			destination.addSubscriber(testSubscriber);
			destination.close();
		} catch (Exception e) {
			fail("unexpected exception");
		}

		destination.put(expected);

	}

	@Test(expected = DestinationClosedException.class)
	public void testNoMessageAfterCloseWithReSubscribers()
			throws DestinationClosedException {
		final String message = "TestMessage-testNoMessageAfterClose";
		final CountDownLatch gate = new CountDownLatch(1);
		final Message expected = new MockMessage(message);
		AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
			public void onMessage(Message received) {

				assertNotNull("Received Null Message", received);
				assertEquals("Message values are not equal", message,
						received.getMessage());
				gate.countDown();
			}
		};
		destination.addSubscriber(testSubscriber);
		destination.close();
		destination.put(expected);
		destination.addSubscriber(testSubscriber);

	}

	@Test
	public void testUniqueMessageForAClient() {
		try {
			int noOfSubscribers = 2;
			final AtomicInteger msgCount = new AtomicInteger(0);
			final String message = "TestMessage-testUniqueMessageForEachClient";
			final CountDownLatch gate = new CountDownLatch(noOfSubscribers);
			final Message expected = new MockMessage(message);
			for (int i = 0; i < noOfSubscribers; i++) {
				AsyncMessageConsumer testSubscriber = new MockQueueReceiver() {
					public void onMessage(Message received) {
						msgCount.incrementAndGet();
						assertNotNull("Received Null Message", received);
						assertFalse("Message values are not equal",
								expected == received);
						received.setMessage("Received at:" + new Date());
						gate.countDown();
					}
				};
				destination.addSubscriber(testSubscriber);
			}
			destination.put(expected);
			boolean messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertFalse("Message was delivered to more than one subscribers",
					messageReceived);
			assertEquals("Unexpected number of messages received", 1,
					msgCount.get());
		} catch (InterruptedException e) {
			fail("Wait InterruptedException" + e.getMessage());
		} catch (DestinationClosedException e) {
			fail("DestinationClosedException" + e.getMessage());
		}
	}
}
