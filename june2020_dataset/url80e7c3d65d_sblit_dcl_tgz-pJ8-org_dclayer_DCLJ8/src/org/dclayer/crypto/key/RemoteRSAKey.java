package org.dclayer.crypto.key;

import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.crypto.InsufficientKeySizeException;
import org.dclayer.net.Data;

public class RemoteRSAKey extends Key {
	
	private RemoteRSAKeyInterface remoteRSAKeyInterface;
	
	private int maxEncryptionBlockNumBytes = -1;

	public RemoteRSAKey(RemoteRSAKeyInterface remoteRSAKeyInterface) throws InsufficientKeySizeException {
		super(remoteRSAKeyInterface.getNumBits());
		this.remoteRSAKeyInterface = remoteRSAKeyInterface;
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
	public int getMaxEncryptionBlockNumBytes() {
		if(maxEncryptionBlockNumBytes < 0) {
			maxEncryptionBlockNumBytes = remoteRSAKeyInterface.queryMaxEncryptionBlockNumBytes();
		}
		return maxEncryptionBlockNumBytes;
	}

	@Override
	public Data encrypt(Data plainData) throws CryptoException {
		return remoteRSAKeyInterface.encrypt(plainData);
	}

	@Override
	public Data decrypt(Data cipherData) throws CryptoException {
		return remoteRSAKeyInterface.decrypt(cipherData);
	}

	@Override
	public Data toData() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("RemoteRSAKey(%s)", remoteRSAKeyInterface);
	}

	@Override
	public boolean equals(Key key) {
		return false;
	}

	@Override
	public int hashCode() {
		return getNumBits();
	}

	@Override
	public int getBlockNumBytes() {
		return getNumBits()/8;
	}

}
