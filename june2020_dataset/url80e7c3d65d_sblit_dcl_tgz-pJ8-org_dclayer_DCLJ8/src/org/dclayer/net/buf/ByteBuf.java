package org.dclayer.net.buf;

import java.nio.charset.Charset;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponentI;

/**
 * an abstract class defining methods to read and write data
 */
public abstract class ByteBuf {
	
	/**
	 * the ASCII charset (used for String writing)
	 */
	public final static Charset CHARSET_ASCII = Charset.forName("US-ASCII");

	/**
	 * reads one byte
	 * @return the byte read
	 * @throws BufException if this operation fails
	 */
	public abstract byte read() throws BufException;
	/**
	 * writes one byte
	 * @param b the byte to write
	 * @throws BufException if this operation fails
	 */
	public abstract void write(byte b) throws BufException;
	/**
	 * copies an amount of data into the given byte array at the given position
	 * @param buf the byte array to copy data to
	 * @param offset the first index in the given byte array to write data to
	 * @param length the amount of bytes to copy
	 * @throws BufException if this operation fails
	 */
	public abstract void read(byte[] buf, int offset, int length) throws BufException;
	/**
	 * copies an amount of data from the given byte array at the given position
	 * @param buf the byte array to copy data from
	 * @param offset the first index in the given byte array to read data from
	 * @param length the amount of bytes to copy
	 * @throws BufException if this operation fails
	 */
	public abstract void write(byte[] buf, int offset, int length) throws BufException;

	/**
	 * reads an unsigned 4-byte integer
	 * @return the unsigned 4-byte integer read, as long
	 * @throws BufException if this operation fails
	 */
	public final long read32() throws BufException {
		return (((long) (read() & 0xFF)) << 24)
				| (((long) (read() & 0xFF)) << 16)
				| (((long) (read() & 0xFF)) << 8) | (long) (read() & 0xFF);
	}
	
	/**
	 * writes a 4-byte integer
	 * @param i the 4-byte integer to write
	 * @throws BufException if this operation fails
	 */
	public final void write32(int i) throws BufException {
		write((byte) (i >> 24));
		write((byte) (i >> 16));
		write((byte) (i >> 8));
		write((byte) (i & 0xFF));
	}

	/**
	 * reads an unsigned 2-byte integer
	 * @return the unsigned 2-byte integer read
	 * @throws BufException if this operation fails
	 */
	public final int read16() throws BufException {
		return ((read() & 0xFF) << 8) | (read() & 0xFF);
	}

	/**
	 * writes a 2-byte integer
	 * @param i the 2-byte integer to write
	 * @throws BufException if this operation fails
	 */
	public final void write16(int i) throws BufException {
		write((byte) (i >> 8));
		write((byte) (i & 0xFF));
	}
	
	/**
	 * flexnum offsets for the different flexnum number ranges
	 */
	public static final long[] flexNumOffsets = new long[8];
	
	/**
	 * calculates the flexnum offset values
	 */
	static {
		for(int i = 0; i < flexNumOffsets.length; i++) {
			flexNumOffsets[i] = ((0x80L << 7*i) + (i > 0 ? flexNumOffsets[i-1] : 0));
		}
	};
	
	/**
	 * calculates the length of a value represented as a flexnum
	 * @param num the value to represent as a flexnum
	 * @return the length of the flexnum, in bytes
	 */
	public static int getFlexNumLength(long num) {
		for(int i = 0; i < flexNumOffsets.length; i++) {
			if(num < flexNumOffsets[i]) {
				return 1+i;
			}
		}
		return 9;
	}
	
	/**
	 * reads a flexnum
	 * @return the flexnum read
	 * @throws BufException if this operation fails
	 */
	public final long readFlexNum() throws BufException {
		byte iByte = read();
		int additionalBytes = 8;
		long num = 0;
		long offset = 0;
		
		for(int i = 0; i < 8; i++) {
			if((iByte & (0x80 >> i)) == 0) {
				additionalBytes = i;
				break;
			}
		}
		
		num |= (iByte & ((0x100>>additionalBytes)-1));
		for(int i = 0; i < additionalBytes; i++) {
			num <<= 8;
			num |= (read() & 0xFF);
			offset += (0x80L << 7*i);
		}
		
		return num + offset;
	}
	
	/**
	 * writes a flexnum
	 * @param num the flexnum to write
	 * @throws BufException if this operation fails
	 */
	public final void writeFlexNum(long originalNum) throws BufException {
		int additionalBytes = 8;
		long num = originalNum - flexNumOffsets[flexNumOffsets.length - 1];
		
		for(int i = 0; i < flexNumOffsets.length; i++) {
			if(originalNum < flexNumOffsets[i]) {
				additionalBytes = i;
				num = originalNum - (i > 0 ? flexNumOffsets[i-1] : 0L);
				break;
			}
		}
		
		byte leadingOneBits = (byte) ((1<<additionalBytes) - 1);
		
		byte iByte = (byte) (leadingOneBits << (8-additionalBytes));
		
		byte valueBitMaskInFirstByte = (byte) ((1 << Math.max(7-additionalBytes, 0)) - 1);
		iByte |= ((num >> 8*additionalBytes) & valueBitMaskInFirstByte);
		
		write(iByte);
		
		for(int i = (additionalBytes-1); i >= 0; i--) {
			write((byte) (num >> 8*i));
		}
	}

