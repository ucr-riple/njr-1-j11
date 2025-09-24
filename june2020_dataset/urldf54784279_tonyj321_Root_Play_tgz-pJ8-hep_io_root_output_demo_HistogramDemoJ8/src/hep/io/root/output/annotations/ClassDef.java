package hep.io.root.output.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Approximate equivalent of Root's ClassDef. Allows classes to be flagged as
 * serializable into a Root file.
 * @author tonyj
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassDef {
    /** The name by which this class is known to Root. Defaults to the simple name
     * of the implementing Java class. 
     */
    String className() default ""; 
    /** The version of this class. Defaults to 0. 
     */
    int version() default 0;
    /** Can be used to flag classes for which the framework should not add a standard
     * class header. Classes for which this is specified normally have their own explicit streamer.
     */
    boolean hasStandardHeader() default true;
    /**
     * The checksum for this class. If not specified is automatically computed (but currently not
     * very successfully).
     */
    int checkSum() default 0;
    
    boolean suppressTStreamerInfo() default false;
}