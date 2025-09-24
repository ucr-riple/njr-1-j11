package org.dclayer.net.link.control.packetbackup;

import org.dclayer.meta.Log;
import org.dclayer.net.link.channel.Channel;
import org.dclayer.net.link.control.ResendPacketQueue;

public class ResendPacketQueueProperties extends Properties {
	
	/**
	 * the {@link ResendPacketQueue} to add this {@link PacketBackup} to as soon as it is sent,
	 * or the {@link ResendPacketQueue} to remove this {@link PacketBackup} from as soon as it is set unused
	 */
	private ResendPacketQueue resendPacketQueue = null;

	// set by ResendPacketQueue
	/**
	 * used by {@link ResendPacketQueue}: the next {@link ResendPacketQueueProperties} object to time out after this one
	 */
	public ResendPacketQueueProperties prior = null;
	/**
	 * used by {@link ResendPacketQueue}: the {@link ResendPacketQueueProperties} object to time out before this one
	 */
	public ResendPacketQueueProperties next = null;

	// used by ResendPacketQueue
	/**
	 * used by the packet resend mechanism: the amount of times this {@link PacketBackup} has been resent
	 */
	private int resentCount = 0;
	/**
	 * used by the packet resend mechanism: the maximum amount of times this {@link PacketBackup} should be resent
	 */
	private int maxAttempts = 0;
	/**
	 * used by the packet resend mechanism: the {@link System#nanoTime()} value of the time this {@link PacketBackup} will be resent
	 */
	private long nextResendTime = 0;
	/**
	 * used by the packet resend mechanism: the last resend delay value that was used
	 */
	private long delay = 0;
	/**
	 * used by the packet resend mechanism: the factor that the resend delay values are multiplied with each time this {@link PacketBackup} is resent 
	 */
	private double delayFactor = 1;
	
	public ResendPacketQueueProperties(PacketBackup packetBackup) {
		super(packetBackup);
	}
	
	public void reset() {
		this.resentCount = 0;
		this.nextResendTime = 0;
	}
	
	/**
	 * sets the resend parameters of this {@link PacketBackup}
	 * @param now the current {@link System#nanoTime()} value
	 * @param initialDelay the delay for the first resend attempt
	 * @param delayFactor the factor future delays will be multiplied with
	 * @param maxAttempts the maximum number of resend attempts
	 * @param resendPacketQueue the {@link ResendPacketQueue} to add this {@link PacketBackup} to as soon as it is sent
	 */
	// called additionally when added to ResendPacketQueue
	public void setResend(long now, long initialDelay, double delayFactor, int maxAttempts, ResendPacketQueue resendPacketQueue) {
		this.resentCount = 0;
		this.nextResendTime = now + initialDelay;
		this.delay = initialDelay;
		this.delayFactor = delayFactor;
		this.maxAttempts = maxAttempts;
		this.resendPacketQueue = resendPacketQueue;
//		System.out.println(String.format("PacketBackup(%s).setResend()", this.getPacketBackup()));
	}
	
	/**
	 * @return the {@link System#nanoTime()} value of the time this {@link PacketBackup} will be resent
	 */
	// called by ResendPacketQueue when this is next in order
	public long getNextResendTime() {
		return nextResendTime;
	}
	
	/**
	 * returns true if the packet stored in this {@link PacketBackup} should be resent once more, false otherwise.<br />
	 * this also updates the number of resends and the next resend time.
	 * @param channel the {@link Channel} this packet is being resent on (used for debug output purposes only)
	 * @return true if this packet should be resent, false otherwise
	 */
	// called by ResendPacketQueue when deciding whether or not to resend this packet
	public boolean resend(Channel channel) {
		resentCount++;
		nextResendTime += (delay * Math.pow(delayFactor, resentCount));
		boolean resend = maxAttempts <= 0 || resentCount <= maxAttempts;
		Log.debug(channel, "PacketBackup.resend(): %s: resentCount=%d, nextResendTime=%d (added %f), returning %s", this.getPacketBackup(), resentCount, nextResendTime, (delay * Math.pow(delayFactor, resentCount)), resend);
		return resend;
	}
	
	public void onUsedChange(boolean used) {
//		System.out.println(String.format("PacketBackup(%s).onUsedChange(used=%s)", this.getPacketBackup(), used));
		if(used == false && resendPacketQueue != null) {
			resendPacketQueue.remove(getPacketBackup());
			resendPacketQueue = null;
		}
	}
	
	public void removeFromResendPacketQueueOnUnUsed(ResendPacketQueue resendPacketQueue) {
//		System.out.println(String.format("PacketBackup(%s).removeFromResendPacketQueueOnUnUsed(resendPacketQueue=%s)", this.getPacketBackup(), resendPacketQueue));
		this.resendPacketQueue = resendPacketQueue;
	}
	
	public void onSent() {
//		System.out.println(String.format("PacketBackup(%s).onSent(): resendPacketQueue=%s", this.getPacketBackup(), resendPacketQueue));
		if(resendPacketQueue != null) {
			resendPacketQueue.queue(getPacketBackup());
		}
	}
}
