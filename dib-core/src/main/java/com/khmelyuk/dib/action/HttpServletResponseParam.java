package com.khmelyuk.dib.action;

import com.khmelyuk.dib.RequestContext;

import javax.servlet.http.HttpServletResponse;

/**
 * The parameter that represents a Servlet Response.
 *
 * @author Ruslan Khmelyuk
 */
public class HttpServletResponseParam extends Param<HttpServletResponse> {

    private static final HttpServletResponseParam instance = new HttpServletResponseParam();

    public static HttpServletResponseParam getInstance() {
        return instance;
    }

    private HttpServletResponseParam() {
        super(HttpServletResponse.class);
    }

    @Override
    public HttpServletResponse tryToExtractValue(RequestContext context) {
        return context.getResponse();
    }
}
