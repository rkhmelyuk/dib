package com.khmelyuk.dib.validation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A container for validation errors.
 *
 * @author Ruslan Khmelyuk
 */
public class ValidationErrors {

    private final List<ValidationError> errors = new LinkedList<ValidationError>();

    public boolean isEmpty() {
        return errors.isEmpty();
    }

    public int size() {
        return errors.size();
    }

    public void add(ValidationError error) {
        errors.add(error);
    }

    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public String toString() {
        final int size = errors.size();
        if (size == 0) return "";

        final StringBuilder builder = new StringBuilder(size * 20);

        for (ValidationError each : errors) {
            builder.append(" - ").append(each).append("\n");
        }

        return builder.toString();
    }

    // -------------------------------------------------------- Validators

    public boolean integerValue(Object value, String field) {
        return Validators.integerValue(value, field, this);
    }

    public boolean longValue(Object value, String field) {
        return Validators.longValue(value, field, this);
    }

    public boolean booleanValue(Object value, String field) {
        return Validators.booleanValue(value, field, this);
    }

    public boolean doubleValue(Object value, String field) {
        return Validators.doubleValue(value, field, this);
    }

    public boolean required(Object value, String field) {
        return Validators.required(value, field, this);
    }

    public boolean required(String value, String field) {
        return Validators.required(value, field, this);
    }

    public boolean email(String value, String field) {
        return Validators.email(value, field, this);
    }

    public boolean maxLength(String value, int maxlength, String field) {
        return Validators.maxLength(value, maxlength, field, this);
    }

    public boolean minLength(String value, int minlength, String field) {
        return Validators.minLength(value, minlength, field, this);
    }

    public boolean url(String value, String field) {
        return Validators.url(value, field, this);
    }

    public boolean minValue(int value, int min, String field) {
        return Validators.minValue(value, min, field, this);
    }

    public boolean maxValue(int value, int max, String field) {
        return Validators.maxValue(value, max, field, this);
    }

    public boolean inRange(int value, int from, int to, String field) {
        return Validators.inRange(value, from, to, field, this);
    }
}
