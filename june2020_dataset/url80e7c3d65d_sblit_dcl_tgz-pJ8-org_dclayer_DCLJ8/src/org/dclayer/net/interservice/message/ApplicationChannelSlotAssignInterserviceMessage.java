package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.component.KeyComponent;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;

public class ApplicationChannelSlotAssignInterserviceMessage extends InterserviceMessage {
	
	private FlexNum applicationChannelSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private FlexNum senderAddressSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private FlexNum receiverAddressSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private String actionIdentifier;

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		applicationChannelSlotFlexNum.read(byteBuf);
		senderAddressSlotFlexNum.read(byteBuf);
		receiverAddressSlotFlexNum.read(byteBuf);
		actionIdentifier = byteBuf.readString();
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		applicationChannelSlotFlexNum.write(byteBuf);
		senderAddressSlotFlexNum.write(byteBuf);
		receiverAddressSlotFlexNum.write(byteBuf);
		byteBuf.writeString(actionIdentifier);
	}

	@Override
	public int length() {
		return applicationChannelSlotFlexNum.length() + senderAddressSlotFlexNum.length() + receiverAddressSlotFlexNum.length() + actionIdentifier.length() + 1;
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("ApplicationChannelSlotAssignInterserviceMessage(applicationChannelSlot=%d, senderAddressSlot=%d, receiverAddressSlot=%d, actionIdentifier=%s)", applicationChannelSlotFlexNum.getNum(), senderAddressSlotFlexNum.getNum(), receiverAddressSlotFlexNum.getNum(), actionIdentifier);
	}
	
	public int getApplicationChannelSlot() {
		return (int) applicationChannelSlotFlexNum.getNum();
	}
	
	public void setApplicationChannelSlot(int slot) {
		applicationChannelSlotFlexNum.setNum(slot);
	}
	
	public int getSenderAddressSlot() {
		return (int) senderAddressSlotFlexNum.getNum();
	}
	
	public void setSenderAddressSlot(int slot) {
		senderAddressSlotFlexNum.setNum(slot);
	}
	
	public int getReceiverAddressSlot() {
		return (int) receiverAddressSlotFlexNum.getNum();
	}
	
	public void setReceiverAddressSlot(int slot) {
		receiverAddressSlotFlexNum.setNum(slot);
	}
	
	public String getActionIdentifier() {
		return actionIdentifier;
	}
	
	public void setActionIdentifier(String actionIdentifier) {
		this.actionIdentifier = actionIdentifier;
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.APPLICATION_CHANNEL_SLOT_ASSIGN;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveApplicationChannelSlotAssignInterserviceMessage(this);
	}
	
}
