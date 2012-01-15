package com.khmelyuk.dib;

import com.khmelyuk.dib.model.Model;

/**
 * Error to fill a map with request data.
 *
 * @author Ruslan Khmelyuk
 */
public class ModelMapException extends ApiException {

    public ModelMapException(Model model) {
        super("Error to map a model " + model.getClass().getName());
    }
}
