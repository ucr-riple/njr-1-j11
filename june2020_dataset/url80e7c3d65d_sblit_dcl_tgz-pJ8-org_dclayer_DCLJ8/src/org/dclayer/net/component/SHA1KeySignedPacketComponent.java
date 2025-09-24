package org.dclayer.net.component;

import org.dclayer.crypto.hash.Hash;
import org.dclayer.crypto.hash.HashAlgorithm;
import org.dclayer.crypto.key.Key;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.CryptoParseException;
import org.dclayer.exception.net.parse.InvalidHashParseException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.CryptoPacketComponent;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;

public abstract class SHA1KeySignedPacketComponent extends CryptoPacketComponent {
	
	private final Hash hash = HashAlgorithm.SHA1.getInstance();
	
	//

	private KeyComponent keyComponent = new KeyComponent();
	
	private Data cipherHashData = new Data();
	private Data plainHashData = new Data();
	
	private DataComponent dataComponent = new DataComponent();
	
	private Data ownHashData = new Data(hash.getHashAlgorithm().getDigestNumBytes());
	
	private PacketComponentI plainPacketComponent = makePlainPacketComponent();
	
	@Override
	public final void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		// 1: key
		keyComponent.read(byteBuf);

		int numBytes;
		try {
			publicKey = keyComponent.getKeyComponent().getKey();
			numBytes = publicKey.getBlockNumBytes();
		} catch (CryptoException e) {
			throw new CryptoParseException(e);
		}
		
		privateKey = null;
		
		// 2: hash
		cipherHashData.prepare(numBytes);
		byteBuf.read(cipherHashData);
		
		try {
			plainHashData = publicKey.decrypt(cipherHashData);
		} catch (CryptoException e) {
			throw new CryptoParseException(e);
		}
		
		// 3: data
		dataComponent.read(byteBuf);
		
		hash.update(dataComponent.getData());
		hash.finish(ownHashData);
		if(!plainHashData.equals(ownHashData)) {
			throw new InvalidHashParseException(plainHashData, ownHashData);
		}
		
		dataComponent.getData(plainPacketComponent);
		
	}

	@Override
	public final void write(ByteBuf byteBuf) throws BufException {
		
		// 1: key
		keyComponent.write(byteBuf);
		
		dataComponent.setData(plainPacketComponent);
		
		hash.update(dataComponent.getData());
		hash.finish(ownHashData);
		
		try {
			cipherHashData = privateKey.encrypt(ownHashData);
		} catch (CryptoException e) {
			throw new BufException(e);
		}
		
		// 2: hash
		byteBuf.write(cipherHashData);
		
		// 3: data
		dataComponent.write(byteBuf);
		
	}
	
	@Override
	protected PacketComponentI[] getCipherChildren() {
		return new PacketComponentI[] { keyComponent, dataComponent };
	}

	@Override
	public final int length() {
		return keyComponent.length() + publicKey.getBlockNumBytes() + dataComponent.lengthForDataLength(plainLength());
	}
	
	@Override
	protected void onSetKeyPair(Key publicKey, Key privateKey) {
		keyComponent.setKey(publicKey);
	}
	
	@Override
	public final String componentName() {
		return String.format("SHA1KeySignedPacketComponent(sha1=%s)", ownHashData);
	}
	
}
