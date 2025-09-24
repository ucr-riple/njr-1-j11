/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.test;

import java.io.*;
import malictus.klang.*;
import malictus.klang.file.*;
import malictus.klang.chunk.*;
import malictus.klang.primitives.*;

/**
 * FileInfoTest is a test to display several different kinds
 * of information regarding a file. It can be useful when testing
 * new file or chunk types. This tests read-only functions, not any file writing.
 * @author Jim Halliday
 */
public class FileInfoTest {
	
	private static final String aiffSamp = "samples/sample.aiff";
	private static final String aviSamp = "samples/sample.avi";
	private static final String rawSamp = "samples/sample.dat";
	private static final String pngSamp = "samples/sample.png";
	private static final String txtSamp = "samples/sample.txt";
	private static final String wavSamp = "samples/sample.wav";

	/**
	 * Run the FileInfoTest application.
	 * @param args currently not used
	 */
	public static void main(String[] args) {
		System.out.println("File info test - Using Klang Version " + KlangConstants.KLANG_VERSION + "\n");
		doParseOf(aiffSamp);
		doParseOf(aviSamp);
		doParseOf(rawSamp);
		doParseOf(pngSamp);
		doParseOf(txtSamp);
		doParseOf(wavSamp);
	}
	
	private static void doParseOf(String filename) {
		File x = new File(filename);
		System.out.println("\n\n---------------Parsing: " + x.getName());
		if (!x.exists() || !x.isFile() || !x.canRead()) {
			System.out.println("The specified file is invalid or cannot be read");
			System.exit(0);
		}
		//general info
		System.out.println("File name: " + x.getName());
		System.out.println("File location: " + x.getParent());
		System.out.print("File size: " + x.length() + " bytes");
		if (x.length() > 1024) {
			System.out.print(" (" + KlangUtil.stringForBytes(x.length()) + ")");
		}
		System.out.println();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(x, "r");
			KlangFile kf = KlangFileFactory.makeNewKlangFile(x, raf);
			raf.close();
			System.out.println("File type: " + kf.getFileType());
			if (kf.getFileLevelData().size() > 0) {
				System.out.println("File-level data: ");
				int counter = 0;
				while (counter < kf.getFileLevelData().size()) {
					displayPrimDataFor(kf.getFileLevelData().get(counter));
					counter = counter + 1;
				}
			}
			System.out.println("--Chunks Section--");
			int counter = 0;
			while (counter < kf.getChunks().size()) {
				outputDataFor(kf.getChunks().get(counter));
				counter = counter + 1;
			}
		} catch (Exception err) {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception rr) {}
			}
			err.printStackTrace();
			System.out.println("ERROR");
		}
	}
	
	/**
	 * Print out information about the given chunk, including any subchunks.
	 * @param chunk the chunk to be examined
	 */
	private static void outputDataFor(Chunk chunk) {
		System.out.println("\n---Chunk---");
		System.out.println("Chunk name: " + chunk.getChunkName());
		System.out.println("Chunk description: " + chunk.getChunkNameDescription());
		if (chunk.getParentChunk() == null) {
			System.out.println("Chunk ancestry: TOP LEVEL");
		} else {
			System.out.println("Chunk ancestry: " + getChunkAncestry(chunk, ""));
		}
		System.out.println("Chunk type: " + chunk.getChunkTypeDescription());
		System.out.println("Start position: " + chunk.getStartPosition());
		System.out.println("End position: " + chunk.getEndPosition());
		System.out.println("Data start position: " + chunk.getDataStartPosition());
		System.out.println("Data end position: " + chunk.getDataEndPosition());
		if (chunk.getPrimitiveData().size() > 0) {
			System.out.println("Chunk data: ");
			int counter = 0;
			while (counter < chunk.getPrimitiveData().size()) {
				displayPrimDataFor(chunk.getPrimitiveData().get(counter));
				counter = counter + 1;
			}
		}
		int counter = 0;
		if (chunk instanceof ContainerChunk) {
			ContainerChunk cc = (ContainerChunk)chunk;
			while (counter < cc.getSubChunks().size()) {
				outputDataFor(cc.getSubChunks().get(counter));
				counter = counter + 1;
			}
		}
	}
	
	/**
	 * Print out the chunk ancestry for this chunk.
	 * @param chunk the chunk in question
	 * @param chunkSoFar the chunk ancestry string
	 * @return the total chunk ancestry
	 */
	private static String getChunkAncestry(Chunk chunk, String chunkSoFar) {
		if (chunk.getParentChunk() == null) {
			return chunkSoFar;
		}
		chunkSoFar = " --> " + chunk.getParentChunk().getChunkName() + chunkSoFar;
		chunkSoFar = getChunkAncestry(chunk.getParentChunk(), chunkSoFar);
		return chunkSoFar;
	}
	
	private static void displayPrimDataFor(PrimitiveData pd) {
		if (pd == null) {
			System.out.println("Primitive is null");
			return;
		}
		System.out.println("Name: " + pd.getName());
		if (!(pd.getPrimitive().valueExists())) {
			System.out.println("\t" + "Value: <NULL>");
		} else {
			try {
				System.out.println("\t" + "Value: " + pd.getPrimitive().writeValueToString());
			} catch (BadValueException err) {
				err.printStackTrace();
			}
		}
		if (pd.getCurrentValueString() != null) {
			System.out.println("\tValue String: " + pd.getCurrentValueString());
		}
		System.out.println("\t" + "Description: " + pd.getDescription());
	}

}
