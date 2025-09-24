package com.github.lindenb.dataindexer;

import java.util.Comparator;

public class NaturalComparator<T extends Comparable<T>>
	implements Comparator<T>
	{
	@Override
	public int compare(T a, T b)
			{
			return a.compareTo(b);
			}
	}
