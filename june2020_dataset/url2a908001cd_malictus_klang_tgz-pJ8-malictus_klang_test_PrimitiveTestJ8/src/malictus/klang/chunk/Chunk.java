/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.chunk;

import java.io.*;
import java.util.*;
import malictus.klang.*;
import malictus.klang.primitives.*;
import malictus.klang.file.*;

/**
 * A Chunk is a definable portion of a file. Chunk is abstract, and all chunk types should 
 * derive from it. 
 * @author Jim Halliday
 */
public abstract class Chunk {
	
	protected KlangFile parentFile;
	protected long startPosition;
	protected long endPosition;
	protected long dataStartPosition;
	protected long dataEndPosition;
	protected int chunkType;
	protected String chunkName;
	protected ContainerChunk parentChunk = null;
	protected String checksum = "";
	protected String datachecksum = "";
	protected Vector<PrimitiveData> primitives = new Vector<PrimitiveData>();
	protected Boolean isBigEndian = null;
	
	/**
	 * Initiate a chunk.
	 * @param parentFile the file that this chunk belongs to
	 * @param startpos the start byte position of the chunk in the file
	 * @param endpos the end byte position of the chunk in the file
	 * @param datastartpos the start position of the actual data in the chunk (not including any header data)
	 * @param dataendpos the end byte position of the actual data in the chunk (not including any footer data)
	 * @param chunkType the chunk type (one of the KlangConstants.CHUNKTYPE_ constants)
	 * @param chunkName the name of the chunk; for RIFF-type files this is something like "AIFF", "fmt ", or "data"
	 * @param parentChunk the parent chunk that this chunk belongs to; this is null if the chunk is at the outermost level
	 * @param isBigEndian true if data in the chunk should be big-endian, and false otherwise; leave as null if not applicable
	 * @param raf a RandomAccessFile pointer to the file (position in the file before this method is not important)
	 * @throws IOException if read/write error occurs
	 */
	public Chunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, Boolean isBigEndian, RandomAccessFile raf) throws IOException {
		this.parentFile = parentFile;
		this.startPosition = startpos;
		this.endPosition = endpos;
		this.dataStartPosition = datastartpos;
		this.dataEndPosition = dataendpos;
		this.chunkType = chunkType;
		this.chunkName = chunkName;
		this.parentChunk = parentChunk;
		this.isBigEndian = isBigEndian;
	}
	
	/**
	 * Initiate a chunk.
	 * @param parentFile the file that this chunk belongs to
	 * @param startpos the start byte position of the chunk in the file
	 * @param endpos the end byte position of the chunk in the file
	 * @param datastartpos the start position of the actual data in the chunk (not including any header data)
	 * @param dataendpos the end byte position of the actual data in the chunk (not including any footer data)
	 * @param chunkType the chunk type (one of the KlangConstants.CHUNKTYPE_ constants)
	 * @param chunkName the name of the chunk; for RIFF-type files this is something like "AIFF", "fmt ", or "data"
	 * @param parentChunk the parent chunk that this chunk belongs to; this is null if the chunk is at the outermost level
	 * @param raf a RandomAccessFile pointer to the file (position in the file before this method is not important)
	 * @throws IOException if read/write error occurs
	 */
	public Chunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		this.parentFile = parentFile;
		this.startPosition = startpos;
		this.endPosition = endpos;
		this.dataStartPosition = datastartpos;
		this.dataEndPosition = dataendpos;
		this.chunkType = chunkType;
		this.chunkName = chunkName;
		this.parentChunk = parentChunk;
	}
	
	/**
	 * Retrieval method for the parent file
	 * @return the parent file for this chunk
	 */
	public KlangFile getParentFile() {
		return parentFile;
	}
	
	/**
	 * Retrieval method for chunk's endianness
	 * @return true if big-endian, false if little-endian, and null if not applicable
	 */
	public Boolean getIsBigEndian() {
		return isBigEndian;
	}
	
	/**
	 * Retrieval method for the parent chunk
	 * @return the parent chunk for this chunk, or null if chunk is already at top level
	 */
	public ContainerChunk getParentChunk() {
		return parentChunk;
	}
	
	/**
	 * Retrieval method for the start position
	 * @return the start byte position for this chunk (including any header information)
	 */
	public long getStartPosition() {
		return startPosition;
	}
	
	/**
	 * Retrieval method for the end position
	 * @return the end byte position for this chunk (including any footer information)
	 */
	public long getEndPosition() {
		return endPosition;
	}
	
	/**
	 * Retrieval method for the data start position
	 * @return the start byte position for the data in this chunk
	 */
	public long getDataStartPosition() {
		return dataStartPosition;
	}
	
	/**
	 * Retrieval method for the data end position
	 * @return the end byte position for data in this chunk
	 */
	public long getDataEndPosition() {
		return dataEndPosition;
	}

	/**
	 * Retrieval method for the chunk's type.
	 * @return the chunk's type, from the KlangConstants.CHUNKTYPE constants
	 */
	public int getChunkType() {
		return chunkType;
	}
	
	/**
	 * Retrieval method for a text-based description of this chunk's type.
	 * @return a string that describes this chunk's type
	 */
	public String getChunkTypeDescription() {
		return KlangConstants.getChunkTypeDescriptionFor(chunkType);
	}
	
	/**
	 * Retrieval method for the chunk's name.
	 * @return the chunk's name
	 */
	public String getChunkName() {
		return chunkName;
	}
	
	/**
	 * Retrieval method for a text-based description of this chunk's name.
	 * This should tell (where possible) a little bit about what function
	 * the chunk has within the file.
	 * @return a string that describes this chunk's function
	 */
	public String getChunkNameDescription() {
		return KlangConstants.getChunkNameDescriptionFor(chunkName);
	}
	
	/**
	 * Retrieve the checksum for the entire chunk.
	 * @return the checksum (MD5) as a string, or an empty string if the checksum has not been calculated
	 */
	public String getChecksum() {
		return checksum;
	}
	
	/**
	 * Calculate the checksum for the entire chunk, and place it in the checksum variable, retrievable via getChecksum()
	 * @throws IOException if a file read error occurs
	 */
	public void calculateChecksum() throws IOException {
		if (this.getParentFile().getFile() == null) {
			throw new IOException(KlangConstants.ERROR_NO_SUCH_FILE);
		}
		checksum = KlangUtil.getChecksum(this.getParentFile().getFile(), this.getStartPosition(), this.getEndPosition());
	}
	
	/**
	 * Retrieve the checksum for the chunk (data only, no header/footer).
	 * @return the checksum (MD5) as a string, or an empty string if the checksum has not been calculated
	 */
	public String getDataChecksum() {
		return datachecksum;
	}
	
	/**
	 * Calculate the checksum for the chunk (data only, no header/footer), and place it in the checksum variable, retrievable via getChecksum()
	 * @throws IOException if a file read error occurs
	 */
	public void calculateDataChecksum() throws IOException {
		if (this.getParentFile() == null) {
			throw new IOException(KlangConstants.ERROR_NO_SUCH_FILE);
		}
		datachecksum = KlangUtil.getChecksum(this.getParentFile().getFile(), this.getDataStartPosition(), this.getDataEndPosition());
	}
	
	/**
	 * Retrieval method for the primitive data for this chunk
	 * @return a vector of the data associated with this chunk, or empty vector
	 * if none available
	 */
	public Vector<PrimitiveData> getPrimitiveData() {
		return primitives;
	}
	
	/**
	 * Given a PrimitiveData object's name (one of the KlangConstants.PRIMITIVE_DATA_ 
	 * constants), return that PrimitiveData object in this chunk
	 * @param name one of the KlangConstants.PRIMITIVE_DATA_ constants
	 * @return the PrimitiveData object that the name represents, or null if that
	 * PrimitiveData obejct is not represented in this chunk
	 */
	public PrimitiveData getPrimitiveValueNamed(String name) {
		int counter = 0;
		while (counter < primitives.size()) {
			PrimitiveData x = primitives.get(counter);
			if (x.getName().equals(name)) {
				return x;
			}
			counter = counter + 1;
		}
		return null;
	}
	
	/**
	 * Determine whether or not this chunk can be deleted.
	 * @return true if chunk can be deleted, false otherwise
	 */
	public boolean chunkCanBeDeleted() {
		ContainerChunk x = this.getParentChunk();
		if (x != null) {
			if (x instanceof EditableContainerChunk) {
				return true;
			}
		} else {
			KlangFile kf = this.getParentFile();
			if (kf instanceof EditableFileBase) {
				//check to see if chunk is the same as the file; if so, no deletion possible
				if ( (this.getStartPosition() == 0) && (this.getEndPosition() >= kf.getFile().length()) ) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check to see whether this chunk's name can be altered.
	 * @return true if the chunk's name can be altered, and false otherwise
	 */
	public boolean chunkNameCanBeAltered() {
		ContainerChunk x = this.getParentChunk();
		if (x != null) {
			if (x instanceof EditableContainerChunk) {
				return true;
			}
		} else {
			KlangFile kf = this.getParentFile();
			if (kf instanceof EditableFileBase) {
				EditableFileBase efb = (EditableFileBase)kf;
				if (efb.canEditChunkNames()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check to see whether this chunk's data can be altered.
	 * @return true if the chunk can be altered, and false otherwise
	 */
	public boolean chunkCanBeAltered() {
		ContainerChunk x = this.getParentChunk();
		if (x != null) {
			if (x instanceof EditableContainerChunk) {
				return true;
			}
		} else {
			KlangFile kf = this.getParentFile();
			if (kf instanceof EditableFileBase) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Delete this chunk from the file entirely
	 * @param raf r/w access to the file
	 * @throws IOException if read/write error occurs
	 */
	public void deleteChunk(RandomAccessFile raf) throws IOException {
		if (!chunkCanBeDeleted()) {
			throw new IOException(KlangConstants.ERROR_CHUNK_ISNT_EDITABLE);
		}
		//warn all chunk parents of upcoming deletion
		this.chunkIsAboutToChangeSize(0, raf);
		//actually delete the chunk
		KlangUtil.deleteFromFile(this.getParentFile().getFile(), this.getStartPosition(), this.getEndPosition());
		//update and reparse
		((EditableFileBase)this.getParentFile()).reparseFile(raf);
		this.endPosition = this.startPosition;
		//do any cleanup needed
		this.chunkJustChanged(raf);
		//reparse again to get changes
		((EditableFileBase)this.getParentFile()).reparseFile(raf);
	}
	
	/**
	 * Rename this chunk
	 * @param newName the new name for the chunk
	 * @param raf r/w access to the file
	 * @throws IOException if read/write error occurs
	 */
	public void renameChunk(String newName, RandomAccessFile raf) throws IOException {
		if (!chunkCanBeAltered()) {
			throw new IOException(KlangConstants.ERROR_CHUNK_ISNT_EDITABLE);
		}
		/**
		 * NOTE: Currently assumes chunk will NOT change size! Will need to be altered
		 * if any chunk/file types arise where changing it's chunk name alters the size
		 */
		if (this.getParentChunk() != null) {
			if (!(this.getParentChunk() instanceof EditableContainerChunk)) {
				throw new IOException(KlangConstants.ERROR_CHUNK_ISNT_EDITABLE);
			}
			EditableContainerChunk parent = (EditableContainerChunk)this.getParentChunk();
			parent.editSubChunkName(this, newName, raf);
		} else {
			if (!(this.getParentFile() instanceof EditableFileBase)) {
				throw new IOException(KlangConstants.ERROR_FILE_ISNT_EDITABLE);
			}
			((EditableFileBase)this.getParentFile()).editChunkName(this, newName, raf);
		}
		//update and reparse
		((EditableFileBase)this.getParentFile()).reparseFile(raf);
		//do any cleanup needed
		this.chunkJustChanged(raf);
		//reparse again to get any changes
		((EditableFileBase)this.getParentFile()).reparseFile(raf);
	}
	
	/**
	 * Any chunk that is about to change in size should call this first to warn
	 * the parent chunks and file.
	 * @param newsize the size that this chunk will be after the change
	 * @param raf RandomAccessFile pointer to file (location isn't important)
	 * @throws IOException if error occurs, or chunk isn't editable
	 */
	protected void chunkIsAboutToChangeSize(long newsize, RandomAccessFile raf) throws IOException {
		if (this.getParentChunk() != null) {
			if (!(this.getParentChunk() instanceof EditableContainerChunk)) {
				throw new IOException(KlangConstants.ERROR_CHUNK_ISNT_EDITABLE);
			}
			Chunk parent = this.getParentChunk();
			long oldParentSize = parent.getEndPosition() - parent.getStartPosition();
			long oldChunkSize = this.getEndPosition() - this.getStartPosition();
			long chunkDiff = newsize - oldChunkSize;
			long newParentSize = oldParentSize + chunkDiff;
			((EditableContainerChunk)this.getParentChunk()).subChunkWillChangeSize(this, newsize, raf);
			//recurse
			this.getParentChunk().chunkIsAboutToChangeSize(newParentSize, raf);
		} else {
			if (!(this.getParentFile() instanceof EditableFileBase)) {
				throw new IOException(KlangConstants.ERROR_FILE_ISNT_EDITABLE);
			}
			((EditableFileBase)this.getParentFile()).chunkWillChangeSize(this, newsize, raf);
		}
	}
	
	/**
	 * Any chunk that has just changed should call this to update parent chunks and 
	 * the file. NOTE: For most files, the important work should be done in 
	 * chunkIsAboutToChangeSize() instead, but some files (PNG) need to calculate a CRC
	 * or do something else that can only be done AFTER a file is actually modified
	 * @param raf RandomAccessFile pointer to file (location isn't important)
	 * @throws IOException if error occurs, or chunk isn't editable
	 */
	public void chunkJustChanged(RandomAccessFile raf) throws IOException {
		if (this.getParentChunk() != null) {
			if (!(this.getParentChunk() instanceof EditableContainerChunk)) {
				throw new IOException(KlangConstants.ERROR_CHUNK_ISNT_EDITABLE);
			}
			((EditableContainerChunk)this.getParentChunk()).subChunkHasChanged(this, raf);
			//recurse
			this.getParentChunk().chunkJustChanged(raf);
		} else {
			if (!(this.getParentFile() instanceof EditableFileBase)) {
				throw new IOException(KlangConstants.ERROR_FILE_ISNT_EDITABLE);
			}
			((EditableFileBase)this.getParentFile()).chunkHasChanged(this, raf);
		}
	}
	
	/**
	 * Alter a file by regenerating this chunk from its PrimitiveData objects, which may
	 * have been modified. This method alters the underlying file.
	 * This is the default implementation. Complex chunks will want to write their own version
	 * of this method.
	 * @param raff a pointer to the file (position is not important)
	 * @throws IOException if any read/write error occurs, or if any primitive objects are not of fixed byte length
	 * 		(these will need to be handled by specific chunk implementations)
	 */
	public void reparseChunkPrimitives(RandomAccessFile raff) throws IOException {
		if ((this.primitives == null) || (this.primitives.size() == 0)) {
			//nothing to do!
			return;
		}
		//calculate needed byte size
		int total = 0;
		int counter = 0;
		while (counter < primitives.size()) {
			Primitive p = primitives.get(counter).getPrimitive();
			if (!(p instanceof PrimitiveFixedByte)) {
				throw new IOException(KlangConstants.ERROR_NOT_FIXED_BYTE);
			}
			total = total + ((PrimitiveFixedByte)p).getFixedLength();
			counter = counter + 1;
		}
		//'total' variable now represents the minimum length for this chunk in order to write out all the primitives			
		long newsize = this.getDataEndPosition() - this.getDataStartPosition();
		if (newsize < total) {
			newsize = total;
		}
		boolean needsPad = false;
		if (this.getParentChunk() != null) {
			if (this.getParentChunk().usesPadByte()) {
				newsize = KlangUtil.adjustForPadByte(newsize);
				needsPad = true;
			}
		}
		if (newsize != (this.getDataEndPosition() - this.getDataStartPosition())) {
			//have to grow chunk
			this.chunkIsAboutToChangeSize(newsize, raff);
			int amt = total - (int)(this.getEndPosition() - this.getStartPosition());
			byte[] newbyte = new byte[amt];
			KlangUtil.insertIntoFile(newbyte, this.getParentFile().getFile(), this.getDataStartPosition());
		}
		//write null to pad byte
		if (needsPad) {
			raff.seek(this.getDataStartPosition() + newsize - 1);
			raff.write(0);
		}
		raff.seek(this.getDataStartPosition());
		//write values back to the file
		counter = 0;
		while (counter < this.primitives.size()) {
			Primitive p = primitives.get(counter).getPrimitive();
			if (p.valueExists()) {
				try {
					p.writeValueToFile(raff);
				} catch (BadValueException err) {
					//shouldn't happen
					err.printStackTrace();
				}
			} else {
				//write nulls instead
				raff.write(new byte[((PrimitiveFixedByte)p).getFixedLength()]);
			}
			counter = counter + 1;
		}
		//reparse the file
		((EditableFileBase)this.getParentFile()).reparseFile(raff);
		this.chunkJustChanged(raff);
	}	
	
	/**
	 * Override of toString method, to enable correct display of trees.
	 * @return the name of this chunk
	 */
	public String toString() {
		return chunkName;
	}
	
	/**
	 * Helper method to determine whether chunk has metadata 
	 * @return true if chunk has only data (no header/footer), and false otherwise
	 */
	public boolean chunkIsAllData() {
		if (getStartPosition() == getDataStartPosition()) {
			if (getEndPosition() == getDataEndPosition()) {
				return true;
			}
		}
		return false;
	}
	
}
