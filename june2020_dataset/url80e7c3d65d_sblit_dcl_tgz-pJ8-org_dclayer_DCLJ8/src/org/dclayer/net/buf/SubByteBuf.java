package org.dclayer.net.buf;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.buf.EndOfBufException;

/**
 * a {@link ByteBuf} implementation simply reading from and/or writing to an underlying {@link ByteBuf} instance,
 * limiting the amount of bytes that can be read from and/or written to the underlying {@link ByteBuf}
 */
public class SubByteBuf extends ByteBuf {
	
	/**
	 * the underlying {@link ByteBuf} instance that is read from and/or written to
	 */
	private ByteBuf byteBuf;
	/**
	 * the amount of bytes that can be read from and/or written to the underlying {@link ByteBuf}
	 */
	private int length;
	/**
	 * the amount of bytes that were read from and/or written to the underlying {@link ByteBuf}
	 */
	private int count;
	
	/**
	 * creates a new {@link SubByteBuf} instance without an underlying {@link ByteBuf}
	 */
	public SubByteBuf() {
		
	}
	
	/**
	 * creates a new {@link PassThroughByteBuf} using the given {@link ByteBuf} as underlying {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} to use as underlying {@link ByteBuf}
	 * @param length the amount of bytes that can be read from and/or written to the underlying {@link ByteBuf}
	 */
	public SubByteBuf(ByteBuf byteBuf, int length) {
		setByteBuf(byteBuf, length);
	}
	
	/**
	 * uses the given {@link ByteBuf} as underlying {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} to use as underlying {@link ByteBuf}
	 * @param length the amount of bytes that can be read from and/or written to the underlying {@link ByteBuf}
	 */
	public void setByteBuf(ByteBuf byteBuf, int length) {
		this.byteBuf = byteBuf;
		this.length = length;
		this.count = 0;
	}

	@Override
	public byte read() throws BufException {
		count++;
		if(count > length) throw new EndOfBufException(length);
		return byteBuf.read();
	}

	@Override
	public void write(byte b) throws BufException {
		count++;
		if(count > length) throw new EndOfBufException(length);
		byteBuf.write(b);
	}

	@Override
	public void read(byte[] buf, int offset, int length) throws BufException {
		this.count += length;
		if(this.count > this.length) throw new EndOfBufException(length);
		byteBuf.read(buf, offset, length);
	}

	@Override
	public void write(byte[] buf, int offset, int length) throws BufException {
		this.count += length;
		if(this.count > this.length) throw new EndOfBufException(length);
		byteBuf.write(buf, offset, length);
	}

}
