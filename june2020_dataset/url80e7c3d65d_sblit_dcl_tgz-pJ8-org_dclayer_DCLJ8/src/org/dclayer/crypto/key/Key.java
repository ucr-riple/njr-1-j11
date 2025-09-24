package org.dclayer.crypto.key;

import org.dclayer.crypto.Crypto;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.crypto.InsufficientKeySizeException;
import org.dclayer.net.Data;

public abstract class Key {
	
	public static final byte RSA = 0;
	
	//
	
	private int numBits = -1;
	
	public Key(int numBits) throws InsufficientKeySizeException {
		this.numBits = numBits;
		if(numBits < Crypto.KEY_MIN_NUMBITS) throw new InsufficientKeySizeException();
	}
	
	public final int getNumBits() {
		return numBits;
	}
	
	public abstract int getType();
	
	public abstract int getMaxBlockNumBits();
	
	public abstract Data encrypt(Data plainData) throws CryptoException;
	public abstract Data decrypt(Data cipherData) throws CryptoException;
	
	public abstract int getBlockNumBytes();
	public abstract int getMaxEncryptionBlockNumBytes();
	
	public abstract Data toData();
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract int hashCode();
	
	public abstract boolean equals(Key key);
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Key)) return false;
		return equals((Key) o);
	}
	
}
