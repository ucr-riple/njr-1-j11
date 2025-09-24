package org.dclayer.crypto.cipher;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.net.Data;

public class AesGcmCipher {
	
	public static final int MAC_BYTES = 16;
	public static final int DEFAULT_KEY_BYTES = 16;
	
	//
	
	private AESEngine aesEngine = new AESEngine();
	private GCMBlockCipher gcmBlockCipher = new GCMBlockCipher(aesEngine);
	
	private boolean forEncryption;
	private KeyParameter keyParameter;
	
	private Data ownOutData = new Data(1024);
	private Data outData;
	private int outDataLength = 0;
	private int inDataLength = 0;
	
	public AesGcmCipher(boolean forEncryption, byte[] key) {
		
		this.forEncryption = forEncryption;
		this.keyParameter = new KeyParameter(key);
		
	}
	
	/**
	 * clears this cipher's in- and out-data and (re-)initializes it with the given IV
	 * @param iv the initialization vector bytes
	 */
	public void reset(byte[] iv) {
		reset(iv, null);
	}
	
	/**
	 * clears this cipher's in- and out-data and (re-)initializes it with the given IV
	 * @param iv the initialization vector bytes
	 * @param outData the {@link Data} object the resulting bytes should be stored in
	 * @param prefixDatas {@link Data} objects containing bytes outData should be prefixed with, or null if outData should not be prefixed
	 */
	public void reset(byte[] iv, Data outData, Data... prefixDatas) {
		if(outData == null) {
			outData = this.ownOutData;
		}
		this.outData = outData;
		if(prefixDatas.length == 0) {
			outData.reset();
		} else {
			int n = 0;
			for(Data prefixData : prefixDatas) {
				if(prefixData != null) {
					n += prefixData.length();
				}
			}
			outData.prepare(n);
			int i = 0;
			for(Data prefixData : prefixDatas) {
				if(prefixData != null) {
					outData.setBytes(i, prefixData);
					i += prefixData.length();
				}
			}
		}
		init(iv);
		outDataLength = inDataLength = outData.length();
	}
	
	/**
	 * (re-)initializes this cipher with the given IV, not clearing its in- and out-data, thus enabling
	 * another message to be processed and returned by {@link AesGcmCipher#finish()} along with its predecessors.
	 * @param iv the initialization vector bytes
	 */
	public void init(byte[] iv) {
		gcmBlockCipher.init(forEncryption, new AEADParameters(keyParameter, MAC_BYTES*8, iv));
	}
	
	public void process(byte[] data, int offset, int length) {
		inDataLength += length;
		outData.enlarge(inDataLength);
		outDataLength += gcmBlockCipher.processBytes(data, offset, length, outData.getData(), outData.offset() + outDataLength);
	}
	
	public void process(byte b) {
		inDataLength++;
		outData.enlarge(inDataLength);
		outDataLength += gcmBlockCipher.processByte(b, outData.getData(), outData.offset() + outDataLength);
	}
	
	public void processAssociatedData(byte[] data, int offset, int length) {
		gcmBlockCipher.processAADBytes(data, offset, length);
	}
	
	public void processAssociatedData(byte b) {
		gcmBlockCipher.processAADByte(b);
	}
	
	public Data finish() throws CryptoException {
		
		outData.resize(inDataLength + MAC_BYTES);
		
		try {
			outDataLength += gcmBlockCipher.doFinal(outData.getData(), outData.offset() + outDataLength);
		} catch (IllegalStateException e) {
			throw new CryptoException(e);
		} catch (InvalidCipherTextException e) {
			throw new CryptoException(e);
		}
		
		outData.resize(outDataLength);
		
		return outData;
		
	}
	
}
