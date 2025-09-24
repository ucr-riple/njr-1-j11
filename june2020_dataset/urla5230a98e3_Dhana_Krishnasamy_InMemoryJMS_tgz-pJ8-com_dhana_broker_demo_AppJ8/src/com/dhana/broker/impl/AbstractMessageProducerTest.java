package com.dhana.broker.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.dhana.broker.exceptions.DestinationClosedException;
import com.dhana.broker.impl.AbstractMessageProducer;

public class AbstractMessageProducerTest {

	AbstractMessageProducer producer;
	MockTopic mocktopic;

	@Before
	public void setUp() {
		mocktopic = new MockTopic();
	}

	@Test
	public void testCreateproducer() {
		try {
			producer = new AbstractMessageProducer(mocktopic) {
			};
		} catch (Throwable e) {
			fail("Error while creating producer:" + e.getMessage());
		}
	}

	@Test
	public void testCreateproducerOpenproducerCount() {
		try {
			producer = new AbstractMessageProducer(mocktopic) {
			};
			assertEquals("producer count was not increased", 1,
					mocktopic.getPublisherCount());
			producer = new AbstractMessageProducer(mocktopic) {
			};
			assertEquals("producer count was not increased", 2,
					mocktopic.getPublisherCount());
		} catch (Throwable e) {
			fail("Error while creating producer:" + e.getMessage());
		}
	}

	@Test
	public void testCreateproducerCloseproducerCount() {
		try {
			producer = new AbstractMessageProducer(mocktopic) {
			};
			assertEquals("producer count was not increased", 1,
					mocktopic.getPublisherCount());
			producer.close();
			assertEquals("producer count was not increased", 0,
					mocktopic.getPublisherCount());
			producer = new AbstractMessageProducer(mocktopic) {
			};
			assertEquals("producer count was not increased", 1,
					mocktopic.getPublisherCount());
			producer.close();
			assertEquals("producer count was not increased", 0,
					mocktopic.getPublisherCount());
		} catch (Throwable e) {
			fail("Error while creating producer:" + e.getMessage());
		}
	}

	@Test(expected = AssertionError.class)
	public void testNullTopic() {
		try {
			producer = new AbstractMessageProducer(null) {
			};
		} catch (DestinationClosedException e) {
			fail("Unexpected exception on constructor");
		}
	}

	
}
