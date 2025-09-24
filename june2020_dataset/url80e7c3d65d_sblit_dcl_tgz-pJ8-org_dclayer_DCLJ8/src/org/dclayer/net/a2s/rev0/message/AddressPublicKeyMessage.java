package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.AddressPublicKeyMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.KeyComponent;

public class AddressPublicKeyMessage extends A2SRevisionSpecificMessage implements AddressPublicKeyMessageI {
	
	private KeyComponent addressPublicKeyComponent = new KeyComponent();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		addressPublicKeyComponent.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		addressPublicKeyComponent.write(byteBuf);
	}
	
	@Override
	public int length() {
		return addressPublicKeyComponent.length();
	}
	
	@Override
	public String toString() {
		return "AddressPublicKeyMessage";
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { addressPublicKeyComponent };
	}
	
	@Override
	public byte getType() {
		return Rev0Message.ADDRESS_PUBLIC_KEY;
	}
	
	@Override
	public KeyComponent getKeyComponent() {
		return addressPublicKeyComponent;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveAddressPublicKeyMessage(addressPublicKeyComponent.getKeyComponent());
	}

	@Override
	public int getMessageRevision() {
		return 0;
	}
	
}
