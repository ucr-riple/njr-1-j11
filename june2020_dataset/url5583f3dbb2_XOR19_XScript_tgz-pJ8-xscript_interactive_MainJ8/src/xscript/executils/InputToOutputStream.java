package xscript.executils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputToOutputStream extends InputStream {

	private InputStream in;
	
	private OutputStream out;
	
	public InputToOutputStream(InputStream in, OutputStream out){
		this.in = in;
		this.out = out;
	}
	
	@Override
	public int read() throws IOException {
		int b = in.read();
		out.write(b);
		return b;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int l = in.read(b);
		out.write(b, 0, l);
		return l;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int l = in.read(b, off, len);
		out.write(b, off, l);
		return l;
	}

	@Override
	public long skip(long n) throws IOException {
		return in.skip(n);
	}

	@Override
	public int available() throws IOException {
		return in.available();
	}

	@Override
	public void close() throws IOException {
		in.close();
		out.close();
	}

}
