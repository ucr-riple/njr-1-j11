package org.dclayer.net.link.control.packetbackup;

import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.link.channel.Channel;

/**
 * a collection of {@link PacketBackup} objects used for sending unreliable packets
 */
public class UnreliablePacketBackupCollection implements HierarchicalLevel {
	
	/**
	 * array containing all available {@link PacketBackup} instances
	 */
	private PacketBackup[] backups;
	/**
	 * the index indicating the start of the ring buffer
	 */
	private int startIndex = 0;
	/**
	 * the amount of currently occupied {@link PacketBackup}s in this {@link UnreliablePacketBackupCollection}
	 */
	private int numUsed = 0;
	
	/**
	 * the {@link Channel} this {@link UnreliablePacketBackupCollection} belongs to
	 */
	private Channel channel;
	
	public UnreliablePacketBackupCollection(Channel channel, int size) {
		this.channel = channel;
		this.backups = new PacketBackup[size];
	}
	
	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return channel;
	}
	
	/**
	 * @return the index of an unused {@link PacketBackup}
	 */
	private int getIndex() {
		if(numUsed < backups.length) {
			for(int i = 0; i < backups.length; i++) {
				int index = (i + startIndex) % backups.length;
				if(backups[index] == null || !backups[index].getUsed()) return index;
			}
		}
		startIndex = backups.length;
		expand();
		return startIndex;
	}
	
	/**
	 * returns a {@link PacketBackup}, resetting it to the given data id, channel id and priority
	 * @param dataId the data id to reset the {@link PacketBackup} to
	 * @param channelId the channel id to reset the {@link PacketBackup} to
	 * @param priority the priority to reset the {@link PacketBackup} to
	 * @return the {@link PacketBackup} to use for the given data id
	 */
	public synchronized PacketBackup get(long dataId, long channelId, int priority) {
		int index = getIndex();
		PacketBackup backup = backups[index];
		if(backup == null) {
			backup = backups[index] = new PacketBackup();
		}
		backup.reset(dataId, channelId, priority);
		setUsed(backup, true);
		Log.debug(this, "put dataId %d to index %d: %s", dataId, index, toString());
		return backups[index];
	}
	
	/**
	 * expands the backups array
	 */
	private void expand() {
		PacketBackup[] newBackups = new PacketBackup[backups.length*4];
		System.arraycopy(backups, startIndex, newBackups, 0, backups.length-startIndex);
		System.arraycopy(backups, 0, newBackups, backups.length-startIndex, startIndex);
		backups = newBackups;
		startIndex = 0;
	}
	
	/**
	 * sets the given {@link PacketBackup}'s used value to the given used value
	 * @param backup the {@link PacketBackup} whose used value to set
	 * @param used what to set the given backup's used value to
	 */
	private void setUsed(PacketBackup backup, boolean used) {
		if(backup.getUsed() != used) numUsed += (used ? 1 : -1);
		backup.setUsed(used);
	}
	
	/**
	 * clears the given {@link PacketBackup}, enabling it for re-use
	 * @param packetBackup the {@link PacketBackup} to clear
	 */
	public synchronized void clear(PacketBackup packetBackup) {
		setUsed(packetBackup, false);
	}
	
	@Override
	public String toString() {
		return "UnreliablePacketBackupCollection";
	}
	
}
