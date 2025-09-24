package org.dclayer.crypto.key;

import java.math.BigInteger;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.OAEPEncoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.dclayer.exception.crypto.InsufficientKeySizeException;
import org.dclayer.exception.crypto.InvalidCipherCryptoException;
import org.dclayer.net.Data;

public abstract class RSAKey extends Key {
	
	private RSAKeyParameters rsaKeyParameters;
	
	private OAEPEncoding encryptionOAEP;
	private OAEPEncoding decryptionOAEP;
	
	public RSAKey(boolean isPrivate, final BigInteger modulus, final BigInteger exponent) throws InsufficientKeySizeException {
		this(new RSAKeyParameters(isPrivate, modulus, exponent));
	}
	
	public RSAKey(RSAKeyParameters rsaKeyParameters) throws InsufficientKeySizeException {
		super(rsaKeyParameters.getModulus().bitLength());
		this.rsaKeyParameters = rsaKeyParameters;
		
		this.encryptionOAEP = new OAEPEncoding(new RSAEngine());
		this.encryptionOAEP.init(true, rsaKeyParameters);
		
		this.decryptionOAEP = new OAEPEncoding(new RSAEngine());
		this.decryptionOAEP.init(false, rsaKeyParameters);
	}
	
	public RSAKeyParameters getRSAKeyParameters() {
		return rsaKeyParameters;
	}

	@Override
	public int getType() {
		return Key.RSA;
	}

	@Override
	public int getMaxBlockNumBits() {
		return getNumBits();
	}
	
	@Override
	public int getBlockNumBytes() {
		return getNumBits()/8;
	}
	
	@Override
	public int getMaxEncryptionBlockNumBytes() {
		return encryptionOAEP.getInputBlockSize();
	}
	
	public BigInteger getModulus() {
		return rsaKeyParameters.getModulus();
	}
	
	public BigInteger getExponent() {
		return rsaKeyParameters.getExponent();
	}
	
	@Override
	public boolean equals(Key key) {
		if(!(key instanceof RSAKey)) return false;
		RSAKey rsaKey = (RSAKey) key;
		return this.getExponent().equals(rsaKey.getExponent()) && this.getModulus().equals(rsaKey.getModulus());
	}
	
	@Override
	public int hashCode() {
		return this.getExponent().hashCode() + this.getModulus().hashCode();
	}
	
	@Override
	public Data encrypt(Data plainData) throws InvalidCipherCryptoException {

		Data cipherData = null;
		
		if(plainData.length() > getMaxEncryptionBlockNumBytes()) {
			cipherData = new Data(getBlockNumBytes() * (((plainData.length() - 1) / getMaxEncryptionBlockNumBytes()) + 1));
		}

		synchronized(encryptionOAEP) {
			
			try {
				
				for(int plainDataOffset = 0, cipherDataOffset = 0;
						plainDataOffset < plainData.length();
						plainDataOffset += getMaxEncryptionBlockNumBytes()) {
					
					byte[] block = encryptionOAEP.processBlock(
							plainData.getData(), 
							plainData.offset() + plainDataOffset,
							Math.min(plainData.length() - plainDataOffset, getMaxEncryptionBlockNumBytes()));
					
					if(cipherData == null) {
						cipherData = new Data(block);
					} else {
						cipherData.setBytes(cipherDataOffset, block, 0, block.length);
					}
					
					cipherDataOffset += block.length;
					
				}
				
			} catch (InvalidCipherTextException e) {
				throw new InvalidCipherCryptoException(e);
			}

		}

		return cipherData;

	}

	@Override
	public Data decrypt(Data cipherData) throws InvalidCipherCryptoException {
		
		Data plainData = null;
		
		if(cipherData.length() > getBlockNumBytes()) {
			plainData = new Data(getMaxEncryptionBlockNumBytes() * (((cipherData.length() - 1) / getBlockNumBytes()) + 1));
		}
		
		int plainDataOffset = 0;

		synchronized(decryptionOAEP) {

			try {
				
				for(int cipherDataOffset = 0;
						cipherDataOffset < cipherData.length();
						cipherDataOffset += getBlockNumBytes()) {
					
					byte[] block = decryptionOAEP.processBlock(
							cipherData.getData(), 
							cipherData.offset() + cipherDataOffset, 
							Math.min(cipherData.length() - cipherDataOffset, getBlockNumBytes()));
					
					if(plainData == null) {
						plainData = new Data(block);
					} else {
						plainData.setBytes(plainDataOffset, block, 0, block.length);
					}
					
					plainDataOffset += block.length;
					
				}
				
			} catch (InvalidCipherTextException e) {
				throw new InvalidCipherCryptoException(e);
			}

		}
		
		plainData.relativeReset(0, plainDataOffset);
		
		return plainData;

	}

	@Override
	public Data toData() {
		
		BigInteger modulus = getModulus();
		BigInteger exponent = getExponent();
		
		byte[] modulusBytes = modulus.toByteArray();
		byte[] exponentBytes = exponent.toByteArray();
		
		Data data = new Data(modulusBytes.length + exponentBytes.length);
		
		data.setBytes(0, modulusBytes, 0, modulusBytes.length);
		data.setBytes(modulusBytes.length, exponentBytes, 0, exponentBytes.length);
		
		return data;
		
	}
	
}
