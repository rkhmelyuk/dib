package com.khmelyuk.dib.client.response;


import com.khmelyuk.dib.client.JsonObject;
import com.khmelyuk.dib.client.ResponseObject;
import com.khmelyuk.dib.client.ValidationErrors;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.io.InputStream;

/**
 * Represents the api response;
 *
 * @author Ruslan Khmelyuk
 */
public class ApiResponse {

    private ResponseStatus status;
    private String errorMessage;
    private ValidationErrors validationErrors = new ValidationErrors();

    private JSON response;
    private InputStream responseStream;

    private long timestamp;
    private long duration;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }

    public void addValidationError(String field, String message) {
        validationErrors.add(field, message);
    }

    public InputStream getResponseStream() {
        return responseStream;
    }

    public void setResponseStream(InputStream responseStream) {
        this.responseStream = responseStream;
    }

    public JSON getResponse() {
        return response;
    }

    public JsonObject getResponseAsJsonObject() {
        if (response instanceof JSONObject) {
            return new JsonObject((JSONObject) response);
        }
        throw new IllegalArgumentException("Can't get response: not a json object.");
    }

    public void setResponse(JSON response) {
        this.response = response;
    }

    // ---------------------  Response mappers

    public ResponseObject mapResponse(ResponseObject object) {
        if (response instanceof JSONObject) {
            object.read(new JsonObject((JSONObject) response));
            return object;
        }

        throw new IllegalArgumentException("Can't map response: not json object.");
    }

    public <T extends ResponseObject> T mapResponse(Class<T> type)
            throws IllegalAccessException, InstantiationException {
        if (response instanceof JSONObject) {
            T instance = type.newInstance();
            instance.read(new JsonObject((JSONObject) response));
            return instance;
        }

        throw new IllegalArgumentException("Can't map response: not json object.");
    }

}
