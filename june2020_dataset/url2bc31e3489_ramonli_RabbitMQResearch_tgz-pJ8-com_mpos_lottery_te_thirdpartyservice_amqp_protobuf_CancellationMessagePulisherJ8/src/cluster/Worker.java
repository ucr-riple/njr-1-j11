package cluster;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Worker {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        /**
         * Be aware that Sender will connect to [localhost, 5672], while the
         * receiver connect to [localhost, 5673]. No matter which node of the
         * cluster the client connect to, they the see same picture.
         */
        Connection connection = factory.newConnection(new Address[] { new Address("localhost", 5673) });
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            doWork(message);
            System.out.println(" [x] Done");
            Thread.sleep(10 * 000);
            System.out.println("Make acknoleadgement.");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

    private static void doWork(String task) throws InterruptedException {
        Thread.currentThread().sleep(10 * 1000);
        for (char ch : task.toCharArray()) {
            if (ch == '.')
                Thread.sleep(1000);
        }
    }
}
