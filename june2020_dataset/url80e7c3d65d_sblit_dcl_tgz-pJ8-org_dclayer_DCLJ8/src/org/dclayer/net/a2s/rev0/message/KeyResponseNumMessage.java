package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.KeyResponseNumMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;

public class KeyResponseNumMessage extends A2SRevisionSpecificMessage implements KeyResponseNumMessageI {
	
	private FlexNum responseFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		responseFlexNum.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		responseFlexNum.write(byteBuf);
	}
	
	@Override
	public int length() {
		return responseFlexNum.length();
	}
	
	@Override
	public String toString() {
		return String.format("KeyResponseNumMessage(responseNum=%d)", responseFlexNum.getNum());
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
	@Override
	public byte getType() {
		return Rev0Message.KEY_RESPONSE_NUM;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveKeyResponseNumMessage((int) responseFlexNum.getNum());
	}

	@Override
	public int getMessageRevision() {
		return 0;
	}

	@Override
	public int getResponseNum() {
		return (int) responseFlexNum.getNum();
	}
	
	@Override
	public void setResponseNum(int responseNum) {
		responseFlexNum.setNum(responseNum);
	}
	
}
