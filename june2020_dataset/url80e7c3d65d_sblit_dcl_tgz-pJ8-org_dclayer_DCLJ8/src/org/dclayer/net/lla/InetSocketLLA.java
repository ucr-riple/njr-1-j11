package org.dclayer.net.lla;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.InvalidServiceAddressException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedServiceAddressTypeException;
import org.dclayer.net.Data;
import org.dclayer.net.buf.ByteBuf;

public class InetSocketLLA extends LLA {
	
	public static void serialize(InetAddress inetAddress, int port, Data data) {
		byte[] addr = inetAddress.getAddress();
		switch(addr.length) {
		case 4: {
			data.prepare(addr.length+1+2);
			data.setByte(0, LLA.TYPE_INET4SOCKET);
			data.setBytes(1, addr, 0, addr.length);
			data.setByte(-2, (byte)((port >> 8) & 0xFF));
			data.setByte(-1, (byte)(port & 0xFF));
			break;
		}
		case 6: {
			data.prepare(addr.length+1+2);
			data.setByte(0, LLA.TYPE_INET6SOCKET);
			data.setBytes(1, addr, 0, addr.length);
			data.setByte(-2, (byte)((port >> 8) & 0xFF));
			data.setByte(-1, (byte)(port & 0xFF));
			break;
		}
		default: {
			data.prepare(addr.length+1+4+2);
			data.setByte(0, LLA.TYPE_INETSOCKET);
			data.setByte(1, (byte)((addr.length >> 24) & 0xFF));
			data.setByte(2, (byte)((addr.length >> 16) & 0xFF));
			data.setByte(3, (byte)((addr.length >> 8) & 0xFF));
			data.setByte(4, (byte)(addr.length & 0xFF));
			data.setBytes(5, addr, 0, addr.length);
			data.setByte(-2, (byte)((port >> 8) & 0xFF));
			data.setByte(-1, (byte)(port & 0xFF));
			break;
		}
		}
	}
	
	public static Data serialize(InetAddress inetAddress, int port) {
		Data data = new Data();
		serialize(inetAddress, port, data);
		return data;
	}
	
	//

	private InetSocketAddress inetSocketAddress;
	private Data data;
	
	public InetSocketLLA(InetAddress inetAddress, int port) {
		this(new InetSocketAddress(inetAddress, port));
	}
	
	public InetSocketLLA(InetSocketAddress inetSocketAddress) {
		this.inetSocketAddress = inetSocketAddress;
		int port = inetSocketAddress.getPort();
		InetAddress inetAddress = inetSocketAddress.getAddress();
		this.data = InetSocketLLA.serialize(inetAddress, port);
	}
	
	public InetSocketLLA(byte type, ByteBuf byteBuf) throws BufException, ParseException {
		this.readFromByteBuf(type, byteBuf);
	}

	private void readFromByteBuf(byte type, ByteBuf byteBuf) throws ParseException, BufException {
		byte[] addr;
		byte[] bytes;
		switch(type) {
		case LLA.TYPE_INET4SOCKET: {
			bytes = new byte[1+4+2];
			bytes[0] = type;
			byteBuf.read(bytes, 1, 4+2);
			addr = Arrays.copyOfRange(bytes, 1, 5);
			break;
		}
		case LLA.TYPE_INET6SOCKET: {
			bytes = new byte[1+16+2];
			bytes[0] = type;
			byteBuf.read(bytes, 1, 16+2);
			addr = Arrays.copyOfRange(bytes, 1, 17);
			break;
		}
		case LLA.TYPE_INETSOCKET: {
			int addrlen = (int) byteBuf.read32();
			bytes = new byte[1+4+addrlen+2];
			bytes[0] = type;
			bytes[1] = (byte)((addrlen >> 24) & 0xFF);
			bytes[2] = (byte)((addrlen >> 16) & 0xFF);
			bytes[3] = (byte)((addrlen >> 8) & 0xFF);
			bytes[4] = (byte)(addrlen & 0xFF);
			byteBuf.read(bytes, 5, addrlen+2);
			addr = Arrays.copyOfRange(bytes, 1, addrlen+1);
			break;
		}
		default: {
			throw new UnsupportedServiceAddressTypeException(type);
		}
		}
		
		this.data = new Data(bytes);
		
		int port = (((bytes[bytes.length-2] & 0xFF) << 8) | (bytes[bytes.length-1] & 0xFF));
		try {
			this.inetSocketAddress = new InetSocketAddress(InetAddress.getByAddress(addr), port);
		} catch (UnknownHostException e) {
			throw new InvalidServiceAddressException(e);
		}
	}

	@Override
	public SocketAddress getSocketAddress() {
		return inetSocketAddress;
	}

	@Override
	public Data getData() {
		return data;
	}

	@Override
	public int length() {
		return data.length();
	}

	

}
