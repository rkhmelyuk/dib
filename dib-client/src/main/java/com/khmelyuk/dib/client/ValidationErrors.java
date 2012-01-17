package com.khmelyuk.dib.client;

import com.khmelyuk.core.utils.StringUtils;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a validation errors received from API.
 *
 * @author Ruslan Khmelyuk
 */
public class ValidationErrors {

    private final Map<String, String> errors = new LinkedHashMap<String, String>();

    public void add(String field, String message) {
        errors.put(field, message);
    }

    public void addAll(Map<String, String> errors) {
        errors.putAll(errors);
    }

    public boolean isHasErrors() {
        return errors.size() > 0;
    }

    public List<FieldError> getAsFieldErrors(String prefix) {
        final List<FieldError> errors = new ArrayList<FieldError>();

        prefix = (StringUtils.isNotEmpty(prefix) ? prefix + '.' : prefix);
        for (Map.Entry<String, String> entry : this.errors.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(prefix)) {
                String name = key.substring(prefix.length());
                if (name.contains(".")) {
                    // it's another object, skip
                    continue;
                }

                // add field error for specified field
                errors.add(new FieldError(prefix, name, entry.getValue()));
            }
        }

        return errors;
    }
}
