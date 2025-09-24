package org.dclayer.net.link.control.packetbackup;
import org.dclayer.net.link.control.FlowControl;
import org.dclayer.net.link.control.discontinuousblock.DiscontinuousBlock;

/**
 * this is the sent counterpart to a {@link DiscontinuousBlock}.<br />
 * stores one sent packet.
 */
public class PacketBackup {
	
	private boolean used = false;
	
	/**
	 * Properties of this {@link PacketBackup} used by the {@link FlowControl} mechanism
	 */
	private final FlowControlProperties flowControlProperties = new FlowControlProperties();
	/**
	 * Properties of this {@link PacketBackup} used by the {@link PacketResendQueue}
	 */
	private final ResendPacketQueueProperties resendPacketQueueProperties = new ResendPacketQueueProperties(this);
	/**
	 * Properties of the packet contained in this {@link PacketBackup} itself 
	 */
	private final PacketProperties packetProperties = new PacketProperties();
	private final PacketBackupCollectionProperties packetBackupCollectionProperties = new PacketBackupCollectionProperties(this);
	
	public FlowControlProperties getFlowControlProperties() {
		return flowControlProperties;
	}
	
	public ResendPacketQueueProperties getResendPacketQueueProperties() {
		return resendPacketQueueProperties;
	}
	
	public PacketProperties getPacketProperties() {
		return packetProperties;
	}
	
	/**
	 * this is called when the {@link PacketBackupCollection} this object is contained in changes
	 * its used value
	 */
	/*// called by PacketBackupCollection upon change of the .used value
	public synchronized void onUsedChanged() {
		if(!used) {
			if(resendPacketQueue != null) {
				resendPacketQueue.remove(this);
			}
		}
	}*/
	
	public void setUsed(boolean used) {
		resendPacketQueueProperties.onUsedChange(used);
		this.used = used;
	}
	
	public boolean getUsed() {
		return used;
	}
	
	/**
	 * resets the values of this {@link PacketBackup}
	 * @param dataId the data id of the packet that will be stored
	 * @param channelId the channel id of the packet that will be stored
	 * @param priority the {@link FlowControl} priority of this {@link PacketBackup}
	 */
	// called when added to PacketBackupCollection and ResendPacketQueue
	public void reset(long dataId, long channelId, int priority) {
		
		resendPacketQueueProperties.reset();
		packetProperties.reset(dataId, channelId);
		flowControlProperties.priority = priority;
		packetBackupCollectionProperties.reset();
		
	}
	
	/**
	 * callback for when this {@link PacketBackup} leaves the flow control queue and is finally sent
	 */
	public void onSent() {
		resendPacketQueueProperties.onSent();
		packetBackupCollectionProperties.onSent();
	}
	
	@Override
	public String toString() {
		return String.format("PacketBackup(dataId=%d, channelId=%d, priority=%d, numBytes=%d)", 
				packetProperties.dataId,
				packetProperties.channelId,
				flowControlProperties.priority,
				packetProperties.data.length());
	}
	
}
