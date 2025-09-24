/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.file;

import java.io.*;

import malictus.klang.chunk.Chunk;

public interface EditableFileBase {

	/**
	 * Reparse the file, resetting all data. This is typically called when a file has been
	 * edited in some way.
	 * @param raf a pointer to the file
	 * @throws IOException if read error occurs
	 */
	public void reparseFile(RandomAccessFile raf) throws IOException;

	/**
	 * Delete the specified chunk from the file. Chunk must be at TOP LEVEL ONLY,
	 * otherwise ContainerChunk should call the method instead.
	 * @param chunk the chunk to delete
	 * @param raf a pointer to the file (position isn't important)
	 * @throws IOException if read write error occurs
	 */
	public void deleteChunk(Chunk chunk, RandomAccessFile raf) throws IOException;
	
	/**
	 * Add a new chunk, using the specified file as the chunk's data.
	 * @param theFile the file to add
	 * @param name the name for the new chunk
	 * @param position position among the other chunks in the file (1 = first, etc.)
	 * @param raf a file pointer (position not important)
	 * @throws IOException if read/write error occurs
	 */
	public void addChunkFromFile(File theFile, String name, int position, RandomAccessFile raf) throws IOException;
	
	/**
	 * Add a new chunk, using the specified byte array as the chunk's data.
	 * @param bytes the byte array to add
	 * @param name the name for the new chunk
	 * @param position position among the other chunks in the file (1 = first, etc.)
	 * @param raf a file pointer (position not important)
	 * @throws IOException if read/write error occurs
	 */
	public void addChunkFromArray(byte[] bytes, String name, int position, RandomAccessFile raf) throws IOException;
	
	/**
	 * Called when a top-level chunk will be changing size. Most file types don't need
	 * to do anything here
	 * @param chunk the chunk that will be changing
	 * @param newsize the new size for the chunk
	 * @param raf a pointer to the file
	 * @throws IOException if read/write error occurs
	 */
	public void chunkWillChangeSize(Chunk chunk, long newsize, RandomAccessFile raf) throws IOException;
	
	/**
	 * Called when a top-level chunk has just changed and been reparsed. 
	 * Most file types don't need to do anything here.
	 * @param chunk the chunk that changed
	 * @param raf a pointer to the file
	 * @throws IOException if read/write error occurs
	 */
	public void chunkHasChanged(Chunk chunk, RandomAccessFile raf) throws IOException;
	
	/**
	 * Return true if chunk names can be edited
	 * @return true if chunk names can be edited, false otherwise
	 */
	public boolean canEditChunkNames();
	
	/**
	 * Edit a chunk's name. 
	 * NOTE: This method should be called internally only, not by external classes!
	 * @param chunk the sub chunk whose name should change
	 * @param newName the new name for the chunk
	 * @param raf a pointer to the file (position isn't important)
	 * @throws IOException if read/write error occurs
	 */
	public void editChunkName(Chunk chunk, String newName, RandomAccessFile raf) throws IOException;
	
	/**
	 * Way to tell whether top-level chunks can be added to this file
	 * @return true if top level chunks can be added, and false if not
	 */
	public boolean canAddTopLevelChunks();
	
}
