package org.dclayer.net.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import org.dclayer.listener.net.OnReceiveListener;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;

/**
 * a UDP Server that provides sending and callback on receive.
 */
public class UDPSocket extends Thread implements DatagramSocket, HierarchicalLevel {
	
	private HierarchicalLevel parentHierarchicalLevel;
	
	/**
	 * the {@link DatagramSocket} to listen on
	 */
	private java.net.DatagramSocket socket;
	/**
	 * the {@link OnReceiveListener} to call upon receipt
	 */
	private OnReceiveListener onReceiveListener;
	
	public UDPSocket(int port) throws SocketException {
		this.socket = new java.net.DatagramSocket(port);
		this.start();
	}
	
	@Override
	public void run() {
		
		Data data = new Data(0xFFFF);
		byte[] buf = data.getData();
		
		DatagramPacket p;
		
		for(;;) {
			
			p = new DatagramPacket(buf, buf.length);
			
			try {
				this.socket.receive(p);
			} catch (IOException e) {
				Log.exception(this, e);
				continue;
			}
			
			data.reset(0, p.getLength());
			
			Log.debug(this, "received %d bytes from %s: %s", data.length(), p.getSocketAddress().toString(), data);
			
			InetSocketAddress inetSocketAddress;
			try {
				inetSocketAddress = (InetSocketAddress) p.getSocketAddress();
			} catch(ClassCastException e) {
				Log.exception(this, e);
				return;
			}
			
			if(this.onReceiveListener == null) {
				
				Log.warning(this, "ignoring datagram from %s, service not ready yet", p.getSocketAddress().toString());
				
			} else {
				this.onReceiveListener.onReceiveS2S(inetSocketAddress, data);
			}
			
		}
	}
	
	/**
	 * send the given {@link Data} to the given {@link SocketAddress}
	 * @param socketAddress the {@link SocketAddress} to send the data
	 * @param data the {@link Data} to send
	 * @throws IOException if sending fails
	 */
	public void send(SocketAddress socketAddress, Data data) throws IOException {
		DatagramPacket p = new DatagramPacket(data.getData(), data.offset(), data.length(), socketAddress);
		this.socket.send(p);
		Log.debug(this, "sent %d bytes to %s: %s", p.getLength(), p.getSocketAddress(), data);
	}

	@Override
	public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
		this.onReceiveListener = onReceiveListener;
	}
	
	public void setParentHierarchicalLevel(HierarchicalLevel parentHierarchicalLevel) {
		this.parentHierarchicalLevel = parentHierarchicalLevel;
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return parentHierarchicalLevel;
	}
	
	@Override
	public String toString() {
		return "UDPSocket";
	}
	
}
