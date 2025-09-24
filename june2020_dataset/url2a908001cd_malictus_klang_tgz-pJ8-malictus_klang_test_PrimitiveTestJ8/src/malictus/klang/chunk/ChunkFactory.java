/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.chunk;

import java.io.*;
import java.util.Vector;
import malictus.klang.*;
import malictus.klang.file.*;
import malictus.klang.primitives.*;
import malictus.klang.ui.TextEncodingDialog;
import malictus.klang.ui.klangeditor.KlangEditor;

/**
 * The ChunkFactor class create instances of Chunk objects, given some basic information
 * about the chunk
 * @author Jim Halliday
 */
public class ChunkFactory {

	private ChunkFactory() {}

	/**
	 * Given some basic information about a chunk, determine its chunk type and fill out the
	 * rest of the information for it.
	 * @param parentFile the file that this chunk belongs to
	 * @param startpos the start byte position of the chunk in the file
	 * @param chunkType the chunk type (one of the KlangConstants.CHUNKTYPE_ constants)
	 * @param parentChunk the parent chunk that this chunk belongs to; this is null if the chunk is at the outermost level
	 * @param raf a RandomAccessFile pointer to the file (position in the file before this method is not important)
	 * @param isBigEndian true if chunk should be parsed as big-endian, and false otherwise; leave null if not applicable
	 * @param endposition the end position of this chunk; note that most chunks can figure out the end position for themselves, in those cases, this can be set to null
	 * @param textEncoding the text encoding (if this chunk is plain text and variable text encoding); null otherwise
	 * @throws IOException if read/write error occurs
	 * @return the appropriate Chunk object
	 */
	public static Chunk createChunk(KlangFile parentFile, long startpos, int chunkType, ContainerChunk parentChunk, RandomAccessFile raf, Boolean isBigEndian, Long endposition, String textEncoding) throws IOException {
		//first, generate other vars, based on chunk type
		long endpos = 0;
		long datastartpos = 0;
		long dataendpos = 0;
		if (endposition != null) {
			endpos = endposition.longValue();
			dataendpos = endpos;
		}
		String chunkName = "";
		raf.seek(startpos);
		try {
			switch (chunkType) {
		        case KlangConstants.CHUNKTYPE_RIFF:
		        {
		        	FourCC fcc= new FourCC();
		        	fcc.setValueFromFile(raf);
		        	chunkName = fcc.getValue();
		        	Primitive size;
		        	if (isBigEndian) {
		        		size = new Int4ByteUnsignedBE();
		        	} else {
		        		size = new Int4ByteUnsignedLE();
		        	}
					size.setValueFromFile(raf);
					datastartpos = raf.getFilePointer();
					endpos = raf.getFilePointer() + Long.valueOf(size.writeValueToString());
					//adjust for pad byte
					endpos = KlangUtil.adjustForPadByte(endpos);
					//adjust if EOF reached instead
					if (raf.length() <= endpos) {
						endpos = raf.length();
					}
					//adjust if end of parent chunk reached instead
					if (parentChunk != null) {
						if (parentChunk.getDataEndPosition() <= endpos) {
							endpos = parentChunk.getDataEndPosition();
						}
					}
					dataendpos = endpos;
					break;
		        }
		        case KlangConstants.CHUNKTYPE_IFF: {
		        	FourCC fcc= new FourCC();
		        	fcc.setValueFromFile(raf);
		        	chunkName = fcc.getValue();
		        	Int4ByteUnsignedBE size = new Int4ByteUnsignedBE();
					size.setValueFromFile(raf);
					datastartpos = raf.getFilePointer();
					endpos = raf.getFilePointer() + size.getValue();
					//adjust for pad byte
					endpos = KlangUtil.adjustForPadByte(endpos);
					//adjust if EOF reached instead
					if (raf.length() <= endpos) {
						endpos = raf.length();
					}
					//adjust if end of parent chunk reached instead
					if (parentChunk != null) {
						if (parentChunk.getDataEndPosition() <= endpos) {
							endpos = parentChunk.getDataEndPosition();
						}
					}
					dataendpos = endpos;
					break;
		        }
		        case KlangConstants.CHUNKTYPE_PNG: {
		        	Int4ByteUnsignedBE size = new Int4ByteUnsignedBE();
					size.setValueFromFile(raf);
		        	FourCC fcc= new FourCC();
		        	fcc.setValueFromFile(raf);
		        	chunkName = fcc.getValue();
					datastartpos = raf.getFilePointer();
					endpos = raf.getFilePointer() + size.getValue() + 4;
					//adjust if EOF reached instead
					if (raf.length() <= endpos) {
						endpos = raf.length();
					}
					//adjust if end of parent chunk reached instead
					if (parentChunk != null) {
						if (parentChunk.getDataEndPosition() <= endpos) {
							endpos = parentChunk.getDataEndPosition();
						}
					}
					dataendpos = endpos - 4;
					break;
		        }
		        case KlangConstants.CHUNKTYPE_PNGFILE: {
		        	endpos = raf.length();
			        dataendpos = raf.length();
			        datastartpos = 8;
			        chunkName = KlangConstants.CHUNKNAME_PNGFILE;
			        break;
		        }
		        case KlangConstants.CHUNKTYPE_NOCHUNK: {
			        endpos = raf.length();
			        dataendpos = raf.length();
			        datastartpos = startpos;
			        chunkName = KlangConstants.CHUNKNAME_RAWDATA;
			        break;
		        }
		        case KlangConstants.CHUNKTYPE_PLAINTEXT: {
			        if (endposition == null) {
			        	endpos = raf.length();
				        dataendpos = raf.length();
			        }
			        datastartpos = startpos;
			        chunkName = KlangConstants.CHUNKNAME_PLAINTEXT;
			        break;
		        }
		        default: {
		        	endpos = raf.length();
			        dataendpos = raf.length();
			        datastartpos = startpos;
			        chunkName = KlangConstants.CHUNKNAME_INVALIDDATA;
			        break;
		        }
			}
		} catch (BadValueException err) {
			chunkType = KlangConstants.CHUNKTYPE_NOCHUNK;
			endpos = raf.length();
	        dataendpos = raf.length();
	        datastartpos = startpos;
	        chunkName = KlangConstants.CHUNKNAME_INVALIDDATA;
		}

		//now, create the chunk, based on information already gathered
		String chunkNameTest = chunkName.toLowerCase();	
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_CHRM.toLowerCase())) {
			return new CHRMChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_COMM.toLowerCase())) {
			return new COMMChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_FACT.toLowerCase())) {
			return new FACTChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, isBigEndian, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_FMT.toLowerCase())) {
			return new FMTChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, isBigEndian, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_FORM.toLowerCase())) {
			return new FORMChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_GAMA.toLowerCase())) {
			return new GAMAChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if ( (chunkNameTest.equals(KlangConstants.CHUNKNAME_IARL.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_IART.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ICMS.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ICMT.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ICOP.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ICRD.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ICRP.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_IDIM.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_IDPI.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_IENG.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_IGNR.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_IKEY.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ILGT.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_IMED.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_INAM.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_IPLT.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_IPRD.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ISBJ.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ISFT.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ISHP.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ISRC.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ISRF.toLowerCase())) ||
				(chunkNameTest.equals(KlangConstants.CHUNKNAME_ITCH.toLowerCase())) ) 	{
			return new NullStringChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf, "US-ASCII", false);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_IHDR.toLowerCase())) {
			return new IHDRChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_INVALIDDATA.toLowerCase()) || 
				chunkNameTest.equals(KlangConstants.CHUNKNAME_RAWDATA.toLowerCase())) {
			return new RawChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_LIST.toLowerCase())) {
			return new RIFFChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, isBigEndian, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_PHYS.toLowerCase())) {
			return new PHYSChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_PNGFILE.toLowerCase())) {
			return new PNGFileChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_RIFF.toLowerCase())) {
			return new RIFFChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, false, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_RIFX.toLowerCase())) {
			return new RIFFChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, true, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_SRGB.toLowerCase())) {
			return new SRGBChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_TEXT.toLowerCase())) {
			return new TEXTChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_ZTXT.toLowerCase())) {
			return new ZTXTChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
		}
		
		if (chunkNameTest.equals(KlangConstants.CHUNKNAME_PLAINTEXT.toLowerCase())) {
			return new PlainTextChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf, textEncoding);
		}
		//plain text chunks where we need to query about the encoding
		if ( (chunkNameTest.equals(KlangConstants.CHUNKNAME_AXML.toLowerCase())) || (chunkNameTest.equals(KlangConstants.CHUNKNAME_IXML.toLowerCase()))) {
			//need to find out encoding of this chunk
			String encoding = "";
			if ((dataendpos - datastartpos) == 0) {
				//assume text could go here
				return new PlainTextChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf, "UTF-8");
			}
			if ((dataendpos - datastartpos) < 50000000) {
				if (TextEncodingDialog.currentEncoding.equals("")) {
					if (KlangEditor.isManualTextMode()) {
						TextEncodingDialog ted = new TextEncodingDialog(chunkName);
						encoding = ted.getChosenEncoding();
					} else {
						encoding = KlangUtil.determineEncodingFor(parentFile.getFile(), datastartpos, dataendpos);
					}
					if (!(encoding.equals(KlangConstants.KLANG_TEXTENC_DIALOG_NONE))) {
						TextEncodingDialog.currentEncoding = encoding;	
					}
				} else {
					encoding = TextEncodingDialog.currentEncoding;
				}
			}
			if (encoding.equals("") || encoding.equals(KlangConstants.KLANG_TEXTENC_DIALOG_NONE)) {
				return new UnknownChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
			} else {
				return new PlainTextChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf, encoding);
			}				
		}
		//if we got to this point, chunk is unknown (although there may still be a description for it in KlangConstants)
		return new UnknownChunk(parentFile, startpos, endpos, datastartpos, dataendpos, chunkType, chunkName, parentChunk, raf);
	}
	
	/**
	 * Given a chunk vector, return a chunk with the given name. This will return 
	 * the first chunk found that matched the given name.
	 * @param chunks the vector of chunk to look through
	 * @param name the chunk name to find
	 * @param recurse if true, the method will also search any child chunks
	 * @return the chunk that matches this name, or null if no match found
	 */
	public static Chunk getChunkNamed(Vector<Chunk> chunks, String name, boolean recurse) {
		int counter = 0;
		while (counter < chunks.size()) {
			Chunk x = chunks.get(counter);
			if (x.getChunkName().equals(name)) {
				return x;
			}
			if (recurse) {
				if (x instanceof ContainerChunk) {
					ContainerChunk xc = (ContainerChunk)x;
					if (xc.getChunkNamed(name, recurse) != null) {
						return xc.getChunkNamed(name, recurse);
					}
				}
			}
			counter = counter + 1;
		}
		return null;
	}
	
	/**
	 * Given a chunk vector, return all chunks with the given name. 
	 * @param chunks the vector of chunk to look through
	 * @param name the chunk name to find
	 * @param recurse if true, the method will also search any child chunks
	 * @return a vector of all chunks that match this name, or null if no match found
	 */
	public static Vector<Chunk> getChunksNamed(Vector<Chunk> chunks, String name, boolean recurse) {
		name = name.toLowerCase();
		Vector<Chunk> returnChunks = new Vector<Chunk>();
		int counter = 0;
		while (counter < chunks.size()) {
			Chunk x = chunks.get(counter);
			if (x.getChunkName().toLowerCase().equals(name)) {
				returnChunks.add(x);
			}
			if (recurse) {
				if (x instanceof ContainerChunk) {
					ContainerChunk xc = (ContainerChunk)x;
					if (xc.getChunksNamed(name, recurse).size() > 0) {
						Vector<Chunk> vc = xc.getChunksNamed(name, recurse);
						int subcounter = 0;
						while (subcounter < vc.size()) {
							Chunk ch = vc.get(subcounter);
							returnChunks.add(ch);
							subcounter = subcounter + 1;
						}
					}
				}
			}
			counter = counter + 1;
		}
		return returnChunks;
	}
	
}
