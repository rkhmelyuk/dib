package com.khmelyuk.dib;

import com.khmelyuk.dib.validation.ParamsValidator;

import java.lang.annotation.*;

/**
 * Specifies a request validator.
 *
 * @author Ruslan Khmelyuk
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestValidator {

    /**
     * The type of the request validator.
     *
     * @return the type of request validator.
     */
    Class<? extends ParamsValidator>[] value();

    String[] params() default {};

}
