package com.khmelyuk.dib.security;

import java.lang.annotation.*;

/**
 * Add security to the API action.
 *
 * @author Ruslan Khmelyuk
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Secured {

    /**
     * Whether to detect user account by id. The default value is false.
     *
     * @return true if detect.
     */
    boolean detectUser() default false;

    /**
     * The array of roles, to allow access to.
     * If any role is specified, then user will be detected, no matter of what detectUser value is.
     *
     * @return the array of roles who are allowed to access.
     */
    String[] roles() default {};
}
