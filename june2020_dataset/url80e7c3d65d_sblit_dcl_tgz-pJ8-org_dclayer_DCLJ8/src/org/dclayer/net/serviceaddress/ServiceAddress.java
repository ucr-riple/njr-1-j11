package org.dclayer.net.serviceaddress;

import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.UnsupportedServiceAddressTypeException;
import org.dclayer.iface.net.RevisionDependency;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.buf.DataByteBuf;

/**
 * base class for addresses describing a remote DCL service instance
 */
public abstract class ServiceAddress implements RevisionDependency {

	/**
	 * an IP version 4 address with a port number
	 */
	public static final byte IPV4_WITH_PORT = 0x00;
	/**
	 * an IP version 6 address with a port number
	 */
	public static final byte IPV6_WITH_PORT = 0x01;

	/**
	 * creates a new {@link ServiceAddress} instance of the given type and the data in the given {@link ByteBuf}
	 * @param type the {@link ServiceAddress} type
	 * @param byteBuf the {@link ByteBuf} to read address data from
	 * @return the new {@link ServiceAddress} instance
	 * @throws BufException if an operation on the given {@link ByteBuf} fails
	 * @throws UnsupportedServiceAddressTypeException if the given {@link ServiceAddress} type is unknown
	 * @throws UnknownHostException if the data in the given {@link ByteBuf} is invalid address data
	 */
	public static ServiceAddress make(byte type, ByteBuf byteBuf)
			throws BufException, UnsupportedServiceAddressTypeException, UnknownHostException {
		switch (type) {
		case IPV4_WITH_PORT: {
			return new ServiceAddressIPv4(byteBuf);
		}
		case IPV6_WITH_PORT: {
			return new ServiceAddressIPv6(byteBuf);
		}
		default: {
			throw new UnsupportedServiceAddressTypeException(type);
		}
		}
	}

	//

	/**
	 * the type of this {@link ServiceAddress}
	 */
	private byte type;
	/**
	 * the address data of this {@link ServiceAddress}
	 */
	private byte[] data;

	public ServiceAddress(byte type) {
		this.type = type;
	}

	/**
	 * @return the type of this {@link ServiceAddress}
	 */
	public byte getType() {
		return this.type;
	}
	
	/**
	 * converts this {@link ServiceAddress} to a {@link SocketAddress} and returns it
	 * @return the newly converted {@link SocketAddress}
	 */
	public abstract SocketAddress getSocketAddress();
	
	/**
	 * @return the address data of this {@link ServiceAddress}, not including the {@link ServiceAddress} type byte
	 */
	public byte[] getData() {
		if(data == null) {
			data = new byte[this.length()];
			try {
				writeToBuf(new DataByteBuf(data));
			} catch (BufException e) {
				throw new RuntimeException(e);
			}
		}
		return data;
	}
	
	/**
	 * writes the address data of this {@link ServiceAddress} to the given {@link ByteBuf} (not including the {@link ServiceAddress} type byte)
	 * @param byteBuf the {@link ByteBuf} to write the address data to
	 * @throws BufException if an operation on the given {@link ByteBuf} fails
	 */
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.write(getData());
	}

	/**
	 * writes the address data of this {@link ServiceAddress} to the given {@link ByteBuf} (not including the {@link ServiceAddress} type byte)
	 * @param byteBuf the {@link ByteBuf} to write the address data to
	 * @throws BufException if an operation on the given {@link ByteBuf} fails
	 */
	protected abstract void writeToBuf(ByteBuf byteBuf) throws BufException;
	/**
	 * @return the length of this {@link ServiceAddress}'s address data in bytes, not including the {@link ServiceAddress} type byte
	 */
	public abstract int length();
	
	public abstract String toString();
	public abstract boolean equals(Object o);

}
