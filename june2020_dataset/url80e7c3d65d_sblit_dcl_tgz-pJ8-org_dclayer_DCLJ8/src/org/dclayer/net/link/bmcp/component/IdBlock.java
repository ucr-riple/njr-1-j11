package org.dclayer.net.link.bmcp.component;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

/**
 * a part of different BMCP commands, describing a block/range of data ids
 */
public class IdBlock extends ParentPacketComponent {
	
	@Child(index = 0) public FlexNum startId;
	@Child(index = 1) public FlexNum innerSize;
	
}
