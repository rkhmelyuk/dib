package com.khmelyuk.dib.validation.tool;

import com.khmelyuk.dib.ParamsMap;
import com.khmelyuk.dib.validation.ParamsValidator;
import com.khmelyuk.dib.validation.ValidationErrors;

/**
 * Validates the pagination parameters. There are two pagination parameters: page and limit.
 *
 * @author Ruslan Khmelyuk
 */
public class PaginationValidator extends ParamsValidator {

    /**
     * Checks that both pagination parameters are integer values.
     *
     * @param params       the parameters.
     * @param customParams the custom parameters.
     * @param errors       the validation errors.
     */
    @Override
    protected void validate(ParamsMap params, String[] customParams, ValidationErrors errors) {
        errors.integerValue(params.get("page"), "page");
        errors.integerValue(params.get("limit"), "limit");
    }
}
