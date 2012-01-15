package com.khmelyuk.dib.action;

import com.khmelyuk.dib.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * The parameter that represents a Servlet Request.
 *
 * @author Ruslan Khmelyuk
 */
public class HttpServletRequestParam extends Param<HttpServletRequest> {

    private static final HttpServletRequestParam instance = new HttpServletRequestParam();

    public static HttpServletRequestParam getInstance() {
        return instance;
    }

    private HttpServletRequestParam() {
        super(HttpServletRequest.class);
    }

    @Override
    public HttpServletRequest tryToExtractValue(RequestContext context) {
        return context.getRequest();
    }
}
