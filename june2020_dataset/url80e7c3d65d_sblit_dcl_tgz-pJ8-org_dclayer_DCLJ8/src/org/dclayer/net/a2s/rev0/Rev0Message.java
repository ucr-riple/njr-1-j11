package org.dclayer.net.a2s.rev0;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedMessageTypeException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessage;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.KeyResponseNumMessageI;
import org.dclayer.net.a2s.rev0.message.AddressPublicKeyMessage;
import org.dclayer.net.a2s.rev0.message.ApplicationChannelAcceptMessage;
import org.dclayer.net.a2s.rev0.message.ApplicationChannelConnectedMessage;
import org.dclayer.net.a2s.rev0.message.ApplicationChannelDataMessage;
import org.dclayer.net.a2s.rev0.message.ApplicationChannelIncomingRequestMessage;
import org.dclayer.net.a2s.rev0.message.ApplicationChannelOutgoingRequestMessage;
import org.dclayer.net.a2s.rev0.message.DataMessage;
import org.dclayer.net.a2s.rev0.message.GenerateKeyMessage;
import org.dclayer.net.a2s.rev0.message.JoinDefaultNetworksMessage;
import org.dclayer.net.a2s.rev0.message.JoinNetworkMessage;
import org.dclayer.net.a2s.rev0.message.KeyCryptoResponseDataMessage;
import org.dclayer.net.a2s.rev0.message.KeyDecryptDataMessage;
import org.dclayer.net.a2s.rev0.message.KeyEncryptDataMessage;
import org.dclayer.net.a2s.rev0.message.KeyMaxEncryptionBlockNumBytesRequestMessage;
import org.dclayer.net.a2s.rev0.message.KeyResponseNumMessage;
import org.dclayer.net.a2s.rev0.message.RevisionMessage;
import org.dclayer.net.a2s.rev0.message.SlotAssignMessage;
import org.dclayer.net.buf.ByteBuf;

/**
 * a message of revision 35
 */
public class Rev0Message extends A2SMessage {

	public static final int REVISION = 0;
	public static final int GENERATE_KEY = 1;
	public static final int JOIN_NETWORK = 2;
	public static final int SLOT_ASSIGN = 3;
	public static final int DATA = 4;
	public static final int ADDRESS_PUBLIC_KEY = 5;
	public static final int JOIN_DEFAULT_NETWORKS = 6;
	public static final int KEY_ENCRYPT_DATA = 7;
	public static final int KEY_DECRYPT_DATA = 8;
	public static final int KEY_CRYPTO_RESPONSE_DATA = 9;
	public static final int APPLICATION_CHANNEL_OUTGOING_REQUEST = 10;
	public static final int APPLICATION_CHANNEL_INCOMING_REQUEST = 11;
	public static final int APPLICATION_CHANNEL_ACCEPT = 12;
	public static final int APPLICATION_CHANNEL_CONNECTED = 13;
	public static final int APPLICATION_CHANNEL_DATA = 14;
	public static final int KEY_MAX_ENCRYPTION_BLOCK_NUM_BYTES_REQUEST = 15;
	public static final int KEY_RESPONSE_NUM = 16;

	private A2SRevisionSpecificMessage message;
	
	private A2SRevisionSpecificMessage[] messages = new A2SRevisionSpecificMessage[] {
		new RevisionMessage(),
		new GenerateKeyMessage(),
		new JoinNetworkMessage(),
		new SlotAssignMessage(),
		new DataMessage(),
		new AddressPublicKeyMessage(),
		new JoinDefaultNetworksMessage(),
		new KeyEncryptDataMessage(),
		new KeyDecryptDataMessage(),
		new KeyCryptoResponseDataMessage(),
		new ApplicationChannelOutgoingRequestMessage(),
		new ApplicationChannelIncomingRequestMessage(),
		new ApplicationChannelAcceptMessage(),
		new ApplicationChannelConnectedMessage(),
		new ApplicationChannelDataMessage(),
		new KeyMaxEncryptionBlockNumBytesRequestMessage()
	};

	@Override
	public byte getRevision() {
		return 0;
	}
	
