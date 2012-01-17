package com.khmelyuk.dib;

import java.lang.annotation.*;

/**
 * Specifies an API method.
 *
 * @author Ruslan Khmelyuk
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {

    /**
     * The action name.
     *
     * @return the suggested action name.
     */
    String name();

    /**
     * The supported HTTP request method.
     *
     * @return the supported method.
     */
    RequestMethod method() default RequestMethod.ANY;

}
