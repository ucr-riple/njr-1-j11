package org.dclayer.net.component;

import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.RSAKey;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedKeyTypeException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.componentinterface.KeyComponentI;

public class KeyComponent extends PacketComponent implements KeyComponentI {
	
	private RSAKeyComponent rsaKey = new RSAKeyComponent();

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		byte typeId = byteBuf.read();
		if(typeId != Key.RSA) throw new UnsupportedKeyTypeException(typeId);
		rsaKey.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.write(rsaKey.getTypeId());
		rsaKey.write(byteBuf);
	}

	@Override
	public int length() {
		return 1 + rsaKey.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { rsaKey };
	}

	@Override
	public String toString() {
		return String.format("KeyComponent(type=%02X)", rsaKey.getTypeId());
	}
	
	@Override
	public RSAKeyComponent setRSAKeyComponent() {
		return rsaKey;
	}
	
	@Override
	public AbsKeyComponent getKeyComponent() {
		return rsaKey;
	}

	@Override
	public void setKey(Key key) {
		// so far we only know RSA keys, so let's stick to that
		rsaKey.setKey((RSAKey) key);
	}

}
