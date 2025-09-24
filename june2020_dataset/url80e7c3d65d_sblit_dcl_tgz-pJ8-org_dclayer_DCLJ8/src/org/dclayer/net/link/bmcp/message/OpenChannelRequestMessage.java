package org.dclayer.net.link.bmcp.message;

import org.dclayer.net.component.FlexNum;
import org.dclayer.net.component.StringComponent;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

public class OpenChannelRequestMessage extends ParentPacketComponent {
	
	@Child(index = 0) public FlexNum channelId;
	@Child(index = 1) public StringComponent protocol;

}
