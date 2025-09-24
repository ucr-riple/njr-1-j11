package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;

public class ApplicationChannelDataInterserviceMessage extends InterserviceMessage {
	
	private FlexNum applicationChannelSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private DataComponent dataComponent = new DataComponent();

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		applicationChannelSlotFlexNum.read(byteBuf);
		dataComponent.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		applicationChannelSlotFlexNum.write(byteBuf);
		dataComponent.write(byteBuf);
	}

	@Override
	public int length() {
		return applicationChannelSlotFlexNum.length() + dataComponent.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { dataComponent };
	}

	@Override
	public String toString() {
		return String.format("ApplicationChannelDataInterserviceMessage(applicationChannelSlot=%d)", applicationChannelSlotFlexNum.getNum());
	}
	
	public int getApplicationChannelSlot() {
		return (int) applicationChannelSlotFlexNum.getNum();
	}
	
	public void setApplicationChannelSlot(int slot) {
		applicationChannelSlotFlexNum.setNum(slot);
	}
	
	public DataComponent getDataComponent() {
		return dataComponent;
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.APPLICATION_CHANNEL_DATA;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveApplicationChannelDataInterserviceMessage(this);
	}
	
}
