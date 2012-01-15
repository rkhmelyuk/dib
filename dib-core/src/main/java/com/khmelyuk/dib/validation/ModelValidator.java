package com.khmelyuk.dib.validation;

import com.khmelyuk.dib.RequestContext;
import com.khmelyuk.dib.model.Model;

/**
 * The model validator.
 *
 * @author Ruslan Khmelyuk
 */
public abstract class ModelValidator<T extends Model> extends BaseValidator {

    @SuppressWarnings("unchecked")
    public final void validate(RequestContext context, ValidationErrors errors) {
        T model = (T) context.getModel();
        if (model != null) {
            validate(model, errors);
        }
    }

    protected abstract void validate(T model, ValidationErrors errors);

}
