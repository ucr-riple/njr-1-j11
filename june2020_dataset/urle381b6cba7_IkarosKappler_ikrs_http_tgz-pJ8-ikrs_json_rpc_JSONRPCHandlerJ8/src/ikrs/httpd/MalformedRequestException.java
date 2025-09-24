package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-07-19
 * @version 1.0.0
 **/

public class MalformedRequestException 
    extends HTTPException  {

    /**
     * Creates a new MalformedRequestException witht the given error message.
     **/
    public MalformedRequestException( String msg ) {
	super( msg );
    }


}