package org.dclayer.net.buf;

import org.dclayer.exception.net.buf.BufException;


public class ListenerByteBuf extends ByteBuf {
	
	private ListenerByteBufInterface listenerByteBufInterface;
	
	public ListenerByteBuf(ListenerByteBufInterface listenerByteBufInterface) {
		this.listenerByteBufInterface = listenerByteBufInterface;
	}

	@Override
	public byte read() throws BufException {
		return listenerByteBufInterface.onRead();
	}

	@Override
	public void write(byte b) throws BufException {
		listenerByteBufInterface.onWrite(b);
	}

	@Override
	public void read(byte[] buf, int offset, int length) throws BufException {
		for(int i = 0; i < length; i++) {
			buf[i+offset] = listenerByteBufInterface.onRead();
		}
	}

	@Override
	public void write(byte[] buf, int offset, int length) throws BufException {
		for(int i = 0; i < length; i++) {
			listenerByteBufInterface.onWrite(buf[i+offset]);
		}
	}

}
