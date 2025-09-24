/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.chunk;

import java.io.*;
import java.util.*;
import malictus.klang.file.*;

/**
 * A container chunk is a chunk that can hold other chunks. This class serves as the parent
 * class of all container chunk classes.
 * @author Jim Halliday
 */
public abstract class ContainerChunk extends Chunk {
	
	protected Vector<Chunk> chunks = new Vector<Chunk>();
	//if true, this container chunk uses pad bytes to make all offsets even (as in RIFF chunks)
	protected boolean usesPadByte = false;
	
	public ContainerChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
	}
	
	public ContainerChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, Boolean isBigEndian, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, isBigEndian, raf);
	}
	
	/**
	 * Retrieve all the subchunks for this chunk.
	 * @return a vector of the subchunks for this chunk
	 */
	public Vector<Chunk> getSubChunks() {
		return chunks;
	}
	
	/**
	 * Find a chunk with the given name, within this chunk's subchunks.
	 * @param name the name to search for
	 * @param recurse if true, will also look within nested subchunks
	 * @return the chunk with the given name, or null if none found
	 */
	public Chunk getChunkNamed(String name, boolean recurse) {
		return ChunkFactory.getChunkNamed(this.getSubChunks(), name, recurse);
	}
	
	/**
	 * Find all chunks with the given name, within this chunk's subchunks.
	 * @param name the name to search for
	 * @param recurse if true, will also look within nested subchunks
	 * @return a vector of the chunks with the given name, or null if none found
	 */
	public Vector<Chunk> getChunksNamed(String name, boolean recurse) {
		return ChunkFactory.getChunksNamed(this.getSubChunks(), name, recurse);
	}
	
	/**
	 * Use this to tell if this chunk uses pad bytes to keep all offsets even (as in RIFF chunks).
	 * Chunk constructors that want this value to be true must set it manually 
	 * in the constructor.
	 * @return true if this chunk should use a pad byte for it children, and false otherwise
	 */
	public boolean usesPadByte() {
		return usesPadByte;
	}

}
