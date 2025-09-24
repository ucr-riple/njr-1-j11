package org.dclayer.application;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class Service {
	
	private InetAddress inetAddress;
	private int port;
	
	/**
	 * Creates a new {@link Service) instance pointing at a DCL service running at the given address and port
	 * @param inetAddress the host address that the DCL service is listening on for incoming application connections
	 * @param port the port that the DCL service is listening on for incoming application connections
	 */
	public Service(InetAddress inetAddress, int port) {
		this.inetAddress = inetAddress;
		this.port = port;
	}
	
	/**
	 * Creates a new {@link Service) instance pointing at a DCL service running at the given host and port
	 * @param host the address or name that the DCL service is listening on for incoming application connections
	 * @param port the port that the DCL service is listening on for incoming application connections
	 */
	public Service(String host, int port) throws UnknownHostException {
		this(InetAddress.getByName(host), port);
	}
	
	/**
	 * Creates a new {@link Service) instance pointing at a DCL service running on the local host, listening on the given port
	 * @param port the port of the local host that the DCL service is listening on for incoming application connections
	 */
	public Service(int port) throws UnknownHostException {
		this(InetAddress.getLoopbackAddress(), port);
	}
	
	/**
	 * Returns a new {@link ApplicationInstanceBuilder} that can be used to get a new {@link ApplicationInstance} connected to this {@link Service}
	 * @return the new {@link ApplicationInstanceBuilder}
	 */
	public ApplicationInstanceBuilder applicationInstance() {
		return new ApplicationInstanceBuilder(this);
	}
	
	/**
	 * Returns the host address that the DCL service listens on for incoming application connections
	 * @return the host address that the DCL service listens on for incoming application connections
	 */
	public InetAddress getInetAddress() {
		return inetAddress;
	}
	
	/**
	 * Returns the port that the DCL service listens on for incoming application connections
	 * @return the port that the DCL service listens on for incoming application connections
	 */
	public int getPort() {
		return port;
	}
	
}
