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
 * A class that represents any chunk that has data that consists of a single null-terminated string.
 * @author Jim Halliday
 */
public class NullStringChunk extends Chunk {
	
	public NullStringChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf, String textEncoding, boolean canChangeEncoding) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */
		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		NullTerminatedString val  = null;
		long bytepos = raf.getFilePointer();
		try {
			val = new NullTerminatedString(raf, dataendpos, textEncoding, canChangeEncoding);
			PrimitiveData valData = new PrimitiveData(val, KlangConstants.PRIMITIVE_DATA_NULLSTRING_CHUNK_VALUE, bytepos, raf.getFilePointer());
			/*
			 * ADD PRIMITIVES TO VECTOR
			 */
			this.primitives.add(valData);
		} catch (BadValueException er) {
			val = new NullTerminatedString(textEncoding, canChangeEncoding);
			PrimitiveData valData = new PrimitiveData(val, KlangConstants.PRIMITIVE_DATA_NULLSTRING_CHUNK_VALUE, bytepos, null);
			this.primitives.add(valData);
		}	
	}
	
	/**
	 * Overwrite of this method in Chunk class to write null terminated string.
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
			long newsize = p.writeValueToString().getBytes(((StringPrim)p).getTextEncoding()).length + 1;
			if (this.getParentChunk() != null) {
				if (this.getParentChunk().usesPadByte()) {
					newsize = KlangUtil.adjustForPadByte(newsize);
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
		//now update and reparse
		((EditableFileBase)this.getParentFile()).reparseFile(raff);
		Chunk c = KlangFile.findDeepestChunkFor(this.getParentFile().getChunks(), pos);
		c.chunkJustChanged(raff);
	}	
	
}
