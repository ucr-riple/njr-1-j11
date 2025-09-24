package com.gesoftware.venta.actors;

import com.gesoftware.venta.network.handlers.IServerHandler;
import com.gesoftware.venta.structures.map.SynchronizedMap;
import com.gesoftware.venta.network.model.ServerResponse;
import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.network.model.Message;
import com.gesoftware.venta.network.Server;
import java.net.InetAddress;
import java.util.Map;

/**
 * Theater class definition
 */
public final class Theater implements Runnable {
    /* Empty response for classes without handlers */
    private final static ServerResponse c_EmptyResponse = new ServerResponse(null);

    /* Real server */
    private final Server m_Server;

    /* Registered actors */
    private final Map<Class, IActor> m_Actors = new SynchronizedMap<Class, IActor>();

    /* *
     * METHOD: Gets actor by class
     * RETURN: Actor if registered, null otherwise
     *  PARAM: [IN] c - class to get actor
     * AUTHOR: Dmitry Eliseev
     * */
    private IActor getActor(final Class c) {
        return m_Actors.get(c);
    } /* End of 'Theater::getActor' method */

    /**
     * Theater::ServerHandler class definition
     */
    private final class ServerHandler implements IServerHandler {
        @Override
        public boolean onConnect(String clientID, InetAddress clientAddress) {
            return true;
        } /* End of 'ServerHandler::onConnect' method */

        @Override
        @SuppressWarnings("unchecked")
        public ServerResponse onReceive(String clientID, Message message) {
            final Object object = message.getObject();
            final IActor actor  = getActor(object.getClass());

            if (actor != null) {
                final Object result = actor.react(clientID, object);
                return (result != null)?new ServerResponse(new Message(result)):c_EmptyResponse;
            } else
                LoggingUtility.core("Actor wasn't registered for objects of " + object.getClass().getName() + " class");

            return c_EmptyResponse;
        } /* End of 'ServerHandler::onReceive' method */

        @Override
        public void onDisconnect(String clientID) {

        } /* End of 'ServerHandler::onDisconnect' method */
    } /* End of 'Theater::ServerHandler' class */

    /* *
     * METHOD: Theater class constructor
     *  PARAM: [IN] port - listening port
     * AUTHOR: Dmitry Eliseev
     * */
    public Theater(final int port) {
        m_Server = new Server(port, new ServerHandler());
    } /* End of 'Theater::Theater' method */

    /* *
     * METHOD: Registers an class actor
     *  PARAM: [IN] c     - expected class
     *  PARAM: [IN] actor - actor to react for objects, that has instance of c (IActor<c>)
     * AUTHOR: Dmitry Eliseev
     * */
    public final <T> void registerActor(final Class<? extends T> c, final IActor<? extends T> actor) {
        m_Actors.put(c, actor);
    } /* End of 'Theater::registerActor' method */

    @Override
    public final void run() {
        m_Server.run();
    } /* End of 'Theater::run' method */

    public final void finish() {
        m_Server.stop();
    }
} /* End of 'Theater' class */
