package org.dclayer.net.a2s.rev35.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.DataMessageI;
import org.dclayer.net.a2s.rev35.Rev35Message;
import org.dclayer.net.a2s.rev35.component.AddressComponent;
import org.dclayer.net.a2s.rev35.component.DataComponent;
import org.dclayer.net.a2s.rev35.component.NumberComponent;
import org.dclayer.net.buf.ByteBuf;

public class DataMessage extends A2SRevisionSpecificMessage implements DataMessageI {
	
	private NumberComponent slotNumberComponent = new NumberComponent();
	private AddressComponent addressComponent = new AddressComponent();
	private DataComponent dataComponent = new DataComponent();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		slotNumberComponent.read(byteBuf);
		addressComponent.read(byteBuf);
		dataComponent.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
		slotNumberComponent.write(byteBuf);
		byteBuf.write((byte)' ');
		addressComponent.write(byteBuf);
		byteBuf.write((byte)' ');
		dataComponent.write(byteBuf);
		
	}
	
	@Override
	public int length() {
		return 	+ slotNumberComponent.length()
				+ 1
				+ addressComponent.length()
				+ 1
				+ dataComponent.length();
	}
	
	@Override
	public String toString() {
		return "DataMessage";
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] {
				slotNumberComponent,
				addressComponent,
				dataComponent
			};
	}
	
	@Override
	public byte getType() {
		return Rev35Message.DATA;
	}
	
	public NumberComponent getSlotNumComponent() {
		return slotNumberComponent;
	}
	
	public AddressComponent getAddressComponent() {
		return addressComponent;
	}
	
	public DataComponent getDataComponent() {
		return dataComponent;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveDataMessage((int) slotNumberComponent.getNum(), addressComponent.getAddressData(), dataComponent.getData());
	}

	@Override
	public int getMessageRevision() {
		return 35;
	}
	
}
