package org.dclayer.net.link.control.packetbackup;

import org.dclayer.net.link.control.FlowControl;

public class FlowControlProperties {
	
	public PacketBackup next;
	public int priority;
	public boolean queued;
	
	/**
	 * used by {@link FlowControl} to get the priority of this {@link PacketBackup}
	 * @return the priority of this {@link PacketBackup}
	 */
	public int getPriority() {
		return priority;
	}
	
}
