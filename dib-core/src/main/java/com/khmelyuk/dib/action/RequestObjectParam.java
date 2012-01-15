package com.khmelyuk.dib.action;

import com.khmelyuk.dib.RequestContext;

/**
 * Represents a Request Object parameter.
 *
 * @author Ruslan Khmelyuk
 */
public final class RequestObjectParam extends Param<RequestContext> {

    private static final RequestObjectParam instance = new RequestObjectParam();

    public static RequestObjectParam getInstance() {
        return instance;
    }

    private RequestObjectParam() {
        super(RequestContext.class);
    }

    @Override
    public RequestContext tryToExtractValue(RequestContext context) {
        return context;
    }
}
