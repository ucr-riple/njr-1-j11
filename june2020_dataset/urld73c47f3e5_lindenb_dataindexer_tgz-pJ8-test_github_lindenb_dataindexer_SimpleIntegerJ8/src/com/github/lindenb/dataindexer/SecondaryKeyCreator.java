package com.github.lindenb.dataindexer;

import java.util.Set;

public interface SecondaryKeyCreator<T, K>
	{
	public Set<K> getSecondaryKeys(T t);
	}
	
