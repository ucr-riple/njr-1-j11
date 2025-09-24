package com.dhana.broker.impl;

import com.dhana.broker.Message;

public class MessageImpl implements Message {
	private String message;
	private long expiration;

	MessageImpl() {
	}

	private MessageImpl(String msg) {
		this.message = msg;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

	public long getExpiration() {
		return this.expiration;
	}

	public boolean hasExpired() {
		Long now = System.currentTimeMillis();
		return now >= expiration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageImpl clone() {
		MessageImpl m = new MessageImpl(this.message);
		m.setExpiration(expiration);
		return m;
	}

	public boolean equals(Object o) {
		if (!(o instanceof MessageImpl))
			return false;
		MessageImpl that = ((MessageImpl) o);
		if (this.message == null)
			return that.message == null;

		return (this == that)
				|| (this.message.equals(that.message) && (this.expiration == that.expiration));

	}

	public int hashCode() {
		if (this.message == null) {
			return super.hashCode();
		}
		return (this.message + this.expiration).hashCode();
	}

}
