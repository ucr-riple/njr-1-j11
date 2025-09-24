package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.KeyDecryptDataMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;

public class KeyDecryptDataMessage extends A2SRevisionSpecificMessage implements KeyDecryptDataMessageI {
	
	private DataComponent cipherDataComponent = new DataComponent();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		cipherDataComponent.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		cipherDataComponent.write(byteBuf);
	}
	
	@Override
	public int length() {
		return cipherDataComponent.length();
	}
	
	@Override
	public String toString() {
		return "KeyDecryptDataMessage";
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { cipherDataComponent };
	}
	
	@Override
	public byte getType() {
		return Rev0Message.KEY_DECRYPT_DATA;
	}
	
	@Override
	public DataComponent getCipherDataComponent() {
		return cipherDataComponent;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveKeyEncryptDataMessage(cipherDataComponent.getData());
	}

	@Override
	public int getMessageRevision() {
		return 0;
	}
	
}
