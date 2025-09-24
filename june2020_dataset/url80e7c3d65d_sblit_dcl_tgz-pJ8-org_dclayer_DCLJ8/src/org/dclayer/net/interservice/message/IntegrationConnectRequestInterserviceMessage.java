package org.dclayer.net.interservice.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceMessage;
import org.dclayer.net.interservice.InterservicePacket;
import org.dclayer.net.lla.LLA;

public class IntegrationConnectRequestInterserviceMessage extends InterserviceMessage {
	
	private LLA lla;

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		this.lla = LLA.fromByteBuf(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		lla.write(byteBuf);
	}

	@Override
	public int length() {
		return lla.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("IntegrationConnectRequestInterserviceMessage(lla=%s)", lla);
	}
	
	/**
	 * @return the read LLA object
	 * the returned object has been newly created while this {@link PacketComponent} was read and can be used without restrictions.
	 */
	public LLA getLLA() {
		return lla;
	}
	
	public void setLLA(LLA lla) {
		this.lla = lla;
	}

	@Override
	public int getTypeId() {
		return InterservicePacket.INTEGRATION_CONNECT_REQUEST;
	}

	@Override
	public void callOnReceiveMethod(InterserviceChannel interserviceChannel) {
		interserviceChannel.onReceiveIntegrationConnectRequestInterserviceMessage(this);
	}
	
}
