package org.dclayer.net.buf;


import org.dclayer.exception.net.buf.EndOfBufException;
import org.dclayer.net.Data;
import org.dclayer.net.buf.ByteBuf;

/**
 * a {@link ByteBuf} implementation with an underlying {@link Data}
 */
public class DataByteBuf extends ByteBuf {
	
	/**
	 * the current position in the {@link Data}
	 */
	private int position = 0;

	/**
	 * the underlying {@link Data}
	 */
	private Data data;
	
	/**
	 * creates a new {@link DataByteBuf}, using the given {@link Data} as underlying data
	 * @param data the {@link Data} to use as underlying data
	 */
	public DataByteBuf(Data data) {
		this.data = data;
	}
	
	/**
	 * creates a new {@link DataByteBuf}, using the given {@link Data} as underlying data and initializing its position to the given value
	 * @param data the {@link Data} to use as underlying data
	 * @param position the position to initialize this {@link DataByteBuf} at
	 */
	public DataByteBuf(Data data, int position) {
		this.data = data;
		this.position = position;
	}
	
	/**
	 * creates a new, empty {@link DataByteBuf} with no underlying {@link Data}.
	 */
	public DataByteBuf() {
		
	}

	/**
	 * creates a new {@link DataByteBuf} instance
	 * @param data the byte array to use for the underlying {@link Data}
	 * @param offset the offset specifying where the data starts in the byte array
	 * @param length the length specifying how long the usable area of the byte array is
	 */
	public DataByteBuf(byte[] data, int offset, int length) {
		this(new Data(data, offset, length));
	}

	/**
	 * creates a new {@link DataByteBuf} instance
	 * @param data the byte array to use for the underlying {@link Data}
	 */
	public DataByteBuf(byte[] data) {
		this(data, 0, data.length);
	}

	/**
	 * creates a new {@link DataByteBuf} instance, creating a new {@link Data} object of the given length to use as underlying data.
	 * pass {@link Data#GROW} to make the underlying data grow infinitely.
	 * @param length the length of the byte array to use for the underlying {@link Data}
	 */
	public DataByteBuf(int length) {
		this(new Data(length));
	}

	/**
	 * returns the underlying {@link Data}
	 * @return the underlying {@link Data}
	 */
	public Data getData() {
		return data;
	}
	
	/**
	 * replaces the underlying {@link Data} with the given {@link Data} and resets the current position to the beginning
	 * @param data the {@link Data} to replace the old underlying {@link Data} with
	 */
	public void setData(Data data) {
		this.data = data;
		seek(0);
	}

	/**
	 * resets this {@link DataByteBuf} and the underlying {@link Data}, updating its offset and length variables to the given values
	 * @param offset the offset specifying where the data starts in the byte array
	 * @param length the length specifying how long the usable area of the byte array is
	 */
	public void reset(int offset, int length) {
		if(this.data != null) {
			this.data.reset(offset, length);
		} else if(length > 0) {
			this.data = new Data(length);
		}
		seek(0);
	}
	
	/**
	 * resets this {@link DataByteBuf} and the underlying {@link Data}, updating its data byte array and its offset and length variables to the given values
	 * @param data the byte array to use for the underlying {@link Data}
	 * @param offset the offset specifying where the data starts in the byte array
	 * @param length the length specifying how long the usable area of the byte array is
	 */
	public void reset(byte[] data, int offset, int length) {
		this.data.reset(data, offset, length);
		seek(0);
	}
	
	/**
	 * resets this {@link DataByteBuf} and the underlying {@link Data}, updating its data byte array and clearing its offset and length variables
	 * @param data the byte array to use for the underlying {@link Data}
	 */
	public void reset(byte[] data) {
		this.reset(data, 0, data.length);
	}
	
	/**
	 * adapts the space in this {@link DataByteBuf} to the given length value, enlarging the underlying {@link Data} if necessary; also resets the position of this {@link DataByteBuf} to zero.
	 * @param length the amount of bytes the space in this {@link DataByteBuf} needs to be adapted to
	 */
	public void prepare(int length) {
		this.data.prepare(length);
		seek(0);
	}

	/**
	 * sets the position of this {@link DataByteBuf} to the given value
	 * @param position the position to use
	 */
	public void seek(int position) {
		this.position = position;
	}
	
	/**
	 * skips the given amount of bytes
	 * @param numBytes the amount of bytes to skip
	 */
	public void skip(int numBytes) {
		seek(this.position + numBytes);
	}
	
	/**
	 * returns the current position inside this {@link DataByteBuf}'s underlying {@link Data}
	 * @return the current position inside this {@link DataByteBuf}'s underlying {@link Data}
	 */
	public int getPosition() {
		return position;
	}

	public byte read() throws EndOfBufException {
		byte b;
		try {
			b = data.getByte(position++);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new EndOfBufException(data.length());
		}
		return b;
	}

	public void write(byte b) throws EndOfBufException {
		try {
			data.setByte(position++, b);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new EndOfBufException(data.length());
		}
	}

	public void read(byte[] buf, int offset, int length) throws EndOfBufException {
		try {
			data.getBytes(position, length, buf, offset);
		} catch(IndexOutOfBoundsException e) {
			throw new EndOfBufException(data.length());
		}
		position += length;
	}

	public void write(byte[] buf, int offset, int length) throws EndOfBufException {
		try {
			data.setBytes(position, buf, offset, length);
		} catch(IndexOutOfBoundsException e) {
			throw new EndOfBufException(data.length());
		}
		position += length;
	}
}
