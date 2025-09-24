package t2;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class NewSend {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws java.io.IOException {
        // create connection
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /**
         * Actually we don't necessarily need to declare the message queue from
         * producer, as producer doesn't need to know message queue. However to
         * make sure the message queue exist, we declare it here, as the
         * consumer may not declare it yet.
         */
        // send message
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = getMessage(argv);
        // The first parameter is the the name of the exchange. The empty string
        // denotes the default or nameless exchange: messages are routed to the
        // queue with the name specified by routingKey, if it exists.
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        // release resource
        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1)
            // return "Hello World!";
            return "..........";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0)
            return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
