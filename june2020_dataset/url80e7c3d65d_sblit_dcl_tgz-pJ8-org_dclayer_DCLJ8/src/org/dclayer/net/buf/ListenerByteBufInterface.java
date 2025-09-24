package org.dclayer.net.buf;

import org.dclayer.exception.net.buf.BufException;


public interface ListenerByteBufInterface {
	
	public void onWrite(byte b) throws BufException;
	public byte onRead() throws BufException;
	
}
