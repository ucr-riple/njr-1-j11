package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.ApplicationChannelIncomingRequestMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.component.KeyComponent;
import org.dclayer.net.componentinterface.DataComponentI;
import org.dclayer.net.lla.LLA;

public class ApplicationChannelIncomingRequestMessage extends A2SRevisionSpecificMessage implements ApplicationChannelIncomingRequestMessageI {
	
	private FlexNum networkSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private String actionIdentifierSuffix;
	private KeyComponent remotePublicKeyComponent = new KeyComponent();
	private LLA senderLLA;
	private DataComponent ignoreDataComponent = new DataComponent();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		networkSlotFlexNum.read(byteBuf);
		actionIdentifierSuffix = byteBuf.readString();
		remotePublicKeyComponent.read(byteBuf);
		senderLLA = LLA.fromByteBuf(byteBuf);
		ignoreDataComponent.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		networkSlotFlexNum.write(byteBuf);
		byteBuf.writeString(actionIdentifierSuffix);
		remotePublicKeyComponent.write(byteBuf);
		senderLLA.write(byteBuf);
		ignoreDataComponent.write(byteBuf);
	}
	
	@Override
	public int length() {
		return networkSlotFlexNum.length() + actionIdentifierSuffix.length() + 1 + remotePublicKeyComponent.length() + senderLLA.length() + ignoreDataComponent.length();
	}
	
	@Override
	public String toString() {
		return String.format("ApplicationChannelIncomingRequestMessage(networkSlot=%d, actionIdentifierSuffix=%s, senderLLA=%s)", networkSlotFlexNum.getNum(), actionIdentifierSuffix, senderLLA);
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { remotePublicKeyComponent, ignoreDataComponent };
	}
	
	@Override
	public byte getType() {
		return Rev0Message.APPLICATION_CHANNEL_INCOMING_REQUEST;
	}
	
	@Override
	public KeyComponent getKeyComponent() {
		return remotePublicKeyComponent;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveApplicationChannelIncomingRequestMessage((int) networkSlotFlexNum.getNum(), actionIdentifierSuffix, remotePublicKeyComponent.getKeyComponent(), senderLLA, ignoreDataComponent.getData());
	}

	@Override
	public int getMessageRevision() {
		return 0;
	}

	@Override
	public int getNetworkSlot() {
		return (int) networkSlotFlexNum.getNum();
	}

	@Override
	public void setNetworkSlot(int networkSlot) {
		networkSlotFlexNum.setNum(networkSlot);
	}
	
	@Override
	public String getActionIdentifierSuffix() {
		return actionIdentifierSuffix;
	}
	
	@Override
	public void setActionIdentifierSuffix(String actionIdentifierSuffix) {
		this.actionIdentifierSuffix = actionIdentifierSuffix;
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
	public DataComponentI getIgnoreDataComponent() {
		return ignoreDataComponent;
	}
	
}
