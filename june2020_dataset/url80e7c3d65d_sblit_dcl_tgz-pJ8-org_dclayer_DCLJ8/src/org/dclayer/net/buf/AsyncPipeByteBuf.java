package org.dclayer.net.buf;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.buf.EndOfBufException;
import org.dclayer.net.buf.ByteBuf;

/**
 * an implementation of {@link ByteBuf} that can be used to directly read written bytes.
 * calls to any write method never block, while calls to any read method will block until sufficient bytes are available.
 */
public class AsyncPipeByteBuf extends ByteBuf {
	
	// TODO add faster copying of whole arrays and array-sections, like in SyncPipeByteBuf
	
	/**
	 * stores the bytes that are written but were not yet read
	 */
	private byte[] buf;
	/**
	 * next read index
	 */
	private volatile int read = 0;
	/**
	 * next write index
	 */
	private volatile int write = 0;
	
	/**
	 * true if buf is full
	 */
	private volatile boolean full = false;
	/**
	 * true if a thread that called a read method is waiting
	 */
	private volatile boolean waiting = false;
	
	private volatile boolean end = false;
	
	/**
	 * creates a new {@link AsyncPipeByteBuf} with a new buffer of the given initial size
	 * @param bufferSize the initial size for the new buffer
	 */
	public AsyncPipeByteBuf(int bufferSize) {
		this.buf = new byte[bufferSize];
	}
	
	/**
	 * reads a byte. if buf is empty, this will block until one is available.
	 * @return a byte from buf
	 */
	private byte readByte() throws EndOfBufException {
		if(read == write && !full) {
			// buffer is empty, wait
			waiting = true;
			try {
				this.wait();
			} catch (InterruptedException e) {}
			waiting = false;
		}
		if(end) {
			throw new EndOfBufException();
		}
		byte b = buf[read];
		read = (read + 1) % buf.length;
		full = false;
		return b;
	}
	
	/**
	 * writes a byte, expanding the buffer if necessary. this will never block.
	 * @param b the byte to write
	 */
	private void writeByte(byte b) {
		if(read == write && full) {
			// buffer is full, expand
			byte[] newBuf = new byte[buf.length*4];
			System.arraycopy(buf, read, newBuf, 0, (buf.length - read));
			System.arraycopy(buf, 0, newBuf, (buf.length - read), read);
			read = 0;
			write = buf.length;
			buf = newBuf;
		}
		buf[write] = b;
		write = (write + 1) % buf.length;
		if(read == write) full = true;
		if(waiting) {
			this.notify();
		}
	}
	
	@Override
	public synchronized byte read() throws BufException {
		return readByte();
	}

	@Override
	public synchronized void write(byte b) throws BufException {
		writeByte(b);
	}

	@Override
	public synchronized void read(byte[] buf, int offset, int length) throws BufException {
		for(int i = 0; i < length; i++) {
			buf[offset + i] = read();
		}
	}

	@Override
	public synchronized void write(byte[] buf, int offset, int length) throws BufException {
		for(int i = 0; i < length; i++) {
			write(buf[offset + i]);
		}
	}
	
	/**
	 * writes the specified amount of bytes from the given {@link ByteBuf} to this {@link AsyncPipeByteBuf}
	 * @param byteBuf the {@link ByteBuf} to copy bytes from
	 * @param length the amount of bytes to copy from the given {@link ByteBuf}
	 */
	public synchronized void write(ByteBuf byteBuf, int length) throws BufException {
		for(int i = 0; i < length; i++) {
			write(byteBuf.read());
		}
	}
	
	public synchronized void end() {
		end = true;
		if(waiting) {
			this.notify();
		}
	}

}
