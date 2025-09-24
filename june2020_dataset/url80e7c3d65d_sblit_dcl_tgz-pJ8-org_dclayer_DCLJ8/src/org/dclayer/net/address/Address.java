package org.dclayer.net.address;

import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.KeyPair;
import org.dclayer.net.Data;
import org.dclayer.net.network.NetworkInstanceCollection;

public class Address<T extends Key> {
	
	private NetworkInstanceCollection networkInstanceCollection;
	private KeyPair<T> keyPair;
	
	private Data data = null;
	
	public Address(KeyPair<T> keyPair, NetworkInstanceCollection networkInstanceCollection) {
		this.keyPair = keyPair;
		this.networkInstanceCollection = networkInstanceCollection;
	}
	
	public Address(KeyPair<T> keyPair) {
		this(keyPair, null);
	}
	
	public NetworkInstanceCollection getNetworkInstanceCollection() {
		return networkInstanceCollection;
	}
	
	public KeyPair<T> getKeyPair() {
		return keyPair;
	}
	
	private Data makeData() {
		return keyPair.getPublicKey().toData();
	}
	
	public Data toData() {
		if(data == null) data = makeData();
		return data;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(!(o instanceof Address)) return false;
		return keyPair.equals(((Address)o).keyPair);
	}
	
	@Override
	public int hashCode() {
		return keyPair.hashCode();
	}
	
}
