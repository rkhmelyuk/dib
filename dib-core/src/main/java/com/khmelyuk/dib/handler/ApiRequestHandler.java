package com.khmelyuk.dib.handler;

import com.khmelyuk.dib.ApiException;
import com.khmelyuk.dib.RequestContext;

/**
 * Handles API context and do some manipulation.
 * There can be a few of such handlers, and they organise some kind of chain.
 *
 * @author Ruslan Khmelyuk
 */
public interface ApiRequestHandler {

    /**
     * Handles a context object.
     *
     * @param context the request context.
     * @throws ApiException error or other problem with handling context.
     */
    void handle(RequestContext context) throws ApiException;

}
