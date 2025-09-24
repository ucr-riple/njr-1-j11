package com.dhana.broker.impl;

import com.dhana.broker.Message;

/**
 * Mechanism for Topic to deliver messages to subscribers
 * */
interface AsyncMessageConsumer {
	void onMessage(Message msg);
}