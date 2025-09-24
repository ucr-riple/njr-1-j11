package com.dhana.broker.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhana.broker.Message;
import com.dhana.broker.QueueReceiver;
import com.dhana.broker.QueueSender;
import com.dhana.broker.TopicPublisher;
import com.dhana.broker.TopicSubscriber;
import com.dhana.broker.exceptions.BrokerError;
import com.dhana.broker.impl.BrokerFactoryImpl;
import com.dhana.broker.impl.MessageImpl;
import com.dhana.broker.impl.Queue;
import com.dhana.broker.impl.QueueReceiverImpl;
import com.dhana.broker.impl.QueueSenderImpl;
import com.dhana.broker.impl.Topic;
import com.dhana.broker.impl.TopicPublisherImpl;
import com.dhana.broker.impl.TopicSubscriberImpl;

public class BrokerFactoryImplTest {
	static Logger logger = LoggerFactory.getLogger(BrokerFactoryImplTest.class);

	@Before
	public void setUp() {
		BrokerFactoryImpl.init();
	}

	@After
	public void tearDown() {
		BrokerFactoryImpl.close();
	}

	// ============= Message Related
	@Test
	public void testCreateMessage() {
		Message msg = BrokerFactoryImpl.createMessage();
		assertNotNull("Message is null", msg);
	}

	@Test
	public void testMessageIsExpectedType() {
		Message msg = BrokerFactoryImpl.createMessage();
		assertNotNull("Message is null", msg);
		assertTrue("Message is not MessageImpl type (could be untested impl)",
				msg instanceof MessageImpl);
	}

	@Test
	public void testMessageIsUnique() {
		Message msg = BrokerFactoryImpl.createMessage();
		assertNotNull("Message is null", msg);
		assertTrue("Message is not MessageImpl type (could be untested impl)",
				msg instanceof MessageImpl);
		Message anotherMessage = BrokerFactoryImpl.createMessage();
		assertNotNull("2nd Message is null", anotherMessage);
		assertTrue(
				"2nd Message is not MessageImpl type (could be untested impl)",
				anotherMessage instanceof MessageImpl);
		assertFalse("First message and 2nd messages are not unique",
				msg == anotherMessage);
	}

	// ============= Publisher Related

	@Test
	public void testCreateQueueSender() {
		QueueSender pub;
		try {
			pub = BrokerFactoryImpl.createSender("TestQueue");
			assertNotNull("TopicPublisher is null", pub);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			fail("Unexpected exception while creating publisher");
		} catch (Exception e) {
			logger.error("Error while creating publisher", e);
		}
	}

