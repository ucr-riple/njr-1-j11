package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-10-06
 * @version 1.0.0
 **/

public class UnsupportedFormatException 
    extends HTTPException {

    
    public UnsupportedFormatException( String message) {
	super( message );
    }

    public UnsupportedFormatException( String message,
				       Throwable reason ) {
	super( message, reason );
    }


}