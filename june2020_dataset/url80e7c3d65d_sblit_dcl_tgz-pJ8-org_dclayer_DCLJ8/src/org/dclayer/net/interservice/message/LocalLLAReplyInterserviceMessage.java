package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;
import org.dclayer.net.lla.LLA;

public class LocalLLAReplyInterserviceMessage extends InterserviceMessage {
	
	private LLA localLLA;

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		localLLA = LLA.fromByteBuf(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		localLLA.write(byteBuf);
	}

	@Override
	public int length() {
		return localLLA.length();
	}

	@Override
	public PacketComponentI[] getChildren() {
		return new PacketComponentI[] { localLLA };
	}
	
	public void setLocalLLA(LLA localLLA) {
		this.localLLA = localLLA;
	}
	
	public LLA getLocalLLA() {
		return localLLA;
	}

	@Override
	public String toString() {
		return "LocalLLAReplyInterserviceMessage";
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.LOCAL_LLA_REPLY;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveLocalLLAReplyInterserviceMessage(this);
	}
	
}
