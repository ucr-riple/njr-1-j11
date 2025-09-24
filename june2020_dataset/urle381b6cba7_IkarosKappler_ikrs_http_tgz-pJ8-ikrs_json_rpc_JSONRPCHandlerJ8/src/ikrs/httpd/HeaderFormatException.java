package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-09-30
 * @version 1.0.0
 **/

public class HeaderFormatException
    extends HTTPException {

    private HTTPHeaderLine headerLine;

    public HeaderFormatException( String msg ) {
	this( msg, null );
    }

    public HeaderFormatException( String msg, HTTPHeaderLine line ) {
	super( msg );

	this.headerLine = line;
    }

    public HTTPHeaderLine getHeaderLine() {
	return this.headerLine;
    }

}