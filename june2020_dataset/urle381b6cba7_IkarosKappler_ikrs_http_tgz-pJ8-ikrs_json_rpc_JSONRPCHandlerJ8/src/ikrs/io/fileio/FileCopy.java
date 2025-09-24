package ikrs.io.fileio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The standard java libraries have no file- and directory- copy routines.
 *
 * This class is a simple solution.
 *
 * @author Ikaros Kappler
 * @date 2012-12-19
 * @version 1.0.0
 **/


public class FileCopy {

    /**
     * The default buffer size to use (in bytes).
     **/
    public static final int DEFAULT_BUFFER_SIZE = 1024;

    
    /**
     * Copy the data from source to destination.
     * Source and destination MUST be regular files or directories.
     *
     * There are four possible cases. Example:
     *  (i)   source      is a regular file,
     *        destination is a regular file.
     *       
     *         Then the bytes from source will be written into the destination.
     *         If the destination already exists it will be overwritten.
     *
     *  (ii)  source is a directory,
     *        destination is a regular file.
     *
     *         This will cause an IllegalArgumentException to be thrown!
     *  
     *  (iii) source is a regular file,
     *        destination is a directory.
     *
     *         A new file will be created inside the destination directory having the
     *         same name as source.
     *
     *  (iv)  source is a directory,
     *        destination is a directory.
     *
     *         The destination directory will be created (if not exists) and all files
     *         and sub directories from source will be copied into the destination 
     *         directory recursively.
     *
     **/
    public static boolean copy( File source,
				File destination ) 
	throws NullPointerException,
	       IOException {

	
	if( source == null )
	    throw new NullPointerException( "Cannot read from null-source." );
	if( !source.exists() )
	    throw new IOException( "Cannot read from source '" + source.getPath() + "': file does not exist." );
	if( !source.isDirectory() && !source.isFile() )
	    throw new IOException( "Cannot read from source '" + source.getPath() + "': it's not a file nor a directory." );

	if( destination == null )
	    throw new NullPointerException( "Cannot write to null-destination." );
	// Create destination directory if not exists
	if( source.isDirectory() && !destination.exists() && !destination.mkdirs() )
	    throw new IOException( "Failed to create non-existing directory '" + destination.getPath() + "'." );
	if( !source.isFile() && !destination.isDirectory() && !destination.isFile() )
	    throw new IOException( "Cannot write to destination '" + destination.getPath() + "': it's not a file nor a directory." );


	
	if( source.isDirectory() ) {

	    if( !destination.isDirectory() )
		throw new IOException( "Cannot copy directory '" + source.getPath() + "' to non-directory '" + destination.getPath() + "." );

	    
		

	    // Coppy all files in source directory to target directory
	    File[] files = source.listFiles();
	    boolean error = false;
	    for( int i = 0; i < files.length && !error; i++ ) {

		if( !files[i].isDirectory() && !files[i].isFile() )
		    continue;
		
		FileCopy.copy( files[i],                                 // source
			       new File(destination,files[i].getName() ) // destination
			     );
			       
		
	    }
	    
	    return !error;
	    
	} else {

	    // Source is definitely a regular file
	    if( destination.isDirectory() ) {
		
		FileCopy.transfer( source,
				   new File(destination, source.getName()),
				   FileCopy.DEFAULT_BUFFER_SIZE
				 );
	    } else {

		// Destination already addresses a normal file
		FileCopy.transfer( source, destination, FileCopy.DEFAULT_BUFFER_SIZE );

	    }

	    return true;

	}

    }

    /**
     * Transfer all bytes from the given input file into the destination output file (non of the files
     * must be a directory).
     *
     * The method returns the number of bytes that were actually transfered during the progress.
     *
     * @param sourceFile      The input file to read from.
     * @param destinationFile The output file to write to.
     * @param bufferSize      The buffer size to use (a value from 512 to 2048 should be okay for most purposes).
     * @throws NullPointerException     If sourceFile or destinationFile is null.
     * @throws IllegalArgumentException If the buffer size is not in range (must be > 0).
     * @throws IOException              If any IO errors occur.
     **/
    public static long transfer( File sourceFile,
				 File destinationFile,
				 int bufferSize ) 
	throws NullPointerException,
	       IllegalArgumentException,
	       IOException {


	FileInputStream  in = null;
	FileOutputStream out = null;

	try {

	    if( sourceFile == null )
		throw new NullPointerException( "Cannot read from null-sourceFile." );
	    if( destinationFile == null )
		throw new NullPointerException( "Cannot write to null-destinationFile." );

	    in  = new FileInputStream( sourceFile );
	    out = new FileOutputStream( destinationFile );
	    
	    return FileCopy.transfer( in, out, bufferSize );
	    
	} catch( IOException e ) {
	    
	    throw e;
	    
	} catch( IllegalArgumentException e ) {

	    throw e;
		
	} finally {

	    if( in != null )
		in.close();
	    if( out != null )
		out.close();

	}

    }

    /**
     * Transfer all bytes from the given input stream into the destination output stream.
     *
     * The method returns the number of bytes that were actually transfered during the progress.
     *
     * @param source      The input stream to read from.
     * @param destination The output stream to write to.
     * @param bufferSize  The buffer size to use (a value from 512 to 2048 should be okay for most purposes).
     * @throws NullPointerException     If source or destination is null.
     * @throws IllegalArgumentException If the buffer size is not in range (must be > 0).
     * @throws IOException              If any IO errors occur.
     **/
    public static long transfer( InputStream source,
				 OutputStream destination,
				 int bufferSize )
	throws NullPointerException,
	       IllegalArgumentException,
	       IOException {
    
	if( source == null )
	    throw new NullPointerException( "Cannot read from null-source." );
	if( destination == null )
	    throw new NullPointerException( "Cannot write to null-destination." );
	if( bufferSize <= 0 )
	    throw new IllegalArgumentException( "The buffer size must be bigger than zero (is " + bufferSize + ")." );
	

	byte[] buffer = new byte[ bufferSize ];
	int len;
	long totalLength = 0L;
	while( (len = source.read(buffer)) > 0 ) {

	    destination.write( buffer, 0, len );
	    totalLength += len;

	}

	return totalLength;
    }
    


    /**
     * For testing purposes only.
     **/
    public static void main( String[] argv ) {

	if( argv.length < 2 ) {
	 
	    System.err.println( "Pass source and destination file names." );
	    System.exit( 1 );

	}

	
	try {

	    System.out.println( "Copying file '" + argv[0] + "' to '" + argv[1] + "' ..." );
	    boolean succeeded = FileCopy.copy( new File(argv[0]), new File(argv[1]) );
	    System.out.println( "Done." );

	} catch( Exception e )  {

	    e.printStackTrace();

	}
	

    }

}