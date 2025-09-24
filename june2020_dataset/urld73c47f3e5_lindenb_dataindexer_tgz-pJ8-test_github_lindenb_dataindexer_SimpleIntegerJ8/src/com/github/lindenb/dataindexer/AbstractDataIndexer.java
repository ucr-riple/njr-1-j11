package com.github.lindenb.dataindexer;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * base class for both primary and secondary database writer
 * @author lindenb
 *
 * @param <T> the class to be stored
 * @param <CONFIG> the config type
 */
public abstract class AbstractDataIndexer<T,CONFIG extends AbstractConfig<T>>
	implements Closeable
	{
	protected static final Logger LOG=Logger.getLogger("com.github.lindenb.dataindexer");

	/** number of items stored */
	protected long numberOfItems=0L;
	/** data binding */
	private TupleBinding<T> dataBinding=null;
	
	private CONFIG config;
	protected AbstractDataIndexer(CONFIG cfg)
		{
		this.dataBinding=cfg.getDataBinding();
		this.config=cfg;
		
		}
	
	protected void checkConfig() throws IOException
		{
		if(getConfig()==null) throw new IllegalStateException("config is null");
		getConfig().validateForReading();
		}
	
	/** close the underlying stream */
	public abstract void close() throws IOException;
	
	/** returns the associated config */
	public final CONFIG getConfig()
		{
		return this.config;
		}
	
	/** get tuple binding for the type T */
	protected final TupleBinding<T> getDataBinding()
		{
		return dataBinding;
		}
	
	/** write the summary file */
	protected void writeSummary()
		throws IOException
		{
		DataOutputStream daos=new DataOutputStream(new FileOutputStream(getConfig().getSummaryFile()));
		daos.writeLong(this.numberOfItems);
		daos.flush();
		daos.close();
		}
	}
