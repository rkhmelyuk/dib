package com.khmelyuk.dib;

/**
 * API exceptions.
 *
 * @author Ruslan Khmelyuk
 */
public class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
