package com.khmelyuk.dib.validation;

import com.khmelyuk.dib.ParamsMap;

/**
 * The request params validator.
 *
 * @author Ruslan Khmelyuk
 */
public class SimpleRequestParamsValidator extends ParamsValidator {

    private final String[] required;

    public SimpleRequestParamsValidator(String[] required) {
        this.required = required;
    }

    @Override
    protected void validate(ParamsMap params, String[] customParams, ValidationErrors errors) {
        for (String each : required) {
            errors.required(params.get(each), each);
        }
    }
}
