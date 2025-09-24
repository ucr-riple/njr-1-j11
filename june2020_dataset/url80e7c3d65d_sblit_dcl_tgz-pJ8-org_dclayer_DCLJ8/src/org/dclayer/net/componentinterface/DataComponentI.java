package org.dclayer.net.componentinterface;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponentI;

public interface DataComponentI {

	public Data getData();
	public void setData(Data data);
	
	public void setData(PacketComponentI packetComponent) throws BufException;
	public void getData(PacketComponentI packetComponent) throws BufException, ParseException;
	
	public int lengthForDataLength(int dataLength);
	
}
