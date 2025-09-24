package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;

public class VersionInterserviceMessage extends InterserviceMessage {
	
	private FlexNum version = new FlexNum();

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		version.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		version.write(byteBuf);
	}

	@Override
	public int length() {
		return version.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("VersionInterserviceMessage(version=%d)", version.getNum());
	}
	
	public long getVersion() {
		return version.getNum();
	}
	
	public void setVersion(long version) {
		this.version.setNum(version);
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.VERSION;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveVersionInterserviceMessage(this);
	}
	
}
