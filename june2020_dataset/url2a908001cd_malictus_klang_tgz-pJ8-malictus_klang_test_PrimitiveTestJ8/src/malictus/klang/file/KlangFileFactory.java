/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.file;

import java.io.*;
import malictus.klang.*;
import malictus.klang.ui.TextEncodingDialog;
import malictus.klang.ui.klangeditor.KlangEditor;

/**
 * This class takes a File object and returns a KlangFile object.
 * @author Jim Halliday
 */
public class KlangFileFactory {

	private KlangFileFactory() {}
	
	/**
	 * Generate a KlangFile object based on the given file.
	 * @param theFile any file object
	 * @param raf a pointer to the file (position not important)
	 * @return a KlangFile object based on the input file
	 * @throws IOException if errors occur
	 */
	public static KlangFile makeNewKlangFile(File theFile, RandomAccessFile raf) throws IOException {
		if (!theFile.exists()) {
			throw new IOException(KlangConstants.ERROR_NO_SUCH_FILE);
		}
		if (!theFile.isFile()) {
			throw new IOException(KlangConstants.ERROR_FILE_IS_DIRECTORY);
		}
		if (!theFile.canRead()) {
			throw new IOException(KlangConstants.ERROR_FILE_CANT_BE_READ);
		}
		raf.seek(0);
		try {
			//tests should be in the best order to ensure that false positives do not occur!
			//PNG File?
			if (PNGFile.fileIsPNGFile(raf)) {
				return new PNGFile(theFile, raf);
			}
			//WAVE File?
			if (WAVEFile.fileIsWAVEFile(raf)) {
				return new WAVEFile(theFile, raf);
			}
			//AVI File?
			if (AVIFile.fileIsAVIFile(raf)) {
				return new AVIFile(theFile, raf);
			}
			//AIFF File?
			if (AIFFFile.fileIsAIFFFile(raf)) {
				return new AIFFFile(theFile, raf);
			}
			//Generic RIFF (test must be attempted AFTER WAV and AVI tests!)
			if (RIFFFile.fileIsRIFFFile(raf)) {
				return new RIFFFile(theFile, raf);
			}
			//Generic FORM (test must be attempted AFTER AIFF test!)
			if (FORMFile.fileIsFORMFile(raf)) {
				return new FORMFile(theFile, raf);
			}
			
			//file is unknown, but might just be text, ask user what they think it is
			//first, check for zero length and a known extension
			if (theFile.length() == 0) {
				//assume text could go here
				return new PlainTextFile(theFile, raf, "UTF-8");				
			}
			String encoding = "";
			if (theFile.length() < 50000000) {
				//will attempt to open entire file as a single string, so we can only do that on smallish files
				if (TextEncodingDialog.currentEncoding.equals("")) {
					if (KlangEditor.isManualTextMode()) {
						TextEncodingDialog ted = new TextEncodingDialog(null);
						encoding = ted.getChosenEncoding();
					} else {
						encoding = KlangUtil.determineEncodingFor(theFile, null, null);
					}
					if (!(encoding.equals(KlangConstants.KLANG_TEXTENC_DIALOG_NONE))) {
						TextEncodingDialog.currentEncoding = encoding;	
					}
				} else {
					encoding = TextEncodingDialog.currentEncoding;
				}
			}
			if (encoding.equals("") || encoding.equals(KlangConstants.KLANG_TEXTENC_DIALOG_NONE)) {
				return new RawFile(theFile, raf);
			} else {
				return new PlainTextFile(theFile, raf, encoding);
			}	
		} catch (IOException err) {
			err.printStackTrace();
			return new RawFile(theFile, raf);
		}
		//Generic file (unknown)
		
	}
	
}
