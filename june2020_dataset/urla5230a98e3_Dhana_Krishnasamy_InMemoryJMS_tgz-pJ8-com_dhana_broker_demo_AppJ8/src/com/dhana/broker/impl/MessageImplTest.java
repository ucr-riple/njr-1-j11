package com.dhana.broker.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import com.dhana.broker.Message;
import com.dhana.broker.impl.MessageImpl;

public class MessageImplTest {

	@Test
	public void testSet() {
		String msgString = "TestMessage123";
		Message msg = new MessageImpl();
		assertNotNull("Message is null", msg);
		msg.setMessage(msgString);
		// No exceptions? then pass
	}

	@Test
	public void testSetGet() {
		String msgString = "TestMessage123";
		Message msg = new MessageImpl();
		assertNotNull("Message is null", msg);
		msg.setMessage(msgString);
		String ret = msg.getMessage();
		assertNotNull("returned messages is null", ret);
		assertEquals("returned messages are not equal", msgString, ret);
	}

	@Test
	public void testOverwriteAndGet() {
		String msgString = "TestMessage123";
		Message msg = new MessageImpl();
		assertNotNull("Message is null", msg);
		msg.setMessage(msgString);
		String ret = msg.getMessage();
		assertNotNull("returned messages is null", ret);
		assertEquals("returned messages are not equal", msgString, ret);
		msg.setMessage(msgString + msgString);
		ret = msg.getMessage();
		assertNotNull("returned messages is null", ret);
		assertEquals("returned messages are not equal", msgString + msgString,
				ret);
	}

	@Test
	public void testMessageIsClonable() {
		String msgString = "TestMessage123";
		MessageImpl msg = new MessageImpl();
		assertNotNull("Message is null", msg);
		msg.setMessage(msgString);
		Message clone = msg.clone();
		assertNotNull("returned messages is null", clone);
		assertEquals("returned messages are not equal", msg, clone);
		assertEquals("cloned message values are not equal", msg.getMessage(),
				clone.getMessage());
	}

	@Test
	public void testMessageIsEmpty() {
		Message msg = new MessageImpl();
		String txt = msg.getMessage();
		assertEquals("Message is not empty", null, txt);
	}

	@Test
	public void testEqualMessagesAreEqual() {
		String message = "Hello John!";
		Message msg = new MessageImpl();
		msg.setMessage(message);
		Message anotherMsg = new MessageImpl();
		anotherMsg.setMessage(message);
		assertEquals("Message with same string are not equal", msg, anotherMsg);
		assertEquals("Message with same string have different hashCode",
				msg.hashCode(), msg.hashCode());
	}

	@Test
	public void testEqualMessagesWithExpiryAreEqual() {
		String message = "Hello John!";
		long expiration = System.currentTimeMillis();
		Message msg = new MessageImpl();
		msg.setMessage(message);
		msg.setExpiration(expiration);
		Message anotherMsg = new MessageImpl();
		anotherMsg.setMessage(message);
		anotherMsg.setExpiration(expiration);
		assertEquals("Message with same string are not equal", msg, anotherMsg);
		assertEquals("Message with same string have different hashCode",
				msg.hashCode(), msg.hashCode());
	}

	@Test
	public void testEqualMessagesAreEqual_NullValue() {
		Message msg = new MessageImpl();
		Message anotherMsg = new MessageImpl();
		assertEquals("Message with null values are not equal", msg, anotherMsg);
		assertEquals("Message with null values have different hashCode",
				msg.hashCode(), msg.hashCode());
	}

	@Test
	public void testEqualMessagesAreEqual_NullValueWithExpiration() {
		long expiration = System.currentTimeMillis();
		Message msg = new MessageImpl();
		msg.setExpiration(expiration);
		Message anotherMsg = new MessageImpl();
		anotherMsg.setExpiration(expiration);
		assertEquals("Message with null values are not equal", msg, anotherMsg);
		assertEquals("Message with null values have different hashCode",
				msg.hashCode(), msg.hashCode());
	}

	@Test
	public void testUnEqualMessagesAreUnEqual() {
		String message = "Hello John!";
		Message msg = new MessageImpl();
		msg.setMessage(message);
		Message anotherMsg = new MessageImpl();
		anotherMsg.setMessage(message + message);
		assertFalse("Message with same string are not equal",
				msg.equals(anotherMsg));
	}

	@Test
	public void testUnEqualMessagesSameExpirationAreUnEqual() {
		long expiration = System.currentTimeMillis();
		String message = "Hello John!";
		Message msg = new MessageImpl();
		msg.setMessage(message);
		msg.setExpiration(expiration);
		Message anotherMsg = new MessageImpl();
		anotherMsg.setMessage(message + message);
		anotherMsg.setExpiration(expiration);
		assertFalse("Message with same string are not equal",
				msg.equals(anotherMsg));
	}

	@Test
	public void testUnEqualMessagesAreUnEqual_ExpirationDifferentStringSame() {
		long expiration = System.currentTimeMillis();
		String message = "Hello John!";
		Message msg = new MessageImpl();
		msg.setMessage(message);
		msg.setExpiration(expiration);
		Message anotherMsg = new MessageImpl();
		anotherMsg.setMessage(message);
		anotherMsg.setExpiration(expiration + expiration);
		assertFalse("Message with same string are not equal",
				msg.equals(anotherMsg));
	}

	@Test
	public void testCanBeUsedInHashDataStructure_Success() {
		String message = "Hello John!";
		HashSet<Message> set = new HashSet<Message>();
		Message msg = new MessageImpl();
		msg.setMessage(message);
		set.add(msg);
		Message anotherMsg = new MessageImpl();
		anotherMsg.setMessage(message);
		assertTrue(
				"Cannot find message in hashset using differnet object with same string",
				set.contains(anotherMsg));
	}

	@Test
	public void testCanBeUsedInHashDataStructure_Failure() {
		String message = "Hello John!";
		HashSet<Message> set = new HashSet<Message>();
		Message msg = new MessageImpl();
		msg.setMessage(message);
		set.add(msg);
		Message anotherMsg = new MessageImpl();
		anotherMsg.setMessage(message + message);
		assertFalse("Unequal messages are matching", set.contains(anotherMsg));
	}

	@Test
	public void testHasExpiredAfterTime() {
		Message msg = new MessageImpl();
		long time = System.currentTimeMillis();
		msg.setExpiration(time - 2);
		assertTrue("Did not expire in time", msg.hasExpired());
	}

	@Test
	public void testHasExpiredBeforeTime() {
		Message msg = new MessageImpl();
		long time = System.currentTimeMillis();
		msg.setExpiration(time + 2);
		assertFalse("Expired too soon", msg.hasExpired());

	}
}
