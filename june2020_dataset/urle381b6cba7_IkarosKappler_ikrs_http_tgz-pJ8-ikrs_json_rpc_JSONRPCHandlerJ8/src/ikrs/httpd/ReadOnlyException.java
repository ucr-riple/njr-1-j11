package ikrs.httpd;


import java.io.IOException;


/**
 * @author  Ikaros Kappler
 * @date    2012-07-20
 * @version 1.0.0
 **/


public class ReadOnlyException
    extends IOException {


    public ReadOnlyException( String msg ) {
	super( msg );
    }

}