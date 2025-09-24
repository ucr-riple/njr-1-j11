package org.dclayer.net.link.control;

import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.link.Link;
import org.dclayer.net.link.LinkSendInterface;
import org.dclayer.net.link.channel.data.DataChannel;
import org.dclayer.net.link.channel.management.ManagementChannel;
import org.dclayer.net.link.control.packetbackup.FlowControlProperties;
import org.dclayer.net.link.control.packetbackup.PacketBackup;

/**
 * Helps finding an efficient rate at which data is sent over {@link Link}s
 * @author Martin Exner
 *
 */
public class FlowControl extends Thread implements HierarchicalLevel {
	
	/**
	 * Priority for {@link ManagementChannel} packets
	 */
	public static final int PRIO_MGMT = 0;
	/**
	 * Priority for normal (i.e. {@link DataChannel}) packets being resent
	 */
	public static final int PRIO_RESEND = 1;
	/**
	 * Priority for normal (i.e. {@link DataChannel}) packets
	 */
	public static final int PRIO_DATA = 2;
	
	/**
	 * The number of different priority values
	 */
	private static final int NUMPRIO = 3;
	
	/**
	 * The current rate at which data is sent over the {@link Link}
	 */
	private long currentBytesPerSecond = 0;
	
	/**
	 * the total amount of bytes sent so far
	 */
	private long numBytesSent = 0;
	
	/**
	 * The next {@link PacketBackup} to send in each priority's queue
	 */
	private PacketBackup[] firstPacketBackup = new PacketBackup[NUMPRIO];
	
	/**
	 * The last {@link PacketBackup} to send in each priority's queue
	 */
	private PacketBackup[] lastPacketBackup = new PacketBackup[NUMPRIO];
	
	/**
	 * The {@link Link} this {@link FlowControl} instance belongs to
	 */
	private Link link;
	
	@Override
	public String toString() {
		return "FlowControl";
	}
	
	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return link;
	}
	
	/**
	 * Creates a new {@link FlowControl} instance, sending {@link PacketBackup}s over the given {@link LinkSendInterface}
	 * @param linkSendInterface the {@link LinkSendInterface} for sending {@link PacketBackup}s
	 */
	public FlowControl(Link link) {
		this.link = link;
		this.start();
	}
	
	/**
	 * Sets the rate at which data is sent over the {@link Link}
	 * @param bytesPerSecond the rate at which data is sent over the {@link Link}
	 */
	public void setBytesPerSecond(long bytesPerSecond) {
		this.currentBytesPerSecond = bytesPerSecond;
	}
	
	/**
	 * @return the current maximum rate at which data can be sent over the {@link Link}
	 */
	public long getBytesPerSecond() {
		return currentBytesPerSecond;
	}
	
	/**
	 * @return the total amount of bytes sent so far
	 */
	public long getNumBytesSent() {
		return numBytesSent;
	}
	
	@Override
	public void run() {
		for(;;) {
			PacketBackup packetBackup = null;
			
			synchronized(this) {
				
				for(int i = 0; i < firstPacketBackup.length; i++) {
					packetBackup = firstPacketBackup[i];
					if(packetBackup != null) {
						firstPacketBackup[i] = packetBackup.getFlowControlProperties().next;
						if(firstPacketBackup[i] == null) lastPacketBackup[i] = null;
						break;
					}
				}
				
				if(packetBackup == null) {
					try {
						this.wait();
					} catch (InterruptedException e) {}
					continue;
				}
				
			}
			
			int length = sendPacketBackup(packetBackup);
			
			numBytesSent += length;
			
			if(currentBytesPerSecond > 0) {
				
				// let transmission rate increase slowly (here: approx. 100KB/s per second when transmitting at 1MB/s)
				currentBytesPerSecond += Math.max(1, length/10);
					
				long nanos = (1000000000L * length) / currentBytesPerSecond;
				try {
					Thread.sleep(nanos/1000000, (int)(nanos%1000000));
				} catch (InterruptedException e) {}
				
			}
			
		}
	}
	
	/**
	 * sends the given {@link PacketBackup}, returning the length of its data in bytes
	 * @param packetBackup
	 * @return
	 */
	private int sendPacketBackup(PacketBackup packetBackup) {
		Data data = packetBackup.getPacketProperties().data;
		link.transmitNow(data);
		packetBackup.onSent();
		synchronized(packetBackup.getFlowControlProperties()) {
			packetBackup.getFlowControlProperties().queued = false;
			packetBackup.getFlowControlProperties().notify();
		}
		return data.length();
	}
	
	/**
	 * Queues the given {@link PacketBackup}, sending it when the {@link Link} is ready
	 * @param packetBackup the {@link PacketBackup} to queue for transmission
	 * @param waitTilSent pass true if this operation should block until the {@link PacketBackup} is actually sent, pass false otherwise
	 */
	public void queue(PacketBackup packetBackup, boolean waitTilSent) {
		queue(packetBackup, packetBackup.getFlowControlProperties().priority, waitTilSent);
	}
	
	/**
	 * Queues the given {@link PacketBackup}, sending it when the {@link Link} is ready
	 * @param packetBackup the {@link PacketBackup} to queue for transmission
	 * @param priority the priority of the transmission of the given {@link PacketBackup}
	 * @param waitTilSent pass true if this operation should block until the {@link PacketBackup} is actually sent, pass false otherwise
	 */
	private void queue(PacketBackup packetBackup, int priority, boolean waitTilSent) {
		FlowControlProperties flowControlProperties = packetBackup.getFlowControlProperties();
		synchronized(flowControlProperties) {
			if(flowControlProperties.queued) {
				Log.debug(this, "not queueing PacketBackup (already queued): %s", packetBackup.toString());
				return;
			}
			flowControlProperties.queued = true;
			synchronized(this) {
				Log.debug(this, "queueing PacketBackup: %s", packetBackup.toString());
				flowControlProperties.next = null;
				PacketBackup last = this.lastPacketBackup[priority];
				if(last == null) {
					firstPacketBackup[priority] = lastPacketBackup[priority] = packetBackup;
					this.notify();
				} else {
					lastPacketBackup[priority].getFlowControlProperties().next = packetBackup;
					lastPacketBackup[priority] = packetBackup;
				}
			}
			if(waitTilSent) {
				try {
					packetBackup.getFlowControlProperties().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
