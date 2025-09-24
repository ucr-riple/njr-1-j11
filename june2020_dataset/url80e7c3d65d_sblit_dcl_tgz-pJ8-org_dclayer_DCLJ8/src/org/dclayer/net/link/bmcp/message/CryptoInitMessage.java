package org.dclayer.net.link.bmcp.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitPacketComponentI;
import org.dclayer.net.packetcomponent.ChildPacketComponent;

public class CryptoInitMessage extends ChildPacketComponent {
	
	private CryptoInitPacketComponentI cryptoInitPacketComponent;
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		cryptoInitPacketComponent.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		cryptoInitPacketComponent.write(byteBuf);
	}

	@Override
	public int length() {
		return cryptoInitPacketComponent.length();
	}

	@Override
	public PacketComponentI[] getChildren() {
		return new PacketComponentI[] { cryptoInitPacketComponent };
	}

	@Override
	public String toString() {
		return String.format("CryptoInitMessage(%s)", cryptoInitPacketComponent.getCryptoInitializationMethod().name());
	}
	
	public void setCryptoInitPacketComponent(CryptoInitPacketComponentI cryptoInitPacketComponent) {
		this.cryptoInitPacketComponent = cryptoInitPacketComponent;
	}
	
	public CryptoInitPacketComponentI getCryptoInitPacketComponent() {
		return cryptoInitPacketComponent;
	}

}
