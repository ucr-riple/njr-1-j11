package com.github.lindenb.dataindexer;

import java.io.IOException;

public interface PrimaryForEach<T>
	{
	public void onBegin() throws IOException;
	public int apply(T object) throws IOException;
	public void onEnd() throws IOException;
	}
