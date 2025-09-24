package org.dclayer.net;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.buf.ByteBuf;

public interface PacketComponentI {

	public void write(ByteBuf byteBuf) throws BufException;
	public void read(ByteBuf byteBuf) throws ParseException, BufException;
	public int length();
	public PacketComponentI[] getChildren();
	public String toString();
	
	public String represent();
	public String represent(boolean tree);
	public String represent(boolean tree, int level);
	
}
