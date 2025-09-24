/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.chunk;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.*;
import malictus.klang.file.*;
import malictus.klang.*;
import malictus.klang.primitives.*;

/**
 * A class that represents any chunk that has only plain text in it, but that can be encoded in any different text encoding.
 * @author Jim Halliday
 */
public class PlainTextChunk extends Chunk {
	
	String textEncoding = "";
	
	public PlainTextChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf, String textEncoding) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		this.textEncoding = textEncoding;
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */
		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		PlainString val  = null;
		try {
			long bytepos = raf.getFilePointer();
			val = new PlainString(raf, dataendpos, textEncoding, true);
			PrimitiveData valData = new PrimitiveData(val, KlangConstants.PRIMITIVE_DATA_PLAINSTRING_CHUNK_VALUE, bytepos, raf.getFilePointer());
			/*
			 * ADD PRIMITIVES TO VECTOR
			 */
			this.primitives.add(valData);
		} catch (BadValueException er) {
			er.printStackTrace();
			try {
				val = new PlainString("", textEncoding, true);
			} catch (BadValueException e) {
				throw new IOException();
			}
		}	
	}
	
	public void setTextEncoding(String newEncoding) {
		this.textEncoding = newEncoding;
	}
	
	public String getTextEncoding() {
		return textEncoding;
	}
	
	/**
	 * Overwrite of this method in Chunk class to write string.
	 * @param raff a pointer to the file (position is not important)
	 * @throws IOException if any read/write error occurs
	 */
	public void reparseChunkPrimitives(RandomAccessFile raff) throws IOException {
		if ((this.primitives == null) || (this.primitives.size() == 0)) {
			//nothing to do!
			return;
		}
		Primitive p = primitives.get(0).getPrimitive();		
		long oldsize = this.getDataEndPosition() - this.getDataStartPosition();
		long pos = 0;
		try {
			long newsize = p.writeValueToString().getBytes(textEncoding).length;
			boolean needsPad = false;
			if (this.getParentChunk() != null) {
				if (this.getParentChunk().usesPadByte()) {
					newsize = KlangUtil.adjustForPadByte(newsize);
					needsPad = true;
				}
			}
			long totnewsize = newsize + (this.getEndPosition() - this.getStartPosition()) - (this.getDataEndPosition() - this.getDataStartPosition());
			if (newsize < oldsize) {
				this.chunkIsAboutToChangeSize(totnewsize, raff);
				KlangUtil.deleteFromFile(this.getParentFile().getFile(), this.getDataStartPosition(), this.getDataStartPosition() + (oldsize - newsize));	
			} else if (newsize > oldsize) {
				this.chunkIsAboutToChangeSize(totnewsize, raff);
				int amt = (int)(newsize - oldsize);
				byte[] newbyte = new byte[amt];
				KlangUtil.insertIntoFile(newbyte, this.getParentFile().getFile(), this.getDataStartPosition());
			}
			//write null to pad byte
			if (needsPad) {
				raff.seek(this.getDataStartPosition() + newsize - 1);
				raff.write(0);
			}
			raff.seek(this.getDataStartPosition());
			pos = raff.getFilePointer();
			//write value back to the file
			p.writeValueToFile(raff);
		} catch (BadValueException err) {
			err.printStackTrace();
			throw new IOException(KlangConstants.ERROR_BAD_VALUE_EXCEPTION_WEIRDVALUE);
		} catch (CharacterCodingException er) {
			er.printStackTrace();
			throw new IOException(KlangConstants.ERROR_BAD_VALUE_EXCEPTION_WEIRDVALUE);
		}
		//reparse the file
		((EditableFileBase)this.getParentFile()).reparseFile(raff);
		Chunk c = KlangFile.findDeepestChunkFor(this.getParentFile().getChunks(), pos);
		c.chunkJustChanged(raff);
	}	
	
}
