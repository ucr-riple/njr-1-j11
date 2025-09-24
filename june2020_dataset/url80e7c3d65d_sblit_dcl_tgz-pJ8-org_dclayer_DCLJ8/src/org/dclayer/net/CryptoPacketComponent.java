package org.dclayer.net;

import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.KeyPair;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.misc.Toolbox;
import org.dclayer.net.buf.ByteBuf;

public abstract class CryptoPacketComponent extends PacketComponent implements CryptoPacketComponentI {
	
	protected Key publicKey;
	protected Key privateKey;

	@Override
	public final PacketComponentI[] getChildren() {
		return Toolbox.append(getCipherChildren(), getPlainChildren());
	}

	@Override
	public final String toString() {
		return String.format("%s(length=%d)/%s", componentName(), length(), plainToString());
	}
	
	@Override
	public final void setKeyPair(KeyPair keyPair) {
		setKeyPair(keyPair.getPublicKey(), keyPair.getPrivateKey());
	}
	
	@Override
	public final void setKeyPair(Key publicKey, Key privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		onSetKeyPair(publicKey, privateKey);
	}
	
	@Override
	public final Key getPrivateKey() {
		return privateKey;
	}
	
	@Override
	public final Key getPublicKey() {
		return publicKey;
	}
	
	protected PacketComponentI makePlainPacketComponent() {
		return new PacketComponentI() {
			@Override public void write(ByteBuf byteBuf) throws BufException { writePlain(byteBuf); }
			@Override public String represent(boolean tree, int level) { return null; }
			@Override public String represent(boolean tree) { return null; }
			@Override public String represent() { return null; }
			@Override public void read(ByteBuf byteBuf) throws ParseException, BufException { readPlain(byteBuf); }
			@Override public int length() { return plainLength(); }
			@Override public PacketComponentI[] getChildren() { return getPlainChildren(); }
		};
	}
	
	protected abstract void readPlain(ByteBuf byteBuf) throws ParseException, BufException;
	protected abstract void writePlain(ByteBuf byteBuf) throws BufException;
	protected abstract int plainLength();
	protected abstract PacketComponentI[] getPlainChildren();
	protected abstract String plainToString();
	
	protected abstract void onSetKeyPair(Key publicKey, Key privateKey);
	
	protected abstract PacketComponentI[] getCipherChildren();
	protected abstract String componentName();
	
}
