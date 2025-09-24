package com.sjl.health;

import java.lang.annotation.*;

/**
 * Indicate that the object returned from the annotated method should be
 * instrumented, and that invocations of its methods should contribute to 
 * the health of the owning component.
 * 
 * @author steve
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InstrumentedResult {

}
