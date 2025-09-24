package pl.cc.core;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;



public class NetPrintWriter extends PrintWriter {
	static final Logger log = Logger.getLogger(NetPrintWriter.class);
	
	public NetPrintWriter(OutputStream out) {
		super(new OutputStreamWriter(out, Charset.forName("UTF-8")), true);
	}

	public synchronized void println(String s) {
		if (!s.toUpperCase().equals("PING")){
			log.debug("SEND: "+s);
		}
		super.print(s + "\r\n");
		flush();
		if (checkError()){
			log.warn("Błąd przy println");
		}
	}
	
	public void print(String s) {
		println (s+" WARNING: print");
		throw new RuntimeException("print w NetPrintWriter");
	}
}
