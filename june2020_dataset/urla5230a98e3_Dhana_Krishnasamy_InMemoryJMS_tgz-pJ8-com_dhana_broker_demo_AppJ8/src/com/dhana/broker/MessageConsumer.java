package com.dhana.broker;

import java.util.concurrent.TimeoutException;

import com.dhana.broker.exceptions.DestinationClosedException;

public interface MessageConsumer {

	Message receive() throws InterruptedException, DestinationClosedException;

	Message receive(int timeOutMilliseconds) throws InterruptedException,
			TimeoutException, DestinationClosedException;

	void close();

}