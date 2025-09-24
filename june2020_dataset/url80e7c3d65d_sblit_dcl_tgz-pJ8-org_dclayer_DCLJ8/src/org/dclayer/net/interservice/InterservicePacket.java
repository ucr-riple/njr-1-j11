package org.dclayer.net.interservice;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedMessageTypeException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.interservice.message.ApplicationChannelDataInterserviceMessage;
import org.dclayer.net.interservice.message.ApplicationChannelSlotAssignInterserviceMessage;
import org.dclayer.net.interservice.message.ConnectionbaseNoticeInterserviceMessage;
import org.dclayer.net.interservice.message.CryptoChallengeReplyInterserviceMessage;
import org.dclayer.net.interservice.message.CryptoChallengeRequestInterserviceMessage;
import org.dclayer.net.interservice.message.LocalLLAReplyInterserviceMessage;
import org.dclayer.net.interservice.message.LocalLLARequestInterserviceMessage;
import org.dclayer.net.interservice.message.IntegrationConnectRequestInterserviceMessage;
import org.dclayer.net.interservice.message.IntegrationRequestInterserviceMessage;
import org.dclayer.net.interservice.message.LLAReplyInterserviceMessage;
import org.dclayer.net.interservice.message.LLARequestInterserviceMessage;
import org.dclayer.net.interservice.message.NetworkJoinNoticeInterserviceMessage;
import org.dclayer.net.interservice.message.NetworkLeaveNoticeInterserviceMessage;
import org.dclayer.net.interservice.message.NetworkPacketInterserviceMessage;
import org.dclayer.net.interservice.message.TrustedSwitchInterserviceMessage;
import org.dclayer.net.interservice.message.VersionInterserviceMessage;

/**
 * Main interservice packet type containing both the type and the message
 * @author Martin Exner
 */
public class InterservicePacket extends PacketComponent {
	
	public static final int VERSION = 0x00;
	public static final int LLA_REQUEST = 0x01;
	public static final int LLA_REPLY = 0x02;
	public static final int TRUSTED_SWITCH = 0x03;
	public static final int CRYPTO_CHALLENGE_REQUEST = 0x04;
	public static final int CRYPTO_CHALLENGE_REPLY = 0x05;
	public static final int CONNECTIONBASE_NOTICE = 0x06;
	public static final int NETWORK_JOIN_NOTICE = 0x07;
	public static final int NETWORK_LEAVE_NOTICE = 0x08;
	public static final int INTEGRATION_REQUEST = 0x09;
	public static final int INTEGRATION_CONNECT_REQUEST = 0x0a;
	public static final int LOCAL_LLA_REQUEST = 0x0b;
	public static final int LOCAL_LLA_REPLY = 0x0c;
	public static final int NETWORK_PACKET = 0x0d;
	public static final int APPLICATION_CHANNEL_SLOT_ASSIGN = 0x0e;
	public static final int APPLICATION_CHANNEL_DATA = 0x0f;
	
	private FlexNum type = new FlexNum(0, Integer.MAX_VALUE);
	
	private InterserviceMessage[] ownInterserviceMessages;
	private InterserviceMessage interserviceMessage;
	
