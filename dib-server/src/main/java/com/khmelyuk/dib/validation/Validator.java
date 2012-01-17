package com.khmelyuk.dib.validation;

import com.khmelyuk.dib.RequestContext;

/**
 * The validator for context object.
 *
 * @author Ruslan Khmelyuk
 */
public interface Validator {

    /**
     * Validates the context object.
     *
     * @param context the request context.
     * @param errors  the holder for validation errors.
     */
    void validate(RequestContext context, ValidationErrors errors);
}
