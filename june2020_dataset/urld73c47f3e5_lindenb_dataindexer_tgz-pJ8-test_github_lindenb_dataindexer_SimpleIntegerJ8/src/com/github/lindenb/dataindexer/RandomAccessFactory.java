package com.github.lindenb.dataindexer;

import java.io.File;
import java.io.IOException;

public interface RandomAccessFactory
	{
	public RandomAccessOutput openForWriting(File file) throws IOException;
	public RandomAccessInput openForReading(File file) throws IOException;
	}