	public InterservicePacket(NetworkPacketProvider networkPacketProvider) {
		this.ownInterserviceMessages = new InterserviceMessage[] {
				/* 0x00 */ new VersionInterserviceMessage(),
				/* 0x01 */ new LLARequestInterserviceMessage(),
				/* 0x02 */ new LLAReplyInterserviceMessage(),
				/* 0x03 */ new TrustedSwitchInterserviceMessage(),
				/* 0x04 */ new CryptoChallengeRequestInterserviceMessage(),
				/* 0x05 */ new CryptoChallengeReplyInterserviceMessage(),
				/* 0x06 */ new ConnectionbaseNoticeInterserviceMessage(),
				/* 0x07 */ new NetworkJoinNoticeInterserviceMessage(),
				/* 0x08 */ new NetworkLeaveNoticeInterserviceMessage(),
				/* 0x09 */ new IntegrationRequestInterserviceMessage(),
				/* 0x0a */ new IntegrationConnectRequestInterserviceMessage(),
				/* 0x0b */ new LocalLLARequestInterserviceMessage(),
				/* 0x0c */ new LocalLLAReplyInterserviceMessage(),
				/* 0x0d */ new NetworkPacketInterserviceMessage(networkPacketProvider),
				/* 0x0e */ new ApplicationChannelSlotAssignInterserviceMessage(),
				/* 0x0f */ new ApplicationChannelDataInterserviceMessage()
		};
	}

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		type.read(byteBuf);
		int typeId = (int) Math.min(Integer.MAX_VALUE, type.getNum());
		if(typeId >= ownInterserviceMessages.length) throw new UnsupportedMessageTypeException(typeId);
		interserviceMessage = ownInterserviceMessages[typeId];
		interserviceMessage.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		if(type.getNum() != interserviceMessage.getTypeId()) type.setNum(interserviceMessage.getTypeId());
		type.write(byteBuf);
		interserviceMessage.write(byteBuf);
	}

	@Override
	public int length() {
		return type.length() + interserviceMessage.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { interserviceMessage };
	}

	@Override
	public String toString() {
		return String.format("InterservicePacket(type=%02X)", type.getNum());
	}
	
	public InterserviceMessage getInterserviceMessage() {
		return interserviceMessage;
	}
	
	public VersionInterserviceMessage setVersionInterserviceMessage() {
		return (VersionInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[VERSION]);
	}
	
	public LLARequestInterserviceMessage setLLARequestInterserviceMessage() {
		return (LLARequestInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[LLA_REQUEST]);
	}
	
	public LLAReplyInterserviceMessage setLLAReplyInterserviceMessage() {
		return (LLAReplyInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[LLA_REPLY]);
	}
	
	public TrustedSwitchInterserviceMessage setTrustedSwitchInterserviceMessage() {
		return (TrustedSwitchInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[TRUSTED_SWITCH]);
	}
	
	public CryptoChallengeRequestInterserviceMessage setCryptoChallengeRequestInterserviceMessage() {
		return (CryptoChallengeRequestInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[CRYPTO_CHALLENGE_REQUEST]);
	}
	
	public CryptoChallengeReplyInterserviceMessage setCryptoChallengeReplyInterserviceMessage() {
		return (CryptoChallengeReplyInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[CRYPTO_CHALLENGE_REPLY]);
	}
	
	public ConnectionbaseNoticeInterserviceMessage setConnectionbaseNoticeInterserviceMessage() {
		return (ConnectionbaseNoticeInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[CONNECTIONBASE_NOTICE]);
	}
	
	public NetworkJoinNoticeInterserviceMessage setNetworkJoinNoticeInterserviceMessage() {
		return (NetworkJoinNoticeInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[NETWORK_JOIN_NOTICE]);
	}
	
	public NetworkLeaveNoticeInterserviceMessage setNetworkLeaveNoticeInterserviceMessage() {
		return (NetworkLeaveNoticeInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[NETWORK_LEAVE_NOTICE]);
	}
	
	public IntegrationRequestInterserviceMessage setIntegrationRequestInterserviceMessage() {
		return (IntegrationRequestInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[INTEGRATION_REQUEST]);
	}
	
	public IntegrationConnectRequestInterserviceMessage setIntegrationConnectRequestInterserviceMessage() {
		return (IntegrationConnectRequestInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[INTEGRATION_CONNECT_REQUEST]);
	}
	
	public LocalLLARequestInterserviceMessage setLocalLLARequestInterserviceMessage() {
		return (LocalLLARequestInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[LOCAL_LLA_REQUEST]);
	}
	
	public LocalLLAReplyInterserviceMessage setLocalLLAReplyInterserviceMessage() {
		return (LocalLLAReplyInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[LOCAL_LLA_REPLY]);
	}
	
	public NetworkPacketInterserviceMessage setNetworkPacketInterserviceMessage() {
		return (NetworkPacketInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[NETWORK_PACKET]);
	}
	
	public ApplicationChannelSlotAssignInterserviceMessage setApplicationChannelSlotAssignInterserviceMessage() {
		return (ApplicationChannelSlotAssignInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[APPLICATION_CHANNEL_SLOT_ASSIGN]);
	}
	
	public ApplicationChannelDataInterserviceMessage setApplicationChannelDataInterserviceMessage() {
		return (ApplicationChannelDataInterserviceMessage)(this.interserviceMessage = ownInterserviceMessages[APPLICATION_CHANNEL_DATA]);
	}

}
