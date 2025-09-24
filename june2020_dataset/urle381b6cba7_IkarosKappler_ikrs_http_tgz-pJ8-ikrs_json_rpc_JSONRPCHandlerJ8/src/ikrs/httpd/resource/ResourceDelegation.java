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
 * The ResourceDelegation is an abstract wrapper class that holds an internal
 * Resource instance. All methods are implemented and just redirect the calls
 * to the internal delegated core instance.
 *
 * This class is meant to be extended and some desired method(s) to be overriden
 * to slightly change its behavior.
 *
 *
 * Example: the RangedResource class overrides the open(...) method and skips
 *          some bytes at the beginning.
 *
 * @author Ikaros Kappler
 * @date 2013-02-27
 * @version 1.0.0
 **/


public abstract class ResourceDelegation
    extends AbstractResource {

    /**
     * The internal resource.
     **/
    private Resource coreResource;
    

    public ResourceDelegation( Resource resource,

			       HTTPHandler handler,
			       CustomLogger logger ) {
	super( handler,
	       logger,
	       false    // if fairLocks or not is already defined in the passed resource
	       );

	this.coreResource = resource;
    }

    protected Resource getCoreResource() {
	return this.coreResource;
    }

    //--- BEGIN -------------------- AbstractResource implementation ----------------------
    /**
     * Get the meta data for this resource.
     **/
    public ResourceMetaData getMetaData() {
	return this.coreResource.getMetaData();
    }

    /**
     * Get the resource's hypertext access file settings. If the resource has no
     * hypertext access settings available the method may return null.
     **/
    public HypertextAccessFile getHypertextAccessFile() {
	return this.coreResource.getHypertextAccessFile();
    }

    /**
     * This method returns the read lock for this resource.
     **/
    public ReentrantReadWriteLock.ReadLock getReadLock() {
	return this.coreResource.getReadLock();
    }

    /**
     * This method returns the write lock for this resource.
     **/
    public ReentrantReadWriteLock.WriteLock getWriteLock() {
	return this.coreResource.getWriteLock();
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
    public void open( boolean readOnly )
	throws IOException {

	this.coreResource.open( readOnly );
    }

    /**
     * This method determines if this resource was alerady opened or not.
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isOpen()
	throws IOException {

	return this.coreResource.isOpen();
    }


    /**
     * This method returns true if the underlying resource is read-only (in general).
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isReadOnly()
	throws IOException {

	return this.coreResource.isReadOnly();
    }

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
	throws IOException {
	
	return this.coreResource.getLength();
    }


    /**
     * Get the output stream to this resource.
     *
     * @throws ReadOnlyException If this resource was opened with the read-only flag set.
     * @throws IOException If any other IO error occurs.
     **/
    public OutputStream getOutputStream()
	throws ReadOnlyException,
	       IOException {

	return this.coreResource.getOutputStream();
    }

    /**
     * Get the input stream from this resource.
     *
     * @throws IOException If any IO error occurs.
     **/
    public InputStream getInputStream()
	throws IOException {

	return this.coreResource.getInputStream();
    }


    /**
     * Closes this resource.
     *
     * @return false if the resource was already closed, false otherwise.
     **/
    public boolean close()
	throws IOException {

	return this.coreResource.close();
    }
    //--- END ---------------------- AbstractResource implementation ----------------------

}