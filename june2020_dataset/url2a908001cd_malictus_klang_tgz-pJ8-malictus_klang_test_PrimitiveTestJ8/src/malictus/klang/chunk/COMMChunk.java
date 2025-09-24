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
 * A class that represents the COMM chunk in an AIFF file.
 * @author Jim Halliday
 */
public class COMMChunk extends Chunk {
	
	public COMMChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */
		Primitive numchannels= new Int2ByteUnsignedBE();
		PrimitiveData numchannelsData;
		Primitive numframes= new Int4ByteUnsignedBE();
		PrimitiveData numframesData;
		Primitive sampsize = new Int2ByteUnsignedBE();
		PrimitiveData sampsizeData;
		Primitive samprate = new IEEE80ByteFloat();
		PrimitiveData samprateData;
		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		boolean keepgoing = true;
		if (endpos >= (raf.getFilePointer() + 2)) {
			try {
				long bytepos = raf.getFilePointer();
				numchannels.setValueFromFile(raf);
				numchannelsData = new PrimitiveData(numchannels, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_NUM_CHANNELS, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				numchannelsData = new PrimitiveData(numchannels, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_NUM_CHANNELS, null, null);
			}
		} else {
			numchannelsData = new PrimitiveData(numchannels, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_NUM_CHANNELS, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				numframes.setValueFromFile(raf);
				numframesData = new PrimitiveData(numframes, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_NUM_FRAMES, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				numframesData = new PrimitiveData(numframes, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_NUM_FRAMES, null, null);
			}
		} else {
			numframesData = new PrimitiveData(numframes, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_NUM_FRAMES, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 2))) {
			try {
				long bytepos = raf.getFilePointer();
				sampsize.setValueFromFile(raf);
				sampsizeData = new PrimitiveData(sampsize, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_SIZE, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				sampsizeData = new PrimitiveData(sampsize, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_SIZE, null, null);
			}
		} else {
			sampsizeData = new PrimitiveData(sampsize, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_SIZE, null, null);
		}
		if (keepgoing && (endpos >= (raf.getFilePointer() + 10))) {
			try {
				long bytepos = raf.getFilePointer();
				samprate.setValueFromFile(raf);
				samprateData = new PrimitiveData(samprate, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_RATE, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				keepgoing = false;
				samprateData = new PrimitiveData(samprate, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_RATE, null, null);
			}
		} else {
			samprateData = new PrimitiveData(samprate, KlangConstants.PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_RATE, null, null);
		}
		/*
		 * ADD PRIMITIVES TO VECTOR
		 */
		this.primitives.add(numchannelsData);
		this.primitives.add(numframesData);
		this.primitives.add(sampsizeData);
		this.primitives.add(samprateData);
	}
	
}
