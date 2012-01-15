package com.khmelyuk.dib.model;

import com.khmelyuk.dib.RequestContext;

/**
 * Represents a model.
 *
 * @author Ruslan Khmelyuk
 */
public interface Model {

    /**
     * Uses a context to fill model with data.
     *
     * @param context the request context.
     */
    void map(RequestContext context);

}
