package com.github.lindenb.dataindexer;

import java.io.IOException;
import java.io.OutputStream;

public abstract class RandomAccessOutput extends OutputStream
	{
	public RandomAccessOutput()
		{
		}
	
	public abstract long getOffset() throws IOException;
	
	
	}
