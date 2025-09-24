/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.file;

import java.io.*;

import malictus.klang.KlangConstants;
import malictus.klang.KlangUtil;
import malictus.klang.chunk.*;
import malictus.klang.primitives.*;

/**
 * A class theat represents a generic FORM (IFF) file. Specific file types
 * such as AIFF will inherit this.
 * @author Jim Halliday
 */
public class FORMFile extends KlangFile implements EditableFileBase {

	/**
	 * Create a FORMFile object from the given File object.
	 * @param theFile the file to read
	 * @param raf a pointer to the file (position isn't important)
	 * @throws IOException if error occurs
	 */
	public FORMFile(File theFile, RandomAccessFile raf) throws IOException {
		super(theFile, raf);
		if (!FORMFile.fileIsFORMFile(raf)) {
			throw new IOException(KlangConstants.ERROR_FILE_IS_INCORRECT_TYPE);
		}
		reparseFile(raf);
	}
	
	/**
	 * Test to tell if this file is truly a FORM file.
	 * @param raf access to file
	 * @return true if file is FORM file, and false otherwise
	 * @throws IOException if file error occurs
	 */
	public static boolean fileIsFORMFile(RandomAccessFile raf) throws IOException {
		if (raf.length() < 12) {
			return false;
		}
		raf.seek(0);
		try {
			FourCC fcc = new FourCC();
			fcc.setValueFromFile(raf);
			if (!fcc.getValue().equals("FORM")) {
				return false;
			}
			return true;
		} catch (BadValueException err) {
			return false;
		}
	}
    
	public void reparseFile(RandomAccessFile raf) throws IOException {
    	this.clearAllData();
    	long pointer = 0;
    	while (pointer < raf.length()) {
    		Chunk cChunk = ChunkFactory.createChunk(this, pointer, KlangConstants.CHUNKTYPE_IFF, null, raf, true, null, null);
    		if ((!(cChunk instanceof FORMChunk)) || (!(cChunk.getChunkName().equals("FORM"))) ) {
    			//only 'FORM' Chunks allowed in FORM files
    			cChunk = ChunkFactory.createChunk(this, pointer, KlangConstants.CHUNKTYPE_NOCHUNK, null, raf, true, null, null);
    		}
    		this.chunks.add(cChunk);
    		pointer = cChunk.getEndPosition();
    	}
    }

	public void deleteChunk(Chunk chunk, RandomAccessFile raf) throws IOException {
		if (!this.getChunks().contains(chunk)) {
			throw new IOException(KlangConstants.ERROR_NO_SUCH_CHUNK);
		}
		if ( (chunk.getStartPosition() == 0) && (chunk.getEndPosition() >= this.getFile().length()) ) {
			throw new IOException(KlangConstants.ERROR_CHUNK_IS_FILE);
		}
		long end = KlangUtil.adjustForPadByte(chunk.getEndPosition());
		KlangUtil.deleteFromFile(this.getFile(), chunk.getStartPosition(), end);
		reparseFile(raf);
	}
	
	public void addChunkFromFile(File theFile, String name, int position, RandomAccessFile raf) throws IOException {
		//only FORM chunks can be added at root level
		if (!name.equals("FORM")) {
			throw new IOException(KlangConstants.ERROR_NOT_FORM);
		}
		if (position < 1) {
			position = 1;
		}
		if (position > this.getChunks().size() + 1) {
			position = this.getChunks().size() + 1;
		}
		long start = -1;
		if (position == 1) {
			start = 0;
		} else {
			start = this.getChunks().get(position - 2).getEndPosition();
		}
		//first, add header to a tempfile, then copy original file and possibly a pad byte
		File tempFile = File.createTempFile("klang", ".data");
		RandomAccessFile tempraf = new RandomAccessFile(tempFile, "rw");
		try {
			tempraf.seek(0);
			//header
			FourCC fccname = new FourCC();
			fccname.setValueFromString(name);
			fccname.writeValueToFile(tempraf);
			Int4ByteUnsignedBE size = new Int4ByteUnsignedBE();
			Long sizeval = KlangUtil.adjustForPadByte(theFile.length());
			size.setValue(sizeval);
			size.writeValueToFile(tempraf);
		} catch (BadValueException err) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
		//now write file to temp file
		KlangUtil.appendToFile(theFile, tempFile);
		if (KlangUtil.adjustForPadByte(theFile.length()) != theFile.length()) {
			//add pad byte
			KlangUtil.appendToFile(new byte[1], tempFile);
		}
		//now merge tempfile into file
		KlangUtil.insertIntoFile(tempFile, this.getFile(), start);
		tempFile.delete();
		reparseFile(raf);
	}
	
	public void addChunkFromArray(byte[] bytes, String name, int position, RandomAccessFile raf) throws IOException {
		//only FORM chunks can be added at root level
		if (!name.equals("FORM")) {
			throw new IOException(KlangConstants.ERROR_NOT_FORM);
		}
		if (position < 1) {
			position = 1;
		}
		if (position > this.getChunks().size() + 1) {
			position = this.getChunks().size() + 1;
		}
		long start = -1;
		if (position == 1) {
			start = 0;
		} else {
			start = this.getChunks().get(position - 2).getEndPosition();
		}
		//first, add header to a tempfile, then add bytes and possibly a pad byte
		File tempFile = File.createTempFile("klang", ".data");
		RandomAccessFile tempraf = new RandomAccessFile(tempFile, "rw");
		try {
			tempraf.seek(0);
			//header
			FourCC fccname = new FourCC();
			fccname.setValueFromString(name);
			fccname.writeValueToFile(tempraf);
			Int4ByteUnsignedBE size = new Int4ByteUnsignedBE();
			Long sizeval = KlangUtil.adjustForPadByte(bytes.length);
			size.setValue(sizeval);
			size.writeValueToFile(tempraf);
		} catch (BadValueException err) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
		//now write bytes to temp file
		KlangUtil.appendToFile(bytes, tempFile);
		if (KlangUtil.adjustForPadByte(bytes.length) != bytes.length) {
			//add pad byte
			KlangUtil.appendToFile(new byte[1], tempFile);
		}
		//now merge tempfile into file
		KlangUtil.insertIntoFile(tempFile, this.getFile(), start);
		tempFile.delete();
		reparseFile(raf);		
	}
	
	public void chunkWillChangeSize(Chunk chunk, long newsize, RandomAccessFile raf) throws IOException {
		//no need to do anything here
	}

	public void chunkHasChanged(Chunk chunk, RandomAccessFile raf) throws IOException {
		//no need to do anything here
	}
	
	public void editChunkName(Chunk chunk, String newName, RandomAccessFile raf) throws IOException {
		try {
			raf.seek(chunk.getStartPosition());
			FourCC fcc = new FourCC();
			fcc.setValueFromString(newName);
			fcc.writeValueToFile(raf);
		} catch (BadValueException err) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
	}
	
	public boolean canEditChunkNames() {
		return true;
	}
	
	public boolean canAddTopLevelChunks() {
		return true;
	}
	
}
