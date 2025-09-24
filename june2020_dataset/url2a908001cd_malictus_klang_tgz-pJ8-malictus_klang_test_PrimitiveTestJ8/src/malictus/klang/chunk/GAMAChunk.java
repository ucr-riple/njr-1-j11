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
 * A class that represents the GAMA chunk in a PNG file.
 * @author Jim Halliday
 */
public class GAMAChunk extends Chunk {
	
	public GAMAChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */
		Primitive gamma = new Int4ByteUnsignedBE();		
		PrimitiveData gammaData;
		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		if (dataendpos >= (raf.getFilePointer() + 4)) {
			try {
				long bytepos = raf.getFilePointer();
				gamma.setValueFromFile(raf);
				gammaData = new PrimitiveData(gamma, KlangConstants.PRIMITIVE_DATA_GAMA_CHUNK_GAMMA, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				gammaData = new PrimitiveData(gamma, KlangConstants.PRIMITIVE_DATA_GAMA_CHUNK_GAMMA, null, null);
			}
		} else {
			gammaData = new PrimitiveData(gamma, KlangConstants.PRIMITIVE_DATA_GAMA_CHUNK_GAMMA, null, null);
		}
		/*
		 * ADD PRIMITIVES TO VECTOR
		 */
		this.primitives.add(gammaData);
	}
	
}
