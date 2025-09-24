package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;

public class NetworkLeaveNoticeInterserviceMessage extends InterserviceMessage {
	
	private FlexNum addressSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private FlexNum networkSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		addressSlotFlexNum.read(byteBuf);
		networkSlotFlexNum.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		addressSlotFlexNum.write(byteBuf);
		networkSlotFlexNum.write(byteBuf);
	}

	@Override
	public int length() {
		return addressSlotFlexNum.length() + networkSlotFlexNum.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("NetworkLeaveNoticeInterserviceMessage(addressSlot=%d, networkSlot=%d)", addressSlotFlexNum.getNum(), networkSlotFlexNum.getNum());
	}
	
	public int getAddressSlot() {
		return (int) addressSlotFlexNum.getNum();
	}
	
	public void setAddressSlot(int slot) {
		addressSlotFlexNum.setNum(slot);
	}
	
	public int getNetworkSlot() {
		return (int) networkSlotFlexNum.getNum();
	}
	
	public void setNetworkSlot(int slot) {
		networkSlotFlexNum.setNum(slot);
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.NETWORK_LEAVE_NOTICE;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveNetworkLeaveNoticeInterserviceMessage(this);
	}
	
}
