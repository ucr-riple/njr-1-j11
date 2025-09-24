package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.KeyCryptoResponseDataMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;

public class KeyCryptoResponseDataMessage extends A2SRevisionSpecificMessage implements KeyCryptoResponseDataMessageI {
	
	private DataComponent responseDataComponent = new DataComponent();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		responseDataComponent.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		responseDataComponent.write(byteBuf);
	}
	
	@Override
	public int length() {
		return responseDataComponent.length();
	}
	
	@Override
	public String toString() {
		return "KeyCryptoResponseDataMessage";
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { responseDataComponent };
	}
	
	@Override
	public byte getType() {
		return Rev0Message.KEY_CRYPTO_RESPONSE_DATA;
	}
	
	@Override
	public DataComponent getResponseDataComponent() {
		return responseDataComponent;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveKeyCryptoResponseDataMessage(responseDataComponent.getData());
	}

	@Override
	public int getMessageRevision() {
		return 0;
	}
	
}
