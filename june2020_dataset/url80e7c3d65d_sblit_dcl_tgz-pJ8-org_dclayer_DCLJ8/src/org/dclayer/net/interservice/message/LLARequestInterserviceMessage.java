package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;

public class LLARequestInterserviceMessage extends InterserviceMessage {
	
	private FlexNum limit = new FlexNum();

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		limit.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		limit.write(byteBuf);
	}

	@Override
	public int length() {
		return limit.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("LLARequestInterserviceMessage(limit=%d)", limit.getNum());
	}
	
	public long getLimit() {
		return limit.getNum();
	}
	
	public void setLimit(long limit) {
		this.limit.setNum(limit);
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.LLA_REQUEST;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveLLARequestInterserviceMessage(this);
	}
	
}
