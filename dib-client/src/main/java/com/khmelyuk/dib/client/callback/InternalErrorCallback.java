package com.khmelyuk.dib.client.callback;

import com.khmelyuk.dib.client.response.ApiResponse;

/**
 * The interface of an internal error callback.
 *
 * @author Ruslan Khmelyuk
 */
public interface InternalErrorCallback {

    void internalError(ApiResponse response, String message) throws Exception;

}
