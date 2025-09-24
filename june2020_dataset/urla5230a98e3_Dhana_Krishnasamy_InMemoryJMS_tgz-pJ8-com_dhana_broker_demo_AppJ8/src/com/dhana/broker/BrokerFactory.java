package com.dhana.broker;

import com.dhana.broker.exceptions.BrokerError;
import com.dhana.broker.impl.BrokerFactoryImpl;

public class BrokerFactory {
	static {
		BrokerFactoryImpl.init();
	}

	public static QueueSender createSender(String queueName)
			throws InterruptedException, BrokerError {
		return BrokerFactoryImpl.createSender(queueName);
	}

	public static QueueReceiver createReceiver(String queueName)
			throws InterruptedException, BrokerError {
		return BrokerFactoryImpl.createReceiver(queueName);
	}

	public static TopicPublisher createPublisher(String topicName)
			throws InterruptedException, BrokerError {
		return BrokerFactoryImpl.createPublisher(topicName);
	}

	public static TopicSubscriber createSubscriber(String topicName)
			throws InterruptedException, BrokerError {
		return BrokerFactoryImpl.createSubscriber(topicName);
	}

	public static Message createMessage() {
		return BrokerFactoryImpl.createMessage();
	}

}
