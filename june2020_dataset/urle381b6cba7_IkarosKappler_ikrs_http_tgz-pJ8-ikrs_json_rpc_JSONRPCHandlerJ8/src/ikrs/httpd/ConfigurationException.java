package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-07-29
 * @version 1.0.0
 **/

public class ConfigurationException 
    extends Exception {

    public ConfigurationException( String msg ) {
	super( msg );
    }


    public ConfigurationException( String msg, Throwable cause ) {
	super( msg, cause );
    }


}