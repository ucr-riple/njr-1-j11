package org.dclayer.crypto.hash;

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.dclayer.net.Data;


public class SHA1Hash extends Hash {
	
	private SHA1Digest digest = new SHA1Digest();
	
	public SHA1Hash() {
		super(HashAlgorithm.SHA1);
	}

	@Override
	public int getDigestSize() {
		return digest.getDigestSize();
	}

	@Override
	protected void updateDigest(Data data) {
		digest.update(data.getData(), data.offset(), data.length());
	}

	@Override
	protected void finishDigest(Data outputData, int offset) {
		digest.doFinal(outputData.getData(), outputData.offset() + offset);
	}

}
