package org.dclayer.net.link.bmcp.message;
import org.dclayer.net.component.StringComponent;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

public class ChangeProtocolRequestMessage extends ParentPacketComponent {
	
	@Child(index = 0) StringComponent protocol;
	
}
