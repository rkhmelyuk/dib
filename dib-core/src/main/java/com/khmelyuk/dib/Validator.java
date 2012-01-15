package com.khmelyuk.dib;

import com.khmelyuk.dib.validation.ModelValidator;

import java.lang.annotation.*;

/**
 * Used to annotate a validator for the Model.
 *
 * @author Ruslan Khmelyuk
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validator {

    /**
     * The type of the model validator.
     *
     * @return the model validator type.
     */
    Class<? extends ModelValidator> value();

}
