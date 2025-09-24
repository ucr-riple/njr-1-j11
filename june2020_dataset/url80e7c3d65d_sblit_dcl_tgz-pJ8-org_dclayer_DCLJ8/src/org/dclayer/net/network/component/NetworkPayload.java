package org.dclayer.net.network.component;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.DataByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.network.NetworkType;

public abstract class NetworkPayload extends PacketComponent {
	
	private NetworkType networkType;
	
	private DataByteBuf dataByteBuf = new DataByteBuf();
	private Data writeData = new Data();
	
	private DataComponent readDataComponent;
	
	public NetworkPayload(NetworkType networkType) {
		this.networkType = networkType;
	}
	
	public NetworkType getNetworkType() {
		return networkType;
	}
	
	public void write(DataComponent dataComponent) throws BufException {
		writeData.prepare(length());
		dataByteBuf.setData(writeData);
		write(dataByteBuf);
		dataComponent.setData(writeData);
	}
	
	public void read() throws ParseException, BufException {
		dataByteBuf.setData(readDataComponent.getData());
		read(dataByteBuf);
	}
	
	public void setReadDataComponent(DataComponent dataComponent) {
		this.readDataComponent = dataComponent;
	}
	
	public abstract void setDestinedForService(boolean destinedForService);
	public abstract boolean isDestinedForService();
	
	public abstract Data getSourceAddressData();
	
	public abstract Data getPayloadData();
	public abstract void setPayloadData(Data payloadData);
	
	public abstract void setPayloadData(PacketComponentI packetComponent) throws BufException;
	public abstract void getPayloadData(PacketComponentI packetComponent) throws BufException, ParseException;
	
}
