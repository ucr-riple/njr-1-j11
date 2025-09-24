/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.file;

import java.io.*;
import java.util.*;
import malictus.klang.chunk.*;
import malictus.klang.*;
import malictus.klang.primitives.*;

/**
 * A KlangFile is any existing file object. It may contain additional hierarchical chunk levels,
 * or it may just be one chunk of raw data. Generic file methods are contained at this
 * level.
 * @author Jim Halliday
 */
public abstract class KlangFile {
	
	protected File theFile;
	protected Vector<Chunk> chunks = new Vector<Chunk>();
	protected Vector<PrimitiveData> fileLevelData = new Vector<PrimitiveData>();
	protected String checksum = "";
	
	/**
	 * Initialize a KlangFile object.
	 * @param theFile the file associated with this object; this file should already exist
	 * @param raf pointer to the file (position not important)
	 * @throws IOException if read error occurs
	 */
	public KlangFile(File theFile, RandomAccessFile raf) throws IOException {
		if (!theFile.exists()) {
			throw new IOException(KlangConstants.ERROR_NO_SUCH_FILE);
		}
		if (!theFile.isFile()) {
			throw new IOException(KlangConstants.ERROR_FILE_IS_DIRECTORY);
		}
		if (!theFile.canRead()) {
			throw new IOException(KlangConstants.ERROR_FILE_CANT_BE_READ);
		}
		this.theFile = theFile;
		//individual file classes should attempt to parse out the chunks
	}
	
	/**
	 * Retrieval method for the file itself
	 * @return the file associated with this object
	 */
	public File getFile() {
		return theFile;
	}
	
	/**
	 * Retrieve a vector containing all of this file's chunks
	 * @return a vector containing all of this file's chunks
	 */
	public Vector<Chunk> getChunks() {
		return chunks;
	}
	
	/**
	 * Retrieve the checksum for the overall file.
	 * @return the checksum (MD5) as a string, or an empty string if the checksum has not been calculated
	 */
	public String getChecksum() {
		return checksum;
	}
	
	/**
	 * Calculate the checksum for the file, and place it in the checksum variable, retrievable via getChecksum()
	 * @throws IOException if a file read error occurs
	 */
	public void calculateChecksum() throws IOException {
		if (theFile == null) {
			throw new IOException(KlangConstants.ERROR_NO_SUCH_FILE);
		}
		checksum = KlangUtil.getChecksum(theFile, 0, theFile.length());
	}
	
	/**
	 * Retrieve a string describing this file's type
	 * @return a string describing this file's type
	 */
	public String getFileType() {
		return KlangConstants.getFileTypeDescriptionFor(this.getClass().getName());
	}
	
	/**
	 * Retrieval method for any file-level data.
	 * @return file-level data, or empty vector if none exists
	 */
	public Vector<PrimitiveData> getFileLevelData() {
		return fileLevelData;
	}
	
	/**
	 * Given a chunk name, return the chunk with that name. This method
	 * will return only the first chunk that matches that name.
	 * @param name the chunk name to match
	 * @param recurse if true, this method will search subchunks as well
	 * @return the matching chunk, or null if no matches
	 */
	public Chunk getChunkNamed(String name, boolean recurse) {
		return ChunkFactory.getChunkNamed(this.getChunks(), name, recurse);
	}
	
	/**
	 * Given a chunk name, return a vector of all chunks with that name.
	 * @param name the chunk name to match
	 * @param recurse if true, this method will search subchunks as well
	 * @return the matching chunks, or null if no matches
	 */
	public Vector<Chunk> getChunksNamed(String name, boolean recurse) {
		return ChunkFactory.getChunksNamed(this.getChunks(), name, recurse);
	}
	
	/**
	 * Given a byte position and a vector of chunks, return the deepest chunk which contains that byte.
	 * @param chunks the chunks to check
	 * @param position the byte position to match
	 * @return the deepest matching chunk, or null if no matches found
	 * @throws IOException if read error occurs
	 */
	public static Chunk findDeepestChunkFor(Vector<Chunk> chunks, long position) throws IOException {
		int counter = 0;
		while (counter < chunks.size()) {
			Chunk x = chunks.get(counter);
			if ( (x.getStartPosition() <= position) && (x.getEndPosition() > position) ) {
				//position matches
				if (x instanceof ContainerChunk) {
					Chunk zz = findDeepestChunkFor(((ContainerChunk)x).getSubChunks(), position);
					if (zz == null) {
						return x;
					} else {
						return zz;
					}
				}
				return x;
			}
			counter = counter + 1;
		}
		return null;
	}
	
	/**
	 * Export a chunk to a file. If file exists, it will be overwritten. Note
	 * that only chunk data (no metadata) will be written to the file.
	 * @param theChunk the chunk to export
	 * @param targetFile the file to write to; if it already exists, it will be overwritten
	 * @throws IOException if read/write error occurs
	 */
	public void exportChunk(Chunk theChunk, File targetFile) throws IOException {
		KlangUtil.writeToFile(this.getFile(), targetFile, theChunk.getDataStartPosition(), theChunk.getDataEndPosition());
	}
	
	/**
	 * Reset a file by clearing all of its data
	 */
	protected void clearAllData() {
		chunks = new Vector<Chunk>();
		fileLevelData = new Vector<PrimitiveData>();
	}
	
}
