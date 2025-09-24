package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.InvalidSlotException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;
import org.dclayer.net.interservice.NetworkPacketProvider;
import org.dclayer.net.network.component.NetworkPacket;

public class NetworkPacketInterserviceMessage extends InterserviceMessage {

	private FlexNum slotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private NetworkPacket networkPacket;
	
	private NetworkPacketProvider networkPacketProvider;
	
	public NetworkPacketInterserviceMessage(NetworkPacketProvider networkPacketProvider) {
		this.networkPacketProvider = networkPacketProvider;
	}

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		slotFlexNum.read(byteBuf);
		networkPacket = networkPacketProvider.getNetworkPacket((int) slotFlexNum.getNum());
		if(networkPacket == null) {
			throw new InvalidSlotException((int) slotFlexNum.getNum());
		}
		networkPacket.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		slotFlexNum.write(byteBuf);
		networkPacket.write(byteBuf);
	}

	@Override
	public int length() {
		return slotFlexNum.length() + networkPacket.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("NetworkPacketInterserviceMessage(slot=%d)", slotFlexNum.getNum());
	}
	
	public NetworkPacket getNetworkPacket() {
		return networkPacket;
	}
	
	public void setNetworkPacket(NetworkPacket networkPacket) {
		this.networkPacket = networkPacket;
	}
	
	public int getSlot() {
		return (int) slotFlexNum.getNum();
	}
	
	public void setSlot(int slot) {
		slotFlexNum.setNum(slot);
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.NETWORK_PACKET;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveNetworkPacketInterserviceMessage(this);
	}
	
}
