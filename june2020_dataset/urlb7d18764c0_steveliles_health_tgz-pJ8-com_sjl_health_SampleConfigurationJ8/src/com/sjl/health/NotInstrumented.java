package com.sjl.health;

import java.lang.annotation.*;

/**
 * Indicate to the HealthService that a method should not be instrumented.
 * Methods annotated as such will not contribute at all to the HealthInfo status
 * of the instrumented component.
 * 
 * @author steve
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotInstrumented {

}
