package org.dclayer.net.link.control.packetbackup;

import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.link.channel.Channel;
import org.dclayer.net.link.control.discontinuousblock.DiscontinuousBlockCollection;

/**
 * the {@link PacketBackup}-storing counterpart to the {@link DiscontinuousBlockCollection}
 */
public class PacketBackupCollection implements HierarchicalLevel {
	
	/**
	 * array containing all available {@link PacketBackup} instances
	 */
	private PacketBackup[] backups;
	/**
	 * the lowest data id stored in this {@link PacketBackupCollection}
	 */
	private long dataIdOffset = -1;
	/**
	 * the index containing the {@link PacketBackup} object with the lowest data id, or,
	 * in other words, the index indicating the start of the ring buffer {@link PacketBackup#backups}
	 */
	private int startIndex = 0;
	/**
	 * the amount of currently occupied {@link PacketBackup}s in this {@link PacketBackupCollection}
	 */
	private int numUsed = 0;
	
	/**
	 * the {@link Channel} this {@link PacketBackupCollection} belongs to
	 */
	private Channel channel;
	
	public PacketBackupCollection(Channel channel, int size) {
		this.channel = channel;
		this.backups = new PacketBackup[size];
	}
	
	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return channel;
	}
	
	/**
	 * calculates and returns the ordinal number in the ring buffer of a given data id,
	 * additionally performing boundary checks and resizing the array if it is full and
	 * the expandIfNecessary parameter is set true
	 * @param dataId the data it to return the ordinal number for
	 * @param expandIfNecessary set to true if the backups array should be expanded if it is full
	 * @return the ordinal number in the ring buffer of a given data id
	 */
	private long getNum(long dataId, boolean expandIfNecessary, boolean throwArrayIndexOutOfBoundsException) {
		if(dataIdOffset < 0) dataIdOffset = dataId;
		long num = dataId - dataIdOffset;
		if(expandIfNecessary && num >= backups.length) {
			expand();
			num = dataId - dataIdOffset;
		}
		if(num < 0 || num >= backups.length) {
			if(throwArrayIndexOutOfBoundsException) {
				throw new ArrayIndexOutOfBoundsException(String.format("dataId %d is outside this %s's range (%d - %d)", dataId, toString(), dataIdOffset, dataIdOffset+backups.length-1));
			} else {
				return -1;
			}
		}
		return num;
	}
	
	/**
	 * @param dataId the data id to check
	 * @return true if the given data id exceeds this {@link PacketBackupCollection}'s range, false otherwise
	 */
	private boolean outOfBounds(long dataId) {
		long num = dataId - dataIdOffset;
		return num < 0 || num >= backups.length;
	}
	
	/**
	 * calculates and returns the index in the backups array for a given ordinal number
	 * @param num the ordinal number of the element in the ring buffer
	 * @return the index in the backups array for a given ordinal number
	 */
	private int getIndexFromNum(long num) {
		return (int)((num + startIndex) % backups.length);
	}
	
	/**
	 * calculates and returns the index in the backups array for a given data id,
	 * optionally expanding the backups array if it is full and the expandIfNecessary
	 * parameter is set true
	 * @param dataId the data id to return the index for
	 * @param expandIfNecessary set this to true if the backups array should be expanded if it is full
	 * @return the index in the backups array for the given data id
	 */
	private int getIndex(long dataId, boolean expandIfNecessary, boolean throwArrayIndexOutOfBoundsException) {
		long num = getNum(dataId, expandIfNecessary, throwArrayIndexOutOfBoundsException);
		if(num < 0) return -1;
		return getIndexFromNum(num);
	}
	
	/**
	 * returns the {@link PacketBackup} stored in the backups array at
	 * the corresponding index to the given data id
	 * @param dataId the data id to return the {@link PacketBackup} for
	 * @return the {@link PacketBackup} to use for the given data id
	 */
	public synchronized PacketBackup put(long dataId, long channelId, int priority) {
		int index = getIndex(dataId, true, true);
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
	 * sets the used value of the {@link PacketBackup} object at the given index
	 * in the backups array to the given value
	 * @param index the index of the {@link PacketBackup} object in the backups array
	 * @param used what to set the backup's used value to
	 */
	private void setUsed(int index, boolean used) {
		PacketBackup backup = backups[index];
		if(backup != null) setUsed(backup, used);
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
	 * clears the {@link PacketBackup} with the data id of <i>startDataId</i>
	 * and the <i>numFollowing</i> backups after it 
	 * @param startDataId the data id of the first backup to clear
	 * @param numFollowing how many following backups to clear
	 */
	public synchronized void clear(long startDataId, int numFollowing) {
		long stopDataId = startDataId+numFollowing;
		long startNum = getNum(startDataId, false, true);
		long stopNum = getNum(stopDataId, false, true);
		for(long n = startNum; n <= stopNum; n++) {
			setUsed(getIndexFromNum(n), false);
		}
		if(startNum <= 0) { // if we're deleting from the beginning of our backup
			if(this.numUsed <= 0) {
				// BackupCollection is empty
				this.startIndex = 0;
				this.dataIdOffset = -1;
			} else {
				// set startIndex and dataIdOffset to slot after the cleared area
				this.startIndex = getIndexFromNum(stopNum + 1);
				this.dataIdOffset = stopDataId + 1;
				// move startIndex and dataIdOffset to first used backup slot
				while(backups[this.startIndex] == null || !backups[this.startIndex].getUsed()) {
					this.startIndex = ((this.startIndex + 1) % backups.length);
					this.dataIdOffset++;
				}
			}
		}
		Log.debug(this, "cleared dataId %d (and %d following entries): %s", startDataId, numFollowing, details());
	}
	
	/**
	 * tries to clear the given data id, doing nothing if the given data id exceeds the valid range (instead of throwing an {@link ArrayIndexOutOfBoundsException})
	 * @param dataId the data id to try to clear
	 */
	public synchronized void tryClear(long dataId) {
		if(outOfBounds(dataId)) return;
		clear(dataId, 0);
	}
	
	/**
	 * clears the backup with the given data id and all its precedors
	 * @param dataId which data id to clear up to
	 */
	public synchronized void clearUpTo(long dataId) {
		if(dataIdOffset > dataId || outOfBounds(dataId)) return;
		clear(dataIdOffset, (int)(dataId-dataIdOffset));
	}
	
	@Override
	public String toString() {
		return "PacketBackupCollection";
	}
	
	private String details() {
		return String.format("PacketBackupCollection(%d/%d backups, dataIdOffset=%d, startIndex=%d)", numUsed, backups.length, dataIdOffset, startIndex);
	}
	
	/**
	 * returns the {@link PacketBackup} object stored for the given data id
	 * @param dataId the data id to return the {@link PacketBackup} object for
	 * @return the {@link PacketBackup} object stored for the given data id
	 */
	public synchronized PacketBackup get(long dataId) {
		int index = getIndex(dataId, false, false);
		if(index < 0 || !backups[index].getPacketProperties().ready) return null;
		return backups[index];
	}
	
}
