/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang;

import java.awt.Font;
import java.util.*;
import malictus.klang.primitives.*;

/**
 * This class stores all constants, string messages, and static content for Klang.
 * @author Jim Halliday
 */
public class KlangConstants {
	
	private KlangConstants() {}
	
	///////////////////////////////////////////////////////////////////////////
	////////////////////  GENERAL CONSTANTS ///////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	public static final String KLANG_VERSION = "0.12";
	/**
	 * Buffer size for file operations.
	 */
	public static final int BUFFER_SIZE = 1024;
	
    ///////////////////////////////////////////////////////////////////////////
	////////////////////  ERROR MESSAGES  /////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	public static final String ERROR_BAD_VALUE_EXCEPTION = " cannot be correctly parsed \ninto a valid value for this data type.";
	public static final String ERROR_BAD_VALUE_EXCEPTION_NOVALUE = "That value has not been initialized";
	public static final String ERROR_BAD_VALUE_EXCEPTION_WEIRDVALUE = "Error formatting value";
	public static final String ERROR_NO_SUCH_FILE = "File does not exist";
	public static final String ERROR_NO_SUCH_CHUNK = "Chunk does not exist";
	public static final String ERROR_NO_SUCH_PRIM = "Primitive does not exist";
	public static final String ERROR_FILE_IS_DIRECTORY = "The specified file object is a directory, not a file";
	public static final String ERROR_FILE_CANT_BE_READ = "The specified file cannot be read";
	public static final String ERROR_FILE_IS_INCORRECT_TYPE = "The specified file is of an incorrect type";
	public static final String ERROR_FILE_DOESNT_SUPPORT = "This file type does not support this operation";
	public static final String ERROR_CHUNK_IS_FILE = "The chunk represents the entire file, and can't be deleted";
	public static final String ERROR_GENERAL_FILE = "General file read/write error";
	public static final String ERROR_CHUNK_ISNT_EDITABLE = "This chunk is not editable";
	public static final String ERROR_FILE_ISNT_EDITABLE = "This file type is not editable";
	public static final String ERROR_NOT_FIXED_BYTE = "This value is not of a fixed byte length and cannot be parsed";
	public static final String ERROR_TITLE = "Error";
	public static final String ERROR_IN_OUT_IDENTICAL = "Input and output files cannot be identical";
	public static final String ERROR_NOT_RIFF_RIFX = "At the outermost level, the chunk name must be RIFF or RIFX";
	public static final String ERROR_NOT_FORM = "At the outermost level, the chunk name must be FORM";
	public static final String ERROR_TEXT_ENCODING = "Invalid text encoding string";
	
    ///////////////////////////////////////////////////////////////////////////
	//////////////////  PRIMITIVE TYPES ///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Retrieval method for primitive descriptions
	 * @param className name of the primitive class
	 * @return a text-based description of that primitive
	 */
	public static String getPrimitiveDescriptionFor(String className) {
		if (className.equals("malictus.klang.primitives.ASCIIChar")) {
			return "1-byte ASCII char";
		}
		if (className.equals("malictus.klang.primitives.CompressedString")) {
			return "ZLIB compressed string";
		}
		if (className.equals("malictus.klang.primitives.FourCC")) {
			return "Four-byte ASCII chunk name";
		}
		if (className.equals("malictus.klang.primitives.GUID")) {
			return "Microsoft Globally Unique Identifier";
		}
		if (className.equals("malictus.klang.primitives.IEEE80ByteFloat")) {
			return "IEEE 80 Byte Float (Limited)";
		}
		if (className.equals("malictus.klang.primitives.Int2ByteSignedBE")) {
			return "2-byte, signed, big-endian integer";
		}
		if (className.equals("malictus.klang.primitives.Int2ByteSignedLE")) {
			return "2-byte, signed, little-endian integer";
		}
		if (className.equals("malictus.klang.primitives.Int2ByteUnsignedBE")) {
			return "2-byte, unsigned, big-endian integer";
		}
		if (className.equals("malictus.klang.primitives.Int2ByteUnsignedLE")) {
			return "2-byte, unsigned, little-endian integer";
		}
		if (className.equals("malictus.klang.primitives.Int4ByteSignedBE")) {
			return "4-byte, signed, big-endian integer";
		}
		if (className.equals("malictus.klang.primitives.Int4ByteSignedLE")) {
			return "4-byte, signed, little-endian integer";
		}
		if (className.equals("malictus.klang.primitives.Int4ByteUnsignedBE")) {
			return "4-byte, unsigned, big-endian integer";
		}
		if (className.equals("malictus.klang.primitives.Int4ByteUnsignedLE")) {
			return "4-byte, unsigned, little-endian integer";
		}
		if (className.equals("malictus.klang.primitives.IntByteSigned")) {
			return "1-byte signed integer";
		}
		if (className.equals("malictus.klang.primitives.IntByteUnsigned")) {
			return "1-byte unsigned integer";
		}
		if (className.equals("malictus.klang.primitives.NullTerminatedString")) {
			return "Null-terminated string";
		}
		if (className.equals("malictus.klang.primitives.PlainString")) {
			return "Plain string";
		}
		return "Unknown primitive type";
	}	
	
