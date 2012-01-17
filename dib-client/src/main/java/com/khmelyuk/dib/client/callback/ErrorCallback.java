package com.khmelyuk.dib.client.callback;

import com.khmelyuk.dib.client.response.ApiResponse;

/**
 * The interface of an error callback.
 *
 * @author Ruslan Khmelyuk
 */
public interface ErrorCallback {

    void error(ApiResponse response, String message)  throws Exception;
}
