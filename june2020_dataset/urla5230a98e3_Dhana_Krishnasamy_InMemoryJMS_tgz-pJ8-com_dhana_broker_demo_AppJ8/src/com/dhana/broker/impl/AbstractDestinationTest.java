package com.dhana.broker.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dhana.broker.Message;
import com.dhana.broker.MockMessage;
import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.impl.AbstractDestination;
import com.dhana.broker.impl.AsyncMessageConsumer;

public class AbstractDestinationTest {

	protected AbstractDestination destination;
	protected String topicName = "TestTopic";

	@Before
	public void setup() {
		this.destination = new AbstractDestination(topicName) {
		};
	}

	@After
	public void tearDown() {
		this.destination.close();
	}

	@Test
	public void testPutMessage() {
		try {
			Message msg = new MockMessage("TestMessage");
			destination.put(msg);
			assertEquals("Message count didnot match", 1,
					destination.getMessageCount());
		} catch (Throwable e) {
			fail("Error while putting message in topic" + e.getMessage());
		}
	}

	@Test(expected = NullPointerException.class)
	public void testPutNullMessage() {
		try {
			destination.put(null);
		} catch (DestinationClosedException e) {
			fail("Found a closed topic" + e.getMessage());
		}
	}

	@Test
	public void testPutMultipleMessages() {
		try {
			int noOfMessages = 5;
			for (int i = 0; i < noOfMessages; i++) {
				Message msg = new MockMessage("TestMessage");
				destination.put(msg);
			}
			assertEquals("Message count didnot match", noOfMessages,
					destination.getMessageCount());
		} catch (Throwable e) {
			fail("Error while putting message in topic" + e.getMessage());
		}

	}

	@Test
	public void testAddSubscriber() {
		try {
			AsyncMessageConsumer testSubscriber = new MockTopicSubscriber();
			destination.addSubscriber(testSubscriber);
		} catch (Throwable e) {
			fail("Error while subscribing to the topic" + e.getMessage());
		}
	}

	@Test(expected = AssertionError.class)
	public void testAddNullSubscriber() {
		try {
			destination.addSubscriber(null);
		} catch (DestinationClosedException e) {
			fail("Found a closed topic" + e.getMessage());
		}
	}

	@Test
	public void testRemoveSubscriber() {
		try {
			AsyncMessageConsumer testSubscriber = new MockTopicSubscriber();
			destination.addSubscriber(testSubscriber);
			destination.removeSubscriber(testSubscriber);
		} catch (Throwable e) {
			fail("Error while unsubscribing to the topic" + e.getMessage());
		}
	}

	@Test(expected = DestinationClosedException.class)
	public void testNoMessageAfterClose() throws DestinationClosedException {
		final String message = "TestMessage-testNoMessageAfterClose";
		final Message expected = new MockMessage(message);
		destination.close();
		destination.put(expected);
	}

	@Test
	public void testNotificationOnPubSubCountZero()
			throws DestinationClosedException {
		MockMessageProducer publisher = new MockMessageProducer(destination);
		MockMessageConsumer subscriber = new MockMessageConsumer(destination);
		final CountDownLatch gate = new CountDownLatch(1);
		destination.addObserver(new Observer() {

			public void update(Observable o, Object arg) {
				assertEquals("Topic name didnt match", topicName, arg);
				gate.countDown();
			}
		});
		boolean messageReceived;
		try {
			publisher.close();
			subscriber.close();
			messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertTrue("Did not receive message in 100ms", messageReceived);
		} catch (InterruptedException e) {
			fail("Wait InterruptedException" + e.getMessage());
		}

	}

	@Test
	public void testNoNotificationOnPubCountZero()
			throws DestinationClosedException {
		MockMessageProducer publisher = new MockMessageProducer(destination);
		MockMessageConsumer subscriber = new MockMessageConsumer(destination);
		final CountDownLatch gate = new CountDownLatch(1);
		destination.addObserver(new Observer() {

			public void update(Observable o, Object arg) {
				gate.countDown();
			}
		});
		boolean messageReceived;
		try {
			publisher.close();
			messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertFalse("Notification received when subscriber was open",
					messageReceived);
		} catch (InterruptedException e) {
			fail("Wait InterruptedException" + e.getMessage());
		}

	}

	@Test
	public void testNoNotificationOnSubCountZero()
			throws DestinationClosedException {
		MockMessageProducer publisher = new MockMessageProducer(destination);
		MockMessageConsumer subscriber = new MockMessageConsumer(destination);
		final CountDownLatch gate = new CountDownLatch(1);
		destination.addObserver(new Observer() {

			public void update(Observable o, Object arg) {
				gate.countDown();
			}
		});
		boolean messageReceived;
		try {
			subscriber.close();
			messageReceived = gate.await(100, TimeUnit.MILLISECONDS);
			assertFalse("Notification received when publisher was open",
					messageReceived);
		} catch (InterruptedException e) {
			fail("Wait InterruptedException" + e.getMessage());
		}

	}
}
