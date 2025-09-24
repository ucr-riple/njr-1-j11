package org.dclayer.net.link.bmcp.message;
import org.dclayer.net.component.ArrayPacketComponent;
import org.dclayer.net.link.bmcp.component.ChannelReportComponent;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

public class ChannelBlockStatusReportMessage extends ParentPacketComponent {
	
	@Child(index = 0, create = false) public ArrayPacketComponent<ChannelReportComponent> channelReports = new ArrayPacketComponent<ChannelReportComponent>() {
		@Override
		protected ChannelReportComponent newElementPacketComponent() {
			return new ChannelReportComponent();
		}
	};
	
}
