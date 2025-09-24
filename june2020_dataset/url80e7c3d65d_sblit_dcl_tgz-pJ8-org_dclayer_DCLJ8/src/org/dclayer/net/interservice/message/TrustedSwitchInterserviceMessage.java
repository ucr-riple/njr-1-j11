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

public class TrustedSwitchInterserviceMessage extends InterserviceMessage {
	
	private FlexNum addressSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private KeyComponent keyComponent = new KeyComponent();

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		addressSlotFlexNum.read(byteBuf);
		keyComponent.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		addressSlotFlexNum.write(byteBuf);
		keyComponent.write(byteBuf);
	}

	@Override
	public int length() {
		return addressSlotFlexNum.length() + keyComponent.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { keyComponent };
	}

	@Override
	public String toString() {
		return String.format("TrustedSwitchInterserviceMessage(addressSlot=%d)", addressSlotFlexNum.getNum());
	}
	
	public int getAddressSlot() {
		return (int) addressSlotFlexNum.getNum();
	}
	
	public void setAddressSlot(int slot) {
		addressSlotFlexNum.setNum(slot);
	}
	
	public KeyComponent getKeyComponent() {
		return keyComponent;
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.TRUSTED_SWITCH;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveTrustedSwitchInterserviceMessage(this);
	}
	
}
