package org.dclayer.net.apbr;

import org.dclayer.crypto.Crypto;
import org.dclayer.exception.net.parse.MalformedAttributeStringException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.address.Address;
import org.dclayer.net.network.NetworkInstance;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.NetworkType;
import org.dclayer.net.network.component.NetworkPacket;
import org.dclayer.net.network.component.NetworkPayload;
import org.dclayer.net.network.properties.CommonNetworkPayloadProperties;
import org.dclayer.net.network.routing.RouteQuality;
import org.dclayer.net.network.routing.RoutingTable;
import org.dclayer.net.network.slot.GenericNetworkSlot;


public class APBRNetworkType extends NetworkType<APBRNetworkType> {
	
	private int numParts = 1;
	private int partBits = 1;
	private int bitLength;
	private int byteLength;
	
	public APBRNetworkType(String attributeString) throws ParseException {
		super(NetworkType.IDENTIFIER_APBR);
		parseAttributeString(attributeString);
		this.bitLength = this.numParts * this.partBits;
		this.byteLength = (int) Math.ceil(this.bitLength / 8d);
	}
	
	public <T extends NetworkPacket> APBRNetworkType(int numParts, int partBits) {
		super(NetworkType.IDENTIFIER_APBR);
		this.numParts = numParts;
		this.partBits = partBits;
		this.bitLength = this.numParts * this.partBits;
		this.byteLength = (int) Math.ceil(this.bitLength / 8d);
	}
	
	private void parseAttributeString(String attributeString) throws ParseException {
		String[] attributes = attributeString.split("/");
		if(attributes.length != 2) throw new MalformedAttributeStringException(this, attributeString);
		try {
			this.numParts = Integer.parseInt(attributes[0]);
			this.partBits = Integer.parseInt(attributes[1]);
		} catch(NumberFormatException e) {
			throw new MalformedAttributeStringException(this, attributeString, e);
		}
	}

	@Override
	public String getAttributeString() {
		return String.format("%d/%d", numParts, partBits);
	}
	
	public int getNumParts() {
		return numParts;
	}
	
	public int getPartBits() {
		return partBits;
	}

	@Override
	public int getAddressNumBytes() {
		return byteLength;
	}

	@Override
	public Data scaleAddress(Address address) {
		
		Data fullData = Crypto.sha1(address.toData());
		Data scaledData = new Data(byteLength);
		
		for(int i = 0; i < fullData.length(); i++) {
			int scaledIndex = i % scaledData.length();
			scaledData.setByte(scaledIndex, (byte)(scaledData.getByte(scaledIndex) ^ fullData.getByte(i)));
		}
		
		return scaledData;
		
	}
	
	@Override
	public RouteQuality getRouteQuality(Data fromAddress, Data toAddress) {
		return null;
	}

	@Override
	public NetworkPacket makeNetworkPacket(GenericNetworkSlot<NetworkNode> networkSlot) {
		return new APBRPacket(networkSlot, this);
	}

	@Override
	public NetworkPayload makeInNetworkPayload(CommonNetworkPayloadProperties commonNetworkPayloadProperties) {
		return new APBRNetworkPayload(this, commonNetworkPayloadProperties);
	}
	
	@Override
	public NetworkPayload makeOutNetworkPayload(Data scaledAddressData, CommonNetworkPayloadProperties commonNetworkPayloadProperties) {
		return new APBRNetworkPayload(this, scaledAddressData, commonNetworkPayloadProperties);
	}

	@Override
	public RoutingTable makeRoutingTable(NetworkInstance networkInstance) {
		return new APBRRoutingTable(this, networkInstance);
	}

	@Override
	public boolean attributesEqual(APBRNetworkType apbrNetworkType) {
		return this.bitLength == apbrNetworkType.bitLength && this.numParts == apbrNetworkType.numParts;
	}

	@Override
	public int attributesHashCode() {
		return numParts<<16 + bitLength;
	}

}
