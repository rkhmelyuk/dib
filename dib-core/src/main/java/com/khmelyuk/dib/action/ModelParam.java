package com.khmelyuk.dib.action;

import com.khmelyuk.dib.RequestContext;
import com.khmelyuk.dib.model.Model;
import com.khmelyuk.dib.validation.ModelValidator;

/**
 * The param represented by Model object.
 *
 * @author Ruslan Khmelyuk
 */
public class ModelParam extends Param<Model> {

    private ModelValidator validator;

    public ModelParam(Class<Model> type) {
        super(type);
    }

    public ModelValidator getValidator() {
        return validator;
    }

    public void setValidator(ModelValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean isModel() {
        return true;
    }

    @Override
    public Model tryToExtractValue(RequestContext context) {
        return context.getModel();
    }
}
