package org.dclayer.net.buf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.buf.BufNoReadException;
import org.dclayer.exception.net.buf.BufNoWriteException;
import org.dclayer.exception.net.buf.EndOfBufException;
import org.dclayer.exception.net.buf.StreamBufIOException;
import org.dclayer.net.PacketComponentI;

/**
 * a {@link ByteBuf} implementation with an underlying {@link InputStream} and {@link OutputStream} 
 */
public class StreamByteBuf extends ByteBuf {
	
	/**
	 * the underlying {@link InputStream}
	 */
	private InputStream inputStream;
	/**
	 * the underlying {@link OutputStream}
	 */
	private OutputStream outputStream;
	
	/**
	 * creates a new {@link StreamByteBuf}
	 * @param inputStream the {@link InputStream} to use (read operations will throw a {@link BufNoReadException} if null is passed)
	 * @param outputStream the {@link OutputStream} to use (write operations will throw a {@link BufNoWriteException} if null is passed)
	 */
	public StreamByteBuf(InputStream inputStream, OutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}
	
	/**
	 * creates a new {@link StreamByteBuf}, write operations will throw a {@link BufNoWriteException} due to the missing {@link OutputStream}
	 * @param inputStream the {@link InputStream} to use (read operations will throw a {@link BufNoReadException})
	 */
	public StreamByteBuf(InputStream inputStream) {
		this(inputStream, null);
	}
	
	/**
	 * creates a new {@link StreamByteBuf}, read operations will throw a {@link BufNoReadException} due to the missing {@link InputStream}
	 * @param outputStream the {@link OutputStream} to use (write operations will throw a {@link BufNoWriteException} if null is passed)
	 */
	public StreamByteBuf(OutputStream outputStream) {
		this(null, outputStream);
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public byte read() throws StreamBufIOException, BufNoReadException, EndOfBufException {
		if(inputStream == null) throw new BufNoReadException();
		int b;
		try {
			b = inputStream.read();
		} catch (IOException e) {
			throw new StreamBufIOException(e);
		}
		if(b < 0) throw new EndOfBufException();
		return (byte)b;
	}

	@Override
	public void write(byte b) throws StreamBufIOException, BufNoWriteException {
		if(outputStream == null) throw new BufNoWriteException();
		try {
			outputStream.write(b & 0xFF);
		} catch (IOException e) {
			throw new StreamBufIOException(e);
		}
	}

	@Override
	public void read(byte[] buf, int offset, int length) throws BufNoReadException, StreamBufIOException {
		if(inputStream == null) throw new BufNoReadException();
		try {
			inputStream.read(buf, offset, length);
		} catch (IOException e) {
			throw new StreamBufIOException(e);
		}
	}

	@Override
	public void write(byte[] buf, int offset, int length) throws BufNoWriteException, StreamBufIOException {
		if(outputStream == null) throw new BufNoWriteException();
		try {
			outputStream.write(buf, offset, length);
		} catch (IOException e) {
			throw new StreamBufIOException(e);
		}
	}
	
	/**
	 * calls {@link PacketComponentI#write(ByteBuf)} on the given {@link PacketComponentI},
	 * writing its contents to this {@link ByteBuf}.
	 * Flushes the underlying {@link OutputStream} afterwards.
	 * @param packetComponent the {@link PacketComponentI} to write
	 * @throws BufException if this operation fails
	 */
	@Override
	public void write(PacketComponentI packetComponent) throws BufException {
		super.write(packetComponent);
		try {
			outputStream.flush();
		} catch (IOException e) {
			throw new StreamBufIOException(e);
		}
	}

}
