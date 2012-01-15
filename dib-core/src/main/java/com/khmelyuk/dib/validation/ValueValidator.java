package com.khmelyuk.dib.validation;

/**
 * Used to validate a single value using a chain of validators.
 *
 * @author Ruslan Khmelyuk
 */
public class ValueValidator {

    private String field;
    private ValidationErrors errors;

    public ValueValidator(String field, ValidationErrors errors) {
        this.field = field;
        this.errors = errors;
    }

    public ValueValidator required(Object value) {
        Validators.required(value, field, errors);
        return this;
    }

    public ValueValidator integerValue(Object value) {
        Validators.integerValue(value, field, errors);
        return this;
    }

    public ValueValidator longValue(Object value) {
        Validators.longValue(value, field, errors);
        return this;
    }

    public ValueValidator doubleValue(Object value) {
        Validators.doubleValue(value, field, errors);
        return this;
    }

    public ValueValidator booleanValue(Object value) {
        Validators.booleanValue(value, field, errors);
        return this;
    }

    public ValueValidator minLength(String value, int minLength) {
        Validators.minLength(value, minLength, field, errors);
        return this;
    }

    public ValueValidator maxLength(String value, int maxLength) {
        Validators.maxLength(value, maxLength, field, errors);
        return this;
    }

    public ValueValidator min(int value, int min) {
        Validators.minValue(value, min, field, errors);
        return this;
    }

    public ValueValidator max(int value, int max) {
        Validators.maxValue(value, max, field, errors);
        return this;
    }

    public ValueValidator inRage(int value, int min, int max) {
        Validators.inRange(value, min, max, field, errors);
        return this;
    }

    public ValueValidator email(String value) {
        Validators.email(value, field, errors);
        return this;
    }

    public ValueValidator url(String value) {
        Validators.url(value, field, errors);
        return this;
    }
}
