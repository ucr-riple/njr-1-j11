package org.dclayer.net.a2s.rev35.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.RevisionMessageI;
import org.dclayer.net.a2s.rev35.Rev35Message;
import org.dclayer.net.a2s.rev35.component.NumberComponent;
import org.dclayer.net.buf.ByteBuf;

public class RevisionMessage extends A2SRevisionSpecificMessage implements RevisionMessageI {
	
	public NumberComponent revisionNumberComponent = new NumberComponent(0, Integer.MAX_VALUE);
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		revisionNumberComponent.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		revisionNumberComponent.write(byteBuf);
	}
	
	@Override
	public int length() {
		return revisionNumberComponent.length();
	}
	
	@Override
	public String toString() {
		return String.format("RevisionMessage(revision=%d)", revisionNumberComponent.getNum());
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
	@Override
	public byte getType() {
		return Rev35Message.REVISION;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveRevisionMessage((int) revisionNumberComponent.getNum());
	}

	@Override
	public void setRevision(int revision) {
		revisionNumberComponent.setNum(revision);
	}

	@Override
	public int getRevision() {
		return (int) revisionNumberComponent.getNum();
	}

	@Override
	public int getMessageRevision() {
		return 35;
	}
	
}
