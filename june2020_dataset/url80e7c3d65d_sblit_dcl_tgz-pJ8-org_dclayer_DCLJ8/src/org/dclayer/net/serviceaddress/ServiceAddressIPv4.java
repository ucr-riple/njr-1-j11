package org.dclayer.net.serviceaddress;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.net.buf.ByteBuf;

public class ServiceAddressIPv4 extends ServiceAddress {
	
	/**
	 * creates a new {@link InetSocketAddress} from the given {@link InetAddress} and port
	 * @param host the {@link InetAddress} to use for the new {@link InetSocketAddress}
	 * @param port the port to use for the new {@link InetSocketAddress}
	 * @return a new {@link InetSocketAddress} from the given {@link InetAddress} and port
	 */
	private static SocketAddress makeSocketAddress(InetAddress host, int port) {
		return new InetSocketAddress(host, port);
	}
	
	/**
	 * creates a new {@link InetAddress} from the given host address data
	 * @param host the host address data to use for the new {@link InetAddress}
	 * @return a new {@link InetAddress} from the given host address data
	 * @throws UnknownHostException if the given host address data is invalid
	 */
	private static InetAddress makeInetAddress(byte[] host) throws UnknownHostException {
		return Inet4Address.getByAddress(host);
	}
	
	/**
	 * the host address data (i.e. the IP address)
	 */
	private byte[] host;
	/**
	 * the port
	 */
	private int port;
	/**
	 * a {@link SocketAddress} that equals this {@link ServiceAddress}
	 */
	private SocketAddress socketAddress;
	
	public ServiceAddressIPv4(Inet4Address host, int port) {
		super(ServiceAddress.IPV4_WITH_PORT);
		this.socketAddress = makeSocketAddress(host, port);
		this.host = host.getAddress();
		this.port = port;
	}
	
	public ServiceAddressIPv4(ByteBuf byteBuf) throws BufException, UnknownHostException {
		super(ServiceAddress.IPV4_WITH_PORT);
		this.read(byteBuf);
		this.socketAddress = makeSocketAddress(makeInetAddress(host), port);
	}

	@Override
	protected void writeToBuf(ByteBuf byteBuf) throws BufException {
		byteBuf.write(host);
		byteBuf.write16(port);
	}
	
	/**
	 * reconstructs this {@link ServiceAddress} from data in the given {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} from which to read the data for reconstruction
	 * @throws BufException if an operation on the {@link ByteBuf} fails
	 */
	private void read(ByteBuf byteBuf) throws BufException {
		this.host = new byte[4];
		byteBuf.read(this.host);
		this.port = byteBuf.read16();
	}

	@Override
	public byte getMinRevision() {
		return 0;
	}

	@Override
	public int length() {
		return 6;
	}

	@Override
	public SocketAddress getSocketAddress() {
		return socketAddress;
	}
	
	@Override
	public String toString() {
		return String.format("%d.%d.%d.%d:%d", 0xFF&host[0], 0xFF&host[1], 0xFF&host[2], 0xFF&host[3], port);
	}
	
	/**
	 * @return the port of this {@link ServiceAddress}
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * @return the host address data of this {@link ServiceAddress}
	 */
	public byte[] getHostData() {
		return host;
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(!(o instanceof ServiceAddressIPv4)) return false;
		ServiceAddressIPv4 addr = (ServiceAddressIPv4) o;
		return this.port == addr.getPort() && Arrays.equals(addr.getHostData(), this.getHostData());
	}

}
