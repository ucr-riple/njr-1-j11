package ikrs.httpd.resource;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock; 


import ikrs.httpd.HTTPHandler;
import ikrs.httpd.ReadOnlyException;
import ikrs.httpd.Resource;
import ikrs.httpd.ResourceMetaData;
import ikrs.util.CustomLogger;

import ikrs.io.fileio.htaccess.HypertextAccessFile;

/**
 * @author  Ikaros Kappler
 * @date    2012-07-20
 * @version 1.0.0
 **/


public abstract class AbstractResource
    implements Resource {

    private HTTPHandler httpHandler;

    private ResourceMetaData metaData;

    private HypertextAccessFile hypertextAccessFile;

    private CustomLogger logger;


    /**
     * The read-write-lock.
     **/
    private ReentrantReadWriteLock rwLock;

    
    /**
     * Create a new AbstractResource.
     **/
    public AbstractResource( HTTPHandler handler,
			     CustomLogger logger,
			     boolean useFairLocks ) {
	super();

	this.httpHandler  = handler;
	this.rwLock       = new ReentrantReadWriteLock( useFairLocks );
	this.metaData     = new ResourceMetaData();
	this.logger       = logger;
    }

    /**
     * Get the global HTTP handler.
     **/
    public HTTPHandler getHTTPHandler() {
	return this.httpHandler;
    }

    public CustomLogger getLogger() {
	return this.logger;
    }

    protected void setHypertextAccessFile( HypertextAccessFile hypertextAccessFile ) {
	this.hypertextAccessFile = hypertextAccessFile;
    }

    //---BEGIN------------------- Resource implementation ----------------------------
    /**
     * Get the meta data for this resource.
     **/
    public ResourceMetaData getMetaData() {
	return this.metaData;
    }

    /**
     * Get the resource's hypertext access file settings. If the resource has not
     * hypertext access settings available the method may return null.
     **/
    public HypertextAccessFile getHypertextAccessFile() {
	return this.hypertextAccessFile;
    }

    /**
     * This method returns the read lock for this resource.
     **/
    public ReentrantReadWriteLock.ReadLock getReadLock() {
	return this.rwLock.readLock();
    }

    /**
     * This method returns the write lock for this resource.
     **/
    public ReentrantReadWriteLock.WriteLock getWriteLock() {
	return this.rwLock.writeLock();
    }

    /**
     * This method opens the underlying resource. Don't forget to close.
     *
     * @param readOnly if set to true, the resource will be opned in read-only mode.
     *
     * @throws ReadOnlyException If the underlying resource is read-only in general.
     * @throws IOException If any other IO error occurs.
     * @see isReadOnly()
     **/
    public abstract void open( boolean readOnly )
	throws IOException;

    /**
     * This method determines if this resource was alerady opened or not.
     *
     * @throws IOException If any IO error occurs.
     **/
    public abstract boolean isOpen()
	throws IOException;


    /**
     * This method returns true if the underlying resource is read-only (in general).
     *
     * @throws IOException If any IO error occurs.
     **/
    public abstract boolean isReadOnly()
	throws IOException;

    /**
     * This method returns the *actual* length of the underlying resource. This length will
     * be used in the HTTP header fields to specify the transaction length.
     *
     * During read-process (you used the locks, didn't you?) the length MUST NOT change.
     *
     * @return the length of the resource's data in bytes.
     * @throws IOException If any IO error occurs.
     **/
    public abstract long getLength()
	throws IOException;


    /**
     * Get the output stream to this resource.
     *
     * @throws ReadOnlyException If this resource was opened with the read-only flag set.
     * @throws IOException If any other IO error occurs.
     **/
    public abstract OutputStream getOutputStream()
	throws ReadOnlyException,
	       IOException;

    /**
     * Get the input stream from this resource.
     *
     * @throws IOException If any IO error occurs.
     **/
    public abstract InputStream getInputStream()
	throws IOException;


    /**
     * Closes this resource.
     *
     * @return false if the resource was already closed, false otherwise.
     **/
    public abstract boolean close()
	throws IOException;
    //---END--------------------- Resource implementation ----------------------------


}