	///////////////////////////////////////////////////////////////////////////
	//////////////////  PRIMITIVE DATA   //////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/* 
	 * Note: these data type strings should be unique (ie not 'SAMPLE_RATE',
	 * but (FMT_CHUNK_SAMPLE_RATE)
	 */
	public static final String PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTX = "CHRM_CHUNK_WHITEPOINTX";
	public static final String PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTY = "CHRM_CHUNK_WHITEPOINTY";
	public static final String PRIMITIVE_DATA_CHRM_CHUNK_REDX = "CHRM_CHUNK_REDX";
	public static final String PRIMITIVE_DATA_CHRM_CHUNK_REDY = "CHRM_CHUNK_REDY";
	public static final String PRIMITIVE_DATA_CHRM_CHUNK_GREENX = "CHRM_CHUNK_GREENX";
	public static final String PRIMITIVE_DATA_CHRM_CHUNK_GREENY = "CHRM_CHUNK_GREENY";
	public static final String PRIMITIVE_DATA_CHRM_CHUNK_BLUEX = "CHRM_CHUNK_BLUEX";
	public static final String PRIMITIVE_DATA_CHRM_CHUNK_BLUEY = "CHRM_CHUNK_BLUEY";
	public static final String PRIMITIVE_DATA_COMM_CHUNK_NUM_CHANNELS = "COMM_CHUNK_NUM_CHANNELS";
	public static final String PRIMITIVE_DATA_COMM_CHUNK_NUM_FRAMES = "COMM_CHUNK_NUM_FRAMES";
	public static final String PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_SIZE = "COMM_CHUNK_SAMPLE_SIZE";
	public static final String PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_RATE = "COMM_CHUNK_SAMPLE_RATE";
	public static final String PRIMITIVE_DATA_FACT_CHUNK_NUM_SAMPS = "FACT_CHUNK_NUM_SAMPS";
	public static final String PRIMITIVE_DATA_FMT_CHUNK_BLOCK_ALIGN = "FMT_CHUNK_BLOCK_ALIGN";
	public static final String PRIMITIVE_DATA_FMT_CHUNK_BPS = "FMT_CHUNK_AVERAGE_BYTES_PER_SECOND";
	public static final String PRIMITIVE_DATA_FMT_CHUNK_CHANNEL_MASK = "FMT_CHUNK_CHANNEL_MASK";
	public static final String PRIMITIVE_DATA_FMT_CHUNK_COMP_CODE = "FMT_CHUNK_COMP_CODE";
	public static final String PRIMITIVE_DATA_FMT_CHUNK_NUM_CHANNELS = "FMT_CHUNK_NUM_CHANNELS";
	public static final String PRIMITIVE_DATA_FMT_CHUNK_SAMP_RATE = "FMT_CHUNK_SAMP_RATE";
	public static final String PRIMITIVE_DATA_FMT_CHUNK_SIGBITS = "FMT_CHUNK_SIGBITS";
	public static final String PRIMITIVE_DATA_FMT_CHUNK_SUBFORMAT = "FMT_CHUNK_SUBFORMAT";
	public static final String PRIMITIVE_DATA_FMT_CHUNK_BPSSPB = "FMT_CHUNK_BPSSPB";
	public static final String PRIMITIVE_DATA_FORM_CHUNK_NAME = "FORM_CHUNK_NAME";
	public static final String PRIMITIVE_DATA_GAMA_CHUNK_GAMMA = "GAMA_CHUNK_GAMMA";
	public static final String PRIMITIVE_DATA_IHDR_CHUNK_BITDEPTH = "IHDR_CHUNK_BITDEPTH";
	public static final String PRIMITIVE_DATA_IHDR_CHUNK_COLORTYPE = "IHDR_CHUNK_COLORTYPE";
	public static final String PRIMITIVE_DATA_IHDR_CHUNK_COMPRESS = "IHDR_CHUNK_COMPRESS";
	public static final String PRIMITIVE_DATA_IHDR_CHUNK_FILTER = "IHDR_CHUNK_FILTER";
	public static final String PRIMITIVE_DATA_IHDR_CHUNK_HEIGHT = "IHDR_CHUNK_HEIGHT";
	public static final String PRIMITIVE_DATA_IHDR_CHUNK_INTERLACE = "IHDR_CHUNK_INTERLACE";
	public static final String PRIMITIVE_DATA_IHDR_CHUNK_WIDTH = "IHDR_CHUNK_WIDTH";
	public static final String PRIMITIVE_DATA_NULLSTRING_CHUNK_VALUE = "NULLSTRING_CHUNK_VALUE";
	public static final String PRIMITIVE_DATA_PHYS_CHUNK_PIX_X = "IHDR_CHUNK_PIX_X";
	public static final String PRIMITIVE_DATA_PHYS_CHUNK_PIX_Y = "IHDR_CHUNK_PIX_Y";
	public static final String PRIMITIVE_DATA_PHYS_CHUNK_UNIT = "IHDR_CHUNK_UNIT";
	public static final String PRIMITIVE_DATA_PLAINSTRING_CHUNK_VALUE = "PLAINSTRING_CHUNK_VALUE";
	public static final String PRIMITIVE_DATA_RAW_VALUE_NAME = "RAW_VALUE";
	public static final String PRIMITIVE_DATA_RIFF_CHUNK_NAME = "RIFF_CHUNK_NAME";
	public static final String PRIMITIVE_DATA_SRGB_CHUNK_RENDERING_INTENT = "SRGB_CHUNK_RENDERING_INTENT";
	public static final String PRIMITIVE_DATA_TEXT_CHUNK_KEYWORD = "TEXT_CHUNK_KEYWORD";
	public static final String PRIMITIVE_DATA_TEXT_CHUNK_VALUE = "TEXT_CHUNK_VALUE";
	public static final String PRIMITIVE_DATA_ZTXT_CHUNK_COMP_METHOD = "ZTXT_CHUNK_COMP_METHOD";
	
	/**
	 * Retrieval method for a text description of a primitive data value.
	 * @param primitiveDataName one of the primitive data constants above
	 * @return a text-based description of that value's function in the file
	 */
	public static String getPrimitiveDataDescriptionFor(String primitiveDataName) {
		if (primitiveDataName.equals(PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTX)) {
			return "White Point X";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_CHRM_CHUNK_WHITEPOINTY)) {
			return "White Point Y";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_CHRM_CHUNK_REDX)) {
			return "Red X";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_CHRM_CHUNK_REDY)) {
			return "Red Y";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_CHRM_CHUNK_GREENX)) {
			return "Green X";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_CHRM_CHUNK_GREENY)) {
			return "Green Y";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_CHRM_CHUNK_BLUEX)) {
			return "Blue X";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_CHRM_CHUNK_BLUEY)) {
			return "Blue Y";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_COMM_CHUNK_NUM_CHANNELS)) {
			return "Number of channels";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_COMM_CHUNK_NUM_FRAMES)) {
			return "Number of frames";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_RATE)) {
			return "Sampling rate";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_COMM_CHUNK_SAMPLE_SIZE)) {
			return "Sample size";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FACT_CHUNK_NUM_SAMPS)) {
			return "Number of samples";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_BLOCK_ALIGN)) {
			return "Block align";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_BPS)) {
			return "Average bytes per second";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_CHANNEL_MASK)) {
			return "(Optional) Channel mask";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_COMP_CODE)) {
			return "Compression code";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_NUM_CHANNELS)) {
			return "Number of channels";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_SAMP_RATE)) {
			return "Sampling rate";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_SIGBITS)) {
			return "Significant bits per sample";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_SUBFORMAT)) {
			return "(Optional) Subformat";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_BPSSPB)) {
			return "(Optional) Bits per sample/samples per block";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FORM_CHUNK_NAME)) {
			return "Chunk type";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_GAMA_CHUNK_GAMMA)) {
			return "Image gamma";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_IHDR_CHUNK_BITDEPTH)) {
			return "Bit Depth";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_IHDR_CHUNK_COLORTYPE)) {
			return "Color Type";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_IHDR_CHUNK_COMPRESS)) {
			return "Compression Method";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_IHDR_CHUNK_FILTER)) {
			return "Filter Method";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_IHDR_CHUNK_HEIGHT)) {
			return "Height";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_IHDR_CHUNK_INTERLACE)) {
			return "Interlace Method";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_IHDR_CHUNK_WIDTH)) {
			return "Width";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_NULLSTRING_CHUNK_VALUE)) {
			return "String value";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_PHYS_CHUNK_PIX_X)) {
			return "Pixels per unit (X axis)";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_PHYS_CHUNK_PIX_Y)) {
			return "Pixels per unit (Y axis)";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_PHYS_CHUNK_UNIT)) {
			return "Unit specifier";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_PLAINSTRING_CHUNK_VALUE)) {
			return "Plain text";
		}	
		if (primitiveDataName.equals(PRIMITIVE_DATA_RAW_VALUE_NAME)) {
			return "Raw value";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_RIFF_CHUNK_NAME)) {
			return "Chunk type";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_SRGB_CHUNK_RENDERING_INTENT)) {
			return "Rendering intent";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_TEXT_CHUNK_KEYWORD)) {
			return "Text data keyword";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_TEXT_CHUNK_VALUE)) {
			return "Text data value";
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_ZTXT_CHUNK_COMP_METHOD)) {
			return "Compression method";
		}
		return "Unknown primitive data type";
	}	
	
	/**
	 * Given a primitive data object, return a list of mappings of that
	 * value to a string that describes the value. Return null if
	 * no mapping exists.
	 * @param primitiveDataName one of the primitive data constants above
	 * @return a LinkedHashMap of mapping of primitive string values to descriptive strings, or null if no mapping exists
	 */
	public static LinkedHashMap<String, String> getValueStringsFor(String primitiveDataName) {
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_COMP_CODE)) {
			return VALUESTRINGS_WAVECOMPCODES;
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_FMT_CHUNK_SUBFORMAT)) {
			return VALUESTRINGS_WAVECOMPCODES;
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_RIFF_CHUNK_NAME)) {
			return VALUESTRINGS_RIFFCHUNKCODES;
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_SRGB_CHUNK_RENDERING_INTENT)) {
			return VALUESTRINGS_RENDERINGINTENT;
		}
		if (primitiveDataName.equals(PRIMITIVE_DATA_ZTXT_CHUNK_COMP_METHOD)) {
			return VALUESTRINGS_ZTXTCOMPMETHOD;
		}
		return null;
	}
	
	/**
	 * Given a primitive data name, and a primitive, return the string that describes that value.
	 * Return null if no mapping exists.
	 * @param name one of the primitive data constants above
	 * @param primitive a primitive value to test
	 * @return a text-based description of the primitive's value, or null if none exists
	 */
	public static String getCurrentValueStringFor(String name, Primitive primitive) {
		if (getValueStringsFor(name) == null) {
			return null;
		}
		LinkedHashMap<String, String> ht = getValueStringsFor(name);
		String x = null;
		try {
			x = primitive.writeValueToString();
		} catch (Exception err) {
			return null;
		}
		if (x == null) {
			return null;
		}
		return ht.get(x);
	}
	
    ///////////////////////////////////////////////////////////////////////////
	////////////  PRIMITIVEDATA VALUE STRING TABLES ///////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * RIFF chunk subtype codes
	 */
	public static LinkedHashMap<String, String> VALUESTRINGS_RIFFCHUNKCODES = new LinkedHashMap<String, String>();
	static {
		try {
			VALUESTRINGS_RIFFCHUNKCODES.put("WAVE", "WAVE File Container");
			VALUESTRINGS_RIFFCHUNKCODES.put("INFO", "RIFF File Metadata");
		} catch (Exception err) {
			//shouldn't happen
			err.printStackTrace();
		}
	}	
	
	/*
	 * SRGB (PNG file) Rendering intent codes
	 */
	public static LinkedHashMap<String, String> VALUESTRINGS_RENDERINGINTENT = new LinkedHashMap<String, String>();
	static {
		try {
			VALUESTRINGS_RENDERINGINTENT.put("0", "Perceptual");
			VALUESTRINGS_RENDERINGINTENT.put("1", "Relative colorimetric");
			VALUESTRINGS_RENDERINGINTENT.put("2", "Saturation");
			VALUESTRINGS_RENDERINGINTENT.put("3", "Absolute colorimetric");
		} catch (Exception err) {
			//shouldn't happen
			err.printStackTrace();
		}
	}	
	
	/*
	 * ZTXT (PNG file) Compression code
	 */
	public static LinkedHashMap<String, String> VALUESTRINGS_ZTXTCOMPMETHOD = new LinkedHashMap<String, String>();
	static {
		try {
			VALUESTRINGS_ZTXTCOMPMETHOD.put("0", "Inflate/Deflate Compression");
		} catch (Exception err) {
			//shouldn't happen
			err.printStackTrace();
		}
	}	
	
	/*
	 * WAV file FMT chunk compression codes
	 */
	public static LinkedHashMap<String, String> VALUESTRINGS_WAVECOMPCODES = new LinkedHashMap<String, String>();
	static {
		try {
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0)), "Unknown");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(1)), "Microsoft PCM/Uncompressed");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(2)), "Microsoft ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(3)), "Microsoft IEEE float");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(4)), "Compaq VSELP");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(5)), "IBM CVSD");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(6)), "Microsoft a-Law");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(7)), "Microsoft u-Law");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(8)), "Microsoft DTS");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(9)), "DRM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa)), "WMA 9 Speech");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xb)), "Microsoft Windows Media RT Voice");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x10)), "OKI-ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x11)), "Intel IMA/DVI-ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x12)), "Videologic Mediaspace ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x13)), "Sierra ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x14)), "Antex G.723 ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x15)), "DSP Solutions DIGISTD");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x16)), "DSP Solutions DIGIFIX");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x17)), "Dialoic OKI ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x18)), "Media Vision ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x19)), "HP CU");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1a)), "HP Dynamic Voice");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x20)), "Yamaha ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x21)), "SONARC Speech Compression");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x22)), "DSP Group True Speech");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x23)), "Echo Speech Corp.");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x24)), "Virtual Music Audiofile AF36");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x25)), "Audio Processing Tech.");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x26)), "Virtual Music Audiofile AF10");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x27)), "Aculab Prosody 1612");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x28)), "Merging Tech. LRC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x30)), "Dolby AC2");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x31)), "Microsoft GSM610");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x32)), "MSN Audio");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x33)), "Antex ADPCME");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x34)), "Control Resources VQLPC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x35)), "DSP Solutions DIGIREAL");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x36)), "DSP Solutions DIGIADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x37)), "Control Resources CR10");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x38)), "Natural MicroSystems VBX ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x39)), "Crystal Semiconductor IMA ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x3a)), "Echo Speech ECHOSC3");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x3b)), "Rockwell ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x3c)), "Rockwell DIGITALK");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x3d)), "Xebec Multimedia");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x40)), "Antex G.721 ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x41)), "Antex G.728 CELP");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x42)), "Microsoft MSG723");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x43)), "IBM AVC ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x45)), "ITU-T G.726");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x50)), "Microsoft MPEG");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x51)), "RT23 or PAC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x52)), "InSoft RT24");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x53)), "InSoft PAC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x55)), "MP3");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x59)), "Cirrus");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x60)), "Cirrus Logic");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x61)), "ESS Tech. PCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x62)), "Voxware Inc.");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x63)), "Canopus ATRAC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x64)), "APICOM G.726 ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x65)), "APICOM G.722 ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x66)), "Microsoft DSAT");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x67)), "Micorsoft DSAT DISPLAY");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x69)), "Voxware Byte Aligned");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x70)), "Voxware AC8");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x71)), "Voxware AC10");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x72)), "Voxware AC16");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x73)), "Voxware AC20");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x74)), "Voxware MetaVoice");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x75)), "Voxware MetaSound");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x76)), "Voxware RT29HW");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x77)), "Voxware VR12");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x78)), "Voxware VR18");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x79)), "Voxware TQ40");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x7a)), "Voxware SC3");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x7b)), "Voxware SC3");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x80)), "Soundsoft");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x81)), "Voxware TQ60");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x82)), "Microsoft MSRT24");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x83)), "AT&T G.729A");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x84)), "Motion Pixels MVI MV12");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x85)), "DataFusion G.726");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x86)), "DataFusion GSM610");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x88)), "Iterated Systems Audio");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x89)), "Onlive");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x8a)), "Multitude, Inc. FT SX20");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x8b)), "Infocom ITS A/S G.721 ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x8c)), "Convedia G729");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x8d)), "Not specified congruency, Inc.");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x91)), "Siemens SBC24");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x92)), "Sonic Foundry Dolby AC3 APDIF");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x93)), "MediaSonic G.723");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x94)), "Aculab Prosody 8kbps");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x97)), "ZyXEL ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x98)), "Philips LPCBB");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x99)), "Studer Professional Audio Packed");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa0)), "Malden PhonyTalk");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa1)), "Racal Recorder GSM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa2)), "Racal Recorder G720.a");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa3)), "Racal G723.1");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa4)), "Racal Tetra ACELP");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xb0)), "NEC AAC NEC Corporation");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xff)), "AAC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x100)), "Rhetorex ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x101)), "IBM u-Law");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x102)), "IBM a-Law");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x103)), "IBM ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x111)), "Vivo G.723");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x112)), "Vivo Siren");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x120)), "Philips Speech Processing CELP");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x121)), "Philips Speech Processing GRUNDIG");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x123)), "Digital G.723");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x125)), "Sanyo LD ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x130)), "Sipro Lab ACEPLNET");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x131)), "Sipro Lab ACELP4800");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x132)), "Sipro Lab ACELP8V3");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x133)), "Sipro Lab G.729");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x134)), "Sipro Lab G.729A");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x135)), "Sipro Lab Kelvin");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x136)), "VoiceAge AMR");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x140)), "Dictaphone G.726 ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x150)), "Qualcomm PureVoice");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x151)), "Qualcomm HalfRate");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x155)), "Ring Zero Systems TUBGSM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x160)), "Microsoft Audio1");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x161)), "Windows Media Audio V2 V7 V8 V9 / DivX audio (WMA) / Alex AC3 Audio");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x162)), "Windows Media Audio Professional V9");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x163)), "Windows Media Audio Lossless V9");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x164)), "WMA Pro over S/PDIF");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x170)), "UNISYS NAP ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x171)), "UNISYS NAP ULAW");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x172)), "UNISYS NAP ALAW");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x173)), "UNISYS NAP 16K");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x174)), "MM SYCOM ACM SYC008 SyCom Technologies");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x175)), "MM SYCOM ACM SYC701 G726L SyCom Technologies");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x176)), "MM SYCOM ACM SYC701 CELP54 SyCom Technologies");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x177)), "MM SYCOM ACM SYC701 CELP68 SyCom Technologies");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x178)), "Knowledge Adventure ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x180)), "Fraunhofer IIS MPEG2AAC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x190)), "Digital Theater Systems DTS DS");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x200)), "Creative Labs ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x202)), "Creative Labs FASTSPEECH8");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x203)), "Creative Labs FASTSPEECH10");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x210)), "UHER ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x215)), "Ulead DV ACM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x216)), "Ulead DV ACM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x220)), "Quarterdeck Corp.");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x230)), "I-Link VC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x240)), "Aureal Semiconductor Raw Sport");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x241)), "ESST AC3");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x250)), "Interactive Products HSX");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x251)), "Interactive Products RPELP");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x260)), "Consistent CS2");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x270)), "Sony SCX");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x271)), "Sony SCY");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x272)), "Sony ATRAC3");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x273)), "Sony SPC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x280)), "TELUM Telum Inc.");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x281)), "TELUMIA Telum Inc.");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x285)), "Norcom Voice Systems ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x300)), "Fujitsu FM TOWNS SND");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x301)), "Fujitsu (not specified)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x302)), "Fujitsu (not specified)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x303)), "Fujitsu (not specified)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x304)), "Fujitsu (not specified)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x305)), "Fujitsu (not specified)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x306)), "Fujitsu (not specified)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x307)), "Fujitsu (not specified)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x308)), "Fujitsu (not specified)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x350)), "Micronas Semiconductors, Inc. Development");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x351)), "Micronas Semiconductors, Inc. CELP833");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x400)), "Brooktree Digital");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x401)), "Intel Music Coder (IMC)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x402)), "Ligos Indeo Audio");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x450)), "QDesign Music");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x500)), "On2 VP7 On2 Technologies");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x501)), "On2 VP6 On2 Technologies");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x680)), "AT&T VME VMPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x681)), "AT&T TCP");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x700)), "YMPEG Alpha (dummy for MPEG-2 compressor)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x8ae)), "ClearJump LiteWave (lossless)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1000)), "Olivetti GSM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1000)), "Olivetti");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1001)), "Olivetti ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1002)), "Olivetti CELP");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1003)), "Olivetti SBC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1004)), "Olivetti OPR");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1100)), "Lernout & Hauspie");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1101)), "Lernout & Hauspie CELP codec");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1102)), "Lernout & Hauspie SBC codec");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1103)), "Lernout & Hauspie SBC codec");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1104)), "Lernout & Hauspie SBC codec");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1400)), "Norris Comm. Inc.");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1401)), "ISIAudio");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1500)), "AT&T Soundspace Music Compression");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x181c)), "VoxWare RT24 speech codec");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x181e)), "Lucent elemedia AX24000P Music codec");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1971)), "Sonic Foundry LOSSLESS");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1979)), "Innings Telecom Inc. ADPCM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1c07)), "Lucent SX8300P speech codec");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1c0c)), "Lucent SX5363S G.723 compliant codec");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1f03)), "CUseeMe DigiTalk (ex-Rocwell)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x1fc4)), "NCT Soft ALF2CD ACM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x2000)), "FAST Multimedia DVM");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x2001)), "Dolby DTS (Digital Theater System)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x2002)), "RealAudio 1 / 2 14.4");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x2003)), "RealAudio 1 / 2 28.8");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x2004)), "RealAudio G2 / 8 Cook (low bitrate)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x2005)), "RealAudio 3 / 4 / 5 Music (DNET)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x2006)), "RealAudio 10 AAC (RAAC)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x2007)), "RealAudio 10 AAC+ (RACP)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x3313)), "makeAVIS (ffvfw fake AVI sound from AviSynth scripts)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x4143)), "Divio MPEG-4 AAC audio");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x4201)), "Nokia adaptive multirate");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x4243)), "Divio G726 Divio, Inc.");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x434c)), "LEAD Speech");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x564c)), "LEAD Vorbis");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x5756)), "WavPack Audio");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x674f)), "Ogg Vorbis (mode 1)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x6750)), "Ogg Vorbis (mode 2)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x6751)), "Ogg Vorbis (mode 3)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x676f)), "Ogg Vorbis (mode 1+)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x6770)), "Ogg Vorbis (mode 2+)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x6771)), "Ogg Vorbis (mode 3+)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x7000)), "3COM NBX 3Com Corporation");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x706d)), "FAAD AAC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x7a21)), "GSM-AMR (CBR, no SID)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0x7a22)), "GSM-AMR (VBR, including SID)");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa100)), "Comverse Infosys Ltd. G723 1");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa101)), "Comverse Infosys Ltd. AVQSBC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa102)), "Comverse Infosys Ltd. OLDSBC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa103)), "Symbol Technologies G729A");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa104)), "VoiceAge AMR WB VoiceAge Corporation");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa105)), "Ingenient Technologies Inc. G726");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa106)), "ISO/MPEG-4 advanced audio Coding");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa107)), "Encore Software Ltd G726");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xa109)), "Speex ACM Codec xiph.org");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xdfac)), "DebugMode SonicFoundry Vegas FrameServer ACM Codec");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xe708)), "Unknown");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xf1ac)), "Free Lossless Audio Codec FLAC");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xfffe)), "Extensible");
			VALUESTRINGS_WAVECOMPCODES.put(String.valueOf(new Long(0xffff)), "Development");
		} catch (Exception err) {
			//shouldn't happen
			err.printStackTrace();
		}
	}	
	
	///////////////////////////////////////////////////////////////////////////
	////////////////////  CHUNK TYPES /////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * RIFF chunk type - contains an 8-byte header:
	 * a 4-byte ASCII chunk name, and a 4-byte little-endian length value
	 */
	public static final int CHUNKTYPE_RIFF = 0;
	/**
	 * IFF chunk type - contains an 8-byte header:
	 * a 4-byte ASCII chunk name, and a 4-byte big-endian length value
	 */
	public static final int CHUNKTYPE_IFF = 1;
	/**
	 * PNG chunk type - contains an 8-byte header:
	 * a 4-byte ASCII chunk name, and a 4-byte big-endian length value
	 * and a 4-byte footer (at end of chunk) with CRC checksum info
	 */
	public static final int CHUNKTYPE_PNG = 2;
	/**
	 * A chunk that represents an entire PNG file -
	 * a global header followed by a series of PNG chunks
	 */
	public static final int CHUNKTYPE_PNGFILE = 3;
	/**
	 * A chunk with raw, unknown data in it. No header/footer information.
	 * Unknown file types will contain a single chunk of this type, 
	 * and other file types may have extra bytes at
	 * the end of the file that will be this chunk type as well.
	 */
	public static final int CHUNKTYPE_NOCHUNK = 4;
	/**
	 * A chunk with nothing but plain text in it (may be any encoding, though)
	 */
	public static final int CHUNKTYPE_PLAINTEXT = 5;
	
	/**
	 * Retrieval method for chunk type descriptions
	 * @param chunkType the chunk type constant
	 * @return a text-based description of that chunk type
	 */
	public static String getChunkTypeDescriptionFor(int chunkType) {
		switch (chunkType) {
	        case CHUNKTYPE_RIFF:  return "RIFF";
	        case CHUNKTYPE_IFF:  return "IFF";
	        case CHUNKTYPE_PNG:  return "PNG";
	        case CHUNKTYPE_PNGFILE:  return "PNG file";
	        case CHUNKTYPE_NOCHUNK:  return "Raw data";
	        case CHUNKTYPE_PLAINTEXT:  return "Plain text";
	        default: return "Unknown";
		}
	}
	
    ///////////////////////////////////////////////////////////////////////////
	////////////////////  CHUNK NAMES /////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	public static final String CHUNKNAME_AXML = "AXML";
	public static final String CHUNKNAME_CHRM = "cHRM";
	public static final String CHUNKNAME_COMM = "COMM";
	public static final String CHUNKNAME_DATA = "data";
	public static final String CHUNKNAME_FACT = "fact";
	public static final String CHUNKNAME_FMT = "fmt ";
	public static final String CHUNKNAME_FORM = "FORM";
	public static final String CHUNKNAME_GAMA = "gAMA";
	public static final String CHUNKNAME_IARL = "IARL";
	public static final String CHUNKNAME_IART = "IART";
	public static final String CHUNKNAME_ICMS = "ICMS";
	public static final String CHUNKNAME_ICMT = "ICMT";
	public static final String CHUNKNAME_ICOP = "ICOP";
	public static final String CHUNKNAME_ICRD = "ICRD";
	public static final String CHUNKNAME_ICRP = "ICRP";
	public static final String CHUNKNAME_IDAT = "IDAT";
	public static final String CHUNKNAME_IDIM = "IDIM";
	public static final String CHUNKNAME_IDPI = "IDPI";
	public static final String CHUNKNAME_IEND = "IEND";
	public static final String CHUNKNAME_IENG = "IENG";
	public static final String CHUNKNAME_IGNR = "IGNR";
	public static final String CHUNKNAME_IHDR = "IHDR";
	public static final String CHUNKNAME_IKEY = "IKEY";
	public static final String CHUNKNAME_ILGT = "ILGT";
	public static final String CHUNKNAME_IMED = "IMED";
	public static final String CHUNKNAME_INAM = "INAM";
	public static final String CHUNKNAME_INVALIDDATA = "INVALID";
	public static final String CHUNKNAME_IPLT = "IPLT";
	public static final String CHUNKNAME_IPRD = "IPRD";
	public static final String CHUNKNAME_ISBJ = "ISBJ";
	public static final String CHUNKNAME_ISFT = "ISFT";
	public static final String CHUNKNAME_ISHP = "ISHP";
	public static final String CHUNKNAME_ISRC = "ISRC";
	public static final String CHUNKNAME_ISRF = "ISRF";
	public static final String CHUNKNAME_ITCH = "ITCH";
	public static final String CHUNKNAME_IXML = "IXML";
	public static final String CHUNKNAME_LIST = "LIST";
	public static final String CHUNKNAME_MKBF = "mkBF";
	public static final String CHUNKNAME_MKBS = "mkBS";
	public static final String CHUNKNAME_MKBT = "mkBT";
	public static final String CHUNKNAME_MKTS = "mkTS";	
	public static final String CHUNKNAME_PHYS = "PHYS";
	public static final String CHUNKNAME_PLAINTEXT = "PLAINTEXT";
	public static final String CHUNKNAME_PNGFILE = "PNG_FILE";
	public static final String CHUNKNAME_PRVW = "prVW";
	public static final String CHUNKNAME_RAWDATA = "RAW_DATA";
	public static final String CHUNKNAME_RIFF = "RIFF";
	public static final String CHUNKNAME_RIFX = "RIFX";
	public static final String CHUNKNAME_SBIT = "SBIT";
	public static final String CHUNKNAME_SRGB = "sRGB";
	public static final String CHUNKNAME_TEXT = "tEXt";
	public static final String CHUNKNAME_ZTXT = "zTXt";
	
	/**
	 * Retrieval method for chunk descriptions
	 * @param chunkName name of the chunk (one of the CHUNKNAME_ constants)
	 * @return a text-based description of that chunk
	 */
	public static String getChunkNameDescriptionFor(String chunkName) {
		/*
		 * NOTE: So far I've been able to use lower case
		 * only to determine a chunk type. This may change eventually.
		 */
		chunkName = chunkName.toLowerCase();
		if (chunkName.equals(CHUNKNAME_AXML.toLowerCase())) {
			return "AXML metadata chunk (Broadcast WAVE)";
		}
		if (chunkName.equals(CHUNKNAME_CHRM.toLowerCase())) {
			return "Primary chromaticities for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_COMM.toLowerCase())) {
			return "Required basic information chunk for an AIFF file";
		}
		if (chunkName.equals(CHUNKNAME_DATA.toLowerCase())) {
			return "A WAVE file's actual sample data";
		}
		if (chunkName.equals(CHUNKNAME_FACT.toLowerCase())) {
			return "Additional information for compressed/extended WAV files.";
		}
		if (chunkName.equals(CHUNKNAME_FMT.toLowerCase())) {
			return "Required format chunk for a WAVE file";
		}
		if (chunkName.equals(CHUNKNAME_FORM.toLowerCase())) {
			return "Parent chunk for an IFF formatted file such as AIFF";
		}
		if (chunkName.equals(CHUNKNAME_GAMA.toLowerCase())) {
			return "Image gamma for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_IARL.toLowerCase())) {
			return "Info chunk (Archival Location)";
		}
		if (chunkName.equals(CHUNKNAME_IART.toLowerCase())) {
			return "Info chunk (Artist)";
		}
		if (chunkName.equals(CHUNKNAME_ICMS.toLowerCase())) {
			return "Info chunk (Commissioned)";
		}
		if (chunkName.equals(CHUNKNAME_ICMT.toLowerCase())) {
			return "Info chunk (Comment)";
		}
		if (chunkName.equals(CHUNKNAME_ICOP.toLowerCase())) {
			return "Info chunk (Copyright)";
		}
		if (chunkName.equals(CHUNKNAME_ICRD.toLowerCase())) {
			return "Info chunk (Date Created)";
		}
		if (chunkName.equals(CHUNKNAME_ICRP.toLowerCase())) {
			return "Info chunk (Cropped)";
		}
		if (chunkName.equals(CHUNKNAME_IDAT.toLowerCase())) {
			return "Image data chunk for PNG file";
		}
		if (chunkName.equals(CHUNKNAME_IDIM.toLowerCase())) {
			return "Info chunk (Dimensions)";
		}
		if (chunkName.equals(CHUNKNAME_IDPI.toLowerCase())) {
			return "Info chunk (Dots Per Inch)";
		}
		if (chunkName.equals(CHUNKNAME_IEND.toLowerCase())) {
			return "Final (empty) chunk for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_IENG.toLowerCase())) {
			return "Info chunk (Engineer)";
		}
		if (chunkName.equals(CHUNKNAME_IGNR.toLowerCase())) {
			return "Info chunk (Genre)";
		}
		if (chunkName.equals(CHUNKNAME_IHDR.toLowerCase())) {
			return "PNG Header Chunk";
		}
		if (chunkName.equals(CHUNKNAME_IKEY.toLowerCase())) {
			return "Info chunk (Keywords)";
		}
		if (chunkName.equals(CHUNKNAME_ILGT.toLowerCase())) {
			return "Info chunk (Lightness)";
		}
		if (chunkName.equals(CHUNKNAME_IMED.toLowerCase())) {
			return "Info chunk (Medium)";
		}
		if (chunkName.equals(CHUNKNAME_INAM.toLowerCase())) {
			return "Info chunk (Title)";
		}
		if (chunkName.equals(CHUNKNAME_INVALIDDATA.toLowerCase())) {
			return "Data which is invalid and cannot be parsed correctly";
		}
		if (chunkName.equals(CHUNKNAME_IPLT.toLowerCase())) {
			return "Info chunk (Number of Colors)";
		}
		if (chunkName.equals(CHUNKNAME_IPRD.toLowerCase())) {
			return "Info chunk (Product)";
		}
		if (chunkName.equals(CHUNKNAME_ISBJ.toLowerCase())) {
			return "Info chunk (Subject)";
		}
		if (chunkName.equals(CHUNKNAME_ISFT.toLowerCase())) {
			return "Info chunk (Software)";
		}
		if (chunkName.equals(CHUNKNAME_ISHP.toLowerCase())) {
			return "Info chunk (Sharpness)";
		}
		if (chunkName.equals(CHUNKNAME_ISRC.toLowerCase())) {
			return "Info chunk (Source)";
		}
		if (chunkName.equals(CHUNKNAME_ISRF.toLowerCase())) {
			return "Info chunk (SourceForm)";
		}
		if (chunkName.equals(CHUNKNAME_ITCH.toLowerCase())) {
			return "Info chunk (Technician)";
		}
		if (chunkName.equals(CHUNKNAME_IXML.toLowerCase())) {
			return "IXML metadata chunk";
		}
		if (chunkName.equals(CHUNKNAME_LIST.toLowerCase())) {
			return "A list chunk contains subchunk data for RIFF-formatted files";
		}
		if (chunkName.equals(CHUNKNAME_MKBF.toLowerCase())) {
			return "Private Adobe Fireworks chunk for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_MKBS.toLowerCase())) {
			return "Private Adobe Fireworks chunk for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_MKBT.toLowerCase())) {
			return "Private Adobe Fireworks chunk for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_MKTS.toLowerCase())) {
			return "Private Adobe Fireworks chunk for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_PHYS.toLowerCase())) {
			return "Physical pixel dimensions for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_PLAINTEXT.toLowerCase())) {
			return "A chunk of plain text";
		}
		if (chunkName.equals(CHUNKNAME_PNGFILE.toLowerCase())) {
			return "Data which represents a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_PRVW.toLowerCase())) {
			return "Private Adobe Fireworks chunk for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_RAWDATA.toLowerCase())) {
			return "Unstructured or unknown data";
		}
		if (chunkName.equals(CHUNKNAME_RIFF.toLowerCase())) {
			return "Parent chunk for a RIFF formatted file such as WAV or AVI";
		}
		if (chunkName.equals(CHUNKNAME_RIFX.toLowerCase())) {
			return "Variant of RIFF chunk for big-endian data";
		}
		if (chunkName.equals(CHUNKNAME_SBIT.toLowerCase())) {
			return "Significant bits chunk for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_SRGB.toLowerCase())) {
			return "Standard RGB color space for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_TEXT.toLowerCase())) {
			return "Text chunk for a PNG file";
		}
		if (chunkName.equals(CHUNKNAME_ZTXT.toLowerCase())) {
			return "Compressed text chunk for a PNG file";
		}
		return "Unknown chunk type";
	}
	
    ///////////////////////////////////////////////////////////////////////////
	////////////////////  FILE TYPES  /////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	public static String getFileTypeDescriptionFor(String className) {
		if (className.equals("malictus.klang.file.AIFFFile")) {
			return "AIFF audio file";
		} else if (className.equals("malictus.klang.file.AVIFile")) {
			return "AVI video file";
		} else if (className.equals("malictus.klang.file.FORMFile")) {
			return "Generic FORM (IFF) file";
		} else if (className.equals("malictus.klang.file.PNGFile")) {
			return "PNG image file";
		} else if (className.equals("malictus.klang.file.RawFile")) {
			return "Raw file (type is unknown)";
		} else if (className.equals("malictus.klang.file.RIFFFile")) {
			return "Generic RIFF file";
		} else if (className.equals("malictus.klang.file.WAVEFile")) {
			return "WAVE audio file";
		} else if (className.equals("malictus.klang.file.PlainTextFile")) {
			return "Plain Text File";
		} else {
			return "Unknown file type";
		}
	}

	///////////////////////////////////////////////////////////////////////////
	/////////////////  KLANG EDITOR STRINGS     ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	//the font used by text fields (NOTE: MUST BE A MONOSPACED FONT!!)
	public static final Font KLANGEDITOR_TEXTFIELD_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11);
	public static final Font KLANGEDITOR_BORDERTITLE_FONT = new java.awt.Font(("Sans-serif"), Font.BOLD, 10);
	public static final String KLANGEDITOR_TITLE = "Klang Editor";
	public static final String KLANGEDITOR_ABOUT_TEXT = KLANGEDITOR_TITLE + " version " 
			+ KLANG_VERSION + "\n" + "Jim Halliday, 2009";
	public static final String KLANGEDITOR_MENU_QUIT = "Quit";
	public static final String KLANGEDITOR_MENU_CLOSE = "Close";
	public static final String KLANGEDITOR_MENU_OPEN = "Open...";
	public static final String KLANGEDITOR_MENU_WEB = "Visit Website...";
	public static final String KLANGEDITOR_MENU_DONATE = "Donate...";
	public static final String KLANGEDITOR_MENU_CREDITS = "About Klang Editor...";
	public static final String KLANGEDITOR_MENU_FILE = "File";
	public static final String KLANGEDITOR_MENU_ABOUT = "About";
	public static final String KLANGEDITOR_MENU_READONLY = "Read-Only Mode";
	public static final String KLANGEDITOR_MENU_DEC = "Decimal Display";
	public static final String KLANGEDITOR_MENU_HEX = "Hex Display";
	public static final String KLANGEDITOR_MENU_OPTIONS = "Options";
	public static final String KLANGEDITOR_MENU_AUTOTEXT = "Determine Text/Encoding Automatically";
	public static final String KLANGEDITOR_MENU_MANUALTEXT = "Always Ask for Unknown Text Encoding";
	public static final String KLANGEDITOR_URL_PROJECT = "http://malictus.net/klang/";
	public static final String KLANGEDITOR_URL_DONATE = "http://www.malictus.net/klang/?n=Main.Donate";
	public static final String KLANGEDITOR_ERROR_OPENING_TITLE = "Error opening file";
	public static final String KLANGEDITOR_ERROR_OPENING = "Error opening file:\n";
	public static final String KLANGEDITOR_BASICINFO_NAME = "File Name: ";
	public static final String KLANGEDITOR_BASICINFO_LOCATION = "Location: ";
	public static final String KLANGEDITOR_BASICINFO_SIZE = "Size: ";
	public static final String KLANGEDITOR_BASICINFO_FILETYPE = "File Type: ";
	public static final String KLANGEDITOR_BASICINFO_PANELNAME = "FILE INFORMATION";
	public static final String KLANGEDITOR_CHUNKINFO_PANELNAME = "CHUNK INFORMATION";
	public static final String KLANGEDITOR_CHUNKINFO_NAME = "Chunk Name: ";
	public static final String KLANGEDITOR_CHUNKINFO_DESC = "Description: ";
	public static final String KLANGEDITOR_CHUNKINFO_TYPE = "Type: ";
	public static final String KLANGEDITOR_CHUNKINFO_START = "Start: ";
	public static final String KLANGEDITOR_CHUNKINFO_END = "End: ";
	public static final String KLANGEDITOR_CHUNKINFO_SIZE = "Size: ";
	public static final String KLANGEDITOR_CHUNKINFO_DATASTART = "Data Start: ";
	public static final String KLANGEDITOR_CHUNKINFO_DATAEND = "Data End: ";
	public static final String KLANGEDITOR_CHUNKINFO_DATASIZE = "Data Size: ";
	public static final String KLANGEDITOR_CHUNKINFO_CHECKSUMLABEL = "Checksum: ";
	public static final String KLANGEDITOR_CHUNKINFO_CHECKSUMLABELDATA = "Data Only Checksum: ";
	public static final String KLANGEDITOR_CHUNKINFO_CHECKSUMBUTTON = "Generate checksum";
	public static final String KLANGEDITOR_CHUNKINFO_CHECKSUMDATALABEL = "Data Checksum: ";
	public static final String KLANGEDITOR_CHUNKEDIT_ADD = "Add New Chunk";
	public static final String KLANGEDITOR_CHUNKEDIT_EDITNAME = "Edit Name";
	public static final String KLANGEDITOR_CHUNKNAME_PROMPT = "Enter a new chunk name:";
	public static final String KLANGEDITOR_CHUNKNAME_PROMPT_TITLE = "Enter new chunk name";
	public static final String KLANGEDITOR_CONTAINER_NAME_WARN = "WARNING: Editing the name of a container chunk \nmay cause " +
			"any child chunks to become unavailable.";
	public static final String KLANGEDITOR_CONTAINER_NAME_WARN_TITLE = "Edit container chunk warning";
	public static final String KLANGEDITOR_CHUNKPRIM_PANELNAME = "CHUNK DATA";
	public static final String KLANGEDITOR_PRIMDIALOG_TITLE = "Edit";
	public static final String KLANGEDITOR_PRIMDIALOG_PRIMNAME = "Data Name";
	public static final String KLANGEDITOR_PRIMDIALOG_PRIMTYPE = "Data Type";
	public static final String KLANGEDITOR_PRIMDIALOG_PRIMVAL = "Value";
	public static final String KLANGEDITOR_PRIMDIALOG_TEXTENC = "Save As Text Encoding:";
	public static final String KLANGEDITOR_RAWEDIT_PANELNAME = "RAW FILE ACCESS";
	public static final String KLANGEDITOR_RAWEDIT_CURBYTELABEL = "Current Byte";
	public static final String KLANGEDITOR_RAWEDIT_LBLASCII = "ASCII";
	public static final String KLANGEDITOR_RAWEDIT_LBLHEX = "Raw Hex";
	public static final String KLANGEDITOR_RAWEDIT_JUMPBUTTON = "Jump";
	public static final String KLANGEDITOR_RAWEDIT_BACK1 = "<-- 1";
	public static final String KLANGEDITOR_RAWEDIT_BACK16 = "<-- 16";
	public static final String KLANGEDITOR_RAWEDIT_FOR1 = "1 -->";
	public static final String KLANGEDITOR_RAWEDIT_FOR16 = "16 -->";
	public static final String KLANGEDITOR_RAWEDIT_INSERTBYTES = "Insert";
	public static final String KLANGEDITOR_RAWEDIT_DELETEBYTES = "Delete";
	public static final String KLANGEDITOR_RAWEDIT_EXPORTBYTES = "Export";
	public static final String KLANGEDITOR_RAWEDIT_PRIMVIEW1 = "Current Position As:";
	public static final String KLANGEDITOR_RAWEDIT_PRIMEDITBUTTON = "Edit";
	public static final String KLANGEDITOR_PRIMSUB_EDIT = "Edit";
	public static final String KLANGEDITOR_PRIMSUB_JUMPTO = "Go";
	public static final String KLANGEDITOR_TREEVIEW_PANELNAME = "CHUNK HIERARCHY";
	public static final String KLANGEDITOR_DELETEBYTESDIALOG_TITLE = "Delete Raw Bytes";
	public static final String KLANGEDITOR_BYTESDIALOG_FROM = "Start Byte Position";
	public static final String KLANGEDITOR_BYTESDIALOG_TO = "End Byte Position";
	public static final String KLANGEDITOR_EXPORTBYTESDIALOG_FILELBL = "Export To File:";
	public static final String KLANGEDITOR_BROWSEPANEL_FILEBTN = "Browse";
	public static final String KLANGEDITOR_INSERTBYTESDIALOG_TITLE = "Insert Bytes";
	public static final String KLANGEDITOR_INSERTBYTESDIALOG_FILELBL = "Insert From File:";
	public static final String KLANGEDITOR_INSERTBYTESDIALOG_RADBLANK = "Insert ";
	public static final String KLANGEDITOR_INSERTBYTESDIALOG_FILEEND = "Empty Bytes";
	public static final String KLANGEDITOR_INSERTBYTESDIALOG_RADFILE = "Insert a file (in its entirety)";
	public static final String KLANGEDITOR_RAWEDIT_NUMERR = "Cannot format string input as numbers";
	public static final String KLANGEDITOR_RAWEDIT_NUMERR2 = "End byte must be greater than start byte";
	public static final String KLANGEDITOR_RAWEDIT_NUMERR3 = "Error in exporting data";
	public static final String KLANGEDITOR_RAWEDIT_COFIRMDEL = "File already exists. Overwrite?";
	public static final String KLANGEDITOR_EXPORTBYTESDIALOG_TITLE = "Export Raw Bytes";
	public static final String KLANG_TEXTENC_DIALOG_TITLE = "Choose a text encoding";
	public static final String KLANG_TEXTENC_DIALOG_TEXT = "Document may contain plain text. Please enter a likely text encoding.";
	public static final String KLANG_TEXTENC_DIALOG_TEXT1 = "The chunk \"";
	public static final String KLANG_TEXTENC_DIALOG_TEXT2 = "\" may contain plain text. Please enter a likely text encoding";
	public static final String KLANG_TEXTENC_DIALOG_NONE = "Not text";
	
	public static final String KLANGEDITOR_CHUNKEDIT_EXPORT = "Export";
	public static final String KLANGEDITOR_CHUNKEDIT_DELETE = "Delete";
	public static final String KLANGEDITOR_WARNING_FILE_EXISTS = "File exists; overwrite?";
	public static final String KLANGEDITOR_WARNING_FILE_EXISTS_TITLE = "Overwrite?";
	public static final String KLANGEDITOR_ERROR_TITLE = "Error";
	public static final String KLANGEDITOR_REALLY_DELETE = "Really delete the chunk?\n This change is permanent.";
	public static final String KLANGEDITOR_REALLY_DELETE_TITLE = "Really delete?";
	
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_TITLE = "Add New Chunk";
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_CURCHUNKTEXT1 = "Add new chunk";
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_CHUNKNAMELBL = "New Chunk Name:";
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_BEFORE = "before";
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_AFTER = "after";
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_UNDER = "under (as first child)";
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_CURCHUNK = "Current Chunk Name";
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_RADBLANK = "Insert empty chunk";
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_RADFILE = "Insert chunk data from file";
	public static final String KLANGEDITOR_ADDNEWCHUNKDIALOG_FILELBL = "Add Data From File";
	
	public static final int KLANGEDITOR_INITIAL_WIDTH = 800;
	public static final int KLANGEDITOR_INITIAL_HEIGHT = 600;
	public static final int KLANGEDITOR_BASICPANEL_HEIGHT = 160;
	public static final int KLANGEDITOR_BASICPANEL_WIDTH = 350;
	public static final int KLANGEDITOR_CHUNKEDITPANEL_HEIGHT = 160;
	public static final int KLANGEDITOR_CHUNKEDITPANEL_WIDTH = 250;
	public static final int KLANGEDITOR_CHUNKTREE_PANELWIDTH = 335;
	public static final int KLANGEDITOR_PROGRESSWINDOW_WIDTH = 390;
	public static final int KLANGEDITOR_PROGRESSWINDOW_HEIGHT = 88;
	public static final int KLANGEDITOR_RAWEDIT_EDITSTRIP_WIDTH = 600;
	
	/**
	 * Comma-separated list of all supported file type extensions (case insensitive)
	 */
	public static final String KLANGEDITOR_SUPPORTED_FILE_EXTENSIONS = "wav,wave,bwf,bwav,bwave,avi,avix,png,aiff,iff,aifc,afc,aif,html,htm,xml,xhtml,xht,txt,properties,jsp,php,css,pl,c,java,js,dtd,sgml,xsl,xslt,xsd,wsdl,cgi,perl,sql";
	public static final String KLANGEDITOR_SUPPORTED_FILE_EXTENSIONS_DESC = "All supported file types";

	/**
	 * All the primitives that should show up in the raw panel's combo box should go here
	 * NOTE: These should be only fixed byte primitives!
	 */
	public static Vector<String> KLANGEDITOR_DISPLAY_PRIMS = new Vector<String>();
	static {
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.IntByteSigned");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.IntByteUnsigned");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.Int2ByteSignedBE");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.Int2ByteSignedLE");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.Int2ByteUnsignedBE");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.Int2ByteUnsignedLE");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.Int4ByteSignedBE");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.Int4ByteSignedLE");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.Int4ByteUnsignedBE");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.Int4ByteUnsignedLE");
		KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.add("malictus.klang.primitives.ASCIIChar");	
	}
}
