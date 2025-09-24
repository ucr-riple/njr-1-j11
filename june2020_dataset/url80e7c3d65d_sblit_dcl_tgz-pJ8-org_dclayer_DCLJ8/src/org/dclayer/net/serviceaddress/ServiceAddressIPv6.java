package org.dclayer.net.serviceaddress;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.net.buf.ByteBuf;

public class ServiceAddressIPv6 extends ServiceAddress {
	
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
		return Inet6Address.getByAddress(host);
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
	
	public ServiceAddressIPv6(Inet6Address host, int port) {
		super(ServiceAddress.IPV6_WITH_PORT);
		this.socketAddress = makeSocketAddress(host, port);
		this.host = host.getAddress();
		this.port = port;
	}
	
	public ServiceAddressIPv6(ByteBuf byteBuf) throws BufException, UnknownHostException {
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
		this.host = new byte[16];
		byteBuf.read(this.host);
		this.port = byteBuf.read16();
	}

	@Override
	public byte getMinRevision() {
		return 0;
	}

	@Override
	public int length() {
		return 18;
	}

	@Override
	public SocketAddress getSocketAddress() {
		return socketAddress;
	}
	
	@Override
	public String toString() {
		return String.format("[%X:%X:%X:%X:%X:%X:%X:%X]:%d",
				0xFFFF&((host[0]<<8)|host[1]), 0xFFFF&((host[2]<<8)|host[3]),
				0xFFFF&((host[4]<<8)|host[5]), 0xFFFF&((host[6]<<8)|host[7]),
				0xFFFF&((host[8]<<8)|host[9]), 0xFFFF&((host[10]<<8)|host[11]),
				0xFFFF&((host[12]<<8)|host[13]), 0xFFFF&((host[14]<<8)|host[15]),
				port);
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
		if(!(o instanceof ServiceAddressIPv6)) return false;
		ServiceAddressIPv6 addr = (ServiceAddressIPv6) o;
		return this.port == addr.getPort() && Arrays.equals(addr.getHostData(), this.getHostData());
	}
}
