package org.dclayer.exception.net.parse;

public class WriteOnlyException extends ParseException {
	
	public WriteOnlyException() {
		super("PacketComponent is write-only");
	}
	
}
