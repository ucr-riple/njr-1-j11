package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.component.NetworkTypeComponent;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;

public class NetworkJoinNoticeInterserviceMessage extends InterserviceMessage {

	private FlexNum addressSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private FlexNum networkSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private NetworkTypeComponent networkTypeComponent = new NetworkTypeComponent(true);

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		addressSlotFlexNum.read(byteBuf);
		networkSlotFlexNum.read(byteBuf);
		networkTypeComponent.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		addressSlotFlexNum.write(byteBuf);
		networkSlotFlexNum.write(byteBuf);
		networkTypeComponent.write(byteBuf);
	}

	@Override
	public int length() {
		return addressSlotFlexNum.length() + networkSlotFlexNum.length() + networkTypeComponent.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { networkTypeComponent };
	}

	@Override
	public String toString() {
		return String.format("NetworkJoinNoticeInterserviceMessage(addressSlot=%d, networkSlot=%d)", addressSlotFlexNum.getNum(), networkSlotFlexNum.getNum());
	}
	
	public NetworkTypeComponent getNetworkTypeComponent() {
		return networkTypeComponent;
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
		return InterservicePacket.NETWORK_JOIN_NOTICE;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveNetworkJoinNoticeInterserviceMessage(this);
	}
	
}
