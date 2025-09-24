package xscript.executils.console;

import java.io.IOException;
import java.io.InputStream;

public class ConsoleInputStream extends InputStream {

	private ConsoleIO io;
	
	public ConsoleInputStream(ConsoleIO io) {
		this.io = io;
	}

	@Override
	public int read() throws IOException {
		byte[] b = new byte[1];
		read(b, 0, 1);
		return b[0];
	}

	@Override
	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		String s = io.readInput();
		byte[] bytes = s.getBytes();
		int l = bytes.length>len?len:bytes.length;
		System.arraycopy(bytes, 0, b, off, l);
		return l;
	}

	
	
}
