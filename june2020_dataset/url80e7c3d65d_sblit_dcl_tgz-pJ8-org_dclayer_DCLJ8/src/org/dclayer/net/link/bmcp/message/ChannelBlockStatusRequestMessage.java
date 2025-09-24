package org.dclayer.net.link.bmcp.message;
import org.dclayer.net.component.ArrayPacketComponent;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

public class ChannelBlockStatusRequestMessage extends ParentPacketComponent {
	
	@Child(index = 0, create = false) public ArrayPacketComponent<FlexNum> channelIds = new ArrayPacketComponent<FlexNum>() {
		@Override
		protected FlexNum newElementPacketComponent() {
			return new FlexNum();
		}
	};
	
}
