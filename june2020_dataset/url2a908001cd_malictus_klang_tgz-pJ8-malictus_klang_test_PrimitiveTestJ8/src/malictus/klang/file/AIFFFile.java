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

/**
 * A class that represents an entire AIFF File.
 * @author Jim Halliday
 */
public class AIFFFile extends FORMFile {

	/**
	 * Create an AIFFFile object from the given File object.
	 * @param theFile the file to read
	 * @param raf a pointer to the file (position isn't important)
	 * @throws IOException if error occurs
	 */
	public AIFFFile(File theFile, RandomAccessFile raf) throws IOException {
		super(theFile, raf);
		if (!AIFFFile.fileIsAIFFFile(raf)) {
			throw new IOException(KlangConstants.ERROR_FILE_IS_INCORRECT_TYPE);
		}
		//chunks are already parsed at FORMFile level
	}
	
	/**
	 * Test to tell if this file is truly an AIFF file.
	 * @param raf access to file
	 * @return true if file is AIFF file, and false otherwise
	 * @throws IOException if file error occurs
	 */
	public static boolean fileIsAIFFFile(RandomAccessFile raf) throws IOException {
		if (raf.length() < 12) {
			return false;
		}
		raf.seek(0);
		try {
			FourCC fcc = new FourCC();
			fcc.setValueFromFile(raf);
			if (!fcc.getValue().equals("FORM")) {
				return false;
			}
			raf.skipBytes(4);
			fcc.setValueFromFile(raf);
			if (!fcc.getValue().equals("AIFF")) {
				return false;
			}
			return true;
		} catch (BadValueException err) {
			return false;
		}
	}
	
}
