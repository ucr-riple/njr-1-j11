package ikrs.httpd;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock; 

import ikrs.io.fileio.htaccess.HypertextAccessFile;

/**
 * @author  Ikaros Kappler
 * @date    2012-07-20
 * @version 1.0.0
 **/

public interface Resource {

    /**
     * Get the meta data for this resource.
     **/
    public ResourceMetaData getMetaData();

    /**
     * Get the resource's hypertext access file settings. If the resource has no
     * hypertext access settings available the method may return null.
     **/
    public HypertextAccessFile getHypertextAccessFile();

    /**
     * This method returns the read lock for this resource.
     **/
    public ReentrantReadWriteLock.ReadLock getReadLock();

    /**
     * This method returns the write lock for this resource.
     **/
    public ReentrantReadWriteLock.WriteLock getWriteLock();

    /**
     * This method opens the underlying resource. Don't forget to close.
     *
     * @param readOnly if set to true, the resource will be opned in read-only mode.
     *
     * @throws ReadOnlyException If the underlying resource is read-only in general.
     * @throws IOException If any other IO error occurs.
     * @see isReadOnly()
     **/
    public void open( boolean readOnly )
	throws IOException;

    
    /**
     * This method determines if this resource was alerady opened or not.
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isOpen()
	throws IOException;

    
    /**
     * This method returns true if the underlying resource is read-only (in general).
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isReadOnly()
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
    public long getLength()
	throws IOException;


    /**
     * Get the output stream to this resource.
     *
     * @throws ReadOnlyException If this resource was opened with the read-only flag set.
     * @throws IOException If any other IO error occurs.
     **/
    public OutputStream getOutputStream()
	throws ReadOnlyException,
	       IOException;

    /**
     * Get the input stream from this resource.
     *
     * @throws IOException If any IO error occurs.
     **/
    public InputStream getInputStream()
	throws IOException;


    /**
     * Closes this resource.
     *
     * @return false if the resource was already closed, false otherwise.
     **/
    public boolean close()
	throws IOException;



}