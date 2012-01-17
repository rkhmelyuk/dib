package com.khmelyuk.dib.validation.tool;

import com.khmelyuk.dib.ParamsMap;
import com.khmelyuk.dib.validation.ParamsValidator;
import com.khmelyuk.dib.validation.ValidationErrors;

/**
 * This is commonly used validator, that is used to validate 'id' parameter
 * is specified and is an integer value.
 *
 * @author Ruslan Khmelyuk
 */
public class IdValidator extends ParamsValidator {

    /**
     * May accept custom parameters. The first custom parameter is custom id param name.
     * By default id param name is "id".
     *
     * @param params       the parameters map.
     * @param customParams the custom parameters.
     * @param errors       the validation errors.
     */
    @Override
    protected void validate(ParamsMap params, String[] customParams, ValidationErrors errors) {
        String idParamName = "id";
        if (customParams != null && customParams.length > 0) {
            idParamName = customParams[0];
        }
        String id = params.get(idParamName);
        errors.required(id, idParamName);
        errors.integerValue(id, idParamName);
    }
}
