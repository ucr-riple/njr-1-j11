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
 * A class that represents the PHYS chunk in a PNG file.
 * @author Jim Halliday
 */
public class PHYSChunk extends Chunk {
	
	public PHYSChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */
		Primitive pixPerUnitX = new Int4ByteUnsignedBE();
		PrimitiveData pixPerUnitXData;
		Primitive pixPerUnitY = new Int4ByteUnsignedBE();
		PrimitiveData pixPerUnitYData;
		Primitive unitSpecifier = new IntByteUnsigned();
		PrimitiveData unitSpecifierData;
		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		boolean keepgoing = true;
		if (endpos >= (raf.getFilePointer() + 4)) {
			try {
				long bytepos = raf.getFilePointer();
				pixPerUnitX.setValueFromFile(raf);
				pixPerUnitXData = new PrimitiveData(pixPerUnitX, KlangConstants.PRIMITIVE_DATA_PHYS_CHUNK_PIX_X, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				pixPerUnitXData = new PrimitiveData(pixPerUnitX, KlangConstants.PRIMITIVE_DATA_PHYS_CHUNK_PIX_X, null, null);
			}
		} else {
			pixPerUnitXData = new PrimitiveData(pixPerUnitX, KlangConstants.PRIMITIVE_DATA_PHYS_CHUNK_PIX_X, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				pixPerUnitY.setValueFromFile(raf);
				pixPerUnitYData = new PrimitiveData(pixPerUnitY, KlangConstants.PRIMITIVE_DATA_PHYS_CHUNK_PIX_Y, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				pixPerUnitYData = new PrimitiveData(pixPerUnitY, KlangConstants.PRIMITIVE_DATA_PHYS_CHUNK_PIX_Y, null, null);
			}
		} else {
			pixPerUnitYData = new PrimitiveData(pixPerUnitY, KlangConstants.PRIMITIVE_DATA_PHYS_CHUNK_PIX_Y, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 1))) {
			try {
				long bytepos = raf.getFilePointer();
				unitSpecifier.setValueFromFile(raf);
				unitSpecifierData = new PrimitiveData(unitSpecifier, KlangConstants.PRIMITIVE_DATA_PHYS_CHUNK_UNIT, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				unitSpecifierData = new PrimitiveData(unitSpecifier, KlangConstants.PRIMITIVE_DATA_PHYS_CHUNK_UNIT, null, null);
			}
		} else {
			unitSpecifierData = new PrimitiveData(unitSpecifier, KlangConstants.PRIMITIVE_DATA_PHYS_CHUNK_UNIT, null, null);
		}
		/*
		 * ADD PRIMITIVES TO VECTOR
		 */
		this.primitives.add(pixPerUnitXData);
		this.primitives.add(pixPerUnitYData);
		this.primitives.add(unitSpecifierData);
	}
}
