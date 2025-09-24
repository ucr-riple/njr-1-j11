package com.github.lindenb.dataindexer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/** default implementation of RandomAccessFactory using 
 * a FileOutputStream and a RandomAccessFile
 *
 */
public class BlockCompressedRandomAccessFactory implements
		RandomAccessFactory
	{
	@Override
	public RandomAccessOutput openForWriting(File file) throws IOException
		{
		return new BlockCompressedOutputStreamProxy(file);
		}
	@Override
	public RandomAccessInput openForReading(File file) throws IOException {
		return new BlockCompressedInputStreamProxy(file);
		}
	@Override
	public String toString() {
		return getClass().getName();
		}
	
	
	
	
	private static class BlockCompressedInputStreamProxy
		extends RandomAccessInput
		{
		private Class<?> blockCompressedInputStreamClass;
		private Object blockCompressedInputStream;
		private Method seekMethod;
		private Method readMethod;
		private Method closeMethod;
		BlockCompressedInputStreamProxy(File f) throws IOException
			{
			try
				{
				this.blockCompressedInputStreamClass=Class.forName("net.sf.samtools.util.BlockCompressedInputStream");
				Constructor<?> ctor=this.blockCompressedInputStreamClass.getConstructor(File.class);
				this.blockCompressedInputStream=ctor.newInstance(f);
				
				this.seekMethod=this.blockCompressedInputStreamClass.getMethod("seek", long.class);
				this.readMethod=this.blockCompressedInputStreamClass.getMethod("read", byte[].class,int.class,int.class);
				this.closeMethod=this.blockCompressedInputStreamClass.getMethod("close");
				}
			catch (Exception e)
				{
				throw new IOException(e);
				}
			}
		
		
		@Override
		public void seek(long offset) throws IOException
			{
			try
				{
				this.seekMethod.invoke(this.blockCompressedInputStream, offset);
				}
			catch (Exception e)
				{
				throw new IOException(e);
				}
			}
		@Override
		public int read(byte[] b, int off, int len) throws IOException
			{
			try
				{
				return (Integer)this.readMethod.invoke(this.blockCompressedInputStream, b,off,len);
				}
			catch (Exception e)
				{
				throw new IOException(e);
				}
			}
		
		@Override
		public void close() throws IOException
			{
			try
				{
				this.closeMethod.invoke(this.blockCompressedInputStream);
				}
			catch (Exception e)
				{
				throw new IOException(e);
				}
			}

		
		@Override
		public int read(byte[] b) throws IOException
			{
			return read(b,0,b.length);
			}
		
		@Override
		public int read() throws IOException
			{
			byte c[]=new byte[1];
			int n=this.read(c, 0, 1);
			return n==-1?c[0]:-1;
			}
		}	
	
		private static class BlockCompressedOutputStreamProxy
		extends RandomAccessOutput
			{
			private Class<?> blockCompressedOutputStreamClass;
			private Object blockCompressedOutputStream;
			private Method offsetMethod;
			private Method writeMethod;
			private Method closeMethod;
			private Method flushMethod;
			BlockCompressedOutputStreamProxy(File f) throws IOException
				{
				try
					{
					this.blockCompressedOutputStreamClass=Class.forName("net.sf.samtools.util.BlockCompressedOutputStream");
					Constructor<?> ctor=this.blockCompressedOutputStreamClass.getConstructor(File.class);
					this.blockCompressedOutputStream=ctor.newInstance(f);
					
					this.offsetMethod=this.blockCompressedOutputStreamClass.getMethod("getFilePointer");
					this.writeMethod=this.blockCompressedOutputStreamClass.getMethod("write", byte[].class,int.class,int.class);
					this.closeMethod=this.blockCompressedOutputStreamClass.getMethod("close");
					this.flushMethod=this.blockCompressedOutputStreamClass.getMethod("flush");
					}
				catch (Exception e)
					{
					throw new IOException(e);
					}
				}
			
			@Override
			public long getOffset() throws IOException
				{
				try
					{
					return (Long)this.offsetMethod.invoke(this.blockCompressedOutputStream);
					}
				catch (Exception e)
					{
					throw new IOException(e);
					}
				}
			
			@Override
			public void flush() throws IOException
				{
				try
					{
					this.flushMethod.invoke(this.blockCompressedOutputStream);
					}
				catch (Exception e)
					{
					throw new IOException(e);
					}
				}
			
			@Override
			public void close() throws IOException
				{
				try
					{
					this.closeMethod.invoke(this.blockCompressedOutputStream);
					}
				catch (Exception e)
					{
					throw new IOException(e);
					}
				}
		
			
			@Override
			public void write(byte[] b, int off, int len) throws IOException
				{
				try
					{
					this.writeMethod.invoke(this.blockCompressedOutputStream,b,off,len);
					}
				catch (Exception e)
					{
					throw new IOException(e);
					}
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
			}	

	}
