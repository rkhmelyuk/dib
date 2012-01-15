package com.khmelyuk.dib.response;

/**
 * Represents the response status code.
 *
 * @author Ruslan Khmelyuk
 */
public enum ResponseStatus {

    /**
     * Success response
     */
    Success(1),

    /**
     * Failed to do something, so return error.
     */
    Error(2),

    /**
     * Request parameters validation errors.
     */
    ValidationError(3),

    /**
     * One of many possible internal errors..
     */
    InternalError(4),

    /**
     * Access denied.
     */
    AccessDenied(5);

    final int code;

    private ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
