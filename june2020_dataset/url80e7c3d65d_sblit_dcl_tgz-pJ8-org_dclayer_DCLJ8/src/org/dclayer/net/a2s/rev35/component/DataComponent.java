package org.dclayer.net.a2s.rev35.component;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.componentinterface.DataComponentI;

public class DataComponent extends PacketComponent implements DataComponentI {
	
	private Data data;

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		String string = byteBuf.readTextModeString();
		data = new Data(string.getBytes(ByteBuf.CHARSET_ASCII));
		byteBuf.readTilSpaceOrEOL();
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.writeTextModeString(new String(data.copyToByteArray(), ByteBuf.CHARSET_ASCII));
		byteBuf.write((byte)' ');
	}

	@Override
	public int length() {
		return 2 + data.length() + 1;
	}
	
	@Override
	public int lengthForDataLength(int dataLength) {
		return 2 + dataLength + 1;
	}

	@Override
	public String toString() {
		return String.format("DataComponent(len=%d)", data.length());
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public void setData(PacketComponentI packetComponent) throws BufException {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void getData(PacketComponentI packetComponent) throws BufException, ParseException {
		throw new RuntimeException("not implemented");
	}
	
}
