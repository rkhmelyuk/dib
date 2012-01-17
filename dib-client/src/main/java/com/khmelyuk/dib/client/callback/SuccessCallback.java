package com.khmelyuk.dib.client.callback;

import com.khmelyuk.dib.client.response.ApiResponse;

/**
 * The callback to be called on success response.
 *
 * @author Ruslan Khmelyuk
 */
public interface SuccessCallback {

    void success(ApiResponse response, Object object) throws Exception;

}
