package com.khmelyuk.dib.client.request;

import com.khmelyuk.dib.client.callback.ErrorCallback;
import com.khmelyuk.dib.client.callback.InternalErrorCallback;
import com.khmelyuk.dib.client.callback.SuccessCallback;
import com.khmelyuk.dib.client.callback.ValidationErrorCallback;
import com.khmelyuk.dib.client.response.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * The API request interface.
 *
 * @author Ruslan Khmelyuk
 */
public interface ApiRequest {

    /**
     * Set the request parameters as map.
     *
     * @param params the parameters map.
     * @return the current instance reference.
     */
    ApiRequest params(Map<String, String> params);

    /**
     * Add a single parameter with specified name and value.
     *
     * @param key   the name of parameter.
     * @param value the value of parameter.
     * @return the current instance reference.
     */
    public ApiRequest addParam(String key, Object value);

    /**
     * Add a single parameter with specified name and values.
     *
     * @param key    the name of parameter.
     * @param values the values of parameter.
     * @return the current instance reference.
     */
    public ApiRequest addParam(String key, List values);

    /**
     * Setup success callback.
     *
     * @param callback the success callback.
     * @return the current instance reference.
     */
    ApiRequest success(SuccessCallback callback);

    /**
     * Setup error callback.
     *
     * @param callback the error callback.
     * @return the current instance reference.
     */
    ApiRequest error(ErrorCallback callback);

    /**
     * Setup validation error callback.
     *
     * @param callback the validation error callback.
     * @return the current instance reference.
     */
    ApiRequest validationError(ValidationErrorCallback callback);

    /**
     * Setup internal error callback.
     *
     * @param callback the internal error callback.
     * @return the current instance reference.
     */
    ApiRequest internalError(InternalErrorCallback callback);

    /**
     * Call the api request to be executed.
     *
     * @return the result of execution.
     */
    ApiResponse call() throws Exception;

    /** Call the api request asynchronously. */
    void callAsync();
}
