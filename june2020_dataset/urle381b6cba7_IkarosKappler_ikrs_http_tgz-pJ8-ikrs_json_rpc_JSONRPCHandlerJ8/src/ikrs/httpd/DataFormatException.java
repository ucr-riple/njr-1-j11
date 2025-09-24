package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-10-02
 * @version 1.0.0
 **/

public class DataFormatException 
    extends HTTPException {

    private long errorOffset;
    
    public DataFormatException( String msg ) {
	this( msg, -1 );
    }
    

    public DataFormatException( String msg, long errorOffset ) {
	super( msg );

	this.errorOffset = errorOffset;
    }

    public long getErrorOffset() {
	return this.errorOffset;
    }

}