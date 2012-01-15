package com.khmelyuk.dib;

import com.khmelyuk.dib.action.ApiAction;

/**
 * Error to invoke an action.
 * Usually means incorrect parameters used or action method is no accessible.
 *
 * @author Ruslan Khmelyuk
 */
public class ActionInvocationException extends ApiException {

    private final ApiAction action;

    public ActionInvocationException(ApiAction action) {
        super("Error to invoke an action " + action);
        this.action = action;
    }

    public ApiAction getAction() {
        return action;
    }
}
