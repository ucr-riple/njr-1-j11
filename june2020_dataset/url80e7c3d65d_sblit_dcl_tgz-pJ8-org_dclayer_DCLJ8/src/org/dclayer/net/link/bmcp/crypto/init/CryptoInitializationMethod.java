package org.dclayer.net.link.bmcp.crypto.init;

import org.dclayer.net.link.Link;
import org.dclayer.net.link.bmcp.crypto.init.plain.PlainCryptoInitializer;
import org.dclayer.net.link.bmcp.crypto.init.rsaaesgcm.RsaAesGcmCryptoInitializer;

public enum CryptoInitializationMethod {
	
	CRYPTO_INIT_PLAIN(0) {
		@Override
		public CryptoInitializer make(Link link) {
			return new PlainCryptoInitializer();
		}
	},
	CRYPTO_INIT_RSA_AES128_GCM(CryptoInitializationMethod.PUBLIC) {
		@Override
		public CryptoInitializer make(Link link) {
			return new RsaAesGcmCryptoInitializer(link);
		}
	};
	
	private static final int PUBLIC = (1 << 0);
	
	private static final CryptoInitializationMethod[] PUBLIC_CRYPTO_INIT_METHODS = getAllWithFlags(PUBLIC);
	
	private static CryptoInitializationMethod[] getAllWithFlags(int flags) {
		
		int n = 0;
		for(CryptoInitializationMethod cryptoInitializationMethod : values()) {
			if(cryptoInitializationMethod.hasFlags(flags)) n++;
		}
		
		CryptoInitializationMethod[] methods = new CryptoInitializationMethod[n];
		
		int i = 0;
		for(CryptoInitializationMethod cryptoInitializationMethod : values()) {
			if(cryptoInitializationMethod.hasFlags(flags)) {
				methods[i++] = cryptoInitializationMethod;
			}
		}
		
		return methods;
		
	}
	
	public static CryptoInitializationMethod get(int id) {
		if(id < 0 || id >= values().length) return null;
		return values()[id];
	}
	
	//
	
	public static CryptoInitializationMethod[] getPubliclyUsableCryptoInitMethods() {
		return PUBLIC_CRYPTO_INIT_METHODS;
	}
	
	//
	
	private int flags;
	
	private CryptoInitializationMethod(int flags) {
		this.flags = flags;
	}
	
	public int getId() {
		return ordinal();
	}
	
	private boolean hasFlags(int flags) {
		return (this.flags & flags) == flags;
	}
	
	public boolean publiclyUsable() {
		return hasFlags(PUBLIC);
	}
	
	public abstract CryptoInitializer make(Link link);
	
}