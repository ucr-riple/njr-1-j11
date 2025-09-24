package com.github.lindenb.dataindexer;

import java.io.IOException;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SecondaryDatabaseReader<PRIMARY,T>
	extends AbstractDatabaseReader<ObjectAndOffset<T>,SecondaryConfig<PRIMARY,T>>
	{
	private PrimaryDatabaseReader<PRIMARY> owner;
	
	
	public SecondaryDatabaseReader(
		PrimaryDatabaseReader<PRIMARY> owner,
		SecondaryConfig<PRIMARY,T> config
		) throws IOException
		{
		super(config);
		this.owner=owner;
		}
	
	public PrimaryDatabaseReader<PRIMARY> getOwner()
		{
		return this.owner;
		}
	
	@Override
	/** this is the sizeof a ObjectAndOffset= sizeof(object)+sizeof(long) */
	protected int getSizeOf()
		{
		return super.getSizeOf()+8;
		}	
	
	protected  long lower_bound(final T object)
		throws IOException
		{
		return lower_bound(0L, this.size(), object);
		}
	
	public boolean contains(final T object) throws IOException
		{
		return !equal_range(object).isEmpty();
		}
	
	private Interval equal_range(
			long first,
            long last,
            final T val,
            boolean include_last
            ) throws IOException
		{	
	    

	        long len = last-first;
	        long half;
	        long middle, left, right;

	        while (len > 0)
	  	{
	  	  half = len /2;
	  	  middle = first;
	  	  middle+=half;
	  	  T at_mid=get(middle).getObject();
	  	  if (getConfig().getComparator().compare(at_mid, val) <0)
	  	    {
	  	      first = middle;
	  	      ++first;
	  	      len = len - half - 1;
	  	    }
	  	  else if (getConfig().getComparator().compare(val,at_mid) <0 )
	  	  	{
	  	    len = half;
	  	  	}
	  	  else
	  	    {
	  	      left =lower_bound(first, middle, val);
	  	     first+=len;
	  	      right = upper_bound(++middle, first, val,include_last);
	  	      return new Interval(left, right);
	  	    }
	  	}
	        return  new Interval(first, first);

		
		}
	
	protected Interval equal_range(
            final T object
            ) throws IOException
		{
		return equal_range(0,size(),object,true);
		}
	
	
	
    /** C+ lower_bound */
    protected  long lower_bound(
                long first,
                long last,
                final T object
                ) throws IOException
        {
    	Comparator<T> cmp=getConfig().getComparator();
        long len = last - first;
        while (len > 0)
                {
                long half = len / 2;
                long middle = first + half;
                ObjectAndOffset<T> oao= get(middle);
                if ( cmp.compare(oao.getObject(), object) < 0  )
                        {
                        first = middle + 1;
                        len -= half + 1;
                        }
                else
                        {
                        len = half;
                        }
                }
        return first;
        }

    private long upper_bound(
    		long first,long last,
    		final T select,
    		boolean include_last
    		) throws IOException
    	{
        long len = last - first;
        while (len > 0)
                {
                long half = len / 2;
                long middle = first + half;
                ObjectAndOffset<T> x= this.get(middle);
                int cmp=getConfig().getComparator().compare(select,x.getObject());
                if (cmp<0)
                        {
                        len = half;
                        }
                else
                        {
                        first = middle + 1;
                        len -= half + 1;
                        }
                }
        return first;
    	}
    
	
	
	private abstract class AbstractIntervalIterator<Z> implements Iterator<Z>
		{
		protected Iterator<Long> delegate;
		AbstractIntervalIterator(Interval interval)
			{
			this.delegate=interval.iterator();
			}
		@Override
		public boolean hasNext() {
			return this.delegate.hasNext();
			}
		@Override
		public void remove()
			{
			this.delegate.remove();
			}
		
		protected abstract Z priv_next() throws IOException;
		
		@Override
		public Z next() {
			try {
				return  priv_next();
			} catch (IOException e) {
				throw new RuntimeException(e);
				}
			}

		
		}
	
	private class IntervalIterator extends AbstractIntervalIterator<T>
		{
		IntervalIterator(Interval interval)
			{
			super(interval);
			}
		@Override
		public T priv_next() throws IOException
			{
			long real_index=this.delegate.next();
			return SecondaryDatabaseReader.this.get(real_index).getObject();
			}
		}
	
	private class PrimaryIterator extends AbstractIntervalIterator<Pair<PRIMARY,T>>
		{
		PrimaryIterator(Interval interval)
			{
			super(interval);
			}
		@Override
		public Pair<PRIMARY,T> priv_next() throws IOException
			{
			long real_index=this.delegate.next();
			ObjectAndOffset<T> oao=SecondaryDatabaseReader.this.get(real_index);
			T t=oao.getObject();
			PRIMARY p=getOwner().getItemFromOffset(oao.getOffset());
			return new Pair<PRIMARY,T>(p, t);
			}
		}
	
	private class PrimaryKeyIterator extends AbstractIntervalIterator<PRIMARY>
		{
		PrimaryKeyIterator(Interval interval)
			{
			super(interval);
			}
		@Override
		public PRIMARY priv_next() throws IOException
			{
			long real_index=this.delegate.next();
			ObjectAndOffset<T> oao=SecondaryDatabaseReader.this.get(real_index);
			PRIMARY p=getOwner().getItemFromOffset(oao.getOffset());
			return p;
			}
		}
	
	
	private abstract class AbstractIntervalList<Z> extends AbstractList<Z>
		{
		protected Interval interval;
		public AbstractIntervalList(Interval interval)
			{
			this.interval=interval;
			}
		protected abstract Z priv_get(long index) throws IOException;
		@Override
		public Z get(int index)
			{
			if(index<0 || index>=interval.distance()) throw new IndexOutOfBoundsException(
					"0<="+index+"<"+interval.distance()
					);
			try {
				return priv_get(interval.getFirst()+index);
				} 
			catch (IOException e)
				{
				throw new RuntimeException(e);
				}
			}

		
		@Override
		public int size()
			{
			return (int)Math.min(Integer.MAX_VALUE,interval.distance());
			}

		
		}
	private class IntervalList extends AbstractIntervalList<T>
		{
		public IntervalList(Interval interval)
			{
			super(interval);
			}
		
		@Override
		protected T priv_get(long real_index) throws IOException
			{
			return SecondaryDatabaseReader.this.get(real_index).getObject();
			}
		@Override
		public Iterator<T> iterator() {
			return new IntervalIterator(this.interval);
			}
		}
	
	private class PrimaryList extends AbstractIntervalList<Pair<PRIMARY, T>>
		{
		public PrimaryList(Interval interval)
			{
			super(interval);
			}
		@Override
		protected Pair<PRIMARY, T> priv_get(long real_index) throws IOException 
			{
			ObjectAndOffset<T> oao=SecondaryDatabaseReader.this.get(real_index);
			T t=oao.getObject();
			PRIMARY p=getOwner().getItemFromOffset(oao.getOffset());
			return new Pair<PRIMARY,T>(p, t);
			}
		@Override
		public Iterator<Pair<PRIMARY, T>> iterator()
			{
			return new PrimaryIterator(this.interval);
			}
		}

	private class PrimaryKeyList extends AbstractIntervalList<PRIMARY>
		{
		public PrimaryKeyList(Interval interval)
			{
			super(interval);
			}
		@Override
		protected PRIMARY priv_get(long real_index) throws IOException 
			{
			ObjectAndOffset<T> oao=SecondaryDatabaseReader.this.get(real_index);
			return getOwner().getItemFromOffset(oao.getOffset());
			}
		@Override
		public Iterator<PRIMARY> iterator()
			{
			return new PrimaryKeyIterator(this.interval);
			}
		}

	
	
	public List<T> getList(final T val) throws IOException
		{
		return new IntervalList(equal_range(val));
		}
	
	public List<Pair<PRIMARY,T>> getPrimaryList(final T val) throws IOException
		{
		return new PrimaryList(equal_range(val));
		}
	
	public List<PRIMARY> getPrimaryKeyList(final T val) throws IOException
		{
		return new PrimaryKeyList(equal_range(val));
		}

	}
