package org.dclayer.net.apbr;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.component.NetworkPacket;
import org.dclayer.net.network.slot.GenericNetworkSlot;

public class APBRPacket extends NetworkPacket {
	
	private APBRNetworkType apbrNetworkType;
	
	private Data ownAddressData;
	private Data addressData;
	
	private DataComponent dataComponent = new DataComponent();
	
	public APBRPacket(GenericNetworkSlot<NetworkNode> networkSlot, APBRNetworkType apbrNetworkType) {
		super(networkSlot);
		this.apbrNetworkType = apbrNetworkType;
		this.addressData = this.ownAddressData = new Data(apbrNetworkType.getAddressNumBytes());
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
		return String.format("APBRPacket(address=%s)", addressData);
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
