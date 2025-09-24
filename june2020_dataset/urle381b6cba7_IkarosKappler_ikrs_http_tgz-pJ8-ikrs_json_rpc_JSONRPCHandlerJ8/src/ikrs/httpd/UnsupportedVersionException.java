package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-07-19
 * @version 1.0.0
 **/

public class UnsupportedVersionException 
    extends HTTPException  {

    /**
     * Creates a new UnsupportedVersionException witht the given error message.
     **/
    public UnsupportedVersionException( String msg ) {
	super( msg );
    }


}