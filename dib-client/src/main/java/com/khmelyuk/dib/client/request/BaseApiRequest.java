package com.khmelyuk.dib.client.request;

import com.khmelyuk.dib.client.callback.ErrorCallback;
import com.khmelyuk.dib.client.callback.InternalErrorCallback;
import com.khmelyuk.dib.client.callback.SuccessCallback;
import com.khmelyuk.dib.client.callback.ValidationErrorCallback;
import com.khmelyuk.dib.client.response.ApiResponse;
import com.khmelyuk.dib.client.response.BinaryResponseParser;
import com.khmelyuk.dib.client.response.JsonResponseParser;
import com.khmelyuk.dib.client.response.ResponseStatus;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * The base implementation of the API request.
 *
 * @author Ruslan Khmelyuk
 */
@SuppressWarnings("unchecked")
abstract class BaseApiRequest implements ApiRequest {

    private static final long MAX_DURATION = 100;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Map<String, Object> params = new LinkedHashMap<String, Object>();

    private final HttpClient client;
    private final HttpMethod method;
    private final String name;
    private boolean profile;

    private final ExecutorService executor;

    private ErrorCallback errorCallback = null;
    private SuccessCallback successCallback = null;
    private ValidationErrorCallback validationErrorCallback = null;
    private InternalErrorCallback internalErrorCallback = DefaultInternalErrorCallback.getInstance();

    protected BaseApiRequest(HttpClient client, HttpMethod method, ExecutorService executor, String name) {
        this.client = client;
        this.method = method;
        this.executor = executor;
        this.name = name;
    }

    public void setProfile(boolean profile) {
        this.profile = profile;
    }

    public ApiRequest params(Map params) {
        this.params.putAll(params);
        return this;
    }

    public ApiRequest addParam(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public ApiRequest addParam(String key, List values) {
        this.params.put(key, values);
        return this;
    }

    public ApiRequest success(SuccessCallback callback) {
        successCallback = callback;
        return this;
    }

    public ApiRequest error(ErrorCallback callback) {
        errorCallback = callback;
        return this;
    }

    public ApiRequest validationError(ValidationErrorCallback callback) {
        validationErrorCallback = callback;
        return this;
    }

    public ApiRequest internalError(InternalErrorCallback callback) {
        internalErrorCallback = callback;
        return this;
    }

    /**
     * Convert parameters if need, and return a usable map.
     *
     * @return the usable map with keys as strings and values as strings.
     */
    protected Map<String, String> getPreparedParametersMap() {
        Map<String, String> result = new LinkedHashMap<String, String>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            result.put(entry.getKey(), ParamsConverter.convert(entry.getValue()));
        }

        return result;
    }

    public ApiResponse call() throws Exception {
        ApiResponse response;

        try {
            response = doCall();
            if (response == null) {
                return null;
            }
        }
        catch (Exception e) {
            log.error("Error to handle api call " + name, e);
            return null;
        }

        executeCallbacks(response);

        return response;
    }

    private ApiResponse doCall() throws Exception {
        try {
            // ---- Fill query parameters

            Map<String, String> parameters = fillParameters(method);

            if (profile) {
                log.info("REQUEST: " + method.getName() + " " + method.getPath() + " -> " + parameters);
            }

            prepareMethodForRequest(method);

            // ---- Execute GET call

            final int status = client.executeMethod(method);
            if (status == HttpStatus.SC_OK) {
                // read the response and return it.
                if (profile && isJson(method)) {
                    log.info("RESPONSE: " + method.getResponseBodyAsString());
                }

                final ApiResponse response = parseResponse(method);

                if (profile) {
                    if (response.getDuration() > MAX_DURATION) {
                        log.warn("!!! *** TOO LONG API CALL: " + name);
                    }
                }

                return response;
            }
            else {
                throw new RuntimeException("Api " + method.getName()
                        + " request failed with status " + status);
            }
        }
        finally {
            method.releaseConnection();
        }
    }

    private void executeCallbacks(ApiResponse response) throws Exception {
        final ResponseStatus status = response.getStatus();

        // call callbacks if set
        if (status == ResponseStatus.Success) {
            if (successCallback != null) {
                successCallback.success(response, response.getResponse());
            }
        }
        else if (status == ResponseStatus.Error) {
            if (errorCallback != null) {
                errorCallback.error(response, response.getErrorMessage());
            }
        }
        else if (status == ResponseStatus.InternalError) {
            if (internalErrorCallback != null) {
                internalErrorCallback.internalError(response, response.getErrorMessage());
            }
        }
        else if (status == ResponseStatus.ValidationError) {
            if (validationErrorCallback != null) {
                validationErrorCallback.validationError(response, response.getValidationErrors());
            }
        }
    }

    public void callAsync() {
        // Executes a call in a separate thread
        executor.submit(new Runnable() {
            public void run() {
                try {
                    call();
                }
                catch (Exception e) {
                    log.error("Error to async call " + name, e);
                }
            }
        });
    }

    /**
     * Prepares the method for request. By default do nothing.
     *
     * @param method the method to prepare.
     */
    protected void prepareMethodForRequest(HttpMethod method) {
        // do nothing
    }

    /**
     * Fills http method with parameters.
     *
     * @param method the request method.
     * @return the map with parameters to print out if need.
     */
    protected abstract Map<String, String> fillParameters(HttpMethod method);

    /**
     * Read the response and convert it to the ApiResponse object.
     *
     * @param method the http method.
     * @return the read ApiResponse instance.
     * @throws WrongResponseFormatException wrong response format.
     */
    protected ApiResponse parseResponse(HttpMethod method) throws Exception {
        if (isJson(method)) {
            // parse as json
            return new JsonResponseParser().parse(method.getResponseBodyAsStream());
        }

        // parse as stream
        return new BinaryResponseParser().parse(method.getResponseBody());
    }

    private boolean isJson(HttpMethod method) {
        Header contentType = method.getResponseHeader("content-type");
        if (contentType != null) {
            String value = contentType.getValue().toLowerCase();
            return value.contains("application/json");
        }
        return false;
    }

}
