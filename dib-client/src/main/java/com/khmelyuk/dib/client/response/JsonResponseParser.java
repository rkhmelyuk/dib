package com.khmelyuk.dib.client.response;

import com.khmelyuk.dib.client.request.WrongResponseFormatException;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

/**
 * A JSON parser for {@link ApiResponse}.
 *
 * @author Ruslan Khmelyuk
 */
@SuppressWarnings("unchecked")
public class JsonResponseParser {

    public ApiResponse parse(InputStream input) throws WrongResponseFormatException {
        final ApiResponse result = new ApiResponse();

        final JSONObject json = JSONObject.fromObject(input);

        // Status value
        if (!json.has("status") || json.get("status") == null) {
            throw new WrongResponseFormatException("'status' field is not found");
        }
        final int statusCode = json.getInt("status");
        final ResponseStatus status = ResponseStatus.findByCode(statusCode);
        result.setStatus(status);

        // Timestamp value
        if (!json.has("timestamp") || json.get("timestamp") == null) {
            throw new WrongResponseFormatException("'timestamp' field is not found");
        }
        result.setTimestamp(json.getLong("timestamp"));

        // Duration value
        if (!json.has("duration") || json.get("duration") == null) {
            throw new WrongResponseFormatException("'duration' field is not found");
        }
        result.setDuration(json.getLong("duration"));

        // error message
        if (status == ResponseStatus.Error || status == ResponseStatus.InternalError) {
            if (!json.has("error")) {
                throw new WrongResponseFormatException("'error' field is not found");
            }
            result.setErrorMessage(json.getString("error"));
        }
        else if (status == ResponseStatus.ValidationError) {
            if (!json.has("errors") || json.get("errors") == null) {
                throw new WrongResponseFormatException("'errors' field is not found");
            }
            populateValidationErrors(result, json.getJSONObject("errors"));
        }
        else if (status == ResponseStatus.Success) {
            if (!json.has("response")) {
                throw new WrongResponseFormatException("'response' field is not found");
            }
            JSONObject object = json.optJSONObject("response");
            if (object != null) {
                populateResponse(result, object);
            }
            else {
                populateResponse(result, json.optJSONArray("response"));
            }
        }

        return result;
    }

    private void populateResponse(ApiResponse result, JSON response) {
        result.setResponse(response);
    }

    private void populateValidationErrors(ApiResponse result, JSONObject errors) {
        for (Object each : errors.entrySet()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) each;
            result.addValidationError(entry.getKey(), entry.getValue());
        }
    }
}
