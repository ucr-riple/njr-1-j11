package com.github.lindenb.dataindexer;

import java.io.Closeable;
import java.io.IOException;

public class PrimaryDatabaseReader<T>
	extends AbstractDatabaseReader<T, PrimaryConfig<T>>
	implements Closeable
	{
	public PrimaryDatabaseReader(PrimaryConfig<T> config) throws IOException
		{
		super(config);
		}
	
	private boolean config_validated=false;
	@Override
	protected void validateConfig() throws IOException
		{
		if(config_validated) return;
		config_validated=true;
		if(getConfig()==null) throw new IOException("config is null");
		getConfig().validateForReading();
		}

	}
