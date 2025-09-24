/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.file;

import java.io.*;

import malictus.klang.KlangConstants;
import malictus.klang.chunk.*;
import malictus.klang.primitives.*;

/**
 * A class that represents an entire PNGFile.
 * @author Jim Halliday
 */
public class PNGFile extends KlangFile implements EditableFileBase {

	/**
	 * Create a PNGFile object from the given File object.
	 * @param theFile the file to read
	 * @param raf a pointer to the file (position not important)
	 * @throws IOException if error occurs
	 */
	public PNGFile(File theFile, RandomAccessFile raf) throws IOException {
		super(theFile, raf);
		if (!PNGFile.fileIsPNGFile(raf)) {
			throw new IOException(KlangConstants.ERROR_FILE_IS_INCORRECT_TYPE);
		}
		reparseFile(raf);
		//parse primitives
		if (getHeightAsPrimitive() != null) {
			this.fileLevelData.add(getHeightAsPrimitive());
		}
		if (getWidthAsPrimitive() != null) {
			this.fileLevelData.add(getWidthAsPrimitive());
		}
	}
	
	/**
	 * Test to tell if this file is truly a PNG file.
	 * @param raf access to file
	 * @return true if file is PNG file, and false otherwise
	 * @throws IOException if file error occurs
	 */
	public static boolean fileIsPNGFile(RandomAccessFile raf) throws IOException {
		if (raf.length() < 8) {
			return false;
		}
		raf.seek(0);
		Int2ByteUnsignedBE png1 = new Int2ByteUnsignedBE();
		Int2ByteUnsignedBE png2 = new Int2ByteUnsignedBE();
		Int2ByteUnsignedBE png3 = new Int2ByteUnsignedBE();
		Int2ByteUnsignedBE png4 = new Int2ByteUnsignedBE();
		try {
			png1.setValueFromFile(raf);
			png2.setValueFromFile(raf);
			png3.setValueFromFile(raf);
			png4.setValueFromFile(raf);
		} catch (BadValueException err) {
			return false;
		}
		if ( (png1.getValue() == 0x8950) && (png2.getValue() == 0x4e47) && 
				(png3.getValue() == 0x0d0a) && (png4.getValue() == 0x1a0a) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Return the image height
	 * @return the height of this image in pixels, or null if data not found
	 */
	public Long getImageHeight() {
		PrimitiveData pd = getHeightAsPrimitive();
		if (pd == null) {
			return null;
		}
		return new Long(((PrimitiveInt)pd.getPrimitive()).getValueAsLong());
	}
	
	/**
	 * Return the image height for this file as a primitive.
	 * @return the height for this image as a primitive, or null if data not found.
	 */
	private PrimitiveData getHeightAsPrimitive() {
		Chunk x = this.getChunkNamed(KlangConstants.CHUNKNAME_IHDR, true);
		if (x == null) {
			return null;
		}
		PrimitiveData pd = x.getPrimitiveValueNamed(KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_HEIGHT);
		return pd;
	}
	
	/**
	 * Return the image width
	 * @return the width of this image in pixels, or null if data not found
	 */
	public Long getImageWidth() {
		PrimitiveData pd = getWidthAsPrimitive();
		if (pd == null) {
			return null;
		}
		return new Long(((PrimitiveInt)pd.getPrimitive()).getValueAsLong());
	}
	
	/**
	 * Return the image width for this file as a primitive.
	 * @return the width for this image as a primitive, or null if data not found.
	 */
	private PrimitiveData getWidthAsPrimitive() {
		Chunk x = this.getChunkNamed(KlangConstants.CHUNKNAME_IHDR, true);
		if (x == null) {
			return null;
		}
		PrimitiveData pd = x.getPrimitiveValueNamed(KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_WIDTH);
		return pd;
	}
    
    public void reparseFile(RandomAccessFile raf) throws IOException {
    	this.clearAllData();
    	//data will be one, large chunk
    	Chunk theOnlyChunk = ChunkFactory.createChunk(this, 0, KlangConstants.CHUNKTYPE_PNGFILE, null, raf, true, null, null);  	
    	if (!(theOnlyChunk instanceof PNGFileChunk))  {
			//something is wrong
    		theOnlyChunk = ChunkFactory.createChunk(this, 0, KlangConstants.CHUNKTYPE_NOCHUNK, null, raf, true, null, null);
		}
    	this.chunks.add(theOnlyChunk);
    }
    
    public void deleteChunk(Chunk chunk, RandomAccessFile raf) throws IOException {
		//not needed - file is always single chunk
	}
	
	public void addChunkFromFile(File theFile, String name, int position, RandomAccessFile raf) throws IOException {
		//not needed - file is always single chunk
	}
	
	public void addChunkFromArray(byte[] bytes, String name, int position, RandomAccessFile raf) throws IOException {
		//not needed - file is always single chunk
	}
	
	public void chunkWillChangeSize(Chunk chunk, long newsize, RandomAccessFile raf) throws IOException {
		//no need to do anything here
	}

	public void chunkHasChanged(Chunk chunk, RandomAccessFile raf) throws IOException {
		//no need to do anything here
	}
	
	public void editChunkName(Chunk chunk, String newName, RandomAccessFile raf) throws IOException {
		//can't be done
		throw new IOException(KlangConstants.ERROR_FILE_ISNT_EDITABLE);
	}
	
	public boolean canEditChunkNames() {
		return false;
	}
	
	public boolean canAddTopLevelChunks() {
		return false;
	}
	
}
