package com.github.lindenb.dataindexer;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Comparator;

public class SecondaryConfig<PRIMARY, K> extends AbstractConfig<ObjectAndOffset<K>>
	{
	protected SecondaryKeyCreator<PRIMARY, K> keyCreator;
	private Comparator<K> comparator;
	private TupleBinding<K> keyBinding=null;
	private TupleBinding<ObjectAndOffset<K>> dataBinding=null;
	private int bufferSize=10000;
	
	@Override
	protected void validate() throws IOException
		{
		super.validate();
		if(getComparator()==null) throw new IllegalStateException("Undefined comparator");
		if(getKeyBinding()==null) throw new IllegalStateException("Undefined keyBinding");
		if(getDataBinding()==null) throw new IllegalStateException("Undefined dataBinding");
		}
	
	
	public void setBufferSize(int bufferSize)
		{
		this.bufferSize = bufferSize;
		}
	
	public int getBufferSize()
		{
		return bufferSize;
		}
	
	@Override
	public void validateForWriting() throws IOException
		{
		super.validateForWriting();
		if(getBufferSize()<=0) throw new IllegalStateException("Illegal buffer size");
		}
	
	public SecondaryKeyCreator<PRIMARY, K> getKeyCreator() {
		return keyCreator;
	}
	public void setKeyCreator(SecondaryKeyCreator<PRIMARY, K> keyCreator) {
		this.keyCreator = keyCreator;
	}
	public Comparator<K> getComparator() {
		return comparator;
	}
	public void setComparator(Comparator<K> comparator) {
		this.comparator = comparator;
	}
	
	
	public void setKeyBinding(TupleBinding<K> keyBinding)
		{
		this.keyBinding = keyBinding;
		this.dataBinding=
				 new TupleBinding<ObjectAndOffset<K>>()
						{
						@Override
						public ObjectAndOffset<K> readObject(DataInputStream in) throws IOException
							{
							K object=getKeyBinding().readObject(in);
							long offset=in.readLong();
							return new ObjectAndOffset<K>(object,offset);
							}
						@Override
						public void writeObject(final ObjectAndOffset<K> o, java.io.DataOutputStream out) throws IOException
							{
							getKeyBinding().writeObject(o.getObject(), out);
							out.writeLong(o.getOffset());
							}
						};
					
		}
	
	public TupleBinding<K> getKeyBinding()
		{
		return keyBinding;
		}
	
	
	
	@Override
	public  TupleBinding<ObjectAndOffset<K>> getDataBinding() {
		return dataBinding;
		}

	
	Comparator<ObjectAndOffset<K>> createObjectAndOffsetComparator()
		{
		return new Comparator<ObjectAndOffset<K>>()
			{
			public int compare(
					final ObjectAndOffset<K> o1, 
					final ObjectAndOffset<K> o2)
				{
				int i= getComparator().compare(o1.getObject(), o2.getObject());
				if(i!=0) return i;
				return (o1.getOffset()==o2.getOffset()?0:o1.getOffset()<o2.getOffset()?-1:1);
				}
			};
		}
	}
