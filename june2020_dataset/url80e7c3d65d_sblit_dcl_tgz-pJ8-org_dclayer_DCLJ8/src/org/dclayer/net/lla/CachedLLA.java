package org.dclayer.net.lla;

import org.dclayer.listener.net.CachedLLAStatusListener;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.net.Data;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterservicePolicy;
import org.dclayer.net.link.Link;
import org.dclayer.net.lla.priority.LLAPriority;
import org.dclayer.net.lla.priority.LLAPriorityAspect;

/**
 * a class corresponding to a specific lower-level address and holding its assigned {@link Link} 
 */
public class CachedLLA {
	
	public static final int DISCONNECTED = 0;
	public static final int CONNECTING_PRELINK = 1;
	public static final int CONNECTING_LINK = 2;
	public static final int CONNECTING_CHANNEL = 3;
	public static final int CONNECTED = 4;
	
	public static final String[] STATUS_NAMES = new String[] {
		"disconnected",
		"connecting_prelink",
		"connecting_link",
		"connecting_channel",
		"connected"
	};
	
	//
	
	private int status = DISCONNECTED;
	private LLAPriority llaPriority = new LLAPriority(this);

	private LLA lla;
	
	/**
	 * the {@link Link} connected to this {@link CachedLLA}
	 */
	private Link link;
	
	private InterserviceChannel interserviceChannel;
	
	private InterservicePolicy interservicePolicy;
	
	/**
	 * the {@link Data} that the first link packet received from this remote is expected to be prefixed with
	 */
	private Data firstLinkPacketPrefixData;
	
	private CachedLLAStatusListener cachedLLAStatusListener;
	
	private Data punchData;
	
	public CachedLLA(LLA lla, CachedLLAStatusListener cachedLLAStatusListener) {
		this.lla = lla;
		this.cachedLLAStatusListener = cachedLLAStatusListener;
	}
	
	public synchronized int getStatus() {
		return status;
	}
	
	public synchronized void setStatus(int status) {
		int oldStatus = this.status;
		this.status = status;
		if(cachedLLAStatusListener != null) cachedLLAStatusListener.onStatusChanged(this, oldStatus, status);
	}
	
	/**
	 * sets this {@link CachedLLA}'s status to the given toStatus, if and only if the current status equals the given fromStatus.
	 * returns true if the status was fromStatus and is now toStatus
	 * @param fromStatus the expected value of the current status
	 * @param toStatus the new status value to set
	 * @return true if the operation succeeded, i.e. the status equaled fromStatus before and now equals toStatus
	 */
	public synchronized boolean changeStatus(int fromStatus, int toStatus) {
		if(this.status != fromStatus) return false;
		this.status = toStatus;
		if(cachedLLAStatusListener != null) cachedLLAStatusListener.onStatusChanged(this, fromStatus, toStatus);
		return true;
	}
	
	public boolean disconnected() {
		return this.status == DISCONNECTED;
	}
	
	public LLA getLLA() {
		return lla;
	}
	
	public void setLink(Link link) {
		this.link = link;
	}
	
	public Link getLink() {
		return link;
	}
	
	public void setInterserviceChannel(InterserviceChannel interserviceChannel) {
		this.interserviceChannel = interserviceChannel;
	}
	
	public InterserviceChannel getInterserviceChannel() {
		return interserviceChannel;
	}
	
	public void setInterservicePolicy(InterservicePolicy interservicePolicy) {
		this.interservicePolicy = interservicePolicy;
	}
	
	public InterservicePolicy getInterservicePolicy() {
		return interservicePolicy;
	}
	
	public void setFirstLinkPacketPrefixData(Data firstLinkPacketPrefixData) {
		this.firstLinkPacketPrefixData = firstLinkPacketPrefixData;
	}
	
	public Data getFirstLinkPacketPrefixData() {
		return firstLinkPacketPrefixData;
	}
	
	public LLAPriorityAspect addLLAPriorityAspect(LLAPriority.Type type, HierarchicalLevel hierarchicalLevel, String identifier) {
		return llaPriority.addLLAPriorityAspect(type, hierarchicalLevel, identifier);
	}
	
	public void setPunchData(Data punchData) {
		this.punchData = punchData;
	}
	
	public Data getPunchData() {
		return punchData;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s) (%s)", lla.toString(), STATUS_NAMES[status], llaPriority);
	}
	
}