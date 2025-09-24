package org.dclayer.net.link.control.discontinuousblock;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.link.control.packetbackup.PacketBackupCollection;

/**
 * the {@link DiscontinuousBlock}-storing counterpart to the {@link PacketBackupCollection}
 */
public class DiscontinuousBlockCollection implements HierarchicalLevel {
	
	private HierarchicalLevel parentHierarchicalLevel;
	
	/**
	 * array containing all available {@link DiscontinuousBlock} instances
	 */
	private DiscontinuousBlock[] blocks;
	/**
	 * the lowest data id stored in this {@link DiscontinuousBlockCollection}
	 */
	private long dataIdOffset = -1;
	/**
	 * the lowest, not-yet-read data id stored in this {@link DiscontinuousBlockCollection}
	 */
	private long dataIdRead = -1;
	/**
	 * the index containing the {@link DiscontinuousBlock} object with the lowest data id, or,
	 * in other words, the index indicating the start of the ring buffer {@link DiscontinuousBlock#blocks}
	 */
	private int startIndex = 0;
	/**
	 * the amount of currently occupied {@link DiscontinuousBlock}s in this {@link DiscontinuousBlockCollection}
	 */
	private int numUsed = 0;
	
	public DiscontinuousBlockCollection(HierarchicalLevel parentHierarchicalLevel, int size) {
		this.parentHierarchicalLevel = parentHierarchicalLevel;
		this.blocks = new DiscontinuousBlock[size];
	}
	
	/**
	 * resets this {@link DiscontinuousBlockCollection}, setting all {@link DiscontinuousBlock}s unused
	 */
	public synchronized void reset() {
		startIndex = 0;
		dataIdOffset = -1;
		dataIdRead = -1;
		numUsed = 0;
		for(DiscontinuousBlock block : blocks) {
			if(block != null) block.used = false;
		}
	}
	
	/**
	 * calculates and returns the ordinal number in the ring buffer of a given data id
	 * @param dataId the data id to calculate the ordinal number for
	 * @return the ordinal number in the ring buffer of a given data id
	 */
	private long calcNum(long dataId) {
		if(dataIdOffset < 0) dataIdOffset = dataIdRead = dataId;
		return dataId - dataIdOffset;
	}
	
	/**
	 * calculates and returns the ordinal number in the ring buffer of a given data id,
	 * additionally performing boundary checks and resizing the array if it is full and
	 * the expandIfNecessary parameter is set true
	 * @param dataId the data it to return the ordinal number for
	 * @param expandIfNecessary set to true if the blocks array should be expanded if it is full
	 * @return the ordinal number in the ring buffer of a given data id
	 */
	private long getNum(long dataId, boolean expandIfNecessary) {
		long num = calcNum(dataId);
		if(expandIfNecessary && num >= blocks.length) {
			expand();
			num = calcNum(dataId);
		}
		if(num < 0 || num >= blocks.length) throw new ArrayIndexOutOfBoundsException(String.format("dataId %d is outside this %s's range (%d - %d)", dataId, toString(), dataIdOffset, dataIdOffset+blocks.length-1));
		return num;
	}
	
	/**
	 * calculates and returns the index in the blocks array for a given ordinal number
	 * @param num the ordinal number of the element in the ring buffer
	 * @return the index in the blocks array for a given ordinal number
	 */
	private int getIndexFromNum(long num) {
		return (int)((num + startIndex) % blocks.length);
	}
	
	/**
	 * calculates and returns the index in the blocks array for a given data id,
	 * optionally expanding the blocks array if it is full and the expandIfNecessary
	 * parameter is set true
	 * @param dataId the data id to return the index for
	 * @param expandIfNecessary set this to true if the blocks array should be expanded if it is full
	 * @return the index in the blocks array for the given data id
	 */
	private int getIndex(long dataId, boolean expandIfNecessary) {
		long num = getNum(dataId, expandIfNecessary);
		return getIndexFromNum(num);
	}
	
	/**
	 * expands the blocks array
	 */
	private void expand() {
		DiscontinuousBlock[] newBlocks = new DiscontinuousBlock[blocks.length*4];
		System.arraycopy(blocks, startIndex, newBlocks, 0, blocks.length-startIndex);
		System.arraycopy(blocks, 0, newBlocks, blocks.length-startIndex, startIndex);
		blocks = newBlocks;
		startIndex = 0;
	}
	
	/**
	 * stores <i>length</i> bytes from <i>byteBuf</i> for the given data id in a {@link DiscontinuousBlock} object
	 * @param dataId the data id of the data that should be stored
	 * @param byteBuf the {@link ByteBuf} holding the data
	 * @param length the amount of bytes to read from the given {@link ByteBuf}
	 * @return true if the given data id is the data id of the block that is next in order for reading, false otherwise
	 * @throws BufException
	 */
	public synchronized boolean put(long dataId, ByteBuf byteBuf, int length) throws BufException {
		int index = getIndex(dataId, true);
		DiscontinuousBlock block = blocks[index];
		if(block == null) {
			block = blocks[index] = new DiscontinuousBlock();
		}
		setUsed(block, true);
		block.store(dataId, byteBuf, length);
		return dataId == dataIdRead; // -> read!
	}
	
