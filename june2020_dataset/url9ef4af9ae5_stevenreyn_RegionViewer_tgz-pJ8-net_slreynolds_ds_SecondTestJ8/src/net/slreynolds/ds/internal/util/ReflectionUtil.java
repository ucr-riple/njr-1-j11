

package net.slreynolds.ds.internal.util;

import net.slreynolds.ds.model.ReflectionException;
import java.lang.reflect.Field;


/**
 *
 */
public class ReflectionUtil {
    
    public static int getInt(Object o, String field) throws ReflectionException {

        try {
            Field eDField = o.getClass().getDeclaredField(field);
            eDField.setAccessible(true);
            return ((Integer) eDField.get(o)).intValue();
        }
        catch (Throwable t) {
            throw new ReflectionException("Error while getting " + field,t);
        }

    }

    public static <T> T getRef(Class<T> clazz, Object o, String field) throws ReflectionException {

        try {
            Field eDField = o.getClass().getDeclaredField(field);
            eDField.setAccessible(true);
            return  clazz.cast(eDField.get(o));
        } 
        catch (Throwable t) {
            throw new ReflectionException("Error while getting " + field,t);
        }

    }    
 
}
