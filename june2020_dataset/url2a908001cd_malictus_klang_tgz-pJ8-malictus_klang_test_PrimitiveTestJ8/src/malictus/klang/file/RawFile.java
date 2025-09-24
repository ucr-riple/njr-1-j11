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
 * A class that represents raw data or an unknown file type.
 * @author Jim Halliday
 */
public class RawFile extends KlangFile {

	/**
	 * Create a RawFile object from the given File object.
	 * @param theFile the file to read
	 * @param raf a pointer to the file (position not important)
	 * @throws IOException if error occurs
	 */
	public RawFile(File theFile, RandomAccessFile raf) throws IOException {
		super(theFile, raf);
		this.clearAllData();
    	Chunk theOnlyChunk = ChunkFactory.createChunk(this, 0, KlangConstants.CHUNKTYPE_NOCHUNK, null, raf, null, null, null);
		this.chunks.add(theOnlyChunk);
	}
	
}
