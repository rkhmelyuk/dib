package com.khmelyuk.dib.render;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.khmelyuk.dib.AppConstant;
import com.khmelyuk.dib.response.ResponseObject;
import com.khmelyuk.dib.response.ResponseStatus;
import com.khmelyuk.dib.validation.ValidationError;
import com.khmelyuk.dib.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;

/**
 * Responsible for rendering object to JSON.
 *
 * @author Ruslan Khmelyuk
 */
@Component
public class JsonResponseRenderer implements ResponseRenderer {

    private final Gson gson;

    @Autowired
    @Qualifier("apiMessages")
    private MessageSource messageSource;

    public JsonResponseRenderer() {
        this.gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat(AppConstant.DATE_FORMAT)
                .registerTypeHierarchyAdapter(Enum.class, new EnumTypeAdapter())
                .registerTypeHierarchyAdapter(CustomSerializable.class, new SerializeGetterAdapter())
                .create();
    }

    // ---------------------------------------------------------

    @Override
    public void write(Object object, Writer writer) throws IOException {
        if (object instanceof ResponseObject) {
            JsonElement json = toJson((ResponseObject) object);
            writer.write(gson.toJson(json));
        }
        else {
            writer.write(gson.toJson(object));
        }
    }

    /**
     * We convert a response object to the JSON element.
     * This is needed to meet the response format, but have usable ResponseObject structure.
     *
     * @param response the response object to convert.
     * @return the result json object.
     */
    private JsonObject toJson(ResponseObject response) {
        final JsonObject result = new JsonObject();
        final ResponseStatus status = response.getStatus();

        result.addProperty("status", status.getCode());

        // Add status dependent content
        if (status == ResponseStatus.Success) {
            result.add("response", toJson(response.getResponse()));
        }
        else if (status == ResponseStatus.ValidationError) {
            result.add("errors", toJson(response.getValidationErrors()));
        }
        else {
            result.addProperty("error", getMessage(response.getError(), null));
        }

        result.addProperty("timestamp", response.getTimestamp());
        result.addProperty("duration", response.getDuration());

        return result;
    }

    /**
     * Converts object to json element.
     *
     * @param object the object to convert.
     * @return the result json element.
     */
    private JsonElement toJson(Object object) {
        return gson.toJsonTree(object);
    }

    /**
     * Converts validation errors to json element.
     *
     * @param errors the validation errors.
     * @return the result json element.
     */
    private JsonObject toJson(ValidationErrors errors) {
        JsonObject result = new JsonObject();
        for (ValidationError each : errors.getErrors()) {
            String message = getMessage(each.getCode(), each.getArgs());
            result.addProperty(each.getField(), message);
        }

        return result;
    }

    private String getMessage(String message, Object[] args) {
        return messageSource.getMessage(message, args, message, null);
    }
}
