package org.dclayer.net.a2s.rev35;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedMessageTypeException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessage;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.AddressPublicKeyMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelAcceptMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelConnectedMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelDataMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelIncomingRequestMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelOutgoingRequestMessageI;
import org.dclayer.net.a2s.message.JoinDefaultNetworksMessageI;
import org.dclayer.net.a2s.message.KeyCryptoResponseDataMessageI;
import org.dclayer.net.a2s.message.KeyDecryptDataMessageI;
import org.dclayer.net.a2s.message.KeyEncryptDataMessageI;
import org.dclayer.net.a2s.message.KeyMaxEncryptionBlockNumBytesRequestMessageI;
import org.dclayer.net.a2s.message.KeyResponseNumMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.a2s.rev35.message.DataMessage;
import org.dclayer.net.a2s.rev35.message.GenerateKeyMessage;
import org.dclayer.net.a2s.rev35.message.JoinNetworkMessage;
import org.dclayer.net.a2s.rev35.message.RevisionMessage;
import org.dclayer.net.a2s.rev35.message.SlotAssignMessage;
import org.dclayer.net.buf.ByteBuf;

/**
 * a message of revision 35
 */
public class Rev35Message extends A2SMessage {

	public static final int REVISION_BINARY = Rev0Message.REVISION; // 0
	public static final int DATA = 0xFF & (byte)'D';
	public static final int GENERATE_KEY = 0xFF & (byte)'G';
	public static final int JOIN_NETWORK = 0xFF & (byte)'J';
	public static final int REVISION = 0xFF & (byte)'R';
	public static final int SLOT_ASSIGN = 0xFF & (byte)'S';

	private A2SRevisionSpecificMessage message;
	
	private A2SRevisionSpecificMessage[] messages = new A2SRevisionSpecificMessage[((int)SLOT_ASSIGN)+1]; {
		messages[REVISION_BINARY] = new org.dclayer.net.a2s.rev0.message.RevisionMessage();
		messages[DATA] = new DataMessage();
		messages[GENERATE_KEY] = new GenerateKeyMessage();
		messages[JOIN_NETWORK] = new JoinNetworkMessage();
		messages[REVISION] = new RevisionMessage();
		messages[SLOT_ASSIGN] = new SlotAssignMessage();
	}

	@Override
	public byte getRevision() {
		return 35;
	}
	
	/**
	 * returns the {@link A2SRevisionSpecificMessage} this revision 35 {@link Rev35Message} contains
	 * @return the {@link A2SRevisionSpecificMessage} this revision 35 {@link Rev35Message} contains
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
		if(message.getMessageRevision() == 35) byteBuf.readTilSpaceOrEOL();
		message.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.write(message.getType());
		byteBuf.write((byte)' ');
		message.write(byteBuf);
		byteBuf.write((byte)'\n');
	}

	@Override
	public int length() {
		return 3 + message.length();
	}

	@Override
	public String toString() {
		return String.format("Rev35Message(type=%02X)", message.getType());
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { message };
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		message.callOnReceiveMethod(a2sMessageReceiver);
	}
	
	public RevisionMessage setRevisionMessage() {
		return (RevisionMessage)(this.message = messages[REVISION]);
	}
	
	public DataMessage setDataMessage() {
		return (DataMessage)(this.message = messages[DATA]);
	}
	
	public SlotAssignMessage setSlotAssignMessage() {
		return (SlotAssignMessage)(this.message = messages[SLOT_ASSIGN]);
	}

	@Override
	public AddressPublicKeyMessageI setAddressPublicKeyMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JoinDefaultNetworksMessageI setJoinDefaultNetworksMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyEncryptDataMessageI setKeyEncryptDataMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyDecryptDataMessageI setKeyDecryptDataMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyCryptoResponseDataMessageI setKeyCryptoResponseDataMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationChannelOutgoingRequestMessageI setApplicationChannelOutgoingRequestMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationChannelIncomingRequestMessageI setApplicationChannelIncomingRequestMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationChannelAcceptMessageI setApplicationChannelAcceptMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationChannelConnectedMessageI setApplicationChannelConnectedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationChannelDataMessageI setApplicationChannelDataMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyMaxEncryptionBlockNumBytesRequestMessageI setKeyMaxEncryptionBlockNumBytesRequestMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyResponseNumMessageI setKeyResponseNumMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
