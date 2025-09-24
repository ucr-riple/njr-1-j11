package org.dclayer.crypto.hash;

import java.util.HashMap;

public enum HashAlgorithm {
	
	SHA1("sha1", 20) {
		@Override
		public Hash getInstance() {
			return new SHA1Hash();
		}
	};
	
	//
	
	private static HashMap<String, HashAlgorithm> identifierMap = new HashMap<>();
	
	static {
		for(HashAlgorithm hashAlgorithm : values()) {
			identifierMap.put(hashAlgorithm.getIdentifier(), hashAlgorithm);
		}
	}
	
	public static HashAlgorithm byIdentifier(String identifier) {
		return identifierMap.get(identifier);
	}
	
	//
	
	private String identifier;
	private int digestNumBytes;
	
	private HashAlgorithm(String identifier, int digestNumBytes) {
		this.identifier = identifier;
		this.digestNumBytes = digestNumBytes;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public int getDigestNumBytes() {
		return digestNumBytes;
	}
	
	public abstract Hash getInstance();
	
}
