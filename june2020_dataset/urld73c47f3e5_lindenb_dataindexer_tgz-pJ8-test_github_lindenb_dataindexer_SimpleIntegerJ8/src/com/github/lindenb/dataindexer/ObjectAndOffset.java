package com.github.lindenb.dataindexer;

class ObjectAndOffset<T>
	extends Pair<T,Long>
	{
	public ObjectAndOffset(T object,long offset)
		{
		super(object,offset);
		}

	public T getObject()
		{
		return getFirst();
		}
	
	public long getOffset()
		{
		return getSecond();
		}
	

	}
