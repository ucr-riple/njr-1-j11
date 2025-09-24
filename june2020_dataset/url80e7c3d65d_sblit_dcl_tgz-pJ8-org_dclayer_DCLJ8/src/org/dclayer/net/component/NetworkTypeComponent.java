package org.dclayer.net.component;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedNetworkIdentifierException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.componentinterface.NetworkTypeComponentI;
import org.dclayer.net.network.NetworkType;

public class NetworkTypeComponent extends PacketComponent implements NetworkTypeComponentI {
	
	private NetworkType networkType;
	private boolean allowNull;
	
	public NetworkTypeComponent(boolean allowNull) {
		this.allowNull = allowNull;
	}
	
	public NetworkTypeComponent() {
		this(false);
	}

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		try {
			this.networkType = NetworkType.fromByteBuf(byteBuf);
		} catch(UnsupportedNetworkIdentifierException e) {
			if(allowNull && e.getNetworkIdentifier().length() <= 0) {
				this.networkType = null;
			} else {
				throw e;
			}
		}
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		if(allowNull && networkType == null) {
			byteBuf.write((byte)0);
		} else {
			networkType.write(byteBuf);
		}
	}

	@Override
	public int length() {
		return networkType == null ? 1 : networkType.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("NetworkTypeComponent(networkType=%s)", networkType);
	}

	@Override
	public NetworkType getNetworkType() {
		return networkType;
	}

	@Override
	public void setNetworkType(NetworkType networkType) {
		this.networkType = networkType;
	}
	
}
