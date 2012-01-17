package com.khmelyuk.dib.client.request;

/**
 * API call internal exception.
 *
 * @author Ruslan Khmelyuk
 */
public class ApiInternalErrorException extends ApiException {

    public ApiInternalErrorException(String message) {
        super(message);
    }
}