	/**
	 * returns the {@link A2SRevisionSpecificMessage} this revision 35 {@link Rev0Message} contains
	 * @return the {@link A2SRevisionSpecificMessage} this revision 35 {@link Rev0Message} contains
	 */
	public A2SRevisionSpecificMessage getMessage() {
		return message;
	}
	
	/**
	 * returns the type of the contained {@link A2SRevisionSpecificMessage}
	 * @return the type of the contained {@link A2SRevisionSpecificMessage}
	 */
	public byte getType() {
		return message.getType();
	}

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		int type = 0xFF & byteBuf.read();
		if(type < messages.length) {
			message = messages[type];
		} else {
			throw new UnsupportedMessageTypeException(type);
		}
		message.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.write(message.getType());
		message.write(byteBuf);
	}

	@Override
	public int length() {
		return 1 + message.length();
	}

	@Override
	public String toString() {
		return String.format("Rev0Message(type=%02X)", message.getType());
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { message };
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		message.callOnReceiveMethod(a2sMessageReceiver);
	}
	
	@Override
	public RevisionMessage setRevisionMessage() {
		return (RevisionMessage)(this.message = messages[REVISION]);
	}
	
	@Override
	public DataMessage setDataMessage() {
		return (DataMessage)(this.message = messages[DATA]);
	}
	
	@Override
	public SlotAssignMessage setSlotAssignMessage() {
		return (SlotAssignMessage)(this.message = messages[SLOT_ASSIGN]);
	}

	@Override
	public AddressPublicKeyMessage setAddressPublicKeyMessage() {
		return (AddressPublicKeyMessage)(this.message = messages[ADDRESS_PUBLIC_KEY]);
	}

	@Override
	public JoinDefaultNetworksMessage setJoinDefaultNetworksMessage() {
		return (JoinDefaultNetworksMessage)(this.message = messages[JOIN_DEFAULT_NETWORKS]);
	}

	@Override
	public KeyEncryptDataMessage setKeyEncryptDataMessage() {
		return (KeyEncryptDataMessage)(this.message = messages[KEY_ENCRYPT_DATA]);
	}
	
	@Override
	public KeyDecryptDataMessage setKeyDecryptDataMessage() {
		return (KeyDecryptDataMessage)(this.message = messages[KEY_DECRYPT_DATA]);
	}

	@Override
	public KeyCryptoResponseDataMessage setKeyCryptoResponseDataMessage() {
		return (KeyCryptoResponseDataMessage)(this.message = messages[KEY_CRYPTO_RESPONSE_DATA]);
	}

	@Override
	public ApplicationChannelOutgoingRequestMessage setApplicationChannelOutgoingRequestMessage() {
		return (ApplicationChannelOutgoingRequestMessage)(this.message = messages[APPLICATION_CHANNEL_OUTGOING_REQUEST]);
	}

	@Override
	public ApplicationChannelIncomingRequestMessage setApplicationChannelIncomingRequestMessage() {
		return (ApplicationChannelIncomingRequestMessage)(this.message = messages[APPLICATION_CHANNEL_INCOMING_REQUEST]);
	}

	@Override
	public ApplicationChannelAcceptMessage setApplicationChannelAcceptMessage() {
		return (ApplicationChannelAcceptMessage)(this.message = messages[APPLICATION_CHANNEL_ACCEPT]);
	}

	@Override
	public ApplicationChannelConnectedMessage setApplicationChannelConnectedMessage() {
		return (ApplicationChannelConnectedMessage)(this.message = messages[APPLICATION_CHANNEL_CONNECTED]);
	}
	
	@Override
	public ApplicationChannelDataMessage setApplicationChannelDataMessage() {
		return (ApplicationChannelDataMessage)(this.message = messages[APPLICATION_CHANNEL_DATA]);
	}
	
	@Override
	public KeyMaxEncryptionBlockNumBytesRequestMessage setKeyMaxEncryptionBlockNumBytesRequestMessage() {
		return (KeyMaxEncryptionBlockNumBytesRequestMessage)(this.message = messages[KEY_MAX_ENCRYPTION_BLOCK_NUM_BYTES_REQUEST]);
	}

	@Override
	public KeyResponseNumMessageI setKeyResponseNumMessage() {
		return (KeyResponseNumMessage)(this.message = messages[KEY_RESPONSE_NUM]);
	}

}
