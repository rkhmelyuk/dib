package com.khmelyuk.dib.handler;

import com.khmelyuk.dib.ApiException;
import com.khmelyuk.dib.RequestContext;
import com.khmelyuk.dib.response.ResponseObject;

/**
 * Execute the action.
 *
 * @author Ruslan Khmelyuk
 */
public class ExecuteHandler implements ApiRequestHandler {

    @Override
    public void handle(RequestContext context) throws ApiException {
        Object result = context.getAction().invoke(context);
        if (result instanceof ResponseObject) {
            context.setActionResponse((ResponseObject) result);
        }
        else {
            context.setActionResponse(ResponseObject.response(result));
        }
    }
}
