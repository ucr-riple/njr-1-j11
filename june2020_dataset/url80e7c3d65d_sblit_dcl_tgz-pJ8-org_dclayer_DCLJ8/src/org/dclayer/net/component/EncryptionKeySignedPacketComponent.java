package org.dclayer.net.component;

import org.dclayer.crypto.key.Key;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.CryptoParseException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.CryptoPacketComponent;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.buf.DataByteBuf;

public abstract class EncryptionKeySignedPacketComponent extends CryptoPacketComponent {

	private KeyComponent keyComponent = new KeyComponent();
	
	private DataComponent cipherDataComponent = new DataComponent();
	
	private Data ownPlainData = new Data();
	private Data plainData = ownPlainData;
	
	private DataByteBuf dataByteBuf = new DataByteBuf();
	
	@Override
	public final void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		keyComponent.read(byteBuf);
		
		try {
			publicKey = keyComponent.getKeyComponent().getKey();
		} catch (CryptoException e) {
			throw new CryptoParseException(e);
		}
		
		privateKey = null;
		
		cipherDataComponent.read(byteBuf);
		
		try {
			plainData = publicKey.decrypt(cipherDataComponent.getData());
		} catch (CryptoException e) {
			throw new CryptoParseException(e);
		}
		
		dataByteBuf.setData(plainData);
		readPlain(dataByteBuf);
		
	}

	@Override
	public final void write(ByteBuf byteBuf) throws BufException {
		
		plainData.prepare(plainLength());
		dataByteBuf.setData(plainData);
		writePlain(dataByteBuf);
		
		keyComponent.write(byteBuf);
		
		try {
			cipherDataComponent.setData(privateKey.encrypt(plainData));
		} catch (CryptoException e) {
			throw new BufException(e);
		}
		
		cipherDataComponent.write(byteBuf);
		
	}
	
	@Override
	protected PacketComponentI[] getCipherChildren() {
		return new PacketComponentI[] { keyComponent };
	}

	@Override
	public final int length() {
		int cipherDataLength = (publicKey.getBlockNumBytes() * (((plainLength() - 1) / publicKey.getMaxEncryptionBlockNumBytes()) + 1));
		return keyComponent.length() + cipherDataComponent.lengthForDataLength(cipherDataLength);
	}
	
	@Override
	protected void onSetKeyPair(Key publicKey, Key privateKey) {
		keyComponent.setKey(publicKey);
	}
	
	@Override
	public final String componentName() {
		return "EncryptionKeySignedPacketComponent";
	}
	
}
