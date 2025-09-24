package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-10-11
 * @version 1.0.0
 **/

public class UnsupportedMethodException 
    extends HTTPException  {

    /**
     * Creates a new UnsupportedMethodException witht the given error message.
     **/
    public UnsupportedMethodException( String method,
				       String msg ) {
	super( msg );
    }


}