package com.mpos.lottery.te.thirdpartyservice.amqp;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BlockedListener;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ReturnListener;

public class RabbitMessagePublishMain {
    private static Log logger = LogFactory.getLog(RabbitMessagePublishMain.class);
    public static final String EXCHANGE_NAME = RabbitMessageConsumerMain.EXCHANGE_NAME;
    private String host;
    private String vhost;
    private int port;
    private String username;
    private String password;

    public RabbitMessagePublishMain(String host, String vhost, int port, String username, String password) {
        this.host = host;
        this.vhost = vhost;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void publish(String exchangeName, String routingKey, AMQP.BasicProperties props, byte[] message)
            throws Exception {
        // ConnectionFactory can be reused between threads.
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.getHost());
        factory.setVirtualHost(this.vhost);
        factory.setPort(this.port);
        factory.setUsername(this.username);
        factory.setPassword(this.password);
        factory.setConnectionTimeout(10 * 1000);
        // doesn't help if server got out of space
        // factory.setRequestedHeartbeat(1);
        final Connection connection = factory.newConnection();
        /**
         * this is new API introduced in client v3.3.5, refer to
         * https://www.rabbitmq.com/connection-blocked.html
         */
        connection.addBlockedListener(new BlockedListener() {

            @Override
            public void handleBlocked(String msg) throws IOException {
                logger.warn("Connection BLOCKED: " + msg);
            }

            @Override
            public void handleUnblocked() throws IOException {
                logger.warn("Connection UNBLOCKED!)");
            }

        });

        Channel channel = connection.createChannel();
        // declare a 'topic' type of exchange
        channel.exchangeDeclare(exchangeName, "topic", true);
        /**
         * If a message is published with the "mandatory" flags set, but cannot
         * be routed, the broker will return it to the sending client (via a
         * AMQP.Basic.Return command).
         * <p>
         * For unroutable messages, the broker will issue a confirm once the
         * exchange verifies a message won't route to any queue (returns an
         * empty list of queues). If the message is also published as mandatory,
         * the basic.return is sent to the client before basic.ack.
         * */
        channel.addReturnListener(new ReturnListener() {

            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
                    AMQP.BasicProperties properties, byte[] body) throws IOException {
                logger.warn("[X]Returned message(replyCode:" + replyCode + ",replyText:" + replyText
                        + ",exchange:" + exchange + ",routingKey:" + routingKey + ",body:" + new String(body));

                /**
                 * You can't close connection before receive the RETURN or
                 * DELIVER command which issued by RabbitMQ broker, otherwise no
                 * any listener will be invoked if events reach after the close
                 * of connection. <b>And no any exception thrown out in this
                 * case...what a pitty!</b>
                 * <p>
                 * OH, also can't close connection here, as if we put a channel
                 * into confirm mode, broker will mark a message as Acked if
                 * decides a message will not be routed to any queues. In this
                 * case both <code>ReturnListener</code> and
                 * <code>ConfirmListener<code> will be called, if close connection here, <code>ConfirmListener</code>
                 * may can't work.
                 * <p>
                 * Can close connection after call
                 * Channel#waitForConfirmsOrDie()
                 */
                // RabbitMessagePublishMain.this.release(connection);
            }

        });

        /**
         * This method sets the channel to use publisher acknowledgements. The
         * client can only use this method on a non-transactional channel.
         * <p>
         * Refer to
         * http://www.rabbitmq.com/amqp-0-9-1-reference.html#class.confirm
         */
        channel.confirmSelect();
        /**
         * Implement this interface in order to be notified of Confirm events.
         * Acks represent messages handled succesfully; Nacks represent messages
         * lost by the broker. Note, the lost messages could still have been
         * delivered to consumers, but the broker cannot guarantee this.
         * <p>
         * Refer to http://www.rabbitmq.com/confirms.html
         */
        channel.addConfirmListener(new ConfirmListener() {

            /**
             * When will messages be confirmed?
             * <p>
             * For unroutable messages, the broker will issue a confirm once the
             * exchange verifies a message won't route to any queue (returns an
             * empty list of queues). If the message is also published as
             * mandatory, the basic.return is sent to the client before
             * basic.ack.
             * <p>
             * For routable messages, the basic.ack is sent when a message has
             * been accepted by all the queues. For persistent messages routed
             * to durable queues, this means persisting to disk. For mirrored
             * queues, this means that all mirrors have accepted the message.
             * <p>
             * Refer to refer to
             * <ol>
             * <li>http://www.rabbitmq.com/confirms.html</li>
             * <li>
             * http://www.rabbitmq.com/blog/2011/02/10/introducing-publisher-
             * confirms/</li>
             * </ol>
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                /**
                 * What is <code>multiple</code>?
                 * <p>
                 * The broker may also set the multiple field in basic.ack to
                 * indicate that all messages up to and including the one with
                 * the sequence number have been handled.
                 */
                logger.info("Ack: " + deliveryTag);
                // RabbitMessagePublishMain.this.release(connection);
            }

            /**
             * However in which case this method will be called??
             * <p>
             * basic.nack will only be delivered if an internal error occurs in
             * the Erlang process responsible for a queue.
             * <p>
             * refer to http://www.rabbitmq.com/confirms.html
             */
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                logger.info("Nack: " + deliveryTag);
                // RabbitMessagePublishMain.this.release(connection);
            }

        });

        /**
         * If set 'immediate' to true, the publisher will throw
         * " com.rabbitmq.client.AlreadyClosedException: clean connection shutdown; reason: Attempt to use closed channel"
         * , as the server will immediately close the connection once received
         * message.
         * <p>
         * Diff it with the case where no 'immediate' set, in fact RabbitMQ
         * doesn't support this parameter.
         */
        logger.debug("Prepare to publish message.");
        channel.basicPublish(exchangeName, routingKey, props, message);
        logger.debug("Publish message successfully");

        /**
         * Why my returnListener is not invoked?
         * <p>
         * http://rabbitmq.1065348.n5.nabble.com/ReturnListener-is-not-invoked-
         * td24549.html
         * <p>
         * The call to Channel#waitForConfirmsOrDie() will wait until all
         * messages published since the last call have been either ack'd or
         * nack'd by the broker.
         * <p>
         * Must call channel.confirmSelecte() first to enable confirm mode on
         * channel.
         */
        logger.debug("Wait for confirmation message");
        channel.waitForConfirmsOrDie(30*1000);
        logger.debug("Message confirmed");
        // now we can close connection
        connection.close();
    }

    private void release(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        }
        catch (Exception e) {
            logger.warn(e);
        }
    }

    public String getHost() {
        return host;
    }

    public static void main(String[] argv) throws Exception {
        RabbitMessagePublishMain main = new RabbitMessagePublishMain("192.168.2.158", "/te", 5672, "amqp",
                "amqp");
        main.publish(EXCHANGE_NAME, EXCHANGE_NAME + ".-1", MessageProperties.PERSISTENT_BASIC,
                "hello, ramon".getBytes());
        logger.debug(" [x] Published message successfully.");
        // deleteExchange(EXCHANGE_NAME);
    }

    public static void deleteExchange(String exchangeName) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.100.68");
        factory.setVirtualHost("/");
        factory.setUsername("amqp");
        factory.setPassword("amqp");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDelete(exchangeName);
        connection.close();
    }

}
