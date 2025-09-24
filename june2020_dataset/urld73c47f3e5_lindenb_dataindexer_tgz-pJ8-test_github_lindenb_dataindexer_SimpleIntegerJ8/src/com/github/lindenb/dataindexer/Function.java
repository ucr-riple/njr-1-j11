package com.github.lindenb.dataindexer;

import java.io.IOException;

public interface Function<T>
	{
	public int apply(final T key) throws IOException;
	}
