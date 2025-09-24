package com.github.lindenb.dataindexer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
/**
 * default implementation of {@link RandomAccessOutput}
 * using a {@link FileOutputStream}
 *
 */
public class DefaultRandomAccessOutput extends RandomAccessOutput
	{
	private OutputStream delegate;
	private long offset=0L;
	public DefaultRandomAccessOutput(File file) throws IOException
		{
		this.delegate=new FileOutputStream(file);
		}

	@Override
	public long getOffset()
		{
		return this.offset;
		}

	@Override
	/* all parent 'write' are final. */
	public void write(byte[] b, int off, int len) throws IOException
		{
		getDelegate().write(b, off, len);
		this.offset+=len;
		}
	

	protected OutputStream getDelegate() {
		return delegate;
		}

	
	
	@Override
	public final void write(byte[] b) throws IOException
		{
		this.write(b,0,b.length);
		}
	
	@Override
	public final void write(int b) throws IOException
		{
		this.write(new byte[]{(byte)b},0,1);
		}
	
	@Override
	public void flush() throws IOException
		{
		getDelegate().flush();
		}
	
	@Override
	public void close() throws IOException
		{
		flush();
		getDelegate().close();
		}

	}
