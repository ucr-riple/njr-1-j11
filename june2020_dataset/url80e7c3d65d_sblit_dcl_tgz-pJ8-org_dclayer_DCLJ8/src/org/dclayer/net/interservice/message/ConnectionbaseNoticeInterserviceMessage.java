package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;

public class ConnectionbaseNoticeInterserviceMessage extends InterserviceMessage {
	
	private FlexNum addressSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private byte connectionBase;
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		addressSlotFlexNum.read(byteBuf);
		this.connectionBase = byteBuf.read();
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		addressSlotFlexNum.write(byteBuf);
		byteBuf.write(connectionBase);
	}

	@Override
	public int length() {
		return addressSlotFlexNum.length() + 1;
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("ConnectionbaseNoticeInterserviceMessage(addressSlot=%d, connectionBase=%d)", addressSlotFlexNum.getNum(), connectionBase);
	}
	
	public int getAddressSlot() {
		return (int) addressSlotFlexNum.getNum();
	}
	
	public void setAddressSlot(int slot) {
		addressSlotFlexNum.setNum(slot);
	}
	
	public byte getConnectionBase() {
		return connectionBase;
	}
	
	public void setConnectionBase(byte connectionBase) {
		this.connectionBase = connectionBase;
	}
	
	@Override
	public int getTypeId() {
		return InterservicePacket.CONNECTIONBASE_NOTICE;
	}
	
	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveConnectionbaseNoticeInterserviceMessage(this);
	}
	
}
