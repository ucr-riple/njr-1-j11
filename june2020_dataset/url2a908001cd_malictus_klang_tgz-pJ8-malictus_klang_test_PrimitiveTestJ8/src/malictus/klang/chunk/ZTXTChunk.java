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
 * A class that represents a zTXt chunk in a PNG file.
 * @author Jim Halliday
 */
public class ZTXTChunk extends Chunk {
	
	public ZTXTChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */
		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		NullTerminatedString keyword  = null;
		IntByteUnsigned method = null;
		CompressedString text = null;
		PrimitiveData keywordData = null;
		PrimitiveData methodData = null;
		PrimitiveData textData = null;
		long bytepos = raf.getFilePointer();
		boolean keepgoing = true;
		try {
			//always Latin-1 according to the spec
			keyword = new NullTerminatedString(raf, dataendpos, "ISO-8859-1", false);
			keywordData = new PrimitiveData(keyword, KlangConstants.PRIMITIVE_DATA_TEXT_CHUNK_KEYWORD, bytepos, raf.getFilePointer() + 1);
			//skip null byte
			raf.seek(raf.getFilePointer() + 1);
		} catch (BadValueException er) {
			keepgoing = false;
			keyword = new NullTerminatedString("ISO-8859-1", false);
			keywordData = new PrimitiveData(keyword, KlangConstants.PRIMITIVE_DATA_TEXT_CHUNK_KEYWORD, bytepos, null);
		}
		if (keepgoing && (dataendpos >= (raf.getFilePointer() + 1))) {
			try {
				bytepos = raf.getFilePointer();
				method = new IntByteUnsigned(raf);
				methodData = new PrimitiveData(method, KlangConstants.PRIMITIVE_DATA_ZTXT_CHUNK_COMP_METHOD, bytepos, raf.getFilePointer());
			} catch (Exception x) {
				try {
					method = new IntByteUnsigned();
					methodData = new PrimitiveData(method, KlangConstants.PRIMITIVE_DATA_ZTXT_CHUNK_COMP_METHOD, bytepos, raf.getFilePointer());
				} catch (Exception e) {} // shouldn't happen
			}
		} else {
			try {
				method = new IntByteUnsigned();
				methodData = new PrimitiveData(method, KlangConstants.PRIMITIVE_DATA_ZTXT_CHUNK_COMP_METHOD, bytepos, raf.getFilePointer());
			} catch (Exception e) {} // shouldn't happen
		}
		
		if (method.valueExists() && (method.getValue() == 0) && keepgoing && (raf.getFilePointer() < dataendpos) ) {
			try {
				bytepos = raf.getFilePointer();
				text = new CompressedString(raf, dataendpos, "ISO-8859-1", false);
				textData = new PrimitiveData(text, KlangConstants.PRIMITIVE_DATA_TEXT_CHUNK_VALUE, bytepos, raf.getFilePointer());
			} catch (Exception x) {
				try {
					text = new CompressedString("", "ISO-8859-1", false);
					textData = new PrimitiveData(text, KlangConstants.PRIMITIVE_DATA_TEXT_CHUNK_VALUE, bytepos, raf.getFilePointer());
				} catch (Exception e) {} // shouldn't happen
			}
		} else {
			try {
				text = new CompressedString("", "ISO-8859-1", false);
				textData = new PrimitiveData(text, KlangConstants.PRIMITIVE_DATA_TEXT_CHUNK_VALUE, bytepos, raf.getFilePointer());
			} catch (Exception e) {} // shouldn't happen
		}
		
		/*
		 * ADD PRIMITIVES TO VECTOR
		 */
		this.primitives.add(keywordData);
		this.primitives.add(methodData);
		this.primitives.add(textData);
	}
	
	/**
	 * Overwrite of this method in Chunk class to write text data.
	 * @param raff a pointer to the file (position is not important)
	 * @throws IOException if any read/write error occurs
	 */
	public void reparseChunkPrimitives(RandomAccessFile raff) throws IOException {
		if ((this.primitives == null) || (this.primitives.size() == 0)) {
			//nothing to do!
			return;
		}
		Primitive p1 = primitives.get(0).getPrimitive();
		Primitive p2 = primitives.get(1).getPrimitive();
		Primitive p3 = primitives.get(2).getPrimitive();
		long oldsize = this.getDataEndPosition() - this.getDataStartPosition();
		long pos = 0;
		try {
			long newsize = 0;
			if (p1.valueExists()) {
				newsize = p1.writeValueToString().getBytes(((StringPrim)p1).getTextEncoding()).length + 1;
			}
			if (p2.valueExists()) {
				newsize = newsize + 1;
			}
			if (p3.valueExists()) {
				newsize = newsize + p3.writeValueToString().getBytes(((StringPrim)p3).getTextEncoding()).length;
			}
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
			//write values back to the file
			p1.writeValueToFile(raff);
			if (p2.valueExists()) {
				p2.writeValueToFile(raff);
			}	
			if (p3.valueExists()) {
				p3.writeValueToFile(raff);
			}
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
