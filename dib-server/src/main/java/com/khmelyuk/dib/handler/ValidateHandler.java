package com.khmelyuk.dib.handler;

import com.khmelyuk.dib.ApiException;
import com.khmelyuk.dib.RequestContext;
import com.khmelyuk.dib.ValidationException;
import com.khmelyuk.dib.action.ApiAction;
import com.khmelyuk.dib.action.ModelParam;
import com.khmelyuk.dib.action.Param;
import com.khmelyuk.dib.validation.ModelValidator;
import com.khmelyuk.dib.validation.ParamsValidator;
import com.khmelyuk.dib.validation.ValidationErrors;

import java.util.List;

/**
 * Validates a context object params or model.
 *
 * @author Ruslan Khmelyuk
 */
public class ValidateHandler implements ApiRequestHandler {

    public void handle(RequestContext context) throws ApiException {
        final ValidationErrors errors = new ValidationErrors();

        validateModels(context, errors);
        validateParameters(context, errors);

        if (!errors.isEmpty()) {
            // throw exception if any validation error found
            throw new ValidationException(errors);
        }
    }

    /**
     * Validates the context parameters.
     *
     * @param context the context context.
     * @param errors  the validation errors holder.
     */
    private void validateParameters(RequestContext context, ValidationErrors errors) {
        final ApiAction action = context.getAction();
        if (action.getRequestValidators().size() > 0) {
            for (ParamsValidator each : action.getRequestValidators()) {
                each.validate(context, errors);
            }
        }
    }

    /**
     * Validates the model object.
     *
     * @param context the request context.
     * @param errors  the validation errors holder.
     */
    private void validateModels(RequestContext context, ValidationErrors errors) {
        final ApiAction action = context.getAction();
        if (context.getModel() == null) {
            return;
        }

        final List<Param> params = action.getMethod().getParams();
        for (Param each : params) {
            if (each.isModel()) {
                ModelValidator validator = ((ModelParam) each).getValidator();
                if (validator != null) {
                    validator.validate(context, errors);
                    break;
                }
            }
        }
    }
}
