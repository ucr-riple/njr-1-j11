package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.ApplicationChannelDataMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.component.FlexNum;

public class ApplicationChannelDataMessage extends A2SRevisionSpecificMessage implements ApplicationChannelDataMessageI {
	
	private FlexNum channelSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private DataComponent dataComponent = new DataComponent();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		channelSlotFlexNum.read(byteBuf);
		dataComponent.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		channelSlotFlexNum.write(byteBuf);
		dataComponent.write(byteBuf);
	}
	
	@Override
	public int length() {
		return channelSlotFlexNum.length() + dataComponent.length();
	}
	
	@Override
	public String toString() {
		return String.format("ApplicationChannelDataMessage(channelSlot=%d)", channelSlotFlexNum.getNum());
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { dataComponent };
	}
	
	@Override
	public byte getType() {
		return Rev0Message.APPLICATION_CHANNEL_DATA;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveApplicationChannelDataMessage((int) channelSlotFlexNum.getNum(), dataComponent.getData());
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
	
	@Override
	public DataComponent getDataComponent() {
		return dataComponent;
	}
	
}
