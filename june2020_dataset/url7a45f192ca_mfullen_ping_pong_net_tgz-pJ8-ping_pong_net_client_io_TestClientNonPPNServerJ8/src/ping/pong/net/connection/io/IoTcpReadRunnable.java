package ping.pong.net.connection.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.ConnectionExceptionHandler;
import ping.pong.net.connection.DisconnectState;
import ping.pong.net.connection.RunnableEventListener;
import ping.pong.net.connection.messaging.MessageProcessor;

/**
 * IoTcpReadRunnable is a thread for reading messages from an input stream.
 * The thread blocks when attempting to read data. When data is read it is passed
 * to the MessageProcessor
 * @author mfullen
 */
class IoTcpReadRunnable<MessageType> implements Runnable
{
    /**
     * Logger for IoTcpReadRunnable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IoTcpReadRunnable.class);
    /**
     * MessageProcessor to process the messages read
     */
    protected MessageProcessor<MessageType> messageProcessor = null;
    /**
     * The socket the thread is reading from
     */
    protected Socket tcpSocket = null;
    /**
     * The inputstream in which the thread is reading from
     */
    protected InputStream inputStream = null;
    /**
     * Flag for whether this thread is running
     */
    protected boolean running = false;
    /**
     * Notifies the listener when this runnable is closed
     */
    protected RunnableEventListener runnableEventListener = null;
    private DataReader dataReader = null;
    protected DisconnectState disconnectState = DisconnectState.NORMAL;

    /**
     * Constructor for the Read Thread
     * @param messageProcessor The processor which handles the incoming message
     * @param tcpSocket The socket in which the message is being read from
     */
    public IoTcpReadRunnable(MessageProcessor<MessageType> messageProcessor, RunnableEventListener runnableEventListener, DataReader dataReader, Socket tcpSocket)
    {
        this.messageProcessor = messageProcessor;
        this.tcpSocket = tcpSocket;
        this.runnableEventListener = runnableEventListener;
        this.dataReader = dataReader;
    }

    /**
     * Initialize the InputStream from the socket
     */
    protected void init()
    {
        //the data reader initializes the input stream
        this.inputStream = dataReader.init(this.tcpSocket);
    }

    /**
     * Is this thread still running/running?
     * @return
     */
    public boolean isRunning()
    {
        return this.running;
    }

    /**
     * Closes the thread but properly shuts down the socket by closing the inputstream
     * and the tcpsocket. Calls Socket.close. This doesn't have an effect if called
     * more than once
     */
    public void close()
    {
        try
        {
            this.running = false;
            if (this.inputStream != null)
            {
                this.inputStream.close();
            }
            synchronized (tcpSocket)
            {
                this.tcpSocket.close();
            }
        }
        catch (IOException ex)
        {
            LOGGER.error("Error closing Socket", ex);
        }
        finally
        {
            if (this.runnableEventListener != null)
            {
                this.runnableEventListener.onRunnableClosed(disconnectState);
                this.runnableEventListener = null;
            }
        }
    }

    @Override
    public void run()
    {
        this.init();
        this.running = true;


        while (this.running)
        {
            try
            {
                //read the data from the provided interface method
                Object readObject = dataReader.readData();
                if (readObject != null)
                {
                    this.messageProcessor.enqueueReceivedMessage((MessageType) readObject);
                }
                else
                {
                    LOGGER.error("Read Object is null");
                    disconnectState = DisconnectState.ERROR;
                    this.running = false;
                }
            }
            catch (Exception ex)
            {
                disconnectState = ConnectionExceptionHandler.handleException(ex, LOGGER);
                this.running = false;
            }
        }
        this.close();
    }
}
