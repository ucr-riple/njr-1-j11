package com.mpos.lottery.te.thirdpartyservice.amqp;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitMessageConsumer implements MessageConsumer {
    private Log logger = LogFactory.getLog(RabbitMessageConsumerMain.class);
    private String host;
    // whether re-connect AMQP broker if connection is broken.
    private boolean autoReconnect = false;
    private long reconnectInterval = 60 * 1000;
    private String username;
    private String password;

    public RabbitMessageConsumer(String host, boolean autoReconnect, String username, String password) {
        super();
        this.host = host;
        this.autoReconnect = autoReconnect;
        this.username = username;
        this.password = password;
    }

    @Override
    public void consume(final String exchangeName, final String queueName, final String[] routingKeys,
            final int qos) throws IOException, InterruptedException {
        this.consume(exchangeName, queueName, routingKeys, qos, true, true);
    }

    @Override
    public void consume(final String exchangeName, final String queueName, final String[] routingKeys,
            final int qos, boolean declareExchange, boolean declareQueue) throws IOException,
            InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.getHost());
        factory.setUsername(this.getUsername());
        factory.setPassword(this.getPassword());

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            if (declareExchange)
                channel.exchangeDeclare(exchangeName, "topic", true);
            if (declareQueue)
                // declare a durable, non-exclusive, non-autodelete queue.
                channel.queueDeclare(queueName, true, false, false, null);
            for (String routingKey : routingKeys)
                channel.queueBind(queueName, exchangeName, routingKey);
            /**
             * distribute workload among all consumers, consumer will prefetch
             * {qos} messages to local buffer.
             */
            channel.basicQos(qos);

            logger.debug(" [*] Waiting for messages. To exit press CTRL+C");

            boolean autoAck = false;
            channel.basicConsume(queueName, autoAck, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                        AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String exchange = envelope.getExchange();
                    String contentType = properties.getContentType();
                    long deliveryTag = envelope.getDeliveryTag();

                    // (process the message components here ...)
                    String message = new String(body);
                    logger.debug("[x] Received " + exchange + "/" + routingKey + ":'" + message + "'");

                    /**
                     * ack message. Broker If a message is delivered to a
                     * consumer and then requeued (because it was not
                     * acknowledged before the consumer connection dropped, for
                     * example) then RabbitMQ will set the redelivered flag on
                     * it when it is delivered again (whether to the same
                     * consumer or a different one).
                     */
                    this.getChannel().basicAck(deliveryTag, false);
                }
            });

            // -------------------------------------------------------
            // As the handling of message is in same thread with connection
            // management, the handling of message may stall processing of
            // all channels on the connection.
            // -------------------------------------------------------
            // QueueingConsumer consumer = new QueueingConsumer(channel);
            // // disable auto-ack. If enable auto-ack, RabbitMQ delivers a
            // // message to
            // // the customer it immediately removes it from memory.
            // boolean autoAck = false;
            // channel.basicConsume(queueName, autoAck, consumer);
            //
            // while (true) {
            // QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            // try {
            // RabbitMessageConsumer.this.consumeMessage(delivery);
            // }
            // catch (Exception e) {
            // // the exception shouldn't affect the next message
            // logger.info("[IGNORE]" + e.getMessage());
            // }
            // /**
            // * If no call to basicAck here, once consumer close
            // * connection, broker will move the unacked message to
            // * redeliver queue.
            // */
            // channel.basicAck(delivery.getEnvelope().getDeliveryTag(),
            // false);
            // }
            // -------------------------------------------------------
        }
        catch (Exception e) {
            logger.warn(e);
            this.release(connection, channel);
        }

        while (true)
            Thread.sleep(10 * 1000);

        // logger.debug("end of main thread");
    }

    private void release(Connection conn, Channel channel) {
        try {
            if (conn != null)
                conn.close();
            if (channel != null)
                channel.abort();
            conn = null;
            channel = null;
        }
        catch (Exception e) {
            // simply ignore this exception
            logger.warn(e.getCause() != null ? e.getCause() : e);
        }
    }

    public String getHost() {
        return host;
    }

    public long getReconnectInterval() {
        return reconnectInterval;
    }

    public void setReconnectInterval(long reconnectInterval) {
        this.reconnectInterval = reconnectInterval;
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
