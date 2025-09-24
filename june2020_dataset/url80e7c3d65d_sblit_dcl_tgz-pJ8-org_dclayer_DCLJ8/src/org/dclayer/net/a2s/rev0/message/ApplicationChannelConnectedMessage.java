package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.ApplicationChannelConnectedMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;

public class ApplicationChannelConnectedMessage extends A2SRevisionSpecificMessage implements ApplicationChannelConnectedMessageI {
	
	private FlexNum channelSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		channelSlotFlexNum.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		channelSlotFlexNum.write(byteBuf);
	}
	
	@Override
	public int length() {
		return channelSlotFlexNum.length();
	}
	
	@Override
	public String toString() {
		return String.format("ApplicationChannelConnectedMessage(channelSlot=%d)", channelSlotFlexNum.getNum());
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
	@Override
	public byte getType() {
		return Rev0Message.APPLICATION_CHANNEL_CONNECTED;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveApplicationChannelConnectMessage((int) channelSlotFlexNum.getNum());
	}

	@Override
	public int getMessageRevision() {
		return 0;
	}

	@Override
	public int getChannelSlot() {
		return (int) channelSlotFlexNum.getNum();
	}

	@Override
	public void setChannelSlot(int channelSlot) {
		channelSlotFlexNum.setNum(channelSlot);
	}
	
}
