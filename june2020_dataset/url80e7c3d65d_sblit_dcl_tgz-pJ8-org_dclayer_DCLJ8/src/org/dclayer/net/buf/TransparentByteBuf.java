package org.dclayer.net.buf;

import org.dclayer.exception.net.buf.BufException;

/**
 * a {@link ByteBuf} implementation reading and/or writing to an underlying {@link ByteBuf}, altering the bytes that are read/written.
 */
public abstract class TransparentByteBuf extends ByteBuf {
	
	/**
	 * the underlying {@link ByteBuf} instance that is read from and/or written to
	 */
	private ByteBuf byteBuf;
	
	/**
	 * uses the given {@link ByteBuf} as underlying {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} to use as underlying {@link ByteBuf}
	 */
	public void setByteBuf(ByteBuf byteBuf) {
		this.byteBuf = byteBuf;
	}

	@Override
	public byte read() throws BufException {
		return translateRead(byteBuf.read());
	}

	@Override
	public void write(byte b) throws BufException {
		byteBuf.write(translateWrite(b));
	}

	@Override
	public void read(byte[] buf, int offset, int length) throws BufException {
		for(int i = 0; i < length; i++) {
			buf[i+offset] = translateRead(byteBuf.read());
		}
	}

	@Override
	public void write(byte[] buf, int offset, int length) throws BufException {
		for(int i = 0; i < length; i++) {
			byteBuf.write(translateWrite(buf[i+offset]));
		}
	}
	
	/**
	 * translates the given byte that is being read and returns the corresponding translation
	 * @param b the byte being read and translated
	 * @return the translation byte
	 */
	public abstract byte translateRead(byte b);
	/**
	 * translates the given byte that is being written and returns the corresponding translation
	 * @param b the byte being written and translated
	 * @return the translation byte
	 */
	public abstract byte translateWrite(byte b);

}
