package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-07-16
 * @version 1.0.0
 **/

public class ParametrizedHTTPException 
    extends HTTPException {

    private int statusCode;
    
    private String reasonPhrase;
    
    public ParametrizedHTTPException( String msg,
				      
				      int statusCode,
				      String reasonPhrase ) {
	super( msg );
	
	this.statusCode   = statusCode;
	this.reasonPhrase = reasonPhrase;
    }
    
    public ParametrizedHTTPException( String msg,			  
				      Throwable reason,
				      
				      int statusCode,
				      String reasonPhrase) {
	super( msg, reason );

	this.statusCode   = statusCode;
	this.reasonPhrase = reasonPhrase;
    }


    public int getStatusCode() {
	return this.statusCode;
    }

    public String getReasonPhrase() {
	return this.reasonPhrase;
    }

}