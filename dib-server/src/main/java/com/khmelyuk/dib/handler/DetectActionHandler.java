package com.khmelyuk.dib.handler;

import com.khmelyuk.dib.*;
import com.khmelyuk.dib.action.ApiAction;

/**
 * Tries to detect the action by it's name and method.
 *
 * @author Ruslan Khmelyuk
 */
public class DetectActionHandler implements ApiRequestHandler {

    private ApiActionHolder actionHolder;

    public void setActionHolder(ApiActionHolder actionHolder) {
        this.actionHolder = actionHolder;
    }

    @Override
    public void handle(RequestContext context) throws ApiException {
        final ActionName actionName = context.getActionName();
        final ApiAction action = actionHolder.getAction(actionName);

        if (action == null) {
            throw new ActionNotFoundException(actionName);
        }
        else {
            String method = context.getRequest().getMethod();
            if (!isMethodSupported(action, method)) {
                throw new MethodNotSupportedException(action, method);
            }
        }

        context.setAction(action);
    }

    /**
     * Checks if method is supported by action.
     *
     * @param action the action.
     * @param method the received http request method.
     * @return true if supported, otherwise false.
     */
    private boolean isMethodSupported(ApiAction action, String method) {
        if (action.getRequestMethod() == RequestMethod.GET) {
            return "GET".equals(method);
        }
        else if (action.getRequestMethod() == RequestMethod.POST) {
            return "POST".equals(method);
        }
        else if (action.getRequestMethod() == RequestMethod.ANY) {
            return "GET".equals(method) || "POST".equals(method);
        }

        return false;
    }
}
