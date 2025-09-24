package ikrs.util.session;

/**
 * This interface can be used to implement simple Session factory classes.
 *
 * @author Ikaros Kappler
 * @date 2012-09-07
 * @version 1.0.0
 **/

public interface SessionFactory<K,V,U> {

    /**
     * Creates a new Session with the configured type signature.
     *
     * @return A newly created session.
     **/
    public Session<K,V,U> create( U userID );


}