	/**
	 * copies data into the given byte array until it is full
	 * @param buf the byte array to copy data to
	 * @throws BufException if this operation fails
	 */
	public final void read(byte[] buf) throws BufException {
		this.read(buf, 0, buf.length);
	}

	/**
	 * copies the data contained in the given byte array
	 * @param buf the byte array to copy data from
	 * @throws BufException if this operation fails
	 */
	public final void write(byte[] buf) throws BufException {
		this.write(buf, 0, buf.length);
	}
	
	/**
	 * copies data into the given {@link Data} object until it is full
	 * @param data the {@link Data} object to copy data to
	 * @throws BufException if this operation fails
	 */
	public final void read(Data data) throws BufException {
		this.read(data.getData(), data.offset(), data.length());
	}
	
	/**
	 * copies the data contained in the given {@link Data} object
	 * @param data the {@link Data} object to copy data from
	 * @throws BufException if this operation fails
	 */
	public final void write(Data data) throws BufException {
		this.write(data.getData(), data.offset(), data.length());
	}
	
	/**
	 * calls {@link PacketComponentI#write(ByteBuf)} on the given {@link PacketComponentI},
	 * writing its contents to this {@link ByteBuf}
	 * @param packetComponent the {@link PacketComponentI} to write
	 * @throws BufException if this operation fails
	 */
	public void write(PacketComponentI packetComponent) throws BufException {
		packetComponent.write(this);
	}
	
	/**
	 * reads a String that is terminated by a zero-byte
	 * @return the zero-byte-terminated String read
	 * @throws BufException if this operation fails
	 */
	public final String readString() throws BufException {
		StringBuilder stringBuilder = new StringBuilder();
		byte b;
		while((b = read()) != 0) {
			stringBuilder.append((char)b);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * reads a non-terminated String of the given length
	 * @param byteCount the amount of bytes to read
	 * @return the non-terminated String of the given length
	 * @throws BufException if this operation fails
	 */
	public final String readNonTerminatedString(int byteCount) throws BufException {
		StringBuilder stringBuilder = new StringBuilder();
		while(byteCount-- > 0) {
			stringBuilder.append((char)read());
		}
		return stringBuilder.toString();
	}
	
	/**
	 * reads a String that is enclosed in two equal bytes (e.g. "string", 'string', [string], etc.)
	 * @return the String read
	 * @throws BufException if this operation fails
	 */
	public final String readTextModeString() throws BufException {
		byte endByte = read();
		StringBuilder stringBuilder = new StringBuilder();
		byte b;
		while((b = read()) != endByte) {
			stringBuilder.append((char)b);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * reads a String that is terminated by either a space character or a newline character
	 * @return the space-or-newline-terminated String read
	 * @throws BufException if this operation fails
	 */
	public final String readSpaceTerminatedString() throws BufException {
		StringBuilder stringBuilder = new StringBuilder();
		byte b;
		while((b = read()) != ' ' && b != '\n') {
			stringBuilder.append((char)b);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * skips until a newline character is read
	 * @throws BufException if this operation fails
	 */
	public final void readTilEOL() throws BufException {
		while(read() != '\n');
	}
	
	/**
	 * skips until a space or a newline character is read
	 * @throws BufException if this operation fails
	 */
	public final void readTilSpaceOrEOL() throws BufException {
		byte b;
		while((b = read()) != ' ' && b != '\n');
	}
	
	/**
	 * writes a String and terminates it with a zero-byte
	 * @param string the String to write
	 * @throws BufException if this operation fails
	 */
	public final void writeString(String string) throws BufException {
		write(string.getBytes(CHARSET_ASCII));
		write((byte)0);
	}
	
	/**
	 * writes a String, not terminating it
	 * @param string the String to write
	 * @throws BufException if this operation fails
	 */
	public final void writeNonTerminatedString(String string) throws BufException {
		write(string.getBytes(CHARSET_ASCII));
	}
	
	/**
	 * writes a String, enclosing it in two quotes ('"')
	 * @param string the String to write
	 * @throws BufException if this operation fails
	 */
	public final void writeTextModeString(String string) throws BufException {
		writeTextModeString((byte)'"', string);
	}
	
	/**
	 * writes a String, enclosing it in two of the given byte
	 * @param enclosingByte the byte in which to enclose the String
	 * @param string the String to write
	 * @throws BufException if this operation fails
	 */
	public final void writeTextModeString(byte enclosingByte, String string) throws BufException {
		write(enclosingByte);
		write(string.getBytes(CHARSET_ASCII));
		write(enclosingByte);
	}
}
