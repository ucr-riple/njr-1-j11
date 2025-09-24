package hep.io.root.output.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allows a title to be attached to a class or a field. The title is written into
 * the streamer info for this class.
 * @author tonyj
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Title {
    String value();
}
