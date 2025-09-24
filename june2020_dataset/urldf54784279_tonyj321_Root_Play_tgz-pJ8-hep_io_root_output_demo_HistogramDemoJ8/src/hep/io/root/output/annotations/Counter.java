package hep.io.root.output.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates an array whose size is controlled by another field.
 * Root allows the size of arrays to be controlled by another field in the 
 * object containing the array.
 * @author tonyj
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Counter {
    /**
     * The name of the field to be used as the counter.
     */
    String value();
}
