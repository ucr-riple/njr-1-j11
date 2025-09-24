package org.dclayer.net.link.bmcp.message;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

/**
 * the throttle BMCP command component
 */
public class AckMessage extends ParentPacketComponent {
	
	@Child(index = 0) public FlexNum ackDataId;
	
}
