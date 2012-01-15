package com.khmelyuk.dib;

import com.khmelyuk.dib.validation.ValidationErrors;

/**
 * Error to validate parameters or model.
 *
 * @author Ruslan Khmelyuk
 */
public class ValidationException extends ApiException {

    private final ValidationErrors errors;

    public ValidationException(ValidationErrors errors) {
        super("Validation errors: \n" + errors.toString());
        this.errors = errors;
    }

    public ValidationErrors getErrors() {
        return errors;
    }
}
