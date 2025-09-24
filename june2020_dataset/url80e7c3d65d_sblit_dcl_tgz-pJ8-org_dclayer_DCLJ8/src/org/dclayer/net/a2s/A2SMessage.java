package org.dclayer.net.a2s;

import org.dclayer.net.a2s.message.AddressPublicKeyMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelAcceptMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelConnectedMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelDataMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelIncomingRequestMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelOutgoingRequestMessageI;
import org.dclayer.net.a2s.message.DataMessageI;
import org.dclayer.net.a2s.message.JoinDefaultNetworksMessageI;
import org.dclayer.net.a2s.message.KeyCryptoResponseDataMessageI;
import org.dclayer.net.a2s.message.KeyDecryptDataMessageI;
import org.dclayer.net.a2s.message.KeyEncryptDataMessageI;
import org.dclayer.net.a2s.message.KeyMaxEncryptionBlockNumBytesRequestMessageI;
import org.dclayer.net.a2s.message.KeyResponseNumMessageI;
import org.dclayer.net.a2s.message.RevisionMessageI;
import org.dclayer.net.a2s.message.SlotAssignMessageI;


public abstract class A2SMessage extends A2SPacketComponent {

	public abstract byte getRevision();
	
	public abstract RevisionMessageI setRevisionMessage();
	public abstract DataMessageI setDataMessage();
	public abstract SlotAssignMessageI setSlotAssignMessage();
	public abstract AddressPublicKeyMessageI setAddressPublicKeyMessage();
	public abstract JoinDefaultNetworksMessageI setJoinDefaultNetworksMessage();
	public abstract KeyEncryptDataMessageI setKeyEncryptDataMessage();
	public abstract KeyDecryptDataMessageI setKeyDecryptDataMessage();
	public abstract KeyCryptoResponseDataMessageI setKeyCryptoResponseDataMessage();
	public abstract ApplicationChannelOutgoingRequestMessageI setApplicationChannelOutgoingRequestMessage();
	public abstract ApplicationChannelIncomingRequestMessageI setApplicationChannelIncomingRequestMessage();
	public abstract ApplicationChannelAcceptMessageI setApplicationChannelAcceptMessage();
	public abstract ApplicationChannelConnectedMessageI setApplicationChannelConnectedMessage();
	public abstract ApplicationChannelDataMessageI setApplicationChannelDataMessage();
	public abstract KeyMaxEncryptionBlockNumBytesRequestMessageI setKeyMaxEncryptionBlockNumBytesRequestMessage();
	public abstract KeyResponseNumMessageI setKeyResponseNumMessage();
	
}
