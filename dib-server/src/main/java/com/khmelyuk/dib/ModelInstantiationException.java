package com.khmelyuk.dib;

import com.khmelyuk.dib.model.Model;

/**
 * Error to instantiate a new model.
 *
 * @author Ruslan Khmelyuk
 */
public class ModelInstantiationException extends ApiException {

    public ModelInstantiationException(Class<Model> modelType, Throwable t) {
        super("Error to instantiate a model " + modelType.getName(), t);
    }

}
