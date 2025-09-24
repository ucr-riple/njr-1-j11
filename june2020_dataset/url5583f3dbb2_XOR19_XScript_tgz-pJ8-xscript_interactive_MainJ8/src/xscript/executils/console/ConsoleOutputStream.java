package xscript.executils.console;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.text.AttributeSet;

public class ConsoleOutputStream extends OutputStream {

	private ConsoleIO io;

	private AttributeSet attrs;
	
	public ConsoleOutputStream(ConsoleIO io, AttributeSet attrs) {
		this.io = io;
		this.attrs = attrs;
	}

	@Override
	public void write(int b) throws IOException {
		io.append(Character.toString((char) b), attrs);
	}

	@Override
	public void write(byte[] b) throws IOException {
		io.append(new String(b), attrs);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		io.append(new String(b, off, len), attrs);
	}

}
