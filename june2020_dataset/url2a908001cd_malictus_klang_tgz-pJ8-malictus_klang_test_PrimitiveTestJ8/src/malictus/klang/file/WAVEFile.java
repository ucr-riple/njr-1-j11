/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.file;

import java.io.*;
import malictus.klang.KlangConstants;
import malictus.klang.primitives.*;
import malictus.klang.chunk.*;

/**
 * A class that represents an entire WAVE File.
 * @author Jim Halliday
 */
public class WAVEFile extends RIFFFile {

	/**
	 * Create a WAVEFile object from the given File object.
	 * @param theFile the file to read
	 * @param raf a pointer to the file (position not important)
	 * @throws IOException if error occurs
	 */
	public WAVEFile(File theFile, RandomAccessFile raf) throws IOException {
		super(theFile, raf);
		if (!WAVEFile.fileIsWAVEFile(raf)) {
			throw new IOException(KlangConstants.ERROR_FILE_IS_INCORRECT_TYPE);
		}
		//chunks is already parsed at RIFFFile level; so just parse primitives
		if (getCompressionCodeAsPrimitive() != null) {
			this.fileLevelData.add(getCompressionCodeAsPrimitive());
			this.fileLevelData.add(getNumChannelsAsPrimitive());
			this.fileLevelData.add(getSampRateAsPrimitive());
		}
	}
	
	/**
	 * Test to tell if this file is truly a WAVE file.
	 * @param raf access to file
	 * @return true if file is WAVE file, and false otherwise
	 * @throws IOException if file error occurs
	 */
	public static boolean fileIsWAVEFile(RandomAccessFile raf) throws IOException {
		if (raf.length() < 12) {
			return false;
		}
		raf.seek(0);
		try {
			FourCC fcc = new FourCC();
			fcc.setValueFromFile(raf);
			if (!(fcc.getValue().equals("RIFF") || fcc.getValue().equals("RIFX"))) {
				return false;
			}
			raf.skipBytes(4);
			fcc.setValueFromFile(raf);
			if (!fcc.getValue().equals("WAVE")) {
				return false;
			}
			return true;
		} catch (BadValueException err) {
			return false;
		}
	}
	
	/**
	 * Return the compression code for this file, or null if none is found
	 * @return the compression code for this file, or null if none is found
	 */
	public Long getCompressionCode() {
		PrimitiveData pd = getCompressionCodeAsPrimitive();
		if (pd == null) {
			return null;
		}
		return new Long(((PrimitiveInt)pd.getPrimitive()).getValueAsLong());
	}
	
	/**
	 * Return the compression code for this file as a primitive.
	 * @return the compression code for this file as a primitive, or null if not found.
	 */
	private PrimitiveData getCompressionCodeAsPrimitive() {
		Chunk x = this.getChunkNamed(KlangConstants.CHUNKNAME_FMT, true);
		if (x == null) {
			return null;
		}
		PrimitiveData pd = x.getPrimitiveValueNamed(KlangConstants.PRIMITIVE_DATA_FMT_CHUNK_SUBFORMAT);
		if (!pd.getPrimitive().valueExists()) {
			pd = x.getPrimitiveValueNamed(KlangConstants.PRIMITIVE_DATA_FMT_CHUNK_COMP_CODE);
		}
		return pd;
	}
	
	/**
	 * Return the number of channels
	 * @return the number of channels
	 */
	public Long getNumChannels() {
		PrimitiveData pd = getNumChannelsAsPrimitive();
		if (pd == null) {
			return null;
		}
		return new Long(((PrimitiveInt)pd.getPrimitive()).getValueAsLong());
	}
	
	/**
	 * Return the number of channels for this file as a primitive.
	 * @return the number of channels for this file as a primitive, or null if not found.
	 */
	private PrimitiveData getNumChannelsAsPrimitive() {
		Chunk x = this.getChunkNamed(KlangConstants.CHUNKNAME_FMT, true);
		if (x == null) {
			return null;
		}
		PrimitiveData pd = x.getPrimitiveValueNamed(KlangConstants.PRIMITIVE_DATA_FMT_CHUNK_NUM_CHANNELS);
		return pd;
	}
	
	/**
	 * Return the sampling rate
	 * @return the sampling rate for each channel, in samples per second, or null if not found
	 */
	public Long getSampRate() {
		PrimitiveData pd = getSampRateAsPrimitive();
		if (pd == null) {
			return null;
		}
		return new Long(((PrimitiveInt)pd.getPrimitive()).getValueAsLong());
	}
	
	/**
	 * Return the sampling rate for this file as a primitive.
	 * @return the sampling rate for this file as a primitive, or null if not found.
	 */
	private PrimitiveData getSampRateAsPrimitive() {
		Chunk x = this.getChunkNamed(KlangConstants.CHUNKNAME_FMT, true);
		if (x == null) {
			return null;
		}
		PrimitiveData pd = x.getPrimitiveValueNamed(KlangConstants.PRIMITIVE_DATA_FMT_CHUNK_SAMP_RATE);
		return pd;
	}
	
}
