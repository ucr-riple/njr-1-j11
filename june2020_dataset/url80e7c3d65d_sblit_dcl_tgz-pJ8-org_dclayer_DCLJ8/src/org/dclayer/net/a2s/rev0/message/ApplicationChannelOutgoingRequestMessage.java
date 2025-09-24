package org.dclayer.net.a2s.rev0.message;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.ApplicationChannelOutgoingRequestMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.component.KeyComponent;

public class ApplicationChannelOutgoingRequestMessage extends A2SRevisionSpecificMessage implements ApplicationChannelOutgoingRequestMessageI {
	
	private FlexNum networkSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private FlexNum channelSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private String actionIdentifierSuffix;
	private KeyComponent remotePublicKeyComponent = new KeyComponent();
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		networkSlotFlexNum.read(byteBuf);
		channelSlotFlexNum.read(byteBuf);
		actionIdentifierSuffix = byteBuf.readString();
		remotePublicKeyComponent.read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		networkSlotFlexNum.write(byteBuf);
		channelSlotFlexNum.write(byteBuf);
		byteBuf.writeString(actionIdentifierSuffix);
		remotePublicKeyComponent.write(byteBuf);
	}
	
	@Override
	public int length() {
		return networkSlotFlexNum.length() + channelSlotFlexNum.length() + actionIdentifierSuffix.length() + 1 + remotePublicKeyComponent.length();
	}
	
	@Override
	public String toString() {
		return String.format("ApplicationChannelOutgoingRequestMessage(networkSlot=%d, channelSlot=%d, actionIdentifierSuffix=%s)", networkSlotFlexNum.getNum(), channelSlotFlexNum.getNum(), actionIdentifierSuffix);
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { remotePublicKeyComponent };
	}
	
	@Override
	public byte getType() {
		return Rev0Message.APPLICATION_CHANNEL_OUTGOING_REQUEST;
	}
	
	@Override
	public KeyComponent getKeyComponent() {
		return remotePublicKeyComponent;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveApplicationChannelOutgoingRequestMessage((int) networkSlotFlexNum.getNum(), (int) channelSlotFlexNum.getNum(), actionIdentifierSuffix, remotePublicKeyComponent.getKeyComponent());
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
	public int getChannelSlot() {
		return (int) channelSlotFlexNum.getNum();
	}

	@Override
	public void setChannelSlot(int channelSlot) {
		channelSlotFlexNum.setNum(channelSlot);
	}
	
	@Override
	public String getActionIdentifierSuffix() {
		return actionIdentifierSuffix;
	}
	
	@Override
	public void setActionIdentifierSuffix(String actionIdentifierSuffix) {
		this.actionIdentifierSuffix = actionIdentifierSuffix;
	}
	
}
