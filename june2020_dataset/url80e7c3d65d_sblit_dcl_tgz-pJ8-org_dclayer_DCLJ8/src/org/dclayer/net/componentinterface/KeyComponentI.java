package org.dclayer.net.componentinterface;

import org.dclayer.crypto.key.Key;

public interface KeyComponentI {

	public RSAKeyComponentI setRSAKeyComponent();
	public AbsKeyComponentI getKeyComponent();
	
	public void setKey(Key key);
	
}
