package ikrs.util;

/**
 * This interface specifies a basic environment factory.
 *
 * @author Ikaros Kappler
 * @date 2012-09-06
 * @version 1.0.0
 **/


public interface EnvironmentFactory<K,V> {

    /**
     * Creates a new and empty environment.
     *
     * @return The new environment.
     **/
    public Environment<K,V> create();


}
