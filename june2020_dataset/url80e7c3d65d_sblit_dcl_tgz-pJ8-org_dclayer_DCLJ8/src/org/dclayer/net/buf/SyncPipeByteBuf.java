package org.dclayer.net.buf;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.net.buf.ByteBuf;

/**
 * an implementation of {@link ByteBuf} that can be used to directly read written bytes.
 * calls to both read and write methods will block until sufficient bytes are available if reading or until all written bytes were read if writing.
 */
public class SyncPipeByteBuf extends ByteBuf {
	
	/**
	 * one single byte that is currently being sent over this {@link SyncPipeByteBuf}
	 */
	private volatile byte cByte;
	
	/**
	 * an array containing bytes that are currently being sent over this {@link SyncPipeByteBuf}
	 */
	private volatile byte[] cBuf;
	/**
	 * the offset from the beginning of the cBuf array
	 */
	private volatile int cOffset;
	/**
	 * the last valid position in the cBuf array
	 */
	private volatile int cEnd;
	
	/**
	 * a {@link ByteBuf} that should be read from
	 */
	private volatile ByteBuf cPassByteBuf;
	/**
	 * the amount of remaining bytes that should be read from cPassByteBuf
	 */
	private volatile int cRemaining;
	
	/**
	 * true if there are bytes available, false otherwise
	 */
	private volatile boolean available = false;
	
	@Override
	public synchronized byte read() throws BufException {
		while(!available) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return 0;
			}
		}
		if(cBuf != null) {
			cByte = cBuf[cOffset];
			cOffset++;
			if(cOffset == cEnd) {
				cBuf = null;
				available = false;
				this.notify();
			}
		} else if(cPassByteBuf != null) {
			cByte = cPassByteBuf.read();
			cRemaining--;
			if(cRemaining <= 0) {
				cPassByteBuf = null;
				available = false;
				this.notify();
			}
		} else {
			available = false;
			this.notify();
		}
		return cByte;
	}
	
	@Override
	public synchronized void write(byte b) throws BufException {
		cByte = b;
		available = true;
		this.notify();
		while(available) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	@Override
	public synchronized void read(byte[] buf, int offset, int length) throws BufException {
		for(int i = 0; i < length;) {
			while(!available) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
			if(cBuf != null) {
				int copyLength = Math.min(cEnd-cOffset, length-i);
				System.arraycopy(cBuf, cOffset, buf, offset+i, copyLength);
				i += copyLength;
				cOffset += copyLength;
				if(cOffset == cEnd) {
					cBuf = null;
					available = false;
					this.notify();
				}
			} else if(cPassByteBuf != null) {
				int copyLength = Math.min(cRemaining, length-i);
				cPassByteBuf.read(buf, offset+i, copyLength);
				i += copyLength;
				cRemaining -= copyLength;
				if(cRemaining <= 0) {
					cPassByteBuf = null;
					available = false;
					this.notify();
				}
			} else {
				available = false;
				this.notify();
				buf[offset + i] = cByte;
				i++;
			}
		}
	}

	@Override
	public synchronized void write(byte[] buf, int offset, int length) throws BufException {
		cBuf = buf;
		cOffset = offset;
		cEnd = offset+length;
		available = true;
		this.notify();
		while(available) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	/**
	 * writes the specified amount of bytes from the given {@link ByteBuf} to this {@link SyncPipeByteBuf}
	 * @param byteBuf the {@link ByteBuf} to copy bytes from
	 * @param length the amount of bytes to copy from the given {@link ByteBuf}
	 */
	public synchronized void write(ByteBuf byteBuf, int length) {
		cPassByteBuf = byteBuf;
		cRemaining = length;
		available = true;
		this.notify();
		while(available) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}

}
