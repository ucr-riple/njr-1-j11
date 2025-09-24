package org.dclayer.net.a2s.rev35.component;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.componentinterface.NetworkTypeComponentI;
import org.dclayer.net.network.NetworkType;

public class NetworkTypeComponent extends PacketComponent implements NetworkTypeComponentI {
	
	private NetworkType networkType;

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		String identifier = byteBuf.readSpaceTerminatedString();
		String attributeString = byteBuf.readSpaceTerminatedString();
		networkType = NetworkType.make(identifier, attributeString);
		
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
		byteBuf.writeNonTerminatedString(networkType.getIdentifier());
		byteBuf.write((byte)' ');
		byteBuf.writeNonTerminatedString(networkType.getAttributeString());
		
	}

	@Override
	public int length() {
		return networkType.getIdentifier().length() + networkType.getAttributeString().length() + 1;
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("NetworkTypeComponent(networkType=%s", networkType);
	}
	
	public NetworkType getNetworkType() {
		return networkType;
	}
	
	public void setNetworkType(NetworkType networkType) {
		this.networkType = networkType;
	}

}
