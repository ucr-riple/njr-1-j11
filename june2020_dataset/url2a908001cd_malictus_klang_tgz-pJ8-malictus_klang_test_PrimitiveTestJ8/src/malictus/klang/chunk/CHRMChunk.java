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
 * A class that represents the CHRM chunk in a PNG file.
 * @author Jim Halliday
 */
public class CHRMChunk extends Chunk {
	
	public CHRMChunk(KlangFile parentFile, long startpos, long endpos, long datastartpos, long dataendpos, int chunkType, String chunkName, ContainerChunk parentChunk, RandomAccessFile raf) throws IOException {
		super(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		raf.seek(datastartpos);
		/*
		 * LOAD PRIMITIVE OBJECTS (UNINITIALIZED)
		 */		
		Primitive whitepointx = new Int4ByteUnsignedBE();		
		PrimitiveData whitepointxData;
		
		Primitive whitepointy = new Int4ByteUnsignedBE();		
		PrimitiveData whitepointyData;
		
		Primitive redx = new Int4ByteUnsignedBE();		
		PrimitiveData redxData;
		
		Primitive redy = new Int4ByteUnsignedBE();		
		PrimitiveData redyData;
		
		Primitive greenx = new Int4ByteUnsignedBE();		
		PrimitiveData greenxData;
		
		Primitive greeny = new Int4ByteUnsignedBE();		
		PrimitiveData greenyData;
		
		Primitive bluex = new Int4ByteUnsignedBE();		
		PrimitiveData bluexData;
		
		Primitive bluey = new Int4ByteUnsignedBE();		
		PrimitiveData blueyData;

		/*
		 * READ FROM FILE TO FILL PRIMITIVE DATA
		 */
		boolean keepgoing = true;
		if (dataendpos >= (raf.getFilePointer() + 4)) {
			try {
				long bytepos = raf.getFilePointer();
				whitepointx.setValueFromFile(raf);
				whitepointxData = new PrimitiveData(whitepointx, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTX, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				whitepointxData = new PrimitiveData(whitepointx, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTX, null, null);
			}
		} else {
			whitepointxData = new PrimitiveData(whitepointx, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTX, null, null);
		}
		
		if (keepgoing && (dataendpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				whitepointy.setValueFromFile(raf);
				whitepointyData = new PrimitiveData(whitepointy, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTY, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				whitepointyData = new PrimitiveData(whitepointy, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTY, null, null);
			}
		} else {
			whitepointyData = new PrimitiveData(whitepointy, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTY, null, null);
		}
		
		if (keepgoing && (dataendpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				redx.setValueFromFile(raf);
				redxData = new PrimitiveData(redx, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_REDX, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				redxData = new PrimitiveData(redx, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_REDX, null, null);
			}
		} else {
			redxData = new PrimitiveData(redx, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_REDX, null, null);
		}
		
		if (keepgoing && (dataendpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				redy.setValueFromFile(raf);
				redyData = new PrimitiveData(redy, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_REDY, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				redyData = new PrimitiveData(redy, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_REDY, null, null);
			}
		} else {
			redyData = new PrimitiveData(redy, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_REDY, null, null);
		}
		
		if (keepgoing && (dataendpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				greenx.setValueFromFile(raf);
				greenxData = new PrimitiveData(greenx, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_GREENX, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				greenxData = new PrimitiveData(greenx, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_GREENX, null, null);
			}
		} else {
			greenxData = new PrimitiveData(greenx, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_GREENX, null, null);
		}
		
		if (keepgoing && (dataendpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				greeny.setValueFromFile(raf);
				greenyData = new PrimitiveData(greeny, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_GREENY, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				greenyData = new PrimitiveData(greeny, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_GREENY, null, null);
			}
		} else {
			greenyData = new PrimitiveData(greeny, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_GREENY, null, null);
		}
		
		if (keepgoing && (dataendpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				bluex.setValueFromFile(raf);
				bluexData = new PrimitiveData(bluex, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_BLUEX, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				bluexData = new PrimitiveData(bluex, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_BLUEX, null, null);
			}
		} else {
			bluexData = new PrimitiveData(bluex, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_BLUEX, null, null);
		}
		
		if (keepgoing && (dataendpos >= (raf.getFilePointer() + 4))) {
			try {
				long bytepos = raf.getFilePointer();
				bluey.setValueFromFile(raf);
				blueyData = new PrimitiveData(bluey, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_BLUEY, bytepos, raf.getFilePointer());
			} catch (BadValueException er) {
				blueyData = new PrimitiveData(bluey, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_BLUEY, null, null);
			}
		} else {
			blueyData = new PrimitiveData(bluey, KlangConstants.PRIMITIVE_DATA_CHRM_CHUNK_BLUEY, null, null);
		}
		/*
		 * ADD PRIMITIVES TO VECTOR
		 */
		this.primitives.add(whitepointxData);
		this.primitives.add(whitepointyData);
		this.primitives.add(redxData);
		this.primitives.add(redyData);
		this.primitives.add(greenxData);
		this.primitives.add(greenyData);
		this.primitives.add(bluexData);
		this.primitives.add(blueyData);
	}
	
}
