package com.khmelyuk.dib;

import java.lang.annotation.*;

/**
 * Specifies a class as API controller.
 *
 * @author Ruslan Khmelyuk
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiController {

    /**
     * The controller name.
     *
     * @return the suggested controller name.
     */
    String value();

}
