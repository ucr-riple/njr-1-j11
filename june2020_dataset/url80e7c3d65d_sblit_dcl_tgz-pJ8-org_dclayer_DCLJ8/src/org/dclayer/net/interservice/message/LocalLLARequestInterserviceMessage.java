package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;

public class LocalLLARequestInterserviceMessage extends InterserviceMessage {

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
	}

	@Override
	public int length() {
		return 0;
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return "LocalLLARequestInterserviceMessage";
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.LOCAL_LLA_REQUEST;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveLocalLLARequestInterserviceMessage(this);
	}
	
}
