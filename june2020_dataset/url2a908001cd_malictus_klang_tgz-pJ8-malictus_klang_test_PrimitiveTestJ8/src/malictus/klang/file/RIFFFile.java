/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.file;

import java.io.*;

import malictus.klang.*;
import malictus.klang.primitives.BadValueException;
import malictus.klang.primitives.FourCC;
import malictus.klang.primitives.Int4ByteUnsignedLE;
import malictus.klang.chunk.*;

/**
 * A class the represents a generic RIFF file. Specific file types
 * such as WAV will inherit this.
 * @author Jim Halliday
 */
public class RIFFFile extends KlangFile implements EditableFileBase {

	/**
	 * Create a RIFFFile object from the given File object.
	 * @param theFile the file to read
	 * @param raf file pointer (position not important)
	 * @throws IOException if error occurs
	 */
	public RIFFFile(File theFile, RandomAccessFile raf) throws IOException {
		super(theFile, raf);
		if (!RIFFFile.fileIsRIFFFile(raf)) {
			throw new IOException(KlangConstants.ERROR_FILE_IS_INCORRECT_TYPE);
		}
		reparseFile(raf);
	}
	
	/**
	 * Test to tell if this file is truly a RIFF file.
	 * @param raf access to file
	 * @return true if file is RIFF file, and false otherwise
	 * @throws IOException if file error occurs
	 */
	public static boolean fileIsRIFFFile(RandomAccessFile raf) throws IOException {
		if (raf.length() < 12) {
			return false;
		}
		raf.seek(0);
		try {
			FourCC fcc = new FourCC();
			fcc.setValueFromFile(raf);
			if (fcc.getValue().equals("RIFF") || fcc.getValue().equals("RIFX")) {
				return true;
			}
			return false;
		} catch (BadValueException err) {
			return false;
		}
	}
	
    public void reparseFile(RandomAccessFile raf) throws IOException {
    	this.clearAllData();
    	long pointer = 0;
    	while (pointer < raf.length()) {
    		raf.seek(pointer);
    		FourCC fcc = new FourCC();
    		try {
    			fcc.setValueFromFile(raf);
    		} catch (BadValueException err) {
    			;
    		}
			Chunk cChunk = null;
			if (fcc.getValue() == null) {
				cChunk = ChunkFactory.createChunk(this, pointer, KlangConstants.CHUNKTYPE_NOCHUNK, null, raf, null, null, null);
			} else if (fcc.getValue().equals("RIFF")) {
				cChunk = ChunkFactory.createChunk(this, pointer, KlangConstants.CHUNKTYPE_RIFF, null, raf, false, null, null);	
			} else if (fcc.getValue().equals("RIFX")) {
				cChunk = ChunkFactory.createChunk(this, pointer, KlangConstants.CHUNKTYPE_RIFF, null, raf, true, null, null);	
			} else {
				cChunk = ChunkFactory.createChunk(this, pointer, KlangConstants.CHUNKTYPE_NOCHUNK, null, raf, null, null, null);
			}
    		if (!(cChunk instanceof RIFFChunk)) {
    			//only 'RIFF Chunks allowed in RIFF files
    			cChunk = ChunkFactory.createChunk(this, pointer, KlangConstants.CHUNKTYPE_NOCHUNK, null, raf, null, null, null);
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
		KlangUtil.deleteFromFile(this.getFile(), chunk.getStartPosition(), chunk.getEndPosition());
		reparseFile(raf);
	}
	
	public void addChunkFromFile(File theFile, String name, int position, RandomAccessFile raf) throws IOException {
		//only RIFF (and RIFX) chunks can be added at root level
		if ( (!name.equals("RIFF")) && (!name.equals("RIFX")) ) {
			throw new IOException(KlangConstants.ERROR_NOT_RIFF_RIFX);
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
			Int4ByteUnsignedLE size = new Int4ByteUnsignedLE();
			Long sizeval = KlangUtil.adjustForPadByte(theFile.length());
			size.setValue(sizeval);
			size.writeValueToFile(tempraf);
			tempraf.close();
		} catch (IOException err) {
			tempraf.close();
			throw err;
		} catch (BadValueException err) {
			tempraf.close();
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
		//only RIFF (and RIFX) chunks can be added at root level
		if ( (!name.equals("RIFF")) && (!name.equals("RIFX")) ) {
			throw new IOException(KlangConstants.ERROR_NOT_RIFF_RIFX);
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
			Int4ByteUnsignedLE size = new Int4ByteUnsignedLE();
			Long sizeval = KlangUtil.adjustForPadByte(bytes.length);
			size.setValue(sizeval);
			size.writeValueToFile(tempraf);
			tempraf.close();
		} catch (IOException err) {
			tempraf.close();
			throw err;
		} catch (BadValueException err) {
			tempraf.close();
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
