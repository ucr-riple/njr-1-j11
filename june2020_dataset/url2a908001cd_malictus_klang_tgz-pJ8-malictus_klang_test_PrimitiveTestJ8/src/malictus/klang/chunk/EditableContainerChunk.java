/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.chunk;

import java.io.*;

/**
 * All instantiations of the ContainerChunk class that are editable should implement 
 * this interface. Calling these methods will change the original file.
 * @author Jim Halliday
 */
public interface EditableContainerChunk {
	
	/**
	 * Add a new chunk from the given file. This method should add the entire file
	 * as data only; the chunk type will determine what header/footer information (if
	 * any) should be written.
	 * @param theFile the file to write as a chunk
	 * @param name the chunk's name
	 * @param position the position of this chunk within the subchunk's array 
	 * 		(ex. 1 = the new chunk should be before all other subchunks)
	 * @param raf a pointer to the original file (position isn't important)
	 * @throws IOException if read/write error occurs
	 */
	public void addChunkFromFile(File theFile, String name, int position, RandomAccessFile raf) throws IOException;
	
	/**
	 * Add a new chunk from a byte array. This method should add the entire array
	 * as data only; the chunk type will determine what header/footer information (if
	 * any) should be written. Can also be used to write an empty chunk by using an empty
	 * (but not null) array.
	 * @param bytes the byte array
	 * @param name the chunk's name
	 * @param position the position of this chunk within the subchunk's array 
	 * 		(ex. 1 = the new chunk should be before all other subchunks)
	 * @param raf a pointer to the original file (position isn't important)
	 * @throws IOException if read/write error occurs
	 */
	public void addChunkFromArray(byte[] bytes, String name, int position, RandomAccessFile raf) throws IOException;
	
	/**
	 * This method should be called any time a container's chunks will be changing size. This
	 * method is a 'warning' to the container chunk that the chunk will be changing,
	 * so any adjustments to parent metadata should occur.
	 * NOTE: This method should be called internally only, not by external classes!
	 * @param chunk the chunk that is about to change
	 * @param newsize the chunk's new size (which hasn't happened yet)
	 * @param raf RandomAccessFile pointer to file (location isn't important)
	 * @throws IOException if read/write error occurs
	 */
	public void subChunkWillChangeSize(Chunk chunk, long newsize, RandomAccessFile raf) throws IOException;
	
	
	/**
	 * This method should be called by any chunk right after it changes (even
	 * it is does not change size!). This method
	 * allows the parent chunk to adjust as necessary to fit the altered chunk.
	 * NOTE: This method should be called internally only, not by external classes!
	 * @param chunk the chunk that just changed
	 * @param raf RandomAccessFile pointer to file (location isn't important)
	 * @throws IOException if read/write error occurs
	 */
	public void subChunkHasChanged(Chunk chunk, RandomAccessFile raf) throws IOException;
	
	/**
	 * Edit a subchunk's name. ContainerChunks that implement this should be sure to
	 * call chunkHasChanged(), and possibly chunkWillChangeSize() as necessary for the
	 * parent chunk or file.
	 * NOTE: This method should be called internally only, not by external classes!
	 * @param chunk the sub chunk whose name should change
	 * @param newName the new name for the chunk
	 * @param raf a pointer to the file (position isn't important)
	 * @throws IOException if read/write error occurs
	 */
	public void editSubChunkName(Chunk chunk, String newName, RandomAccessFile raf) throws IOException;
	
}