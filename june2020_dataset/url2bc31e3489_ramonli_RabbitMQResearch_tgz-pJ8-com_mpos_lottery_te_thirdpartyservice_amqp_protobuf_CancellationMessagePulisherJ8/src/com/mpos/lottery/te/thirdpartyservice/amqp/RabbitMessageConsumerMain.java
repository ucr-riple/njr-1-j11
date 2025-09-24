package com.mpos.lottery.te.thirdpartyservice.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMessageConsumerMain {
    public static final String EXCHANGE_NAME = "TE.452";

    public static void main(String[] argv) throws Exception {
        RabbitMessageConsumer main = new RabbitMessageConsumer("192.168.2.152", true, "amqp", "amqp");
        main.consume(EXCHANGE_NAME, RabbitMessagePublisher.class.getName(), new String[] { EXCHANGE_NAME
                + ".*" }, 1);
    }

    public static void deleteQueue() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.2.152");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel = connection.createChannel();
        channel.queueDelete(RabbitMessagePublisher.class.getName());
        connection.close();
    }
}
