package com.mpos.lottery.te.thirdpartyservice.amqp;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;

/**
 * MessageContext will carry all necessary resources for message handlers.
 * 
 * @author Ramon
 * 
 */
public class MessageContext {
    private Channel channel;
    private Map<Object, Object> properties = new HashMap<Object, Object>();

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setProperty(Object key, Object value) {
        this.properties.put(key, value);
    }

    public Object getProperty(Object key) {
        return this.properties.get(key);
    }
}
