package org.dclayer.net.component;

import org.dclayer.crypto.key.Key;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.CryptoParseException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.buf.DataByteBuf;
import org.dclayer.net.componentinterface.KeyEncryptedPayloadI;

public class KeyEncryptedPayload extends PacketComponent implements KeyEncryptedPayloadI {

	private KeyComponent keyComponent = new KeyComponent();
	
	private Key publicKey;
	private Key privateKey;
	
	private Data ownCipherData = new Data();
	private Data cipherData = ownCipherData;
	
	private Data ownPlainData = new Data();
	private Data plainData = ownPlainData;
	
	private DataByteBuf dataByteBuf = new DataByteBuf();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		keyComponent.read(byteBuf);

		int numBytes;
		try {
			publicKey = keyComponent.getKeyComponent().getKey();
			numBytes = publicKey.getBlockNumBytes();
		} catch (CryptoException e) {
			throw new CryptoParseException(e);
		}
		
		(cipherData = ownCipherData).prepare(numBytes);
		byteBuf.read(cipherData);
		
		try {
			plainData = publicKey.decrypt(cipherData);
		} catch (CryptoException e) {
			throw new CryptoParseException(e);
		}
		
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
		keyComponent.write(byteBuf);
		
		try {
			cipherData = privateKey.encrypt(plainData);
		} catch (CryptoException e) {
			throw new BufException(e);
		}
		
		byteBuf.write(cipherData);
		
	}

	@Override
	public int length() {
		return keyComponent.length() + cipherData.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { keyComponent };
	}

	@Override
	public String toString() {
		return String.format("KeyEncryptedPayload(cipherDataLen=%d, plainDataLen=%d)", cipherData.length(), plainData.length());
	}

	@Override
	public ByteBuf getWriteByteBuf(Key publicKey, Key privateKey, int payloadLength) {
		
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		
		this.plainData = this.ownPlainData;
		this.plainData.prepare(payloadLength);
		
		this.dataByteBuf.setData(this.plainData);
		
		return this.dataByteBuf;
		
	}

	@Override
	public ByteBuf getReadByteBuf() {
		
		this.dataByteBuf.setData(plainData);
		return this.dataByteBuf;
		
	}
	
	@Override
	public Key getKey() {
		return publicKey;
	}

}
