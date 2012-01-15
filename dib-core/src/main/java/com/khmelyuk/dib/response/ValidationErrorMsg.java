package com.khmelyuk.dib.response;

/**
 * The validation error message.
 *
 * @author Ruslan Khmelyuk
 */
public class ValidationErrorMsg {

    private final String field;
    private final String message;

    public ValidationErrorMsg(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
