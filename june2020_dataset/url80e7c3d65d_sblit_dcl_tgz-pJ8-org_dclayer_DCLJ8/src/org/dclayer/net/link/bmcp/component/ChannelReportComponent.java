package org.dclayer.net.link.bmcp.component;
import org.dclayer.net.component.ArrayPacketComponent;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

/**
 * a block status report for a single channel
 */
public class ChannelReportComponent extends ParentPacketComponent {
	
	@Child(index = 0) public FlexNum channelId;
	@Child(index = 1) public FlexNum lowestDataId = new FlexNum();
	@Child(index = 2) public FlexNum highestDataId = new FlexNum();
	@Child(index = 3) public FlexNum numDataIds = new FlexNum();
	
	@Child(index = 4, create = false) public ArrayPacketComponent<FlexNum> missingSingleIds = new ArrayPacketComponent<FlexNum>() {
		@Override
		protected FlexNum newElementPacketComponent() {
			return new FlexNum();
		}
	};
	
	@Child(index = 5, create = false) public ArrayPacketComponent<IdBlock> missingIdBlocks = new ArrayPacketComponent<IdBlock>() {
		@Override
		protected IdBlock newElementPacketComponent() {
			return new IdBlock();
		}
	};
	
	public String toString() {
		return String.format("ChannelReportComponent(channelId=%d, lowestDataId=%d, highestDataId=%d, numDataIds=%d, numMissingSingleIds=%d, numMissingIdBlocks=%d)",
				channelId.getNum(),
				lowestDataId.getNum(),
				highestDataId.getNum(),
				numDataIds.getNum(),
				missingSingleIds.getNumElements(),
				missingIdBlocks.getNumElements());
	}

}
