package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-09-27
 * @version 1.0.0
 **/

public class MissingParamException 
    extends HTTPException  {

    /**
     * Creates a new MissingParamException witht the given error message.
     **/
    public MissingParamException( String msg ) {
	super( msg );
    }


}