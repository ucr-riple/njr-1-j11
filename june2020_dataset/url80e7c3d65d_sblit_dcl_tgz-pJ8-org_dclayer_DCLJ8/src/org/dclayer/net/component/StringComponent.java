package org.dclayer.net.component;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;

public class StringComponent extends PacketComponent {
	
	private String string;

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		string = byteBuf.readString();
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.writeString(string);
	}

	@Override
	public int length() {
		return string.length() + 1;
	}

	@Override
	public PacketComponentI[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("StringComponent(string=%s)", string);
	}

	public String getString() {
		return string;
	}
	
	public void setString(String string) {
		this.string = string;
	}
	
}
