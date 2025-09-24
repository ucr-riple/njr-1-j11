package com.dhana.broker.impl;

import com.dhana.broker.Message;
import com.dhana.broker.exceptions.DestinationClosedException;

interface Destination {
	public boolean put(Message message) throws DestinationClosedException;

	public void addSubscriber(AsyncMessageConsumer testSubscriber) throws DestinationClosedException;

	public void removeSubscriber(AsyncMessageConsumer subscriber);

	public void closePublisher();

	public void openPublisher() throws DestinationClosedException;

	public void close();
}
