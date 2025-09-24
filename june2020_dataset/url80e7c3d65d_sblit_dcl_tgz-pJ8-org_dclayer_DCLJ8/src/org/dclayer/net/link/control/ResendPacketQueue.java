package org.dclayer.net.link.control;

import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.link.channel.Channel;
import org.dclayer.net.link.control.packetbackup.PacketBackup;
import org.dclayer.net.link.control.packetbackup.ResendPacketQueueProperties;

/**
 * a collection for {@link PacketBackup} objects that
 * never blocks when queueing new objects, but
 * when retrieving an object blocks until that object timed out
 */
public class ResendPacketQueue implements HierarchicalLevel {
	
	/**
	 * the {@link Channel} this {@link ResendPacketQueue} belongs to
	 */
	private Channel channel;
	
	/**
	 * the first {@link ResendPacketQueueProperties} (i.e. the first to time out)
	 */
	private ResendPacketQueueProperties firstProperties = null;
	/**
	 * the last {@link ResendPacketQueueProperties} (i.e. the last to time out)
	 */
	private ResendPacketQueueProperties lastProperties = null;
	
	private long lastInvalidPacketBackup = 0;
	
	public ResendPacketQueue(Channel channel) {
		this.channel = channel;
	}
	
	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return channel;
	}
	
	@Override
	public String toString() {
		return "ResendPacketQueue";
	}
	
	/**
	 * queues a {@link PacketBackup}.<br />
	 * this never blocks.
	 * @param packetBackup the packet backup to queue
	 */
	// called by Link upon sending a packet
	public synchronized void queue(PacketBackup packetBackup) {
		
		Log.debug(this, "queuing: %s", packetBackup);
		
		ResendPacketQueueProperties properties = packetBackup.getResendPacketQueueProperties();
		properties.removeFromResendPacketQueueOnUnUsed(this);
		
		if(firstProperties == null) {
			
			// adding the first entry to the queue
			firstProperties = lastProperties = properties;
			properties.next = properties.prior = null;
			this.notify(); // notify the probably-waiting maintenance thread that there now is an item
			
		} else if(lastProperties.getNextResendTime() <= properties.getNextResendTime()) {
			
			// this is resent later than or at the same time as the last item in queue; add it to the end
			lastProperties.next = properties;
			properties.prior = lastProperties;
			properties.next = null;
			lastProperties = properties;
			
		} else if(firstProperties.getNextResendTime() >= properties.getNextResendTime()) {
			
			// this is resent before or at the same time as the first item in queue; add it as new first item
			firstProperties.prior = properties;
			properties.next = firstProperties;
			properties.prior = null;
			firstProperties = properties;
			
		} else {
			
			// this needs to be added somewhere inbetween; find its space, searching backwards
			ResendPacketQueueProperties curProperties = lastProperties.prior; // start with the 2nd-last item (other case would have been covered by if-block above)
			long nextResendTime = packetBackup.getResendPacketQueueProperties().getNextResendTime();
			while(curProperties != null) {
				if(curProperties.getNextResendTime() <= nextResendTime) {
					// add it after curPacketBackup
					properties.next = curProperties.next;
					properties.prior = curProperties;
					curProperties.next.prior = properties; // curPacketBackup.next can not be null (we start with lastPacketBackup.prior)
					curProperties.next = properties;
					break;
				}
				curProperties = curProperties.prior;
			}
			
		}
		
	}
	
	/**
	 * removes the given {@link PacketBackup} from the queue.<br />
	 * this never blocks.
	 * @param packetBackup the packet backup to remove
	 */
	// called by PacketBackupCollection upon removal of this PacketBackup
	public synchronized void remove(PacketBackup packetBackup) {
		
		ResendPacketQueueProperties properties = packetBackup.getResendPacketQueueProperties();
		
		if(properties == firstProperties) {
			
			// removing the first item
			firstProperties = properties.next;
			if(firstProperties == null) lastProperties = null;
			else firstProperties.prior = null;
			this.notify(); // notify the probably-waiting maintenance thread that the item it is waiting for is now gone
			
		} else if(properties == lastProperties) {
			
			lastProperties = properties.prior;
			lastProperties.next = null;
			
		} else {
			
			if(properties.prior != null) properties.prior.next = properties.next;
			if(properties.next != null) properties.next.prior = properties.prior;
			
		}
		
	}
	
	/**
	 * waits for the next {@link PacketBackup} to time out and returns it afterwards
	 * @return the {@link PacketBackup} that first times out
	 */
	public synchronized PacketBackup waitAndPopNext() {
		
		for(;;) {
			
			while(firstProperties == null) {
				// queue is empty, wait
				Log.debug(this, "queue is empty, waiting...");
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					return null;
				}
			}
			
			ResendPacketQueueProperties properties = firstProperties;
			PacketBackup packetBackup = properties.getPacketBackup();
			long dataId = packetBackup.getPacketProperties().dataId;
			long nextResendTime = packetBackup.getResendPacketQueueProperties().getNextResendTime();
			long now = System.nanoTime()/1000000L;
			long diff = nextResendTime - now;
			
			Log.debug(this, "waiting %d millis (%s)", diff, packetBackup);
			if(diff > 0) {
				try {
					this.wait(diff);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return null;
				}
			}
			
			if(properties == firstProperties) {
				// the backup we waited for is still the first; pop it off
				firstProperties = firstProperties.next;
				if(firstProperties == null) lastProperties = null; // queue is now empty, also set last item null
				else firstProperties.prior = null;
				if(packetBackup.getUsed() && packetBackup.getPacketProperties().dataId == dataId) {
					// this is still the exact same PacketBackup
					return packetBackup;
				} else {
					// this PacketBackup has been invalidated meanwhile
					Log.debug(this, "PacketBackup we waited for is invalid (packetBackup.getUsed()=%s, packetBackup's dataId=%d, expected dataId=%d)", packetBackup.getUsed(), packetBackup.getPacketProperties().dataId, dataId);
				}
			} else {
				Log.debug(this, "PacketBackup we waited for is no longer present");
			}
			
			lastInvalidPacketBackup = now;
			
		}
		
	}
	
	public boolean timeout() {
		return lastInvalidPacketBackup != 0 && ((System.nanoTime()/1000000L) - lastInvalidPacketBackup) > 5000;
	}
	
}
