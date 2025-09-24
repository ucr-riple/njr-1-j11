package ikrs.httpd.resource;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import ikrs.httpd.CustomUtil;
import ikrs.httpd.HTTPHandler;
import ikrs.httpd.PostDataWrapper;
import ikrs.httpd.ReadOnlyException;
import ikrs.util.CustomLogger;


/**
 * Processable resources represent system processes that generate any output which will
 * be used as the Resource's output data.
 *
 * The resource expects a system command that will be executed in an external system 
 * process. The resource will read from the process' stdout and pass the received data
 * towards the designated input stream (see Resource.getInputStream()).
 *
 * Note A: Failing the execution of the system command does not necessarily throw
 * any exceptions! Use the ProcessableResource.getExitValue() method to check wheter
 * the execution was successful.
 *
 * Note B: If the execution really fails, the resource contains the data from the error 
 * output (not from stdout).
 *
 *
 * TAKE CARE WITH PLATFORM DEPENDENT COMMANDS!
 *
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-29
 * @version 1.0.0
 **/


public class ProcessableResource 
    extends AbstractResource {

    /**
     * The process builder passed by the calling instance (must not be null).
     **/
    private ProcessBuilder processBuilder;

    private PostDataWrapper postData;

    /**
     * This field stored the underlying buffered resource (contains the process's output data).
     **/
    private BufferedResource bufferedResource;

    /**
     * This field stores the exit-code after the process terminated.
     **/
    private int exitValue = -1;


    /**
     * Create a new ProcessableResource.
     *
     * @param logger       A custom logger to write log messages to (must not be null).
     * @param pb           The process builder to use (must not be null).
     * @param useFairLocks If set to true the class will use fair read locks (writing isn't
     *                     possible at all with this class).
     * @throws NullPointerException If logger or pb is null.
     **/
    public ProcessableResource( HTTPHandler handler,
				CustomLogger logger,
				ProcessBuilder pb,
				PostDataWrapper postData,
				boolean useFairLocks ) 
	throws NullPointerException {

	super( handler, logger, useFairLocks );


	if( pb == null )
	    throw new NullPointerException( "Cannot create ProcessableResources with null-ProcessBuilders." );

	this.processBuilder = pb;
	this.postData       = postData; // May be null!
	
    }

    /**
     * Get the process's exit code after execution (after calling the open() method).
     * Note that failing to execute
     **/
    public int getExitValue() {
	return this.exitValue;
    }
			    

    //--- BEGIN ---------------------------- AbstractResource implementation ------------------------------
    /**
     * This method opens the underlying resource. Don't forget to close.
     *
     * @param readOnly if set to true, the resource will be opened in read-only mode.
     *
     * @throws ReadOnlyException If the underlying resource is read-only in general.
     * @throws IOException If any other IO error occurs.
     * @see isReadOnly()
     **/
    public void open( boolean readOnly )
	throws ReadOnlyException,
	       IOException {

	// Resource cannot be double-opened!
	if( this.isOpen() ) 
	    throw new IOException( "Processable resources cannot be double opened." );

	if( !readOnly ) 
	    throw new IOException( "ProccessableResources can only be opened in read-only mode." );


	this.getLogger().log( Level.INFO,
			      getClass().getName() + ".open(...)",
			      "Starting the internal process builder." );

	// Might throw IOException or a SecurityException. 
	Process process = this.processBuilder.start();
	

	// Write POST data into process's stdin [if POST data is available]
	if( this.postData != null ) {
	    
	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".open(...)",
				  "HTTP POST data is available. Forwarding POST data to system process ..." );
	    long readLength = CustomUtil.transfer( this.postData.getInputStream(),
						   process.getOutputStream(),
						   this.postData.getContentLength(),  // max read length
						   256                                // buffer size
						   );
	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".open(...)",
				  "" + readLength + " bytes of POST data written to system process." );
	    
	}


	// Read the generated output DURING the process is running!
	// Otherwise it may block when the process's internal output buffer is full!
	int bufferSize = 256;
	BufferedInputStream bufferedInputStream = new BufferedInputStream( process.getInputStream(), bufferSize );
	ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream( bufferSize );
	byte[] buffer = new byte[ bufferSize ];
	int len;
	while( (len = bufferedInputStream.read(buffer,0,bufferSize)) != -1 ) {

	    if( len > 0 )
		bufferedOutputStream.write(buffer,0,len);

	}

	// Call process.waitFor() at this point ??? !!!
	try {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".open(...)",
				  "Waiting the system process to terminate ..." );
	    process.waitFor();
	    this.exitValue = process.exitValue();

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".open(...)",
				  "... process terminated (exitValue=" + this.exitValue + ")." );

	} catch( InterruptedException e ) {
	    
	    // This should not happen! The process might not have completely terminated
	    throw new IOException( "[InterruptedException] Execution process was interrupted. Failed to terminate." );

	}


	// This input stream is piped from the process's standard output stream.
	// If the return code is != 0, there were some errors ...
	InputStream in = null;

	if( this.exitValue == 0 ) {

	    // Successfully executed
	    this.getLogger().log( Level.INFO,
				  getClass().getName(),
				  "The system process was successfully executed (exitValue="+this.exitValue+"). Using stdout to retrieve generated data." 
				  );
	    in = new BufferedInputStream( new ByteArrayInputStream(bufferedOutputStream.toByteArray()) ); // process.getInputStream();

	} else {
	    

	    // Ooops, seems that the PHP script caused some errors.
	    this.getLogger().log( Level.INFO,
				  getClass().getName(),
				  "The system process raised some errors! (exitValue="+this.exitValue+"). Using stderr to retrieve error messages." 
				  );
	    // 'Concatenate' std-out with error-out
	    //in = process.getErrorStream();
	    in = new java.io.SequenceInputStream( new BufferedInputStream(new ByteArrayInputStream(bufferedOutputStream.toByteArray())), // process.getInputStream(), 
						  process.getErrorStream() );


	}


	
	// Store the input stream's data into a BufferedResource.
	this.bufferedResource = new BufferedResource( this.getHTTPHandler(),
						      this.getLogger(),
						      in,
						      false    // it is not necessary to use fair locks as this is a private internal resource
						      );

	// Now open my buffered resource; this will cause the while process output to be read and buffered.
	this.bufferedResource.open( true ); // read-only mode (this is a private field)

	// Don't forget to close the input stream!
	in.close();


	// Store current date as date of last modification
	this.getMetaData().setLastModified( new Date(System.currentTimeMillis()) );
    }

    /**
     * This method determines if this resource was alerady opened or not.
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isOpen()
	throws IOException {

	return ( this.bufferedResource != null 
		 && this.bufferedResource.isOpen() 
		 );
    }


    /**
     * This method returns true if the underlying resource is read-only (in general).
     *
     * @throws IOException If any IO error occurs.
     **/
    public  boolean isReadOnly()
	throws IOException {

	// Processable resources are always read-only.
	return true;
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

	if( !this.isOpen() )
	    throw new IOException( "Cannot get the resource's data length if it was not opened." );

	return this.bufferedResource.getLength();
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

	throw new ReadOnlyException( "Processable resources are read-only. They have no output stream!" );
    }

    /**
     * Get the input stream from this resource.
     *
     * @throws IOException If any IO error occurs.
     **/
    public InputStream getInputStream()
	throws IOException {

	if( !this.isOpen() )
	    throw new IOException( "Cannot get the resource's input stream if it was not opened." );	

	return this.bufferedResource.getInputStream();
    }


    /**
     * Closes this resource.
     *
     * @return false if the resource was already closed, true otherwise.
     **/
    public boolean close()
	throws IOException {

	if( !this.isOpen() )
	    return false; // Already closed (or not yet opened)


	this.bufferedResource.close();

	return true;
    }
    //--- END ------------------------------ AbstractResource implementation ------------------------------


    /**
     * For testing only.
     **/
    public static void main( String[] argv ) {

	ProcessableResource pr = null;
	try {

	    java.util.List<String> command = new java.util.LinkedList<String>();
	    // This will print the directory contents of cwd (Linux only)
	    //command.add( "ls" );
	    //command.add( "-l" );

	    // This will call the PHP interpreter to run my test PHP file.
	    // Note that 'php'/php-cli' does NOT print the HTTP response headers!
	    // But 'php-cgi' does :)
	    command.add( "php-cgi" );
	    command.add( "document_root_alpha/php_test/headers.php" );
	    ProcessBuilder pb = new ProcessBuilder( command );

	    pr = new ProcessableResource( null,  // no http handler
					  new ikrs.util.DefaultCustomLogger( "ProcessableResource.main()_TEST" ),
					  pb,
					  null,  // no POST data
					  true   // useFairLocks?
					  );
	    
	    pr.getReadLock().lock();


	    pr.open( true );  // Read-only (actually, writing is not supported here)
	    int b;
	    while( (b = pr.getInputStream().read()) != -1 ) {

		System.out.print( (char)b );

	    }
	    pr.close();
	    

	} catch( IOException e ) {

	    e.printStackTrace();

	} finally {

	    // Dont' forget to unlock!
	    if( pr != null )
		pr.getReadLock().unlock();

	}

    }

}