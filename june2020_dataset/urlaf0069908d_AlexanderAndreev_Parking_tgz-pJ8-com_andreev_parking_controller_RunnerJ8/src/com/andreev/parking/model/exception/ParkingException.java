package com.andreev.parking.model.exception;

public class ParkingException extends CarException {

	private static final long serialVersionUID = -7336047125334832862L;

	public ParkingException() {
	}

	public ParkingException(String message) {
		super(message);
	}

	public ParkingException(Throwable cause) {
		super(cause);	}

	public ParkingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParkingException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
