/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.chunk;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import malictus.klang.KlangConstants;
import malictus.klang.KlangUtil;
import malictus.klang.primitives.*;
import malictus.klang.file.*;

/**
 * This class represents an entire PNG file (here called a 'chunk' for convenience).
 * @author Jim Halliday
 */
public class PNGFileChunk extends ContainerChunk implements EditableContainerChunk {

	public PNGFileChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		raf.seek(datastartpos);
		/*
		 * ADD SUBCHUNKS
		 */
		raf.seek(datastartpos);
		long pointer = raf.getFilePointer();
		while (pointer < dataendpos) {
    		Chunk cChunk = ChunkFactory.createChunk(parentFile, pointer, KlangConstants.CHUNKTYPE_PNG, this, raf, true, null, null);
    		this.chunks.add(cChunk);
    		pointer = cChunk.getEndPosition();
    	}
	}
	
	public void addChunkFromFile(File theFile, String name, int position, RandomAccessFile raff) throws IOException {
		if (!(this.getParentFile() instanceof EditableFileBase)) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
		long newsize = (this.endPosition - this.startPosition) + theFile.length();
		if (newsize < 0) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
		try {
			//warn parent chunks (usually shouldn't be necessary, unless PNG chunk is embedded?)
			this.chunkIsAboutToChangeSize(newsize, raff);
			//now make the addition
			long startingplace = 0;
			if (position > this.getSubChunks().size() + 2) {
				position = this.getSubChunks().size() + 1;
			}
			if (position == this.getSubChunks().size() + 1) {
				if (this.getSubChunks().size() == 0) {
					startingplace = this.endPosition;
				} else {
					startingplace = this.getSubChunks().get(position - 2).getEndPosition();
				}
			} else {
				startingplace = this.getSubChunks().get(position - 1).getStartPosition();
			}
			//insert CRC
			KlangUtil.insertIntoFile(new byte[4], this.getParentFile().getFile(), startingplace);
			//insert file
			KlangUtil.insertIntoFile(theFile, this.getParentFile().getFile(), startingplace);
			//insert name + size
			KlangUtil.insertIntoFile(new byte[8], this.getParentFile().getFile(), startingplace);
			raff.seek(startingplace);
			//write size
			Int4ByteUnsignedBE size = new Int4ByteUnsignedBE();
			size.setValue(theFile.length());
			size.writeValueToFile(raff);	
			//write name
			FourCC fcc = new FourCC(name);
			fcc.writeValueToFile(raff);
			//write CRC
			raff.seek(raff.getFilePointer() + theFile.length());
			long val = KlangUtil.getCRCFor(this.getParentFile().getFile(), startingplace + 4, raff.getFilePointer());
			Int4ByteUnsignedBE cr = new Int4ByteUnsignedBE();
			cr.setValue(val);
			cr.writeValueToFile(raff);
			//now update and reparse
			((EditableFileBase)this.getParentFile()).reparseFile(raff);
			this.chunkJustChanged(raff);
		} catch (BadValueException err) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
	}
	
	public void addChunkFromArray(byte[] bytes, String name, int position, RandomAccessFile raff) throws IOException {
		if (!(this.getParentFile() instanceof EditableFileBase)) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
		long newsize = (this.endPosition - this.startPosition) + bytes.length;
		if (newsize < 0) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
		try {
			//warn parent chunks
			this.chunkIsAboutToChangeSize(newsize, raff);
			//now make the addition
			long startingplace = 0;
			if (position > this.getSubChunks().size() + 2) {
				position = this.getSubChunks().size() + 1;
			}
			if (position == this.getSubChunks().size() + 1) {
				if (this.getSubChunks().size() == 0) {
					startingplace = this.endPosition;
				} else {
					startingplace = this.getSubChunks().get(position - 2).getEndPosition();
				}
			} else {
				startingplace = this.getSubChunks().get(position - 1).getStartPosition();
			}
			//insert CRC
			KlangUtil.insertIntoFile(new byte[4], this.getParentFile().getFile(), startingplace);
			//insert file
			KlangUtil.insertIntoFile(bytes, this.getParentFile().getFile(), startingplace);
			//insert name + size
			KlangUtil.insertIntoFile(new byte[8], this.getParentFile().getFile(), startingplace);
			raff.seek(startingplace);
			//write size
			Int4ByteUnsignedBE size = new Int4ByteUnsignedBE();
			size.setValue(bytes.length);
			size.writeValueToFile(raff);	
			//write name
			FourCC fcc = new FourCC(name);
			fcc.writeValueToFile(raff);
			//write CRC
			raff.seek(raff.getFilePointer() + bytes.length);
			long val = KlangUtil.getCRCFor(this.getParentFile().getFile(), startingplace + 4, raff.getFilePointer());
			Int4ByteUnsignedBE cr = new Int4ByteUnsignedBE();
			cr.setValue(val);
			cr.writeValueToFile(raff);
			//now update and reparse
			((EditableFileBase)this.getParentFile()).reparseFile(raff);
			this.chunkJustChanged(raff);
		} catch (BadValueException err) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
	}
	
	public void subChunkWillChangeSize(Chunk chunk, long newsize, RandomAccessFile raf) throws IOException {
		//rewrite size
		raf.seek(chunk.getStartPosition());
		Int4ByteUnsignedBE size = new Int4ByteUnsignedBE();
		try {
			size.setValue(newsize - 12);
			size.writeValueToFile(raf);
		} catch (BadValueException err) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
	}
	
	public void subChunkHasChanged(Chunk chunk, RandomAccessFile raff) throws IOException {
		if ((chunk.getEndPosition() - chunk.getStartPosition()) < 12) {
			//chunk has probably been deleted entirely, or error; ignore
			return;
		}
		try {
			//rewrite CRC
			raff.seek(chunk.getEndPosition() - 4);
			long crcStart = chunk.startPosition + 4;
			long crcEnd = chunk.endPosition - 4;
			long val = KlangUtil.getCRCFor(this.getParentFile().getFile(), crcStart, crcEnd);
			Int4ByteUnsignedBE cr = new Int4ByteUnsignedBE();
			cr.setValue(val);
			cr.writeValueToFile(raff);
		} catch (BadValueException err) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
	}
	
	public void editSubChunkName(Chunk chunk, String newName, RandomAccessFile raff) throws IOException {
		try {
			raff.seek(chunk.getStartPosition() + 4);
			FourCC fcc = new FourCC();
			fcc.setValueFromString(newName);
			fcc.writeValueToFile(raff);
			//have to redo CRC as well
			raff.seek(chunk.getEndPosition() - 4);
			long val = KlangUtil.getCRCFor(this.getParentFile().getFile(), chunk.getStartPosition() + 4, chunk.getEndPosition() - 4);
			Int4ByteUnsignedBE cr = new Int4ByteUnsignedBE();
			cr.setValue(val);
			cr.writeValueToFile(raff);
		} catch (BadValueException err) {
			throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
		}
	}
	
}
