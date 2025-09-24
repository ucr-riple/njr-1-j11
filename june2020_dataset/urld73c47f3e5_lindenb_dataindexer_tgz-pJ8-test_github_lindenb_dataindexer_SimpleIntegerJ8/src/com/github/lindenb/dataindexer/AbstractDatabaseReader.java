package com.github.lindenb.dataindexer;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.AbstractList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Abstract class  to random-access a list of item 'T' 
 * @author lindenb
 *
 * @param <T> the type of item
 * @param <CONFIG> the configuration type
 */
public class AbstractDatabaseReader<T,CONFIG extends AbstractConfig<T>>
	implements Closeable
	{
	protected static final Logger LOG=Logger.getLogger("com.github.lindenb.dataindexer");
	/** the configuration file */
	private CONFIG config;
	/** number of items (size) stored */
	private long numberOfItems=0;
	/** handle to the index file , if needed. A fixed-size data file doesn't need this */
	private RandomAccessFile indexFile=null;
	/** handle to the data-file */
	private RandomAccessInput dataFile=null;
	/** constructor from a configuration */
	protected  AbstractDatabaseReader(CONFIG config) throws IOException
		{
		this.config=config;
		}
	
	/** returns wether the datafile is currently opened */
	public boolean isOpen()
		{
		return dataFile!=null;
		}
	
	
	protected void validateConfig() throws IOException
		{
		if(getConfig()==null) throw new IOException("config is null");
		getConfig().validateForReading();
		}
	
	/** open the datafile and , if needed, the index file .
	 * Does nothing if the datafile is already opened */
	public void open() throws IOException
		{
		if(isOpen()) return;
		validateConfig();
		
		DataInputStream dis=new DataInputStream(new FileInputStream(getConfig().getSummaryFile()));
		this.numberOfItems=dis.readLong();
		dis.close();

		if(this.numberOfItems<0L) throw new IOException("summary file corrupted.");
		/** not a constant data size, need a index file */
		if(!config.isFixedSizeof())
			{
			this.indexFile=new RandomAccessFile(getConfig().getIndexFile(),"r");
			}
		
		this.dataFile=getConfig().getRandomAccessFactory().openForReading(getConfig().getDataFile());
		}
	
	/** return the configuration */
	public CONFIG getConfig()
		{
		return config;
		}
	
	/** returns the number of items stored in the datafile */
	public long size()
		{	
		return numberOfItems;
		}
	
	private long checkIndexInRange(long idx)
		{
		if(idx<0 || idx>=this.size()) throw new IndexOutOfBoundsException(
				"0<="+idx+"<"+size()
				);
		return idx;
		}
	
	/** if the data have a fixed this, return the size in bytes of the data */
	protected int getSizeOf()
		{
		return getConfig().getSizeOfItem();
		}
	
	/** does T have a fixed size */
	protected boolean isFixedSizeOf()
		{
		return getConfig().isFixedSizeof();
		}
	
	/** convert a item index to a file-offset */
	private long getOffsetFromIndex(long idx)  throws IOException
		{
		checkIndexInRange(idx);
		if(isFixedSizeOf())
			{
			return idx*getSizeOf();
			}
		else
			{
			this.indexFile.seek(idx*8);
			return indexFile.readLong();
			}
		}
	/** return the item 'T' a given offset in datafile */
	T getItemFromOffset(long offset)  throws IOException
		{
		this.dataFile.seek(offset);
		DataInputStream dis=new DataInputStream(this.dataFile);
		return getConfig().getDataBinding().readObject(dis);
		}
	
	/** return the item 'T' a given index in datafile with 0<=idx<size() */
	public T get(long idx) throws IOException
		{
		return getItemFromOffset(getOffsetFromIndex(idx));
		}
	
	@Override
	/** close the underlying streams */
	public void close() throws IOException
		{
		if(this.indexFile!=null)
			{
			this.indexFile.close();
			this.indexFile=null;
			}
		if(this.dataFile!=null)
			{
			this.dataFile.close();
			this.dataFile=null;
			}
		this.numberOfItems=0;
		}
	
	protected void apply(
			long beginIndex,
			long endIndex,
			Function<T> callback
			)throws IOException
			{
			while(beginIndex<endIndex)
				{
				if( callback.apply(get(beginIndex))!=0)
					{
					break;
					}
				++beginIndex;
				}
			}
	
	public void forEach(
			long beginIndex,
			long endIndex,
			PrimaryForEach<T> callback
			) throws IOException
		{
		callback.onBegin();
		while(beginIndex<endIndex)
			{
			if( callback.apply(get(beginIndex))!=0)
				{
				break;
				}
			++beginIndex;
			}
		callback.onEnd();
		}
	
	/** return this database as a list. with a limit of Integer.MAX_VALUE items */
	public List<T> asList()
		{
		return new AbstractList<T>()
			{
			@Override
			public T get(int index)
				{
				try {
					return AbstractDatabaseReader.this.get(index);
					}
				catch (IOException e) {
					throw new RuntimeException(e);
					}
				}
			@Override
			public int size()
					{
					return (int)Math.min((long)Integer.MAX_VALUE, AbstractDatabaseReader.this.size());
					}
			};
		}
		
	}
