package org.dclayer.crypto.hash;

import org.dclayer.net.Data;

public abstract class Hash {
	
	public static final String SHA1 = "sha1";
	
	//
	
	HashAlgorithm hashAlgorithm;
	
	public Hash(HashAlgorithm hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}
	
	public final HashAlgorithm getHashAlgorithm() {
		return hashAlgorithm;
	}
	
	public final Hash update(Data... datas) {
		for(Data data : datas) {
			update(data);
		}
		return this;
	}
	
	public final Hash update(Data data) {
		updateDigest(data);
		return this;
	}
	
	public final Data finish() {
		Data outputData = new Data(getDigestSize());
		finish(outputData);
		return outputData;
	}
	
	public final Data finish(Data outputData) {
		finish(outputData, 0);
		return outputData;
	}
	
	public final Data finish(Data outputData, int offset) {
		finishDigest(outputData, offset);
		return outputData;
	}
	
	protected abstract int getDigestSize();
	protected abstract void updateDigest(Data data);
	protected abstract void finishDigest(Data outputData, int offset);
	
}
