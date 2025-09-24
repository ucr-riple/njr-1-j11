package org.dclayer.net.a2s.rev35.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.SlotAssignMessageI;
import org.dclayer.net.a2s.rev35.Rev35Message;
import org.dclayer.net.a2s.rev35.component.NetworkTypeComponent;
import org.dclayer.net.a2s.rev35.component.NumberComponent;
import org.dclayer.net.buf.ByteBuf;

public class SlotAssignMessage extends A2SRevisionSpecificMessage implements SlotAssignMessageI {

	private NumberComponent slotNumberComponent = new NumberComponent(0, Integer.MAX_VALUE);
	private NetworkTypeComponent networkTypeComponent = new NetworkTypeComponent();
	
	private Data ownAddressData = new Data();
	private Data addressData = ownAddressData;
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		slotNumberComponent.read(byteBuf);
		networkTypeComponent.read(byteBuf);
		(addressData = ownAddressData).parse(byteBuf.readSpaceTerminatedString());
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		slotNumberComponent.write(byteBuf);
		byteBuf.write((byte)' ');
		networkTypeComponent.write(byteBuf);
		byteBuf.write((byte)' ');
		byteBuf.writeNonTerminatedString(addressData.toString());
	}
	
	@Override
	public int length() {
		return slotNumberComponent.length() + 1 + networkTypeComponent.length() + 1 + 2*addressData.length();
	}
	
	@Override
	public String toString() {
		return String.format("SlotAssignMessage(slot=%d, address=%s)", slotNumberComponent.getNum(), addressData);
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { networkTypeComponent };
	}
	
	@Override
	public byte getType() {
		return Rev35Message.SLOT_ASSIGN;
	}
	
	public NetworkTypeComponent getNetworkTypeComponent() {
		return networkTypeComponent;
	}
	
	public int getSlot() {
		return (int) slotNumberComponent.getNum();
	}
	
	public void setSlot(int slot) {
		slotNumberComponent.setNum(slot);
	}
	
	public void setAddressData(Data addressData) {
		this.addressData = addressData;
	}
	
	public Data getAddressData() {
		return addressData;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveSlotAssignMessage(getSlot(), networkTypeComponent.getNetworkType(), addressData);
	}

	@Override
	public int getMessageRevision() {
		return 35;
	}
	
}
