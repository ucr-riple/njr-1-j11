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

/**
 * A class that represents an entire plain text file (HTML, XML, etc.).
 * @author Jim Halliday
 */
public class PlainTextFile extends KlangFile implements EditableFileBase {
	
	private String textEncoding;

	/**
	 * Create a PlainTextFile object from the given File object.
	 * @param theFile the file to read
	 * @param raf a pointer to the file (position not important)
	 * @throws IOException if error occurs
	 */
	public PlainTextFile(File theFile, RandomAccessFile raf, String encoding) throws IOException {
		super(theFile, raf);
		this.textEncoding = encoding;
		reparseFile(raf);
	}
    
    public void reparseFile(RandomAccessFile raf) throws IOException {
    	this.clearAllData();
    	//data will be one, large chunk
    	Chunk theOnlyChunk = ChunkFactory.createChunk(this, 0, KlangConstants.CHUNKTYPE_PLAINTEXT, null, raf, true, raf.length(), textEncoding);  	
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
