package org.dclayer.crypto.key;

public class KeyPair<T extends Key> {
	
	public static <T extends Key> KeyPair<T> fromPublicKey(T publicKey) {
		return new KeyPair<T>(publicKey, null);
	}
	
	public static <T extends Key> KeyPair<T> fromPrivateKey(T privateKey) {
		return new KeyPair<T>(null, privateKey);
	}
	
	public static <T extends Key> KeyPair<T> fromKeys(T publicKey, T privateKey) {
		return new KeyPair<T>(publicKey, privateKey);
	}
	
	//

	private T publicKey;
	private T privateKey;
	
	private KeyPair(T publicKey, T privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public T getPublicKey() {
		return publicKey;
	}

	public T getPrivateKey() {
		return privateKey;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof KeyPair)) return false;
		return equals((KeyPair<?>) o);
	}
	
	public boolean equals(KeyPair<?> keyPair) {
		return ((this.publicKey != null && keyPair.publicKey != null && this.publicKey.equals(keyPair.publicKey)) || this.publicKey == keyPair.publicKey)
				&& ((this.privateKey != null && keyPair.privateKey != null && this.privateKey.equals(keyPair.privateKey)) || this.privateKey == keyPair.privateKey);
	}
	
	@Override
	public int hashCode() {
		return (publicKey == null ? 0 : publicKey.hashCode()) + (privateKey == null ? 0 : privateKey.hashCode()); 
	}
	
}
