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
 * A class that represents the SRGB chunk in a PNG file.
 * @author Jim Halliday
 */
public class SRGBChunk extends Chunk {
	
	public SRGBChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */
		Primitive renderingIntent = new IntByteUnsigned();		
		PrimitiveData renderingIntentData;
		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		if (dataendpos >= (raf.getFilePointer() + 1)) {
			try {
				long bytepos = raf.getFilePointer();
				renderingIntent.setValueFromFile(raf);
				renderingIntentData = new PrimitiveData(renderingIntent, KlangConstants.PRIMITIVE_DATA_SRGB_CHUNK_RENDERING_INTENT, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				renderingIntentData = new PrimitiveData(renderingIntent, KlangConstants.PRIMITIVE_DATA_SRGB_CHUNK_RENDERING_INTENT, null, null);
			}
		} else {
			renderingIntentData = new PrimitiveData(renderingIntent, KlangConstants.PRIMITIVE_DATA_FACT_CHUNK_NUM_SAMPS, null, null);
		}
		/*
		 * ADD PRIMITIVES TO VECTOR
		 */
		this.primitives.add(renderingIntentData);
	}
	
}
