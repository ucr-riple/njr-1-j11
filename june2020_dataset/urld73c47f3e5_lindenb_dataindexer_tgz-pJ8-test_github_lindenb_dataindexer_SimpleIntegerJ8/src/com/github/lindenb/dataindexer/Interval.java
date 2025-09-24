package com.github.lindenb.dataindexer;

import java.util.Iterator;

public class Interval
extends Pair<Long, Long>
implements Iterable<Long>
	{
	public Interval(long B,long E)
		{
		super(B,E);
		}
	public boolean isEmpty()
		{
		return getFirst().equals(getSecond());
		}
	public long distance()
		{
		return getSecond()-getFirst();
		}
	@Override
	public String toString()
		{
		return "["+getFirst()+"-"+getSecond()+"[";
		}
	
	@Override
	public Iterator<Long> iterator()
		{
		return new Iter();
		}
	
	private class Iter implements Iterator<Long>
		{
		private long index;
		Iter()
			{
			this.index=Interval.this.getFirst();
			}
		@Override
		public boolean hasNext()
			{
			return this.index<Interval.this.getSecond();
			}
		@Override
		public Long next()
			{
			if(index>=getSecond()) throw new IllegalStateException();
			long n=this.index;
			this.index++;
			return n;
			}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
			}
		@Override
		public String toString() {
			return String.valueOf(this.index);
			}
		}
	
}
