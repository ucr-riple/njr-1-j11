package org.dclayer.net.link.bmcp.message;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.link.bmcp.crypto.init.component.CryptoInitializerIdentifierComponent;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

public class ConnectReplyMessage extends ParentPacketComponent {
	
	@Child(index = 0) public CryptoInitializerIdentifierComponent cryptoInitializerIdentifierComponent;
	@Child(index = 1) public FlexNum unreliableChannelId;
	
}
