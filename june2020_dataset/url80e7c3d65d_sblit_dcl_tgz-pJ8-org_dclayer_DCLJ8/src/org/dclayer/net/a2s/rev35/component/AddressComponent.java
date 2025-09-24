package org.dclayer.net.a2s.rev35.component;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.componentinterface.AddressComponentI;

public class AddressComponent extends PacketComponent implements AddressComponentI {
	
	private Data addressData;
	private Data ownAddressData = new Data();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		String address = byteBuf.readSpaceTerminatedString();
		(addressData = ownAddressData).parse(address);
		
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
		byteBuf.writeNonTerminatedString(addressData == null ? "0" : addressData.toString());
		
	}

	@Override
	public int length() {
		return (addressData == null ? 0 : addressData.length())*2 + 1;
	}

	@Override
	public String toString() {
		return String.format("AddressComponent(address=%s)", addressData);
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
	public Data getAddressData() {
		return addressData;
	}
	
	public void setAddressData(Data addressData) {
		this.addressData = addressData;
	}
	
}
