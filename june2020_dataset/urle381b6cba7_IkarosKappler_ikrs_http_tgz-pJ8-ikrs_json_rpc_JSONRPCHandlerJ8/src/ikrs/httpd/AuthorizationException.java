package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-08-31
 * @version 1.0.0
 **/

public class AuthorizationException
    extends HTTPException {

    public static final int AUTHORIZATION_REQUIRED = 1;
    public static final int MISSING_PARAM          = 2;

    private int reasonID;

    public AuthorizationException( int reasonID, String msg ) {
	super( msg );

	this.reasonID = reasonID;
    }

    public int getReasonID() {
	return this.reasonID;
    }


}