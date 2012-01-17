package com.khmelyuk.dib.client.callback;

import com.khmelyuk.dib.client.ValidationErrors;
import com.khmelyuk.dib.client.response.ApiResponse;

/**
 * The interface of validation errors callback.
 *
 * @author Ruslan Khmelyuk
 */
public interface ValidationErrorCallback {

    void validationError(ApiResponse response, ValidationErrors errors) throws Exception;

}
