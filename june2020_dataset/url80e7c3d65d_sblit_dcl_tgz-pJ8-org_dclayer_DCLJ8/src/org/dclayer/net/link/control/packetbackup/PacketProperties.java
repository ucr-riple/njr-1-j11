package org.dclayer.net.link.control.packetbackup;

import org.dclayer.net.Data;

public class PacketProperties {
	
	// used by both PacketBackupCollection and ResendPacketQueue
	/**
	 * the data of the packet this stores
	 */
	public Data data = new Data();
	/**
	 * the data id of the packet this stores
	 */
	public long dataId;
	/**
	 * the channel id of the stored packet
	 */
	public long channelId;
	/**
	 * true if the packet is ready for transmission, false otherwise
	 */
	public boolean ready;
	
	/**
	 * resets the packet properties to the supplied values
	 * @param dataId the data id to use
	 * @param channelId the channel id to use
	 */
	public void reset(long dataId, long channelId) {
		this.dataId = dataId;
		this.channelId = channelId;
		this.ready = false;
	}
	
}
