package ikrs.httpd;

import java.io.File;

/**
 * An abstract file filter for directory listings and direct file access. 
 *
 * This class has an 'acceptFile' method
 * similar to the java.io.FileFilter.accept method and an additional 'acceptListing' for the case
 * a file is allowed to be listet in directory listings but not downloadable (accessible).
 *
 * @authur Ikaras Kappler
 * @date 2012-09-28
 * @version 1.0.0
 **/


public class DefaultFileFilter
    extends AbstractFileFilter {


    public DefaultFileFilter() {
	super();
    }


    /**
     * This method should return false if the passed file is not accessible due to security or other reasons.
     *
     * @param file The file the user want's to access.
     * @return true If the file may be accessed.
     **/
    public boolean acceptAccess( File file ) {
	return 
	    // Allow access to directories. The directory's .htaccess file will tell if the dir is accessible then.
	    file.isDirectory()
	    
	    // Allow access to normal files only.
	    // Don't allow access to system configuration files.
	    || ( file.isFile() 
		 && !file.getName().startsWith(".") )
	    
	    ;
    }

    /**
     * This method should return false if the passed file is not allowed to be listed in directory listings.
     *
     * @param file The file the user want's to access.
     * @return true If the file may be accessed.
     **/
    public boolean acceptListing( File file ) {
	return 

	    // List normal files an directories only.
	    ( file.isDirectory() || file.isFile() )

	    && 
	    ( 
	     // Don't list systmem configuration files ...
	     !file.getName().startsWith(".") 
	     
	     // ... unless they are backup files (listed allowed, access not allowed)
	     || file.getName().endsWith("~")

	      )

	    ;
    }

}