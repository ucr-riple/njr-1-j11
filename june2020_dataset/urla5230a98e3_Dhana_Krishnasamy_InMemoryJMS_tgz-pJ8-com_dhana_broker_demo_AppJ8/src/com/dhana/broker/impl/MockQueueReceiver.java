package com.dhana.broker.impl;

import java.util.concurrent.TimeoutException;

import com.dhana.broker.Message;
import com.dhana.broker.QueueReceiver;
import com.dhana.broker.impl.AsyncMessageConsumer;
import com.dhana.broker.impl.Queue;

public class MockQueueReceiver implements QueueReceiver,AsyncMessageConsumer {

	Queue queue;
	
	public void onMessage(Message msg) {

	}

	public Message receive() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	public Message receive(int timeOutMilliseconds)
			throws InterruptedException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}



}
