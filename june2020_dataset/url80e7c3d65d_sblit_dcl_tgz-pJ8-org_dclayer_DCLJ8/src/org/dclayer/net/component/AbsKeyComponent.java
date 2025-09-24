package org.dclayer.net.component;

import org.dclayer.crypto.key.Key;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.componentinterface.AbsKeyComponentI;

public abstract class AbsKeyComponent extends PacketComponent implements AbsKeyComponentI {
	
	public abstract byte getTypeId();
	
	public abstract Key getKey() throws CryptoException;
	
}
