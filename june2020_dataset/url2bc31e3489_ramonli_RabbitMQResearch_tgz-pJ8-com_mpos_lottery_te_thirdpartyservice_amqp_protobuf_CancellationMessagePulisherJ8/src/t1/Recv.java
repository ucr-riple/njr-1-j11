package t1;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        /**
         * java.lang.String basicConsume(java.lang.String queue, boolean
         * autoAck, Consumer callback)throws java.io.IOException
         * <p>
         * Parameters:
         * <ol>
         * <li>queue - the name of the queue</li>
         * <li>autoAck - true if the server should consider messages
         * acknowledged once delivered; false if the server should expect
         * explicit acknowledgements</li>
         * <li>callback - an interface to the consumer object</li>
         * <ol>
         */
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            /**
             * The <code>QueueingConsumer</code> is a class which will buffer
             * the messages pushed to client by RabbitMQ server. So there is a
             * risk, the receiver may lose the messages.
             * <p>
             * Imagine that publisher published 10 messages, and all of them are
             * pushed to receivers(as received will buffer them for later
             * usage), what happened if receiver crashed or power off?
             */
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            Thread.currentThread().sleep(50 * 1000);
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
