package com.khmelyuk.dib.render;

import java.lang.annotation.*;

/**
 * The annotation for getters to serialize into json.
 *
 * @author Ruslan Khmelyuk
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SerializeGetter {

    String value();

}
