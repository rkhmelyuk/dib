package com.khmelyuk.dib.response;

import com.khmelyuk.dib.validation.ValidationErrors;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the response of the API request.
 *
 * @author Ruslan Khmelyuk
 */
public class ResponseObject {

    public static ResponseObject error(String message) {
        final ResponseObject result = new ResponseObject();
        result.setStatus(ResponseStatus.Error);
        result.setError(message);
        return result;
    }

    public static ResponseObject internalError(String message) {
        final ResponseObject result = new ResponseObject();
        result.setStatus(ResponseStatus.InternalError);
        result.setError(message);
        return result;
    }

    public static ResponseObject accessDenied() {
        return accessDenied("access.denied");
    }

    public static ResponseObject accessDenied(String message) {
        final ResponseObject result = new ResponseObject();
        result.setStatus(ResponseStatus.AccessDenied);
        result.setError(message);
        return result;
    }

    public static ResponseObject response(Object value) {
        final ResponseObject result = new ResponseObject();
        result.setStatus(ResponseStatus.Success);
        result.setResponse(value);
        return result;
    }

    public static ResponseObject response(InputStream stream, String contentType) {
        final ResponseObject result = new ResponseObject();
        result.setStatus(ResponseStatus.Success);
        result.setResponse(stream);
        result.setContentType(contentType);
        return result;
    }

    public static ResponseObject response(String key, Object value) {
        final ResponseObject result = new ResponseObject();
        result.setStatus(ResponseStatus.Success);
        result.setResponse(Collections.singletonMap(key, value));
        return result;
    }

    public static ResponseObject response(String key1, Object value1,
                                          String key2, Object value2) {
        final ResponseObject result = new ResponseObject();
        result.setStatus(ResponseStatus.Success);

        Map<String, Object> values = new HashMap<String, Object>();
        values.put(key1, value1);
        values.put(key2, value2);

        result.setResponse(values);

        return result;
    }

    public static ResponseObject response(String key1, Object value1,
                                          String key2, Object value2,
                                          String key3, Object value3) {
        final ResponseObject result = new ResponseObject();
        result.setStatus(ResponseStatus.Success);

        Map<String, Object> values = new HashMap<String, Object>();
        values.put(key1, value1);
        values.put(key2, value2);
        values.put(key3, value3);

        result.setResponse(values);

        return result;
    }

    public static ResponseObject validationErrors(ValidationErrors errors) {
        final ResponseObject result = new ResponseObject();
        result.setStatus(ResponseStatus.ValidationError);
        result.setValidationErrors(errors);
        return result;
    }

    // ------------------------------------------------------

    private ResponseStatus status;

    private String error;
    private ValidationErrors validationErrors;

    private Object response;
    private String contentType;

    private long duration;
    private long timestamp;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(ValidationErrors validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
