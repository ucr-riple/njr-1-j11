package org.dclayer.net.crisp;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedMessageTypeException;
import org.dclayer.exception.net.parse.UnsupportedRevisionException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.crisp.message.NeighborRequestCrispMessageI;
import org.dclayer.net.crisp.rev0.message.NeighborRequestCrispMessage;

public class CrispPacket extends PacketComponent {
	
	public static final int NEIGHBOR_REQUEST = 0;
	
	//
	
	private CrispMessage message;
	
	private CrispMessage[] messages = new CrispMessage[] {
		new NeighborRequestCrispMessage()
	};

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		byte rev = byteBuf.read();
		if(rev != 0) throw new UnsupportedRevisionException(rev & 0xFF);
		
		int type = 0xFF & byteBuf.read();
		if(type > messages.length) throw new UnsupportedMessageTypeException(type);
		
		message = messages[type];
		message.read(byteBuf);
		
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
		byteBuf.write((byte) 0);
		byteBuf.write((byte) message.getType());
		message.write(byteBuf);
		
	}

	@Override
	public int length() {
		return 2 + message.length();
	}

	@Override
	public PacketComponentI[] getChildren() {
		return new PacketComponentI[] { message };
	}

	@Override
	public String toString() {
		return String.format("CrispPacket(type=%d)", message.getType()&0xFF);
	}
	
	public CrispMessage getCrispMessage() {
		return message;
	}
	
	public NeighborRequestCrispMessageI setNeighborRequestCrispMessage() {
		return (NeighborRequestCrispMessage)(this.message = messages[NEIGHBOR_REQUEST]);
	}
	
}
