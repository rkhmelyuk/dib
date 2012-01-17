package com.khmelyuk.dib.client.response;

/**
 * The api response status.
 *
 * @author Ruslan Khmelyuk
 */
public enum ResponseStatus {

    /** Success response */
    Success(1),

    /** Failed to do something, so return error. */
    Error(2),

    /** Request parameters validation errors. */
    ValidationError(3),

    /** One of many possible internal errors.. */
    InternalError(4),

    /** Access denied. */
    AccessDenied(5);

    final int code;

    private ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ResponseStatus findByCode(int code) {
        for (ResponseStatus each : values()) {
            if (each.code == code) {
                return each;
            }
        }
        return null;
    }

}
