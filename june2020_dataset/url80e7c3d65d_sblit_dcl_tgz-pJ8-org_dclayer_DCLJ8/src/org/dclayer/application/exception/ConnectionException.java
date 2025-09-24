package org.dclayer.application.exception;

public class ConnectionException extends Exception {
	
	public ConnectionException(String msg) {
		super(msg);
	}
	
	public ConnectionException(Throwable cause) {
		super(cause);
	}
	
}
