package com.khmelyuk.dib.validation;

/**
 * Represents a single validation error.
 *
 * @author Ruslan Khmelyuk
 */
public class ValidationError {

    private final String field;
    private final String code;
    private final Object[] args;

    public ValidationError(String field, String code, Object... args) {
        this.field = field;
        this.code = code;
        this.args = args;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String toString() {
        return field + ": " + code;
    }
}
