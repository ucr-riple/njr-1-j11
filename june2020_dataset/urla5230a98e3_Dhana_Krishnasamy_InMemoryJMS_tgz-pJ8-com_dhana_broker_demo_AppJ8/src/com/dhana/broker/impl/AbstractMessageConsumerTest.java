package com.dhana.broker.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhana.broker.Message;
import com.dhana.broker.MessageConsumer;
import com.dhana.broker.MockMessage;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.impl.AbstractMessageConsumer;
import com.dhana.broker.impl.MessageDiscardPolicy;

public class AbstractMessageConsumerTest {

	AbstractMessageConsumer consumer;
	MockTopic mockTopic;
	static Logger logger = LoggerFactory
			.getLogger(AbstractMessageConsumerTest.class);
	static Executor e = Executors.newCachedThreadPool();

	@Before
	public void setup() {
		mockTopic = new MockTopic();
	}

	@Test(expected = AssertionError.class)
	public void testCreateTopicSubscriber_NullTopic() {
		try {
			consumer = new AbstractMessageConsumer(null) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}

	}

	@Test
	public void testAddSubscriber() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}

	}

	@Test
	public void testOnMessage() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());

			mockTopic.getTopicSubscriber().onMessage(
					new MockMessage("TestMessage"));
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage");
		}
	}

	@Test(expected = AssertionError.class)
	public void testOnMessage_NullMessage() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
		assertNotNull(mockTopic.getTopicSubscriber());
		mockTopic.getTopicSubscriber().onMessage(null);
	}

	@Test
	public void testReceive() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
		assertNotNull(mockTopic.getTopicSubscriber());
		try {
			Message msg = new MockMessage("TestMessage");
			mockTopic.getTopicSubscriber().onMessage(msg);
			FutureTask<Message> future = getMessageFuture(consumer);
			Message received = future.get(5, TimeUnit.MILLISECONDS);
			assertNotNull("Received message is null ", received);
			assertEquals("Message values dont match.", msg.getMessage(),
					received.getMessage());
		} catch (TimeoutException e) {
			fail("Did not receive message in time");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveTimeout_MessageAlreadyPresent() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
		assertNotNull(mockTopic.getTopicSubscriber());
		try {
			Message msg = new MockMessage("TestMessage");
			mockTopic.getTopicSubscriber().onMessage(msg);
			FutureTask<Message> future = getMessageFuture(consumer, 100);
			Message received = future.get(5, TimeUnit.MILLISECONDS);
			assertNotNull("Received message is null ", received);
			assertEquals("Message values dont match.", msg.getMessage(),
					received.getMessage());
		} catch (TimeoutException e) {
			fail("Timeout did not happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveTimeout_MessageArrivesLater() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
		assertNotNull(mockTopic.getTopicSubscriber());
		try {
			Message msg = new MockMessage("TestMessage");

			FutureTask<Message> future = getMessageFuture(consumer, 100);
			Thread.sleep(90);
			mockTopic.getTopicSubscriber().onMessage(msg);
			Message received = future.get(5, TimeUnit.MILLISECONDS);
			assertNotNull(
					"Received message is null / recived timedout too soon ",
					received);
			assertEquals("Message values dont match.", msg.getMessage(),
					received.getMessage());
		} catch (TimeoutException e) {
			fail("Timeout did not happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test(timeout = 200)
	public void testReceiveTimeout_MessageArrivesAfterTime() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
		assertNotNull(mockTopic.getTopicSubscriber());
		try {
			Message msg = new MockMessage("TestMessage");

			FutureTask<Message> future = getMessageFuture(consumer, 100);
			Thread.sleep(110);
			mockTopic.getTopicSubscriber().onMessage(msg);
			Message received = future.get(5, TimeUnit.MILLISECONDS);
			assertNull("Received message,after timeout, is not null ", received);
			received = consumer.receive();
			assertNotNull("Received message is null ", received);
			assertEquals("Message values dont match.", msg.getMessage(),
					received.getMessage());
		} catch (InterruptedException e) {
			fail("Timeout did not happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveNegativeTimeout_IsImmediate() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
		assertNotNull(mockTopic.getTopicSubscriber());
		Message msg = new MockMessage("TestMessage");
		try {
			FutureTask<Message> future = getMessageFuture(consumer, -6);
			Thread.sleep(4);
			mockTopic.getTopicSubscriber().onMessage(msg);
			Message received = future.get(5, TimeUnit.MILLISECONDS);
			assertNull("Received message is not null ", received);
		} catch (TimeoutException e) {

			fail("Didnot timeout in time");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
		try {
			FutureTask<Message> future = getMessageFuture(consumer);
			Message received = future.get(5, TimeUnit.MILLISECONDS);
			assertNotNull("Received message is null ", received);
			assertEquals("Message values dont match.", msg.getMessage(),
					received.getMessage());
		} catch (TimeoutException e) {
			e.printStackTrace();
			fail("Did not receive message in time");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveTimeoutWithNoMessage() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());

			FutureTask<Message> future = getMessageFuture(consumer, 100);
			Message received = future.get(110, TimeUnit.MILLISECONDS);
			assertNull("Unexpected message received ", received);
		} catch (TimeoutException e) {
			fail("Timeout didnt happen on time");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceive_InsertAfterReceive() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());

			Message msg = new MockMessage("TestMessage");
			FutureTask<Message> future = getMessageFuture(consumer);
			mockTopic.getTopicSubscriber().onMessage(msg);
			Message received = future.get(5, TimeUnit.MILLISECONDS);
			assertNotNull("Received message is null ", received);
			assertEquals("Message values dont match.", msg.getMessage(),
					received.getMessage());
		} catch (TimeoutException e) {
			fail("Did not receive message in time");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceive_InsertAfterReceive_WithDelay() {

		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());
			Message msg = new MockMessage("TestMessage");
			FutureTask<Message> future = getMessageFuture(consumer);
			Thread.sleep(100);
			mockTopic.getTopicSubscriber().onMessage(msg);
			Message received = future.get(5, TimeUnit.MILLISECONDS);
			assertNotNull("Received message is null ", received);
			assertEquals("Message values dont match.", msg.getMessage(),
					received.getMessage());
		} catch (TimeoutException e) {
			fail("Did not receive message in time");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveTimeout_InsertAfterReceive() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());

			Message msg = new MockMessage("TestMessage");
			FutureTask<Message> future = getMessageFuture(consumer, 100);
			mockTopic.getTopicSubscriber().onMessage(msg);
			Message received = future.get(110, TimeUnit.MILLISECONDS);
			assertNotNull("Received message is null ", received);
			assertEquals("Message values dont match.", msg.getMessage(),
					received.getMessage());
		} catch (TimeoutException e) {
			fail("Timeout didnot happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveTimeout_InsertExpiredAfterReceive() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());

			Message msg = new MockMessage("TestMessage");
			msg.setExpiration(0);
			FutureTask<Message> future = getMessageFuture(consumer, 50);
			mockTopic.getTopicSubscriber().onMessage(msg);
			Message received = future.get(70, TimeUnit.MILLISECONDS);
			assertNull("Received message is not null ", received);

		} catch (TimeoutException e) {
			fail("Timeout didnot happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveTimeout_InsertExpiredAndValidAfterReceive() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());

			Message msg = new MockMessage("TestMessage");
			Message valid = new MockMessage("ValidMessage");
			msg.setExpiration(0);
			FutureTask<Message> future = getMessageFuture(consumer, 50);
			mockTopic.getTopicSubscriber().onMessage(msg);
			mockTopic.getTopicSubscriber().onMessage(valid);
			Message received = future.get(70, TimeUnit.MILLISECONDS);
			assertNotNull("Received message is not null ", received);
			assertEquals("Message values dont match.", valid, received);
		} catch (TimeoutException e) {
			fail("Timeout didnot happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveMultiple() {
		int noOfMessages = 5;
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());
			HashSet<Message> messages = new HashSet<Message>();

			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage("TestMessage" + i);
				messages.add(msg);
				mockTopic.getTopicSubscriber().onMessage(msg);
			}
			for (int i = 0; i < noOfMessages; i++) {
				FutureTask<Message> future = getMessageFuture(consumer);
				Message received = future.get(100, TimeUnit.MILLISECONDS);
				assertNotNull("Received message is null ", received);
				assertTrue("Unexpected message found",
						messages.remove(received));
			}
		} catch (TimeoutException e) {
			fail("Did not receive message in time");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testRemoveSubscriptionAfterClose() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
		assertNotNull("No subscription was made",
				mockTopic.getTopicSubscriber());
		consumer.close();
		assertNull("Subscription was not removed after close",
				mockTopic.getTopicSubscriber());
	}

	@Test
	public void testThrowExceptionOnReceiveIfClosed() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull("No subscription was made",
					mockTopic.getTopicSubscriber());

			FutureTask<Message> f = getMessageFuture(consumer);
			consumer.close();
			Message m = f.get(200, TimeUnit.MILLISECONDS);
			assertNull("Unexpected message", m);
		} catch (InterruptedException e) {
			// This happnes if future.get is innterupted
			fail("InterruptedException was not thrown as expected, but wait got interrupted");
		} catch (ExecutionException e) {
			assertEquals(DestinationClosedException.class, e.getCause()
					.getClass());
			// Success
		} catch (TimeoutException e) {
			//Success 

		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
	}

	@Test
	public void testThrowExceptionOnReceiveTimeoutIfClosed() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull("No subscription was made",
					mockTopic.getTopicSubscriber());

			FutureTask<Message> f = getMessageFuture(consumer, 50);
			consumer.close();
			Message m = f.get(150, TimeUnit.MILLISECONDS);
			assertNull("Unexpected message", m);
		} catch (InterruptedException e) {
			// This happnes if future.get is innterupted
			fail("InterruptedException was not thrown as expected, but got ExecutionException");
		} catch (ExecutionException e) {
			assertEquals(DestinationClosedException.class, e.getCause()
					.getClass());
			// Success
		} catch (TimeoutException e) {
			e.printStackTrace();
			fail("InterruptedException was not thrown as expected, but got TimeoutException");

		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
	}

	@Test
	public void testReceiveMultipleTimeout() {
		int noOfMessages = 5;
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());
			HashSet<Message> messages = new HashSet<Message>();

			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage("TestMessage" + i);
				messages.add(msg);
			}
			for (Message msg : messages) {
				FutureTask<Message> future = getMessageFuture(consumer, 50);
				Thread.sleep(20);
				mockTopic.getTopicSubscriber().onMessage(msg);
				Message received = future.get(6, TimeUnit.MILLISECONDS);
				assertNotNull("Received message is null ", received);
				assertTrue("Unexpected message found",
						messages.contains(received));
			}
		} catch (TimeoutException e) {
			fail("Did not receive message in time");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveWithOrder() {
		int noOfMessages = 5;
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());
			ArrayList<Message> messages = new ArrayList<Message>(noOfMessages);

			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage("TestMessage" + i);
				messages.add(i, msg);
				mockTopic.getTopicSubscriber().onMessage(msg);
			}
			for (int i = 0; i < noOfMessages; i++) {
				FutureTask<Message> future = getMessageFuture(consumer);
				Message received = future.get(100, TimeUnit.MILLISECONDS);
				assertNotNull("Received message is null ", received);
				assertEquals("Unexpected message found", messages.get(i),
						received);
			}
		} catch (TimeoutException e) {
			fail("Did not receive message in time");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidQueueBound() {
		try {
			consumer = new AbstractMessageConsumer(mockTopic, 0) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
	}

	@Test
	public void testReceiveMultipleWithBound() {
		int noOfMessages = 5;
		int queueSize = 2;
		try {
			consumer = new AbstractMessageConsumer(mockTopic, queueSize) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());
			HashSet<Message> messages = new HashSet<Message>();

			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage("TestMessage" + i);
				// Insert only two messages, first two
				if (i < queueSize)
					messages.add(msg);
				mockTopic.getTopicSubscriber().onMessage(msg);
			}
			for (int i = 0; i < noOfMessages; i++) {
				FutureTask<Message> future = getMessageFuture(consumer, 100);
				Message received = future.get(115, TimeUnit.MILLISECONDS);
				// First two messages should expected messages, first two
				if (i < queueSize) {
					assertNotNull("Received message is null ", received);
					assertTrue("Unexpected message found",
							messages.remove(received));
				} else {
					// Rest should be null
					assertNull("Unexpected message found, message not null",
							received);
				}
			}
		} catch (TimeoutException e) {
			fail("Timeout did not happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test(timeout = 350)
	public void testReceiveMultipleDiscardOldest() {
		int noOfMessages = 5;
		int queueSize = 2;
		try {
			consumer = new AbstractMessageConsumer(mockTopic, queueSize,
					MessageDiscardPolicy.DiscardOldest) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());
			HashSet<Message> messages = new HashSet<Message>();

			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage("TestMessage" + i);
				// Insert only last few messages
				if (i > noOfMessages - queueSize - 1)
					messages.add(msg);
				mockTopic.getTopicSubscriber().onMessage(msg);
			}
			for (int i = 0; i < noOfMessages; i++) {
				FutureTask<Message> future = getMessageFuture(consumer, 100);
				Message received = future.get(110, TimeUnit.MILLISECONDS);
				// First two messages should expected messages, last two
				if (i < queueSize) {
					assertNotNull("Received message is null ", received);
					assertTrue("Unexpected message found",
							messages.remove(received));
				} else {
					// Rest should be null
					assertNull("Unexpected message found, message not null",
							received);
				}
			}
		} catch (TimeoutException e) {
			fail("Timeout did not happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveMultipleManyExpiredTwoValid() {
		int noOfMessages = 13;
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());
			HashSet<Message> messages = new HashSet<Message>();

			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage("TestMessage" + i);

				// Insert only two messages, first two
				if (i % 2 == 0)
					messages.add(msg);
				else
					msg.setExpiration(0);
				mockTopic.getTopicSubscriber().onMessage(msg);
			}
			for (int i = 0; i < messages.size(); i++) {
				FutureTask<Message> future = getMessageFuture(consumer);
				Message received = future.get(6, TimeUnit.MILLISECONDS);
				assertNotNull("Received message is null ", received);
				assertTrue("Unexpected message found",
						messages.remove(received));
			}
		} catch (TimeoutException e) {
			fail("Timeout did not happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	@Test
	public void testReceiveMultipleManyToBeExpiredSomeValid() {
		int noOfMessages = 13;
		try {
			consumer = new AbstractMessageConsumer(mockTopic) {
			};
			assertNotNull(mockTopic.getTopicSubscriber());
			HashSet<Message> messages = new HashSet<Message>();

			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage(
						"testReceiveMultipleManyToBeExpiredSomeValid" + i);

				// Insert only two messages, first two
				if (i % 2 == 0)
					messages.add(msg);
				else
					msg.setExpiration(System.currentTimeMillis() + 6);
				mockTopic.getTopicSubscriber().onMessage(msg);
			}
			Thread.sleep(7);
			for (int i = 0; i < messages.size(); i++) {
				FutureTask<Message> future = getMessageFuture(consumer);
				Message received = future.get(6, TimeUnit.MILLISECONDS);
				assertNotNull("Received message is null ", received);
				assertTrue("Unexpected message found:" + received,
						messages.remove(received));
			}
		} catch (TimeoutException e) {
			fail("Timeout did not happen on receive method");
		} catch (Exception e) {
			logger.error("Error while calling receiving message", e);
			fail("Error while calling onMessage:" + e.getMessage());
		}
	}

	private static FutureTask<Message> getMessageFuture(
			final MessageConsumer consumer) {
		Callable<Message> callable = new Callable<Message>() {
			public Message call() throws InterruptedException,
					DestinationClosedException {
				try {
					Message m;
					m = consumer.receive();
					logger.debug("received response or timeedout: " + m);
					return m;
				} catch (DestinationClosedException ex) {
					logger.debug("------Destination closed exception-------");
					throw ex;
				}
			}
		};
		FutureTask<Message> future = new FutureTask<Message>(callable);
		e.execute(future);
		return future;
	}

	private static FutureTask<Message> getMessageFuture(
			final MessageConsumer consumer, final int timeoutMills) {
		Callable<Message> callable = new Callable<Message>() {
			public Message call() throws InterruptedException,
					ExecutionException, TimeoutException,
					DestinationClosedException {
				Message m;

				m = consumer.receive(timeoutMills);

				logger.debug("received response or timeedout: " + m);
				return m;
			}
		};
		FutureTask<Message> future = new FutureTask<Message>(callable);
		e.execute(future);
		return future;
	}
}
