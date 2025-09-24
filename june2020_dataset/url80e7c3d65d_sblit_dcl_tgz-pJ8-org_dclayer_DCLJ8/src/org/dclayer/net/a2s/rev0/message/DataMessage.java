package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.DataMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.a2s.rev0.component.AddressComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.componentinterface.NumComponentI;

public class DataMessage extends A2SRevisionSpecificMessage implements DataMessageI {
	
	private FlexNum slotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private AddressComponent addressComponent = new AddressComponent();
	private DataComponent dataComponent = new DataComponent();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		slotFlexNum.read(byteBuf);
		addressComponent.read(byteBuf);
		dataComponent.read(byteBuf);
		
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
		slotFlexNum.write(byteBuf);
		addressComponent.write(byteBuf);
		dataComponent.write(byteBuf);
		
	}
	
	@Override
	public int length() {
		return 	+ slotFlexNum.length()
				+ addressComponent.length()
				+ dataComponent.length();
	}
	
	@Override
	public String toString() {
		return "DataMessage";
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] {
				slotFlexNum,
				addressComponent,
				dataComponent
			};
	}
	
	@Override
	public byte getType() {
		return Rev0Message.DATA;
	}
	
	@Override
	public NumComponentI getSlotNumComponent() {
		return slotFlexNum;
	}
	
	@Override
	public AddressComponent getAddressComponent() {
		return addressComponent;
	}
	
	@Override
	public DataComponent getDataComponent() {
		return dataComponent;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveDataMessage((int) slotFlexNum.getNum(), addressComponent.getAddressData(), dataComponent.getData());
	}

	@Override
	public int getMessageRevision() {
		return 0;
	}
	
}
