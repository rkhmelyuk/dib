package com.khmelyuk.dib.client.request;

/**
 * @author Ruslan Khmelyuk
 */
public abstract class ApiException extends Exception {

    protected ApiException(String message) {
        super(message);
    }

    protected ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
