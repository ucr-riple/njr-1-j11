package org.dclayer.net.circle;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedMessageTypeException;
import org.dclayer.exception.net.parse.WriteOnlyException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.network.component.NetworkPayload;
import org.dclayer.net.network.properties.CommonNetworkPayloadProperties;

public class CircleNetworkPayload extends NetworkPayload {
	
	public static final int SERVICE = 0;
	public static final int APPLICATION = 1;
	
	public static final int FLAG_SRCADDR = (1 << 0);
	
	//
	
	public CircleNetworkPayload(CircleNetworkType circleNetworkType, CommonNetworkPayloadProperties commonNetworkPayloadProperties) {
		this(circleNetworkType, new Data(circleNetworkType.getAddressNumBytes()), false, commonNetworkPayloadProperties);
	}
	
	public CircleNetworkPayload(CircleNetworkType circleNetworkType, Data sourceAddressData, CommonNetworkPayloadProperties commonNetworkPayloadProperties) {
		this(circleNetworkType, sourceAddressData, true, commonNetworkPayloadProperties);
	}
	
	private CircleNetworkPayload(CircleNetworkType circleNetworkType, Data sourceAddressData, boolean writeOnly, CommonNetworkPayloadProperties commonNetworkPayloadProperties) {
		
		super(circleNetworkType);
		
		this.sourceAddressData = sourceAddressData;
		this.writeOnly = writeOnly;
		
		if(commonNetworkPayloadProperties != null) {
			this.destinedForService = commonNetworkPayloadProperties.destinedForService;
			this.sourceAddress = commonNetworkPayloadProperties.sourceAddress;
		}
		
	}
	
	private boolean writeOnly = false;
	
	private boolean destinedForService = false;
	
	private boolean sourceAddress = false;
	private Data sourceAddressData;
	
	private DataComponent payloadDataComponent = new DataComponent();
	
	public void setSourceAddress(boolean sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		if(writeOnly) {
			throw new WriteOnlyException();
		}
		
		int type = 0xFF & byteBuf.read();
		if(type < SERVICE && type > APPLICATION) throw new UnsupportedMessageTypeException(type);
		this.destinedForService = type != APPLICATION;
		
		int flags = 0xFF & byteBuf.read();
		if(sourceAddress = ((flags & FLAG_SRCADDR) != 0)) {
			byteBuf.read(sourceAddressData);
		}
		
		payloadDataComponent.read(byteBuf);
		
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
		byteBuf.write((byte)(destinedForService ? SERVICE : APPLICATION));
		
		byteBuf.write((byte)(
				(sourceAddress ? FLAG_SRCADDR : 0)
		));
		
		if(sourceAddress) {
			byteBuf.write(sourceAddressData);
		}
		
		payloadDataComponent.write(byteBuf);
		
	}

	@Override
	public int length() {
		return 2 + (sourceAddress ? sourceAddressData.length() : 0) + payloadDataComponent.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { payloadDataComponent };
	}

	@Override
	public String toString() {
		return String.format("CircleNetworkPayload(destinedForService=%s, sourceAddress=%s)", destinedForService, sourceAddress ? sourceAddressData.toString() : "-");
	}
	
	@Override
	public void setDestinedForService(boolean destinedForService) {
		this.destinedForService = destinedForService;
	}

	@Override
	public boolean isDestinedForService() {
		return destinedForService;
	}

	@Override
	public Data getSourceAddressData() {
		return sourceAddress ? sourceAddressData : null;
	}

	@Override
	public Data getPayloadData() {
		return payloadDataComponent.getData();
	}

	@Override
	public void setPayloadData(Data payloadData) {
		payloadDataComponent.setData(payloadData);
	}

	@Override
	public void setPayloadData(PacketComponentI packetComponent) throws BufException {
		payloadDataComponent.setData(packetComponent);
	}

	@Override
	public void getPayloadData(PacketComponentI packetComponent) throws BufException, ParseException {
		payloadDataComponent.getData(packetComponent);
	}

}
