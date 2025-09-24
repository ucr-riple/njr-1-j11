/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.test;

import java.io.*;

import malictus.klang.KlangConstants;
import malictus.klang.file.*;

/**
 * A simple test which loads in samples of files of different types to see if 
 * the application can correctly identify the file type.
 * @author Jim Halliday
 */
public class KlangFileFactoryTest {

	private static final String aiffSamp = "samples/sample.aiff";
	private static final String aviSamp = "samples/sample.avi";
	private static final String rawSamp = "samples/sample.dat";
	private static final String pngSamp = "samples/sample.png";
	private static final String txtSamp = "samples/sample.txt";
	private static final String wavSamp = "samples/sample.wav";
	
	public static void main(String[] args) {
		new KlangFileFactoryTest();
	}
	
	public KlangFileFactoryTest() {
		RandomAccessFile raf = null;
		try {
			System.out.println("KlangFileFactory test - Using Klang Version " + KlangConstants.KLANG_VERSION + "\n");
			File x = new File(aiffSamp);
			raf = new RandomAccessFile(x, "r");
			KlangFile samp = KlangFileFactory.makeNewKlangFile(new File(aiffSamp), raf);
			System.out.println(aiffSamp + ": CLASS: " + samp.getClass().getName() + " (" + samp.getFileType() + ") ");
			raf.close();
			
			x = new File(aviSamp);
			raf = new RandomAccessFile(x, "r");
			samp = KlangFileFactory.makeNewKlangFile(new File(aviSamp), raf);
			System.out.println(aviSamp + ": CLASS: " + samp.getClass().getName() + " (" + samp.getFileType() + ") ");
			raf.close();
			
			x = new File(rawSamp);
			raf = new RandomAccessFile(x, "r");
			samp = KlangFileFactory.makeNewKlangFile(new File(rawSamp), raf);
			System.out.println(rawSamp + ": CLASS: " + samp.getClass().getName() + " (" + samp.getFileType() + ") ");
			raf.close();
			
			x = new File(pngSamp);
			raf = new RandomAccessFile(x, "r");
			samp = KlangFileFactory.makeNewKlangFile(new File(pngSamp), raf);
			System.out.println(pngSamp + ": CLASS: " + samp.getClass().getName() + " (" + samp.getFileType() + ") ");
			raf.close();
			
			x = new File(txtSamp);
			raf = new RandomAccessFile(x, "r");
			samp = KlangFileFactory.makeNewKlangFile(new File(txtSamp), raf);
			System.out.println(txtSamp + ": CLASS: " + samp.getClass().getName() + " (" + samp.getFileType() + ") ");
			raf.close();
			
			x = new File(wavSamp);
			raf = new RandomAccessFile(x, "r");
			samp = KlangFileFactory.makeNewKlangFile(new File(wavSamp), raf);
			System.out.println(wavSamp + ": CLASS: " + samp.getClass().getName() + " (" + samp.getFileType() + ") ");
			raf.close();
			
		} catch (Exception err) {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception stupid) {}
			}
			err.printStackTrace();
		}
	}
	
	
}
