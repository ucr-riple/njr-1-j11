package com.github.lindenb.dataindexer;

import java.io.File;
import java.io.IOException;

/** default implementation of RandomAccessFactory using 
 * a FileOutputStream and a RandomAccessFile
 *
 */
public class DefaultRandomAccessFactory implements
		RandomAccessFactory
	{
	@Override
	public RandomAccessOutput openForWriting(File file) throws IOException
		{
		return new DefaultRandomAccessOutput(file);
		}
	@Override
	public RandomAccessInput openForReading(File file) throws IOException {
		return new DefaultRandomAccessInput(file);
		}
	@Override
	public String toString() {
		return getClass().getName();
		}
	}
