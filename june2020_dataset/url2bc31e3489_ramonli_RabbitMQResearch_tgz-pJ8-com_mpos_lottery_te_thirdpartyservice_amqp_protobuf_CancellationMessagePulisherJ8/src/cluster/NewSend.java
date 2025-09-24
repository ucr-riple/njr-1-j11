package cluster;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * > ConnectionFactory factory = new ConnectionFactory();<br/>
 * > Address[] addresses = {new Address("localhost", 12345), new <br/>
 * > Address("localhost", 12346)}; > factory.newConnection(addresses); <br/>
 * > <br/>
 * > If I'll do this, how can I know what node of cluster I'm connected?
 * <p>
 * It shouldn't matter which node you are connected to (unless you care about
 * the locations of queues). Clients see an identical view of the world
 * regardless of which broker in the same cluster they connect to.
 * <p>
 * A channel can query the underlying connection. You can find the address of
 * the clustered node that way if necessary.
 * <p>
 * > If node 1 down, but node 2 still up, how can I reconnect in node 1?
 * <p>
 * The factory connection does not offer robust reconnection logic. If you want
 * clients to reconnect then take a look at the message patterns library:
 * <p>
 * http://hg.rabbitmq.com/rabbitmq-java-messagepatterns
 * <p>
 * This is a higher-level library that offers reconnection logic, which is
 * sufficiently flexible for use with clusters.
 * <p>
 * Refer to https://groups.google.com/forum/#!topic/rabbitmq-discuss/Qvbff7jnAC4
 * 
 * @author Ramon
 * 
 */
public class NewSend {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws java.io.IOException {
        // create connection
        ConnectionFactory factory = new ConnectionFactory();
        // setup a cluster contains 2 nodes[new Address("localhost", 5672), new Address("localhost", 5673)]
        // refer to "A cluster on a single machine"#http://www.rabbitmq.com/clustering.html
        Connection connection = factory.newConnection(new Address[] { new Address("localhost", 5672)});        
        Channel channel = connection.createChannel();

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
