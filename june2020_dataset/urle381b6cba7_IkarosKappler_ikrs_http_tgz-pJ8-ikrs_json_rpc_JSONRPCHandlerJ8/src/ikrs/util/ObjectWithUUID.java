package ikrs.util;

import java.util.UUID;

/**
 * This is a very small interface for all objects that are associated with an UUID.
 * The final(!) UUID is expected to be part of the object.
 *
 * @author Ikaros Kappler
 * @date 2012-05-23
 * @version 1.0.0
 **/

public interface ObjectWithUUID {

    /**
     * Get the unique and final UUID for this object.
     *
     * The returned UUID must never change!
     * 
     * @return The UUID of this object.
     **/
    public UUID getUUID();

}