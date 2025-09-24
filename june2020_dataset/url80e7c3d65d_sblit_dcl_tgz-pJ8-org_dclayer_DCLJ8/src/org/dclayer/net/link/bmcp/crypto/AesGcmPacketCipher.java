package org.dclayer.net.link.bmcp.crypto;

import java.security.SecureRandom;

import org.dclayer.crypto.cipher.AesGcmCipher;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.net.Data;
import org.dclayer.net.buf.ByteBuf;

public class AesGcmPacketCipher extends PacketCipher<ByteBuf> {
	
	public static final int IV_NUMBYTES = 16;
	
	//
	
	private SecureRandom secureRandom = new SecureRandom();
	
	private final AesGcmCipher cipher;
	
	private final Data ivData;
	
	public AesGcmPacketCipher(boolean forEncryption, byte[] key) {
		
		cipher = new AesGcmCipher(forEncryption, key);
		this.ivData = new Data(IV_NUMBYTES);
		
	}
	
	//
	
	public Data getIvData() {
		return ivData;
	}

	@Override
	protected Data finishEncryption() throws CryptoException {
		return cipher.finish();
	}

	@Override
	protected void initEncryption(Data outData, Data prefixData, int inDataLength) throws CryptoException {
		
		// make a new IV
		secureRandom.nextBytes(ivData.getData());
		
		// start encrypting the data, prepend the IV and prefixData to the encrypted data
		cipher.reset(ivData.getData(), outData, prefixData, ivData);
		
	}
	
	@Override
	protected void process(byte[] buf, int offset, int length) {
		cipher.process(buf, offset, length);
	}
	
	@Override
	protected void process(byte b) {
		cipher.process(b);
	}

	@Override
	public Data decrypt(Data encryptedData, Data outData) throws CryptoException {
		
		// read the IV
		ivData.setBytes(0, encryptedData, ivData.length());
		
		// read the encrypted bytes
		cipher.reset(ivData.getData(), outData);
		cipher.process(encryptedData.getData(), encryptedData.offset() + ivData.length(), encryptedData.length() - ivData.length());
		
		return cipher.finish();
		
	}
	
}
