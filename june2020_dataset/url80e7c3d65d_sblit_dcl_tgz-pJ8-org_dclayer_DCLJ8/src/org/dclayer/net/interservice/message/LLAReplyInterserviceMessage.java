package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;
import org.dclayer.net.interservice.component.LowerLevelAddressListComponent;

public class LLAReplyInterserviceMessage extends InterserviceMessage {
	
	private LowerLevelAddressListComponent lowerLevelAddressListComponent = new LowerLevelAddressListComponent();

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		lowerLevelAddressListComponent.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		lowerLevelAddressListComponent.write(byteBuf);
	}

	@Override
	public int length() {
		return lowerLevelAddressListComponent.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { lowerLevelAddressListComponent };
	}

	@Override
	public String toString() {
		return "LLAReplyInterserviceMessage";
	}
	
	public LowerLevelAddressListComponent getLowerLevelAddressListComponent() {
		return lowerLevelAddressListComponent;
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.LLA_REPLY;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveLLAReplyInterserviceMessage(this);
	}
	
}
