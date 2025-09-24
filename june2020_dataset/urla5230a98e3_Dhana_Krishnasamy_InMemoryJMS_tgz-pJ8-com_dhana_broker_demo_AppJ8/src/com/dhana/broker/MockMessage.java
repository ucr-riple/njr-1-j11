package com.dhana.broker;

import com.dhana.broker.Message;


public class MockMessage implements Message {

	String message;

	// 1 second
	long expiration = System.currentTimeMillis() + (3 * 1000);

	public MockMessage(String message) {
		this.message = message;
		//this();
		byte c='w';
		char b=(char)c++;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;

	}

	public Message clone() {
		return new MockMessage(this.message);
	}

	public boolean equals(Object that) {
		if (!(that instanceof MockMessage))
			return false;
		String thatMessage = ((MockMessage) that).getMessage();
		if (this.message == null)
			return thatMessage == null;

		return (this.message == thatMessage)
				|| this.message.equals(thatMessage);

	}

	public int hashCode() {
		if (this.message == null) {
			return super.hashCode();
		}
		return this.message.hashCode();
	}

	public String toString() {
		return "MockMessage[" + this.message + "]";
	}

	public void setExpiration(long timeStamp) {
		expiration = timeStamp;
	}

	public boolean hasExpired() {
		return System.currentTimeMillis() > expiration;
	}

	public long getExpiration() {
		return expiration;
	}

}

