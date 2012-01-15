package com.khmelyuk.dib;

import com.khmelyuk.dib.handler.ApiRequestHandlers;
import com.khmelyuk.dib.render.ResponseRenderer;
import com.khmelyuk.dib.response.ResponseObject;
import com.khmelyuk.dib.security.AccessDeniedException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Responsible to dispatch an API request and return the result of call.
 *
 * @author Ruslan Khmelyuk
 */
@Component("apiDispatcher")
public class ApiDispatcher {

    private static final Logger log = LoggerFactory.getLogger(ApiDispatcher.class);

    @Autowired
    private ResponseRenderer responseRenderer;
    @Autowired
    private RequestUrlMapping requestUrlMapping;
    @Autowired
    private ApiRequestHandlers apiRequestHandlers;

    @Autowired
    private FileUpload fileUpload;

    // -------------------------------------------

    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final long begin = System.currentTimeMillis();

        ResponseObject responseObj;

        try {
            // find action name
            final ActionName actionName = requestUrlMapping.map(request);
            if (actionName == null) {
                throw new ActionNameNotFoundException();
            }

            // builder request context and use it to handle api request
            RequestContext context = buildContext(actionName, request, response);

            apiRequestHandlers.handle(context);

            // get api response.
            responseObj = context.getActionResponse();
        }
        catch (ApiException e) {
            log.error("Error to dispatch a request: " + request.getPathInfo() + "\n\t"
                    + e.getClass().getName() + ": " + e.getMessage());
            responseObj = convertExceptionToResponseObject(e);
        }
        catch (Exception e) {
            log.error("Error to dispatch a request: " + request.getPathInfo(), e);
            responseObj = convertExceptionToResponseObject(e);
        }

        // include debug information
        responseObj.setTimestamp(begin);
        responseObj.setDuration(System.currentTimeMillis() - begin);

        // renders a response
        renderResponse(response, responseObj);
    }

    private RequestContext buildContext(ActionName actionName,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            return buildMultipartRequestContext(actionName, request, response);
        }

        return new RequestContext(actionName, request, response);
    }

    @SuppressWarnings("unchecked")
    private RequestContext buildMultipartRequestContext(ActionName actionName,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        try {
            final ServletRequestContext requestContext = new ServletRequestContext(request);
            final List<FileItem> items = (List<FileItem>) fileUpload.parseRequest(requestContext);

            final List<FileItem> files = new ArrayList<FileItem>();
            final Map<String, String[]> params = new HashMap<String, String[]>();

            for (final FileItem each : items) {
                if (each.isFormField()) {
                    params.put(each.getFieldName(), new String[]{each.getString(AppConstant.CHARSET)});
                }
                else {
                    files.add(each);
                }
            }

            RequestContext context = new RequestContext(
                    actionName, request, response,
                    new ParamsMap(params));

            context.addFiles(files);

            return context;
        }
        catch (Exception e) {
            log.error("Error to read files form the request.", e);
            return null;
        }
    }

    /**
     * Converts possible exceptions to the response object.
     *
     * @param ex the caught exception.
     * @return the response object.
     */
    private ResponseObject convertExceptionToResponseObject(Exception ex) {
        if (ex instanceof ValidationException) {
            return ResponseObject.validationErrors(((ValidationException) ex).getErrors());
        }
        else if (ex instanceof MethodNotSupportedException) {
            return ResponseObject.internalError("method.not.supported.error");
        }
        else if (ex instanceof ActionInvocationException) {
            return ResponseObject.internalError("action.invocation.error");
        }
        else if (ex instanceof ActionNameNotFoundException) {
            return ResponseObject.internalError("action.name.not.found.error");
        }
        else if (ex instanceof ActionNotFoundException) {
            return ResponseObject.internalError("action.not.found.error");
        }
        else if (ex instanceof ModelInstantiationException) {
            return ResponseObject.internalError("model.instantiation.error");
        }
        else if (ex instanceof ModelMapException) {
            return ResponseObject.internalError("model.map.error");
        }
        else if (ex instanceof AccessDeniedException) {
            return ResponseObject.accessDenied("access.denied");
        }
        else if (ex instanceof ApiException) {
            return ResponseObject.internalError("unexpected.api.error");
        }

        return ResponseObject.internalError("unexpected.error");
    }

    /**
     * Renders a response.
     *
     * @param response    the servlet response.
     * @param responseObj the API response object.
     * @throws IOException error to write a response.
     */
    private void renderResponse(HttpServletResponse response, ResponseObject responseObj) throws IOException {
        if (responseObj == null) {
            throw new NullPointerException("Response Object is null.");
        }

        if (responseObj.getResponse() instanceof InputStream) {
            writeBinary(response, responseObj);
        }
        else {
            writeJson(response, responseObj);
        }
    }

    private void writeBinary(HttpServletResponse response, ResponseObject responseObj) throws IOException {
        final InputStream stream = (InputStream) responseObj.getResponse();

        if (responseObj.getContentType() != null) {
            response.setContentType(responseObj.getContentType());
        }
        else {
            response.setContentType("application/octet-stream");
        }

        // write response
        OutputStream out = response.getOutputStream();
        IOUtils.copy(stream, out);
        out.flush();
    }

    private void writeJson(HttpServletResponse response, ResponseObject responseObj) throws IOException {
        // setup response header values
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // write response
        PrintWriter writer = response.getWriter();
        responseRenderer.write(responseObj, writer);

        writer.close();
    }

}
