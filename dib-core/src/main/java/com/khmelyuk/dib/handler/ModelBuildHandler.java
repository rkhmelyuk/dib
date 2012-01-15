package com.khmelyuk.dib.handler;

import com.khmelyuk.dib.ApiException;
import com.khmelyuk.dib.ModelInstantiationException;
import com.khmelyuk.dib.ModelMapException;
import com.khmelyuk.dib.RequestContext;
import com.khmelyuk.dib.model.Model;

/**
 * Builds a model of the context if available.
 *
 * @author Ruslan Khmelyuk
 */
public class ModelBuildHandler implements ApiRequestHandler {

    @Override
    public void handle(RequestContext context) throws ApiException {

        Class<Model> modelType = context.getAction().getModelType();
        if (modelType != null) {
            Model model = newModelInstance(modelType);
            mapModel(model, context);

            context.setModel(model);
        }
    }

    /**
     * Tries to fill a map with data from context object.
     *
     * @param model   the model to fill.
     * @param context the request context.
     * @throws ModelMapException error to fill a model.
     */
    private void mapModel(Model model, RequestContext context) throws ModelMapException {
        try {
            model.map(context);
        }
        catch (Exception e) {
            throw new ModelMapException(model);
        }
    }

    /**
     * Tries to instantiate a new instance of the model by specified type.
     *
     * @param modelType the model type.
     * @return the new instance of the model.
     * @throws ModelInstantiationException error to instantiate a new model.
     */
    private Model newModelInstance(Class<Model> modelType) throws ModelInstantiationException {
        try {
            return modelType.newInstance();
        }
        catch (Exception e) {
            throw new ModelInstantiationException(modelType, e);
        }
    }
}
