/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang;

import java.awt.*;
import java.security.MessageDigest;
import java.text.*;
import java.util.zip.*;
import java.io.*;
import java.util.*;
import malictus.klang.primitives.BadValueException;
import malictus.klang.ui.KlangProgressWindow;

/**
 * General purpose utilities for Klang. Includes file utilites, string utilities, and 
 * checksum utilities.
 * @author Jim Halliday
 */
public class KlangUtil {

	private KlangUtil() {}
	
	/**
	 * Attempt to decompress some compressed data (using ZLIB)
	 * @param compressed the compressed byte array
	 * @return a new byte array with the data uncompressed
	 */
	public static byte[] inflate(byte[] compressed) throws BadValueException {
		Inflater decompresser = new Inflater();
		decompresser.setInput(compressed, 0, compressed.length);
		//not the best way to do this!
		//TODO fix
		Vector<byte[]> results = new Vector<byte[]>();
		boolean finished = false;
		try {
			while (finished == false) {
				byte[] result = new byte[KlangConstants.BUFFER_SIZE];
				int resultLength = decompresser.inflate(result);
				if (resultLength == 0) {
					finished = true;
				} else if (resultLength < result.length) {
					finished = true;
					byte[] endone = new byte[resultLength];
					System.arraycopy(result, 0, endone, 0, endone.length);
					results.add(endone);
				} else {
					results.add(result);
				}
			}
			decompresser.end();
		} catch (Exception err) {
			decompresser.end();
			throw new BadValueException();
		}
		int bytecounter = 0;
		int counter = 0;
		while (counter < results.size()) {
			bytecounter = bytecounter + results.get(counter).length;
			counter = counter + 1;
		}
		byte[] out = new byte[bytecounter];
		counter = 0;
		bytecounter = 0;
		while (counter < results.size()) {
			System.arraycopy(results.get(counter), 0, out, bytecounter, results.get(counter).length);
			bytecounter = bytecounter + results.get(counter).length;
			counter = counter + 1;
		}
		return out;		
	}
	
	/**
	 * Attempt to compress some uncompressed data (using ZLIB)
	 * @param uncompressed the uncompressed byte array
	 * @return a new byte array with the data compressed
	 */
	public static byte[] deflate(byte[] uncompressed) throws BadValueException {
		Deflater compresser = new Deflater();
		compresser.setInput(uncompressed, 0, uncompressed.length);
		compresser.finish();
		//not the best way to do this!
		//TODO fix
		Vector<byte[]> results = new Vector<byte[]>();
		boolean finished = false;
		try {
			while (finished == false) {
				byte[] result = new byte[KlangConstants.BUFFER_SIZE];
				int resultLength = compresser.deflate(result);
				if (resultLength == 0) {
					finished = true;
				} else if (resultLength < result.length) {
					finished = true;
					byte[] endone = new byte[resultLength];
					System.arraycopy(result, 0, endone, 0, endone.length);
					results.add(endone);
				} else {
					results.add(result);
				}
			}
			compresser.end();
		} catch (Exception err) {
			compresser.end();
			throw new BadValueException();
		}
		int bytecounter = 0;
		int counter = 0;
		while (counter < results.size()) {
			bytecounter = bytecounter + results.get(counter).length;
			counter = counter + 1;
		}
		byte[] out = new byte[bytecounter];
		counter = 0;
		bytecounter = 0;
		while (counter < results.size()) {
			System.arraycopy(results.get(counter), 0, out, bytecounter, results.get(counter).length);
			bytecounter = bytecounter + results.get(counter).length;
			counter = counter + 1;
		}
		return out;		
	}
	
	/**
	 * Retrieve a string vector of the most common text encodings for use in Klang combo boxes.
	 * @return a string vector of the most common text encodings for use in Klang combo boxes
	 */
	public static Vector<String> getCommonTextEncodings() {
		Vector<String> st = new Vector<String>();
		st.add("UTF-8");
		st.add("UTF-16");
		st.add("US-ASCII");
		st.add("ISO-8859-1");
		st.add("UTF-16BE");
		st.add("UTF-16LE");
		return st;
	}
	
