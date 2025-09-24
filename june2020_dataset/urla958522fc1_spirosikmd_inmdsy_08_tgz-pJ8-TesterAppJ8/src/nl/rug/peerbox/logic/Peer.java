package nl.rug.peerbox.logic;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Peer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InetAddress address;
	private int port;
	private String name;

	public static Peer createPeer(byte[] ip, int port, String name) {
		Peer h = new Peer();
		try {
			h.address = InetAddress.getByAddress(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		h.port = port;
		h.name = name;
		return h;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Peer)) {
			return false;
		}

		Peer other = (Peer) obj;

		return (address.equals(other.address) && port == other.port && name.equals(other.name));
	}
	
	@Override
	public int hashCode() {
		return address.hashCode() + port + name.hashCode() + 23;
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getName() {
		return name;
	}


	

	@Override
	public String toString() {
		return address.getHostAddress() + ":" + port;
	}
}
