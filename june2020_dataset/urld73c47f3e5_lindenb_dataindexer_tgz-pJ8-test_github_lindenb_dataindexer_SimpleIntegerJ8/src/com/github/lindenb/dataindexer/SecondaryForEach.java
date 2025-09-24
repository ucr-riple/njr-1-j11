package com.github.lindenb.dataindexer;

import java.io.IOException;

public interface SecondaryForEach<PRIMARY, T>
	{
	public void onBegin() throws IOException;
	public int apply(final PRIMARY primaryObject,final T secondaryObject) throws IOException;
	public void onEnd() throws IOException;
	}