	/**
	 * stores the given data for the given data id in a {@link DiscontinuousBlock} object
	 * @param dataId the data id of the data that should be stored
	 * @param data the data to store
	 * @return true if the given data id is the data id of the block that is next in order for reading, false otherwise
	 * @throws BufException
	 */
	public synchronized boolean put(long dataId, Data data) throws BufException {
		int index = getIndex(dataId, true);
		DiscontinuousBlock block = blocks[index];
		if(block == null) {
			block = blocks[index] = new DiscontinuousBlock();
		}
		setUsed(block, true);
		block.store(dataId, data);
		return dataId == dataIdRead; // -> read!
	}
	
	/**
	 * sets the given {@link DiscontinuousBlock}'s used value to the given used value
	 * @param block the {@link DiscontinuousBlock} whose used value to set
	 * @param used what to set the given block's used value to
	 */
	private void setUsed(DiscontinuousBlock block, boolean used) {
		if(block.used != used) numUsed += (used ? 1 : -1);
		block.used = used;
	}
	
	/**
	 * returns the next {@link DiscontinuousBlock} in order for reading, also removing it from this {@link DiscontinuousBlockCollection}
	 * @return the next {@link DiscontinuousBlock} in order for reading
	 */
	public synchronized DiscontinuousBlock clearFirst() {
		return getFirst(true);
	}
	
	/**
	 * returns the next {@link DiscontinuousBlock} in order for reading, keeping it in this {@link DiscontinuousBlockCollection}
	 * @return the next {@link DiscontinuousBlock} in order for reading
	 */
	public synchronized DiscontinuousBlock readFirst() {
		return getFirst(false);
	}
	
	/**
	 * returns the next {@link DiscontinuousBlock} in order for reading,
	 * also removing it if the remove parameter is set true
	 * @param remove set to true if the returned {@link DiscontinuousBlock} should be removed
	 * @return the next {@link DiscontinuousBlock} in order for reading
	 */
	private DiscontinuousBlock getFirst(boolean remove) {
		int index = getIndex(dataIdRead, false);
		DiscontinuousBlock block = blocks[index];
		if(remove) {
			setUsed(block, false);
			dataIdOffset++;
			if(numUsed <= 0) {
				startIndex = 0;
			} else {
				startIndex = (startIndex + 1) % blocks.length;
			}
		}
		dataIdRead++;
		return block;
	}
	
	/**
	 * @return true if the {@link DiscontinuousBlock} that is the next in order for reading exists, false otherwise
	 */
	public synchronized boolean available() {
		long num = calcNum(dataIdRead);
		if(num < 0 || num >= blocks.length) return false;
		int index = getIndexFromNum(num);
		return (blocks[index] != null && blocks[index].used);
	}
	
	/**
	 * @return true if there are no occupied {@link DiscontinuousBlock} objects stored in this {@link DiscontinuousBlockCollection}
	 */
	public synchronized boolean isEmpty() {
		return numUsed <= 0;
	}
	
	/**
	 * @return the lowest data id of all {@link DiscontinuousBlock} objects stored in this {@link DiscontinuousBlockCollection}
	 */
	public synchronized long getDataIdOffset() {
		return dataIdOffset;
	}
	
	/**
	 * @return the lowest, not-yet-read data id stored in this {@link DiscontinuousBlockCollection}
	 */
	public synchronized long getDataIdRead() {
		return dataIdRead;
	}
	
	/**
	 * sets the data id offset of this {@link DiscontinuousBlockCollection}.<br />
	 * see {@link DiscontinuousBlockCollection#dataIdOffset}.
	 * @param dataIdOffset the data id offset value to use
	 */
	public synchronized void setDataIdOffset(long dataIdOffset) {
		this.dataIdOffset = dataIdOffset;
		if(dataIdOffset > dataIdRead) dataIdRead = dataIdOffset;
	}
	
	/**
	 * sets the data id read value of this {@link DiscontinuousBlockCollection}.<br />
	 * see {@link DiscontinuousBlockCollection#dataIdRead}.
	 * @param dataIdOffset the data id read value to use
	 */
	public synchronized void setDataIdRead(long dataIdRead) {
		this.dataIdRead = dataIdRead;
	}
	
	/**
	 * returns the {@link DiscontinuousBlock} object stored for the given data id
	 * @param dataId the data id to return the {@link DiscontinuousBlock} object for
	 * @return the {@link DiscontinuousBlock} object stored for the given data id
	 */
	public synchronized DiscontinuousBlock get(long dataId) {
		int index = getIndex(dataId, false);
		Log.debug(this, "get(dataId %d): index=%d, block=%s", dataId, index, blocks[index]);
		return (blocks[index] != null && blocks[index].used) ? blocks[index] : null;
	}
	
	@Override
	public String toString() {
		return String.format("DiscontinuousBlockCollection(%d/%d blocks, dataIdOffset=%d, startIndex=%d)", numUsed, blocks.length, dataIdOffset, startIndex);
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return parentHierarchicalLevel;
	}
	
}
