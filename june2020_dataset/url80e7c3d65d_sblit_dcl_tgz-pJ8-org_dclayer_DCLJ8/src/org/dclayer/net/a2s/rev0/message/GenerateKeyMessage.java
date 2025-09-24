package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;

public class GenerateKeyMessage extends A2SRevisionSpecificMessage {
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
	}
	
	@Override
	public int length() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "GenerateKeyMessage";
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
	@Override
	public byte getType() {
		return Rev0Message.GENERATE_KEY;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveGenerateKeyMessage();
	}

	@Override
	public int getMessageRevision() {
		return 0;
	}
	
}
