package org.dclayer.net.lla;

import java.net.SocketAddress;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedServiceAddressTypeException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;

public abstract class LLA implements PacketComponentI {
	
	public static final byte TYPE_INETSOCKET = 0x00;
	public static final byte TYPE_INET4SOCKET = 0x01;
	public static final byte TYPE_INET6SOCKET = 0x02;
	
	public static LLA fromByteBuf(ByteBuf byteBuf) throws BufException, ParseException {
		byte type = byteBuf.read();
		switch(type) {
		case TYPE_INETSOCKET:
		case TYPE_INET4SOCKET:
		case TYPE_INET6SOCKET: {
			return new InetSocketLLA(type, byteBuf);
		}
		default: {
			throw new UnsupportedServiceAddressTypeException(type);
		}
		}
	}
	
	//
	
	@Override
	public String toString() {
		return getSocketAddress().toString();
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.write(getData());
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof LLA)) return false;
		return getSocketAddress().equals(((LLA)o).getSocketAddress());
	}
	
	@Override
	public int hashCode() {
		return getSocketAddress().hashCode();
	}
	
	/**
	 * @return a {@link Data} object containing the type and the information of this LLA
	 */
	public abstract Data getData();
	@Override
	public abstract int length();
	public abstract SocketAddress getSocketAddress();
	
	@Override
	public PacketComponentI[] getChildren() {
		return null;
	}
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		throw new ParseException("use LLA.fromByteBuf()");
	}
	
	@Override
	public String represent() {
		return String.format("LLA(%s)", toString());
	}
	
	@Override
	public String represent(boolean tree) {
		return PacketComponent.represent(this, tree);
	}
	
	@Override
	public String represent(boolean tree, int level) {
		return PacketComponent.represent(this, tree, level);
	}
	
}
