package org.dclayer.net.crisp.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.component.EncryptionKeySignedPacketComponent;
import org.dclayer.net.componentinterface.DataComponentI;
import org.dclayer.net.crisp.CrispMessage;
import org.dclayer.net.crisp.CrispMessageReceiver;
import org.dclayer.net.crisp.CrispPacket;
import org.dclayer.net.crisp.message.NeighborRequestCrispMessageI;
import org.dclayer.net.lla.LLA;

public class NeighborRequestCrispMessage extends EncryptionKeySignedPacketComponent implements NeighborRequestCrispMessageI, CrispMessage {
	
	private String actionIdentifier;
	private LLA senderLLA = null;
	private boolean response = false;
	private DataComponent ignoreDataComponent = new DataComponent();
	
	@Override
	public void readPlain(ByteBuf byteBuf) throws ParseException, BufException {
		
		actionIdentifier = byteBuf.readString();
		senderLLA = LLA.fromByteBuf(byteBuf);
		response = byteBuf.read() != 0;
		if(!response) ignoreDataComponent.read(byteBuf);
		
	}

	@Override
	public void writePlain(ByteBuf byteBuf) throws BufException {
		
		byteBuf.writeString(actionIdentifier);
		senderLLA.write(byteBuf);
		byteBuf.write((byte)(response ? 0xFF : 0));
		if(!response) ignoreDataComponent.write(byteBuf);
		
	}

	@Override
	public int plainLength() {
		return actionIdentifier.length() + 1 + senderLLA.length() + 1 + (response ? 0 : ignoreDataComponent.length());
	}

	@Override
	public PacketComponent[] getPlainChildren() {
		return response ? null : new PacketComponent[] { ignoreDataComponent };
	}

	@Override
	public String plainToString() {
		return String.format("NeighborRequestCrispMessage(actionIdentifier=%s, senderLLA=%s, response=%s)", actionIdentifier, senderLLA, response);
	}

	@Override
	public int getType() {
		return CrispPacket.NEIGHBOR_REQUEST;
	}

	@Override
	public String getActionIdentifier() {
		return actionIdentifier;
	}

	@Override
	public void setActionIdentifier(String actionIdentifier) {
		this.actionIdentifier = actionIdentifier;
	}

	@Override
	public LLA getSenderLLA() {
		return senderLLA;
	}

	@Override
	public void setSenderLLA(LLA senderLLA) {
		this.senderLLA = senderLLA;
	}

	@Override
	public boolean isResponse() {
		return response;
	}

	@Override
	public void setResponse(boolean response) {
		this.response = response;
	}

	@Override
	public <T> void callOnReceiveMethod(CrispMessageReceiver<T> crispMessageReceiver, T o) {
		crispMessageReceiver.onReceiveNeighborRequestCrispMessage(this, o);
	}

	@Override
	public DataComponentI getIgnoreDataComponent() {
		return ignoreDataComponent;
	}

}
