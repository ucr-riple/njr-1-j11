package org.dclayer.net.socket;

import java.io.IOException;
import java.net.SocketAddress;

import org.dclayer.listener.net.OnReceiveListener;
import org.dclayer.net.Data;

public interface DatagramSocket {
	
	public void setOnReceiveListener(OnReceiveListener onReceiveListener);
	public void send(SocketAddress socketAddress, Data data) throws IOException;
	
}