	/**
	 * Given a file, determine if it seems to contain text, and if so, determine the
	 * text encoding. Note that currently this is a VERY VERY simple and unsophisticated way
	 * to do this!
	 * @param theFile the file that is being examined
	 * @param startposition the start byte position of the portion of the file to examine; if null, will start at file beginning
	 * @param endposition the end byte position of the poriton of the file to examine; if null, will end at file end
	 * @return the text encoding for the file, or KlangConstants.KLANG_TEXTENC_DIALOG_NONE if
	 * the file does not appear to contain text
	 */
	public static String determineEncodingFor(File theFile, Long startposition, Long endposition) {
		if (startposition == null) {
			startposition = 0L;
		}
		if (endposition == null) {
			endposition = theFile.length();
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(theFile, "r");
			raf.seek(startposition);
			int size = 400;
			if ((endposition - startposition) < 400) {
				size = (int)(endposition - startposition);
			}
			byte[] begin = new byte[size];
			raf.read(begin);
			raf.close();
			//check for UTF-16 bytes at very beginning
			if ( (begin[0] == -2) && (begin[1] == -1) ) {
				return "UTF-16";
			}
			//check for UTF-16BE and UTF-16LE
			//currently done in a very rudimentary way; look for a large number of zeroes
			int zerocount1 = 0;
			int zerocount2 = 0;
			int counter = 0;
			while (counter < begin.length) {
				if ((counter % 2) == 0) {
					if (begin[counter] == 0) {
						zerocount1 = zerocount1 + 1;
					}
				} else {
					if (begin[counter] == 0) {
						zerocount2 = zerocount2 + 1;
					}
				}
				counter = counter + 1;
			}
			int upthresh = begin.length / 2;
			upthresh = (int)(upthresh * 0.85f);
			int downthresh = begin.length / 2;
			downthresh = (int)(downthresh * 0.15f);
			if ((zerocount1 > upthresh) && (zerocount2 < downthresh)) {
				return "UTF-16BE";
			}
			if ((zerocount2 > upthresh) && (zerocount1 < downthresh)) {
				return "UTF-16LE";
			}
			//check for XHTML and XML encoding line
			String s = new String(begin).trim();
			if (s.startsWith("<?xml version")) {
				if (s.toLowerCase().contains("encoding=\"")) {
					int start = s.toLowerCase().indexOf("encoding=\"") + 10;
					String sub = s.substring(start);
					int stop = sub.indexOf("\"");
					if (stop != -1) {
						stop = start + stop;
					}
					String encoding = s.substring(start, stop);
					return encoding;
				}
			}
			if (s.toLowerCase().contains("charset=")) {
				int start = s.toLowerCase().indexOf("charset=") + 8;
				String sub = s.substring(start);
				int stop = sub.indexOf("\"");
				if (stop != -1) {
					stop = start + stop;
				}
				String encoding = s.substring(start, stop);
				if (encoding.length() < 40) {
					return encoding;
				}
			}
			//check for UTF-8
			int negcount = 0;
			counter = 0;
			while (counter < begin.length) {
				if (begin[counter] < 0) {
					negcount = negcount + 1;
				}
				counter = counter + 1;
			}
			int thresh = (int)(begin.length * 0.10f);
			if (negcount <= thresh) {
				return "UTF-8";
			}
		} catch (Exception err) {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e) {}
				return KlangConstants.KLANG_TEXTENC_DIALOG_NONE;
			}
		}
		//probably not text; give up
		return KlangConstants.KLANG_TEXTENC_DIALOG_NONE;
	}
	
	/**
	 * Return a human-readable indication of a size, given a number of bytes.
	 * @param bytes the number of bytes
	 * @return a string such as (1.23GB, 1.4MB, etc.)
	 */
	public static String stringForBytes(long bytes) {
		DecimalFormat deci = new DecimalFormat("0.00");
		double gigs = (((double)bytes/1024d)/1024d/1024d);
		double megs = (((double)bytes/1024d)/1024d);
		double kb = ((double)bytes/1024d);
		if (gigs > 1) {
			return deci.format(gigs) + " GB";
		}
		if (megs > 1) {
			return deci.format(megs) + " MB";
		}
		if (kb > 1) {
			return deci.format(kb) + " KB";
		}
		return bytes + " bytes";
	}
	
	/**
	 * Helper method to adjust for pad bytes, as found in IFF files such as WAV and AIFF
	 * @param input the byte position before the adjustment
	 * @return the byte position after the adjustment (the same if input is even, and +1 if odd)
	 */
	public static long adjustForPadByte(long input) {
		long output = input;
		if ((output % 2) != 0) {
			output = output + 1;
		}
		return output;
	}
	
	/**
     * Center the window on the screen
	 * @param window the window to center on screen
	 */
    public static void centerWindow(Window window) {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = window.getSize();
        if (frameSize.height > screenSize.height) {
        	frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
        	frameSize.width = screenSize.width;
        }
        window.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }
    
    /**
     * Delete a portion of a file.
     * @param file  the file to delete from
     * @param startpos the start byte position to delete from
     * @param endpos the end byte position to delete from
     * @throws IOException if read/write error occurs
     */
    public static void deleteFromFile(File file, long startpos, long endpos) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		try {
			if (endpos >= file.length()) {
				raf.setLength(startpos);
				raf.close();
				return;
			}
			raf.seek(endpos);
			long curpos = raf.getFilePointer();
			byte[] buf = new byte[KlangConstants.BUFFER_SIZE];
			while ((curpos + KlangConstants.BUFFER_SIZE) < raf.length()) {
				KlangProgressWindow.progVal = KlangProgressWindow.PROG_MAX - (int)(((float)(endpos - curpos) / (float)(endpos - startpos)) * KlangProgressWindow.PROG_MAX);
				int x = raf.read(buf);
				if (x != buf.length) {
					throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
				}
				raf.seek(curpos - (endpos - startpos));
				raf.write(buf);
				raf.seek(curpos + KlangConstants.BUFFER_SIZE);
				curpos = raf.getFilePointer();
			}
			if (raf.length() != curpos) {
				buf = new byte[(int)(raf.length() - curpos)];
				int x = raf.read(buf);
				if (x != buf.length) {
					throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
				}
				raf.seek(curpos - (endpos - startpos));
				raf.write(buf);
			}
			long newEnd = raf.getFilePointer();
			raf.setLength(newEnd);
			raf.close();
		} catch (IOException err) {
			raf.close();
			throw err;
		}
    }
    
    /**
     * Append one file (in its entirety) to the end of another
     * @param source the source file
     * @param target the target file
     * @throws IOException if read/write error occurs
     */
    public static void appendToFile(File source, File target) throws IOException {
    	insertIntoFile(source, target, target.length());
    }
    
    /**
     * Append a byte array (in its entirety) to the end of a file
     * @param bytes the byte array to append
     * @param target the target file
     * @throws IOException if read/write error occurs
     */
    public static void appendToFile(byte[] bytes, File target) throws IOException {
    	insertIntoFile(bytes, target, target.length());
    }
    
    /**
     * Insert a byte array (in its entirety) into a file.
     * @param bytes the byte array
     * @param target the target file
     * @param start the starting byte position; all data after this point in the file will be moved forward
     * @throws IOException if read/write error occurs
     */
    public static void insertIntoFile(byte[] bytes, File target, long start) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(target, "rw");
		byte[] buf = new byte[KlangConstants.BUFFER_SIZE];
		try {
			long oldend = raf.length();
			raf.setLength(raf.length() + bytes.length);
			raf.seek(oldend);
			long curpos;
			while ((raf.getFilePointer() - KlangConstants.BUFFER_SIZE) >= start) {
				curpos = raf.getFilePointer();
				KlangProgressWindow.progVal = KlangProgressWindow.PROG_MAX - (int)(((float)(curpos - start) / (float)(oldend - start)) * KlangProgressWindow.PROG_MAX);
				raf.seek(curpos - KlangConstants.BUFFER_SIZE);
				int x = raf.read(buf);
				if (x != buf.length) {
					throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
				}
				raf.seek(curpos - KlangConstants.BUFFER_SIZE + bytes.length);
				raf.write(buf);
				raf.seek(curpos - KlangConstants.BUFFER_SIZE);
			}
			if (raf.getFilePointer() != start) {
				buf = new byte[(int)(raf.getFilePointer() - start)];
				raf.seek(start);
				int x = raf.read(buf);
				if (x != (raf.getFilePointer() - start)) {
					throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
				}
				raf.seek(start + bytes.length);
				raf.write(buf);
			}
			raf.seek(start);
			raf.write(bytes, 0, bytes.length);
			raf.close();
		} catch (IOException err) {
			raf.close();
			throw err;
		}
    }
    
    /**
     * Insert a number of blank bytes (value 0) into a file.
     * @param position the byte position to begin inserting bytes
     * @param numbytes the number of bytes to add
     * @param toFile the file to write the bytes to
     * @throws IOException if read/write error occurs
     */
    public static void insertBlankBytes(long position, long numbytes, File toFile) throws IOException {
		//we'll accomplish this by first creating a temp file of numbytes length, then inserting it
    	File tmp = null;
    	RandomAccessFile raf = null;
    	try {
    		tmp = File.createTempFile("klang", ".dat");
    		raf = new RandomAccessFile(tmp, "rw");
    		//will insert 'undefined' bytes, but these should just be empty bytes
    		raf.setLength(numbytes);
    		raf.close();
    		KlangUtil.insertIntoFile(tmp, toFile, position);
    		tmp.delete();
    	} catch (IOException err) {
			if (tmp != null) {
				tmp.delete();
			}
			if (raf != null) {
				raf.close();
			}
			throw err;
		}
    }
    
    /**
     * Insert a source file (in its entirety) into a target file.
     * @param source the source file
     * @param target the target file
     * @param start the start byte position; all data after this point in the file will be moved forward
     * @throws IOException if read/write error occurs
     */
    public static void insertIntoFile(File source, File target, long start) throws IOException {
		RandomAccessFile rafDest = new RandomAccessFile(target, "rw");
		RandomAccessFile rafSource = new RandomAccessFile(source, "r");
		byte[] buf = new byte[KlangConstants.BUFFER_SIZE];
		try {
			long oldend = rafDest.length();
			long moveAmt = source.length();
			rafDest.setLength(rafDest.length() + moveAmt);
			rafDest.seek(oldend);
			long curpos;
			while ((rafDest.getFilePointer() - KlangConstants.BUFFER_SIZE) >= start) {
				curpos = rafDest.getFilePointer();
				KlangProgressWindow.progVal = KlangProgressWindow.PROG_MAX - (int)(((float)(curpos - start) / (float)(oldend - start)) * KlangProgressWindow.PROG_MAX);
				rafDest.seek(curpos - KlangConstants.BUFFER_SIZE);
				int x = rafDest.read(buf);
				if (x != buf.length) {
					throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
				}
				rafDest.seek(curpos - KlangConstants.BUFFER_SIZE + moveAmt);
				rafDest.write(buf);
				rafDest.seek(curpos - KlangConstants.BUFFER_SIZE);
			}
			if (rafDest.getFilePointer() != start) {
				buf = new byte[(int)(rafDest.getFilePointer() - start)];
				rafDest.seek(start);
				int x = rafDest.read(buf);
				if (x != (rafDest.getFilePointer() - start)) {
					throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
				}
				rafDest.seek(start + moveAmt);
				rafDest.write(buf);
			}
			buf = new byte[KlangConstants.BUFFER_SIZE];
			rafSource.seek(0);
			rafDest.seek(start);
			while ((rafSource.getFilePointer() + KlangConstants.BUFFER_SIZE) <= rafSource.length()) {
				KlangProgressWindow.progVal = KlangProgressWindow.PROG_MAX - (int)(((float)(rafSource.length() - rafSource.getFilePointer()) / (float)(rafSource.length() - start)) * KlangProgressWindow.PROG_MAX);
				int x = rafSource.read(buf);
				if (x != buf.length) {
					throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
				}
				rafDest.write(buf);
			}
			if (rafSource.getFilePointer() < rafSource.length()) {
				buf = new byte[(int)(rafSource.length() - rafSource.getFilePointer())];
				int x = rafSource.read(buf);
				if (x != buf.length) {
					throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
				}
				rafDest.write(buf);
			}
			rafDest.close();
			rafSource.close();
		} catch (IOException err) {
			rafDest.close();
			rafSource.close();
			throw err;
		} 
    }
    
	/**
	 * Copy a file.
	 * @param source the source file to copy
	 * @param dest the destination file; if file already exists, it will be overwritten
	 * @throws IOException if read/write error occurs
	 */
    public static void copyFile(File source, File dest) throws IOException {
    	if (dest.getPath().equals(source.getPath())) {
			throw new IOException(KlangConstants.ERROR_IN_OUT_IDENTICAL);
		}
    	if (dest.exists()) {
			dest.delete();
		}
		dest.createNewFile();
		FileInputStream fin = new FileInputStream(source);
		FileOutputStream fos = new FileOutputStream(dest);
		try {
			byte[] buffer = new byte[KlangConstants.BUFFER_SIZE];
	        int len;
	        while ((len = fin.read(buffer)) > 0) {
	            fos.write(buffer, 0, len);
	        }
	        fin.close();
	        fos.close();
		} catch (IOException err) {
			fin.close();
			fos.close();
			throw err;
		}
	}
	
    /**
	 * Write data from a source file to a destination file. Any existing data in the destination file will be overwritten.
	 * @param source the source file
	 * @param dest the destination file. If this file doesn't exist, it will be created first.
	 * @param sourceStart start position in the source file for the data to be copied
	 * @param sourceEnd end position in the source file for the data to be copied
	 * @throws IOException if read/write errors occur
	 */
	public static void writeToFile(File source, File dest, long sourceStart, long sourceEnd) throws IOException {
		if (dest.getPath().equals(source.getPath())) {
			throw new IOException(KlangConstants.ERROR_IN_OUT_IDENTICAL);
		}
		if (dest.exists()) {
			dest.delete();
    	}
		dest.createNewFile();
		RandomAccessFile fin = new RandomAccessFile(source, "r");
		RandomAccessFile fos = new RandomAccessFile(dest, "rw");
		try {
			byte[] buffer = new byte[KlangConstants.BUFFER_SIZE];
	        fin.seek(sourceStart);
	        fos.seek(0);
	        while (fin.getFilePointer() < sourceEnd) {
	        	//update progress window, if open
	            KlangProgressWindow.progVal = KlangProgressWindow.PROG_MAX - (int)(((float)(sourceEnd - fin.getFilePointer()) / (float)(sourceEnd - sourceStart)) * KlangProgressWindow.PROG_MAX);
	        	int len = fin.read(buffer);
	        	if ((fin.getFilePointer()) <= sourceEnd) {
	        		fos.write(buffer, 0, len);
	        	} else {
	        		fos.write(buffer, 0, ((int)((sourceEnd - sourceStart) % KlangConstants.BUFFER_SIZE)));
	        	}
	        }
	        fin.close();
	        fos.close();
		} catch (IOException err) {
			fin.close();
			fos.close();
			throw err;
		}
	}
    
	/**
	 * Get an MD5 checksum for a portion of a file.
	 * @param theFile the file to check
	 * @param start starting byte position
	 * @param end ending byte position
	 * @return the MD5 checksum as a string
	 * @throws IOException if read/write error occurs
	 */
    public static String getChecksum(File theFile, long start, long end) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(theFile, "r");
		byte[] buffer = new byte[KlangConstants.BUFFER_SIZE];
	    MessageDigest complete = null;
	    try {
	    	complete = MessageDigest.getInstance("MD5");
	    } catch (Exception err) {
	    	err.printStackTrace();
	    	throw new IOException(KlangConstants.ERROR_GENERAL_FILE);
	    }
	    try {
	    	raf.seek(start);
	    	int numRead;
		    do {
		    	//update progress window, if open
	    		KlangProgressWindow.progVal = KlangProgressWindow.PROG_MAX - (int)(((float)(end - raf.getFilePointer()) / (float)(end - start)) * KlangProgressWindow.PROG_MAX);
		    	if (raf.getFilePointer() >= (end - KlangConstants.BUFFER_SIZE)) {
		    		buffer = new byte[(int)(end - raf.getFilePointer())];
		    	}
		    	numRead = raf.read(buffer);
		    	if (numRead > 0) {
		    		complete.update(buffer, 0, numRead);
		        }
		    } while (numRead > 0);
		    raf.close();
		    byte[] inn = complete.digest();
		    byte ch = 0x00;
		    int i = 0;
		    String pseudo[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
		    StringBuffer out = new StringBuffer(inn.length * 2);
		    while (i < inn.length) {
		        ch = (byte) (inn[i] & 0xF0);
		        ch = (byte) (ch >>> 4);
		        ch = (byte) (ch & 0x0F);
		        out.append(pseudo[ (int) ch]);
		        ch = (byte) (inn[i] & 0x0F);
		        out.append(pseudo[ (int) ch]);
		        i++;
		    }
		    return new String(out);
	    } catch (IOException err) {
	    	raf.close();
	    	throw err;
	    }
	}
    
    /**
	 * Retrieve a CRC checksum value for the specified portion of a file.
	 *
	 * @param file the file to target
	 * @param start the start byte position
	 * @param end the end byte position
	 * @throws IOException if read error occurs
	 * @return the CRC value
	 */
	public static long getCRCFor(File file, long start, long end) throws IOException {
		RandomAccessFile raf = null;
		try {
	    	raf = new RandomAccessFile(file, "r");
			byte[] buffer = new byte[1024];
			CRC32 crc = new CRC32();
	    	raf.seek(start);
	    	int numRead;
		    do {
		    	if (raf.getFilePointer() >= (end - 1024)) {
		    		buffer = new byte[(int)(end - raf.getFilePointer())];
		    	}
		    	numRead = raf.read(buffer);
		    	if (numRead > 0) {
		    		crc.update(buffer);
		        }
		    } while (numRead > 0);
		    raf.close();
		    return crc.getValue();
	    } catch (IOException err) {
	    	if (raf != null) {
	    		try {
	    			raf.close();
	    		} catch (Exception foo) {}
	    	}
	    	throw err;
	    }
	}
	
	/**
	 * Convert a long into a hex string.
	 *
	 * @param decimal the long
	 * @return the formatted hex string
	 */
	public static String convertToHex(long decimal) {
		String x = Long.toHexString(decimal);
		if (x.length() <= 2) {
			while (x.length() < 2) {
				x = "0" + x;
			}
		} else if (x.length() <= 4) {
			while (x.length() < 4) {
				x = "0" + x;
			}
		} else if (x.length() <= 6) {
			while (x.length() < 6) {
				x = "0" + x;
			}
		} else {
			while (x.length() < 8) {
				x = "0" + x;
			}
		}
		x = "0x" + x;
		return x;
	}

	/**
	 * Convert an int into a hex string.
	 *
	 * @param decimal the integer
	 * @return the formatted hex string
	 */
	public static String convertToHex(int decimal) {
		String x = Integer.toHexString(decimal);
		while (x.length() < 4) {
			x = "0" + x;
		}
		x = "0x" + x;
		return x;
	}
    
}
