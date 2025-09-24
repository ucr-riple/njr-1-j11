/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.chunk;

import java.io.IOException;
import java.io.RandomAccessFile;
import malictus.klang.file.*;
import malictus.klang.*;
import malictus.klang.primitives.*;

/**
 * A class that represents the IHDR chunk in a PNG file.
 * @author Jim Halliday
 */
public class IHDRChunk extends Chunk {
	
	public IHDRChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */
		Primitive width = new Int4ByteUnsignedBE();
		PrimitiveData widthData;
		Primitive height = new Int4ByteUnsignedBE();
		PrimitiveData heightData;
		Primitive bitDepth = new IntByteUnsigned();
		PrimitiveData bitDepthData;
		Primitive colorType = new IntByteUnsigned();
		PrimitiveData colorTypeData;
		
		Primitive compress = new IntByteUnsigned();
		PrimitiveData compressData;
		Primitive filter = new IntByteUnsigned();
		PrimitiveData filterData;
		Primitive interlace = new IntByteUnsigned();
		PrimitiveData interlaceData;
		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		boolean keepgoing = true;
		if (endpos >= (raf.getFilePointer() + 4)) {
			try {
				long bytepos = raf.getFilePointer();
				width.setValueFromFile(raf);
				widthData = new PrimitiveData(width, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_WIDTH, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				widthData = new PrimitiveData(width, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_WIDTH, null, null);
			}
		} else {
			keepgoing = false;
			widthData = new PrimitiveData(width, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_WIDTH, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				height.setValueFromFile(raf);
				heightData = new PrimitiveData(height, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_HEIGHT, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				heightData = new PrimitiveData(height, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_HEIGHT, null, null);
			}
		} else {
			keepgoing = false;
			heightData = new PrimitiveData(height, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_HEIGHT, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 1))) {
			try {
				long bytepos = raf.getFilePointer();
				bitDepth.setValueFromFile(raf);
				bitDepthData = new PrimitiveData(bitDepth, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_BITDEPTH, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				bitDepthData = new PrimitiveData(bitDepth, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_BITDEPTH, null, null);
			}
		} else {
			keepgoing = false;
			bitDepthData = new PrimitiveData(bitDepth, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_BITDEPTH, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 1))) {
			try {
				long bytepos = raf.getFilePointer();
				colorType.setValueFromFile(raf);
				colorTypeData = new PrimitiveData(colorType, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_COLORTYPE, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				colorTypeData = new PrimitiveData(colorType, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_COLORTYPE, null, null);
			}
		} else {
			keepgoing = false;
			colorTypeData = new PrimitiveData(colorType, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_COLORTYPE, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 1))) {
			try {
				long bytepos = raf.getFilePointer();
				compress.setValueFromFile(raf);
				compressData = new PrimitiveData(compress, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_COMPRESS, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				compressData = new PrimitiveData(compress, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_COMPRESS, null, null);
			}
		} else {
			keepgoing = false;
			compressData = new PrimitiveData(compress, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_COMPRESS, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 1))) {
			try {
				long bytepos = raf.getFilePointer();
				filter.setValueFromFile(raf);
				filterData = new PrimitiveData(filter, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_FILTER, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				filterData = new PrimitiveData(filter, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_FILTER, null, null);
			}
		} else {
			keepgoing = false;
			filterData = new PrimitiveData(filter, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_FILTER, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 1))) {
			try {
				long bytepos = raf.getFilePointer();
				interlace.setValueFromFile(raf);
				interlaceData = new PrimitiveData(interlace, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_INTERLACE, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				interlaceData = new PrimitiveData(interlace, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_INTERLACE, null, null);
			}
		} else {
			keepgoing = false;
			interlaceData = new PrimitiveData(interlace, KlangConstants.PRIMITIVE_DATA_IHDR_CHUNK_INTERLACE, null, null);
		}
		/*
		 * ADD PRIMITIVES TO VECTOR
		 */
		this.primitives.add(widthData);
		this.primitives.add(heightData);
		this.primitives.add(bitDepthData);
		this.primitives.add(colorTypeData);
		this.primitives.add(compressData);
		this.primitives.add(filterData);
		this.primitives.add(interlaceData);
	}
}
