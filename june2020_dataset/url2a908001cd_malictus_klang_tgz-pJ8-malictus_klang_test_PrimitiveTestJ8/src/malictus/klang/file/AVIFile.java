/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.file;

import java.io.*;

import malictus.klang.KlangConstants;
import malictus.klang.primitives.BadValueException;
import malictus.klang.primitives.FourCC;

/**
 * A class that represents an entire AVI File.
 * @author Jim Halliday
 */
public class AVIFile extends RIFFFile {

	/**
	 * Create a AVIFile object from the given File object.
	 * @param theFile the file to read
	 * @param raf a pointer to the file (position isn't important)
	 * @throws IOException if error occurs
	 */
	public AVIFile(File theFile, RandomAccessFile raf) throws IOException {
		super(theFile, raf);
		if (!AVIFile.fileIsAVIFile(raf)) {
			throw new IOException(KlangConstants.ERROR_FILE_IS_INCORRECT_TYPE);
		}
		//chunks are already parsed at RIFFFile level
	}
	
	/**
	 * Test to tell if this file is truly an AVI file.
	 * @param raf access to file
	 * @return true if file is AVI file, and false otherwise
	 * @throws IOException if file error occurs
	 */
	public static boolean fileIsAVIFile(RandomAccessFile raf) throws IOException {
		if (raf.length() < 12) {
			return false;
		}
		raf.seek(0);
		try {
			FourCC fcc = new FourCC();
			fcc.setValueFromFile(raf);
			if (!fcc.getValue().equals("RIFF")) {
				return false;
			}
			raf.skipBytes(4);
			fcc.setValueFromFile(raf);
			if (!fcc.getValue().equals("AVI ")) {
				return false;
			}
			return true;
		} catch (BadValueException err) {
			return false;
		}
	}
	
}
