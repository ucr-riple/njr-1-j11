package ping.pong.net.connection;

/**
 * EventListener for a Runnable so it can notify when the thread was closed
 * @author mfullen
 */
public interface RunnableEventListener
{
    /**
     * Allows the listener to be notified that the runnable has closed.
     * @param disconnectState
     */
    void onRunnableClosed(DisconnectState disconnectState);
}
