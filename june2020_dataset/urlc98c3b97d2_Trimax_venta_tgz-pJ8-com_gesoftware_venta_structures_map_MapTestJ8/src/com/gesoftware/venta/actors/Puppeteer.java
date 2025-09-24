package com.gesoftware.venta.actors;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.network.Connection;
import com.gesoftware.venta.network.model.Message;
import com.gesoftware.venta.network.handlers.IClientHandler;
import com.gesoftware.venta.structures.map.SynchronizedMap;

import java.io.Serializable;
import java.util.Map;

/**
 * Puppeteer class definition
 */
public final class Puppeteer {
    /* Real connection */
    private final Connection m_Connection;

    /* Registered dispatchers */
    private final Map<Class, IDispatcher> m_Dispatchers = new SynchronizedMap<Class, IDispatcher>();

    /* *
     * METHOD: Gets dispatcher by class
     * RETURN: Dispatcher if registered, null otherwise
     *  PARAM: [IN] c - class to get dispatcher
     * AUTHOR: Dmitry Eliseev
     * */
    private IDispatcher getDispatcher(final Class c) {
        return m_Dispatchers.get(c);
    } /* End of 'Puppeteer::getDispatcher' method */

    /**
     * ClientHandler class definition
     */
    private final class ClientHandler implements IClientHandler {

        @Override
        @SuppressWarnings("unchecked")
        public final void onReceive(final Message message) {
            final Object object = message.getObject();
            final IDispatcher dispatcher  = getDispatcher(object.getClass());

            if (dispatcher != null) {
                final Object result = dispatcher.dispatch(object);
                if (result != null)
                    m_Connection.send(new Message(result));
            } else
                LoggingUtility.core("Dispatcher wasn't registered for objects of " + object.getClass().getName() + " class");
        } /* End of 'ClientHandler::onReceive' method */

        @Override
        public void onConnectionLost(String message) {
            LoggingUtility.core("Show finished: " + message);
        } /* End of 'ClientHandler::onConnectionLost' method */
    } /* End of 'Puppeteer::ClientHandler' class */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] host - theater's host
     *  PARAM: [IN] port - theater's port
     * AUTHOR: Dmitry Eliseev
     * */
    public Puppeteer(final String host, final int port) {
        m_Connection = new Connection(host, port, new ClientHandler());
        m_Connection.connect();
    } /* End of 'Puppeteer::Puppeteer' method */

    /* *
     * METHOD: Registers an class dispatcher
     *  PARAM: [IN] c     - expected class
     *  PARAM: [IN] actor - dispatcher to react for objects, that has instance of c (IDispatcher<c>)
     * AUTHOR: Dmitry Eliseev
     * */
    public final <T> void registerDispatcher(final Class<? extends T> c, final IDispatcher<? extends T> dispatcher) {
        m_Dispatchers.put(c, dispatcher);
    } /* End of 'Puppeteer::registerDispatcher' method */

    /* *
     * METHOD: Gives command to theater's actors
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] command - command for actors
     * AUTHOR: Dmitry Eliseev
     * */
    public final boolean giveCommand(final Serializable command) {
        return m_Connection.send(new Message(command));
    } /* End of 'Puppeteer::giveCommand' method */

    /* *
     * METHOD: Finish puppeteer's work
     * AUTHOR: Dmitry Eliseev
     * */
    public final void finish() {
        m_Connection.disconnect();
    } /* End of 'Puppeteer::finish' method */
} /* End of 'Puppeteer' class */
