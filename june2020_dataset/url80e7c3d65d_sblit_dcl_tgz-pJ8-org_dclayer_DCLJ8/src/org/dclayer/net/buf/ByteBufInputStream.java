package org.dclayer.net.buf;

import java.io.IOException;
import java.io.InputStream;

import org.dclayer.exception.net.buf.BufException;

public class ByteBufInputStream extends InputStream {
	
	private ByteBuf byteBuf;
	
	public ByteBufInputStream(ByteBuf byteBuf) {
		this.byteBuf = byteBuf;
	}
	
	public ByteBuf getByteBuf() {
		return byteBuf;
	}

	@Override
	public int read() throws IOException {
		try {
			return 0xFF & byteBuf.read();
		} catch (BufException e) {
			throw new IOException(e);
		}
	}

}
