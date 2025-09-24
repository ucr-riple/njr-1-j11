package org.dclayer.net.circle;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.network.component.NetworkPacket;
import org.dclayer.net.network.slot.GenericNetworkSlot;

public class CirclePacket extends NetworkPacket {
	
	private CircleNetworkType circleNetworkType;
	
	private Data ownAddressData;
	private Data addressData;
	
	private DataComponent dataComponent = new DataComponent();
	
	public CirclePacket(GenericNetworkSlot networkSlot, CircleNetworkType circleNetworkType) {
		super(networkSlot);
		this.circleNetworkType = circleNetworkType;
		this.addressData = this.ownAddressData = new Data(circleNetworkType.getAddressNumBytes());
	}

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		byteBuf.read(addressData = ownAddressData);
		dataComponent.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.write(addressData);
		dataComponent.write(byteBuf);
	}

	@Override
	public int length() {
		return addressData.length() + dataComponent.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { dataComponent };
	}

	@Override
	public String toString() {
		return String.format("CirclePacket(address=%s)", addressData);
	}

	@Override
	public Data getDestinationAddressData() {
		return addressData;
	}

	@Override
	public void setDestinationAddressData(Data destinationAddressData) {
		this.addressData = destinationAddressData;
	}

	@Override
	public DataComponent getDataComponent() {
		return dataComponent;
	}

}
