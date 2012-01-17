package com.khmelyuk.dib.validation;

import com.khmelyuk.dib.ParamsMap;
import com.khmelyuk.dib.RequestContext;

/**
 * The validator for context params.
 *
 * @author Ruslan Khmelyuk
 */
public abstract class ParamsValidator extends BaseValidator {

    private String[] customParams;

    public void setCustomParams(String[] customParams) {
        this.customParams = customParams;
    }

    public final void validate(RequestContext context, ValidationErrors errors) {
        validate(context.getParams(), customParams, errors);
    }

    protected abstract void validate(ParamsMap params, String[] customParams, ValidationErrors errors);

}
