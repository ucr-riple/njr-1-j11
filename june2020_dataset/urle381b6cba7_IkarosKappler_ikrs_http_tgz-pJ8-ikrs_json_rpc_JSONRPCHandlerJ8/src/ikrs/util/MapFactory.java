package ikrs.util;

import java.util.Map;

/**
 * This is a simple map factory interface.
 *
 * @author Ikaros Kappler
 * @date 2012-04-24
 * @version 1.0.0
 **/ 

public interface MapFactory<K,V> {

    /**
     * Creates a new empty map.
     **/
    public Map<K,V> createMap();
    

}
