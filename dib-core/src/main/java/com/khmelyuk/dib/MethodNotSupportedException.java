package com.khmelyuk.dib;

import com.khmelyuk.dib.action.ApiAction;

/**
 * Request method is not supported.
 *
 * @author Ruslan Khmelyuk
 */
public class MethodNotSupportedException extends ApiException {

    public MethodNotSupportedException(ApiAction action, String method) {
        super("Action " + action.getName() + " not supports HTTP method " + method);
    }

}
