/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ping.pong.net.server;

import java.util.Collection;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageListener;

/**
 *
 * @author Adrian
 */
class DefaultServer implements Server
{
    private boolean running;

    public DefaultServer()
    {
    }



    @Override
    public void start()
    {
        this.running = true;
    }

    @Override
    public void shutdown()
    {
        this.running = false;
    }

    @Override
    public Connection getConnection(int id)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Connection> getConnections()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasConnections()
    {
        return false;
    }

    @Override
    public boolean isListening()
    {
        return this.running;
    }

    @Override
    public void addMessageListener(MessageListener listener)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeMessageListener(MessageListener listener)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addConnectionListener(ServerConnectionListener connectionListener)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeConnectionListener(ServerConnectionListener connectionListener)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNextAvailableId()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void broadcast(Envelope message)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
