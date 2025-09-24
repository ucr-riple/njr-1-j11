package com.dhana.broker.impl;

import com.dhana.broker.QueueReceiver;
import com.dhana.broker.exceptions.DestinationClosedException;

public class QueueReceiverImpl extends AbstractMessageConsumer implements
		QueueReceiver { 

	public QueueReceiverImpl(Queue destination) throws DestinationClosedException {
		super(destination);
	}

	public QueueReceiverImpl(Queue destination, int queueSize) throws DestinationClosedException {
		super(destination, queueSize);
	}

	public QueueReceiverImpl(Queue destination, int queueSize,
			MessageDiscardPolicy discardPolicy) throws DestinationClosedException {
		super(destination, queueSize, discardPolicy);
	}
	
	public Queue getQueue(){
		return (Queue) this.destination;
	}

}
