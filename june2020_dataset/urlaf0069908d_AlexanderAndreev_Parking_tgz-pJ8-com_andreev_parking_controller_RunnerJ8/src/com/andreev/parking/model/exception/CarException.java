package com.andreev.parking.model.exception;

public class CarException extends Exception {

	private static final long serialVersionUID = -4036206673118739695L;

	public CarException() {
	}

	public CarException(String message) {
		super(message);
	}

	public CarException(Throwable cause) {
		super(cause);
	}

	public CarException(String message, Throwable cause) {
		super(message, cause);
	}

	public CarException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
