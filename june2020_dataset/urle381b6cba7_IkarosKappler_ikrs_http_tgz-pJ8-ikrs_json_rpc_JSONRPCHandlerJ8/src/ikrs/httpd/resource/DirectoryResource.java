package ikrs.httpd.resource;

import java.io.IOException;
import java.io.OutputStream;

import ikrs.httpd.Resource;
import ikrs.util.MIMEType;
import java.util.UUID;

/**
 * @author   Ikaros Kappler
 * @date     2012-10-11
 * @version  1.0.0
 **/


public interface DirectoryResource
    extends Resource {



    /**
     * This method is designated to build the data for the directory listing.
     *
     * Subclasses must implement this method and write the generated data into the given output stream.
     *
     *
     * @param sid The current session's ID.
     * @param out The output stream to write the list data to.
     * @throws IOException If any IO errors occur.
     **/
    public abstract void generateDirectoryListing( UUID sid,
						   OutputStream out )
	throws IOException;


    /**
     * This method returns the Content-Type this class generates.
     *
     * The returned MIME type must not be null.
     *
     * @return The Content-Type this class generates.
     **/
    public abstract MIMEType getDirectoryListingType();



}