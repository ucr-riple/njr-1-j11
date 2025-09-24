package com.github.lindenb.dataindexer;

import java.io.IOException;
import java.io.InputStream;

/** input stream with random access capabilities */
public abstract class RandomAccessInput extends InputStream
	{
	/** set the file offset for next read action */
	public abstract void seek(long offset) throws IOException;
	}
