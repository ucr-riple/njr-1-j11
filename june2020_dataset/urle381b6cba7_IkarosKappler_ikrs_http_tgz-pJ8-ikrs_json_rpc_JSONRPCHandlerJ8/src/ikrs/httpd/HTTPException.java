package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-07-16
 * @version 1.0.0
 **/

public class HTTPException 
    extends Exception {

    
    public HTTPException( String msg ) {
	super( msg );
    }

    public HTTPException( String msg,
			  Throwable reason ) {
	super( msg, reason );
    }

}