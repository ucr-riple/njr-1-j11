package org.dclayer.net.buf;

import org.dclayer.exception.net.buf.BufException;

/**
 * a {@link ByteBuf} implementation simply reading from and/or writing to an underlying {@link ByteBuf} instance
 */
public class PassThroughByteBuf extends ByteBuf {
	
	/**
	 * the underlying {@link ByteBuf} instance that is read from and/or written to
	 */
	private ByteBuf byteBuf;
	
	/**
	 * creates a new {@link PassThroughByteBuf} instance without an underlying {@link ByteBuf}
	 */
	public PassThroughByteBuf() {
		
	}
	
	/**
	 * creates a new {@link PassThroughByteBuf} using the given {@link ByteBuf} as underlying {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} to use as underlying {@link ByteBuf}
	 */
	public PassThroughByteBuf(ByteBuf byteBuf) {
		setByteBuf(byteBuf);
	}
	
	/**
	 * uses the given {@link ByteBuf} as underlying {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} to use as underlying {@link ByteBuf}
	 */
	public void setByteBuf(ByteBuf byteBuf) {
		this.byteBuf = byteBuf;
	}

	@Override
	public byte read() throws BufException {
		return byteBuf.read();
	}

	@Override
	public void write(byte b) throws BufException {
		byteBuf.write(b);
	}

	@Override
	public void read(byte[] buf, int offset, int length) throws BufException {
		byteBuf.read(buf, offset, length);
	}

	@Override
	public void write(byte[] buf, int offset, int length) throws BufException {
		byteBuf.write(buf, offset, length);
	}

}
