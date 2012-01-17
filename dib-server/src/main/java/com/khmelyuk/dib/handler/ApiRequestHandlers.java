package com.khmelyuk.dib.handler;

import com.khmelyuk.dib.ApiException;
import com.khmelyuk.dib.RequestContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * This is a std handler, but it contains other context handlers
 * and call them one by one for each context. Such context handlers organise a chain.
 *
 * @author Ruslan Khmelyuk
 */
@Component
public class ApiRequestHandlers implements ApiRequestHandler {

    private List<ApiRequestHandler> handlers;

    @Resource(name = "apiRequestHandlersList")
    public void setApiRequestHandlersList(List<ApiRequestHandler> handlers) {
        this.handlers = handlers;
    }

    public void handle(RequestContext context) throws ApiException {
        for (ApiRequestHandler each : handlers) {
            each.handle(context);
        }
    }
}
