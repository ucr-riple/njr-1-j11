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
 * A class that represents the FACT chunk in a WAV file.
 * @author Jim Halliday
 */
public class FACTChunk extends Chunk {
	
	public FACTChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, Boolean isBigEndian, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, isBigEndian, raf);
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */
		Primitive numsamps;
		if (isBigEndian) {
			numsamps = new Int4ByteUnsignedBE();
		} else {
			numsamps = new Int4ByteUnsignedLE();
		}
		
		PrimitiveData numsampsData;
		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		if (endpos >= (raf.getFilePointer() + 4)) {
			try {
				long bytepos = raf.getFilePointer();
				numsamps.setValueFromFile(raf);
				numsampsData = new PrimitiveData(numsamps, KlangConstants.PRIMITIVE_DATA_FACT_CHUNK_NUM_SAMPS, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				numsampsData = new PrimitiveData(numsamps, KlangConstants.PRIMITIVE_DATA_FACT_CHUNK_NUM_SAMPS, null, null);
			}
		} else {
			numsampsData = new PrimitiveData(numsamps, KlangConstants.PRIMITIVE_DATA_FACT_CHUNK_NUM_SAMPS, null, null);
		}
		/*
		 * ADD PRIMITIVES TO VECTOR
		 */
		this.primitives.add(numsampsData);
	}
	
}
