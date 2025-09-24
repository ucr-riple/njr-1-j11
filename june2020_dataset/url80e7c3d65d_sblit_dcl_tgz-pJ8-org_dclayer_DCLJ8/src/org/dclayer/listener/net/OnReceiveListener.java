package org.dclayer.listener.net;

import java.net.InetSocketAddress;

import org.dclayer.net.Data;

/**
 * used to execute callbacks upon data receipt
 */
public interface OnReceiveListener {
	/**
	 * Service-to-Service receive callback, called by UDPSocket
	 * @param inetSocketAddress the InetSocketAddress this was received from
	 * @param data TODO
	 */
	public void onReceiveS2S(InetSocketAddress inetSocketAddress, Data data);
}
