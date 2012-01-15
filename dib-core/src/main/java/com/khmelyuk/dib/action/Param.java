package com.khmelyuk.dib.action;

import com.khmelyuk.dib.RequestContext;

/**
 * Represents a method param.
 *
 * @author Ruslan Khmelyuk
 */
public abstract class Param<T> {

    private final Class<T> type;

    public Param(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    /**
     * If the param is a model.
     *
     * @return true if param is a model.
     */
    public boolean isModel() {
        return false;
    }

    /**
     * Extracts the value from context object if possible.
     *
     * @param context the request context.
     * @return the extract value, if not possible then null.
     */
    public abstract T tryToExtractValue(RequestContext context);

    public String toString() {
        return type.getSimpleName();
    }

}
