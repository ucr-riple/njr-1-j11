package org.dclayer.net.a2s.rev0.component;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.componentinterface.AddressComponentI;

public class AddressComponent extends PacketComponent implements AddressComponentI {
	
	private DataComponent addressDataComponent = new DataComponent();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		addressDataComponent.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		addressDataComponent.write(byteBuf);
	}

	@Override
	public int length() {
		return addressDataComponent.length();
	}

	@Override
	public String toString() {
		return String.format("AddressComponent(address=%s)", addressDataComponent.getData());
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
	@Override
	public Data getAddressData() {
		return addressDataComponent.getData();
	}
	
	@Override
	public void setAddressData(Data addressData) {
		addressDataComponent.setData(addressData);
	}
	
}
