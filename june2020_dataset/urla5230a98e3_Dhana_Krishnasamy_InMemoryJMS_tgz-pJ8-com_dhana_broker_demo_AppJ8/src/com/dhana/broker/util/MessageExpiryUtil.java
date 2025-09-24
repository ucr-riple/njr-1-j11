package com.dhana.broker.util;

import java.util.Date;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhana.broker.Message;

public class MessageExpiryUtil {
	static Logger logger = LoggerFactory.getLogger(MessageExpiryUtil.class);
	private Queue<Message> queue;
	private Timer timer = new Timer();

	public MessageExpiryUtil(Queue<Message> messageQueue) {
		this.queue = messageQueue;
	}

	public void scheduleForExpiry(final Message message) {
		final long now =System.currentTimeMillis();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				boolean isSuccess=queue.remove(message);
				if(isSuccess)
				logger.debug("Removing expired message:" + message+" Expired after:"+(System.currentTimeMillis()-now));
			}
		}, new Date(message.getExpiration()));
	}

	public void cancel() {
		timer.cancel();
	}
}
 