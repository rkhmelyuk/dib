package com.khmelyuk.dib;

import java.lang.annotation.*;

/**
 * Specifies a simple request params validator.
 *
 * @author Ruslan Khmelyuk
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParamsValidator {

    String[] required() default {};
}