	@Test
	public void testCreateSameQueueSenderTwice() {
		QueueSender pub;
		try {
			pub = BrokerFactoryImpl.createSender("TestQueue");
			assertNotNull("QueueSender is null", pub);
			assertTrue(
					"QueueSender is not TopicImpl type (could be untested impl)",
					pub instanceof QueueSenderImpl);
			Queue firstTopic = ((QueueSenderImpl) pub).getQueue();
			pub = BrokerFactoryImpl.createSender("TestQueue");
			assertNotNull("QueueSender is null", pub);
			assertTrue(
					"QueueSender is not QueueSenderImpl type (could be untested impl)",
					pub instanceof QueueSenderImpl);
			assertTrue(
					"TopicPublisher is not QueueSenderImpl type (could be untested impl)",
					pub instanceof QueueSenderImpl);
			assertTrue("TopicPublisher have different topics",
					firstTopic == ((QueueSenderImpl) pub).getQueue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			fail("Unexpected exception while creating publisher");
		}

	}

	@Test
	public void testQueueSenderIsExpectedType() {
		QueueSender pub;
		try {
			pub = BrokerFactoryImpl.createSender("TestTopic");
			assertNotNull("QueueSender is null", pub);
			assertTrue(
					"TopicPublisher is not QueueSenderImpl type (could be untested impl)",
					pub instanceof QueueSenderImpl);
		} catch (InterruptedException e) {
			fail("Erro while creating QueueSender" + e.getMessage());
			logger.error("Erro while creating QueueSender", e);
		} catch (BrokerError e) {
			fail("Unexpected exception while creating QueueSender");
		}

	}

	// ============= Subscriber Related

	@Test
	public void testCreateQueueReceiver() {
		QueueReceiver pub;
		try {
			pub = BrokerFactoryImpl.createReceiver("TestTopic");
			assertNotNull("QueueReceiver is null", pub);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			fail("Unexpected exception while creating subscriber");
		}
	}

	@Test
	public void testCreateSameQueueReceiverTwice() {
		QueueReceiver pub;
		try {
			pub = BrokerFactoryImpl.createReceiver("TestTopic");
			assertNotNull("TopicSubscriber is null", pub);
			assertTrue(
					"TopicSubscriber is not TopicImpl type (could be untested impl)",
					pub instanceof QueueReceiverImpl);
			Queue firstTopic = ((QueueReceiverImpl) pub).getQueue();
			pub = BrokerFactoryImpl.createReceiver("TestTopic");
			assertNotNull("QueueReceiverImpl is null", pub);
			assertTrue(
					"QueueReceiverImpl is not TopicSubscriberImpl type (could be untested impl)",
					pub instanceof QueueReceiverImpl);
			assertTrue(
					"QueueReceiverImpl is not TopicSubscriberImpl type (could be untested impl)",
					pub instanceof QueueReceiverImpl);
			assertTrue("QueueReceiverImpl have different topics",
					firstTopic == ((QueueReceiverImpl) pub).getQueue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			fail("Unexpected exception while creating subscriber");
		}

	}

	@Test
	public void testQueueReceiverIsExpectedType() {
		QueueReceiver pub;
		try {
			pub = BrokerFactoryImpl.createReceiver("TestTopic");
			assertNotNull("TopicSubscriber is null", pub);
			assertTrue(
					"TopicSubscriber is not QueueReceiverImpl type (could be untested impl)",
					pub instanceof QueueReceiverImpl);
		} catch (InterruptedException e) {
			fail("Erro while creating QueueReceiverImpl" + e.getMessage());
			logger.error("Erro while creating QueueReceiverImpl", e);
		} catch (BrokerError e) {
			fail("Unexpected exception while creating QueueReceiverImpl");
		}
	}

	// ============= Publisher Related

	@Test
	public void testCreateTopicPublisher() {
		TopicPublisher pub;
		try {
			pub = BrokerFactoryImpl.createPublisher("TestTopic");
			assertNotNull("TopicPublisher is null", pub);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			fail("Unexpected exception while creating publisher");
		} catch (Exception e) {
			logger.error("Error while creating publisher", e);
		}
	}

	@Test
	public void testCreateSameTopicPublisherTwice() {
		TopicPublisher pub;
		try {
			pub = BrokerFactoryImpl.createPublisher("TestTopic");
			assertNotNull("TopicPublisher is null", pub);
			assertTrue(
					"TopicPublisher is not TopicImpl type (could be untested impl)",
					pub instanceof TopicPublisherImpl);
			Topic firstTopic = ((TopicPublisherImpl) pub).getTopic();
			pub = BrokerFactoryImpl.createPublisher("TestTopic");
			assertNotNull("TopicPublisher is null", pub);
			assertTrue(
					"TopicPublisher is not TopicPublisherImpl type (could be untested impl)",
					pub instanceof TopicPublisherImpl);
			assertTrue(
					"TopicPublisher is not TopicPublisherImpl type (could be untested impl)",
					pub instanceof TopicPublisherImpl);
			assertTrue("TopicPublisher have different topics",
					firstTopic == ((TopicPublisherImpl) pub).getTopic());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			fail("Unexpected exception while creating publisher");
		}

	}

	@Test
	public void testTopicPublisherIsExpectedType() {
		TopicPublisher pub;
		try {
			pub = BrokerFactoryImpl.createPublisher("TestTopic");
			assertNotNull("TopicPublisher is null", pub);
			assertTrue(
					"TopicPublisher is not TopicPublisherImpl type (could be untested impl)",
					pub instanceof TopicPublisherImpl);
		} catch (InterruptedException e) {
			fail("Erro while creating TopicPublisher" + e.getMessage());
			logger.error("Erro while creating TopicPublisher", e);
		} catch (BrokerError e) {
			fail("Unexpected exception while creating publisher");
		}

	}

	// ============= Subscriber Related

	@Test
	public void testCreateTopicSubscriber() {
		TopicSubscriber pub;
		try {
			pub = BrokerFactoryImpl.createSubscriber("TestTopic");
			assertNotNull("TopicSubscriber is null", pub);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			fail("Unexpected exception while creating subscriber");
		}
	}

	@Test
	public void testCreateSameTopicSubscriberTwice() {
		TopicSubscriber pub;
		try {
			pub = BrokerFactoryImpl.createSubscriber("TestTopic");
			assertNotNull("TopicSubscriber is null", pub);
			assertTrue(
					"TopicSubscriber is not TopicImpl type (could be untested impl)",
					pub instanceof TopicSubscriberImpl);
			Topic firstTopic = ((TopicSubscriberImpl) pub).getTopic();
			pub = BrokerFactoryImpl.createSubscriber("TestTopic");
			assertNotNull("TopicSubscriber is null", pub);
			assertTrue(
					"TopicSubscriber is not TopicSubscriberImpl type (could be untested impl)",
					pub instanceof TopicSubscriberImpl);
			assertTrue(
					"TopicSubscriber is not TopicSubscriberImpl type (could be untested impl)",
					pub instanceof TopicSubscriberImpl);
			assertTrue("TopicSubscriber have different topics",
					firstTopic == ((TopicSubscriberImpl) pub).getTopic());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokerError e) {
			fail("Unexpected exception while creating subscriber");
		}

	}

	@Test
	public void testTopicSubscriberIsExpectedType() {
		TopicSubscriber pub;
		try {
			pub = BrokerFactoryImpl.createSubscriber("TestTopic");
			assertNotNull("TopicSubscriber is null", pub);
			assertTrue(
					"TopicSubscriber is not TopicSubscriberImpl type (could be untested impl)",
					pub instanceof TopicSubscriberImpl);
		} catch (InterruptedException e) {
			fail("Erro while creating TopicSubscriber" + e.getMessage());
			logger.error("Erro while creating TopicSubscriber", e);
		} catch (BrokerError e) {
			fail("Unexpected exception while creating subscriber");
		}

	}
}
