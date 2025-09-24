package org.dclayer.net.link.bmcp.crypto.init.component;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitializationMethod;

public class CryptoInitializerIdentifierComponent extends PacketComponent {
	
	private FlexNum flexNum = new FlexNum(0, Integer.MAX_VALUE);
	
	private CryptoInitializationMethod cryptoInitializationMethod;

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		flexNum.read(byteBuf);
		cryptoInitializationMethod = CryptoInitializationMethod.get((int) flexNum.getNum());
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		flexNum.write(byteBuf);
	}

	@Override
	public int length() {
		return flexNum.length();
	}

	@Override
	public PacketComponentI[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("CryptoInitializerIdentifierComponent(id %d, %s)", flexNum.getNum(), cryptoInitializationMethod == null ? "unknown" : cryptoInitializationMethod.name());
	}
	
	public CryptoInitializationMethod getCryptoInitializationMethod() {
		return cryptoInitializationMethod;
	}
	
	public void setCryptoInitializationMethod(CryptoInitializationMethod cryptoInitializationMethod) {
		this.cryptoInitializationMethod = cryptoInitializationMethod;
		this.flexNum.setNum(cryptoInitializationMethod.getId());
	}

}
