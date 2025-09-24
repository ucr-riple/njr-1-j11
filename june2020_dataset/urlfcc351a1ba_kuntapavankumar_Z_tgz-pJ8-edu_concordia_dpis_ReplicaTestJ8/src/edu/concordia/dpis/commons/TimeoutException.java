package edu.concordia.dpis.commons;

// An exception raised when the client cannot receive the reply within the given time.
public class TimeoutException extends Exception {

	private static final long serialVersionUID = 1L;

	public TimeoutException() {
		super("Timed out");
	}

}
