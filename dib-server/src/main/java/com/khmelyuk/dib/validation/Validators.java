package com.khmelyuk.dib.validation;

import com.prutsoft.core.utils.ConversionUtils;
import com.prutsoft.core.utils.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;

/**
 * The available std. validators.
 *
 * @author Ruslan Khmelyuk
 */
public class Validators {

    private static UrlValidator urlValidator = new UrlValidator();

    public static boolean required(Object value, String field, ValidationErrors errors) {
        if (value == null) {
            errors.add(new ValidationError(field, "error.required"));
            return false;
        }
        return true;
    }

    public static boolean required(String value, String field, ValidationErrors errors) {
        if (StringUtils.isEmpty(value)) {
            errors.add(new ValidationError(field, "error.required"));
            return false;
        }
        return true;
    }

    public static boolean maxLength(String value, int maxlength, String field, ValidationErrors errors) {
        if (value != null && value.length() > maxlength) {
            errors.add(new ValidationError(field, "error.maxlength", maxlength));
            return false;
        }
        return true;
    }

    public static boolean minLength(String value, int minlength, String field, ValidationErrors errors) {
        if (value != null && value.length() < minlength) {
            errors.add(new ValidationError(field, "error.minlength", minlength));
            return false;
        }
        return true;
    }

    public static boolean email(String value, String field, ValidationErrors errors) {
        if (value != null && !EmailValidator.getInstance().isValid(value)) {
            errors.add(new ValidationError(field, "error.email"));
            return false;
        }
        return true;
    }

    public static boolean url(String value, String field, ValidationErrors errors) {
        if (value != null && !urlValidator.isValid(value)) {
            errors.add(new ValidationError(field, "error.url"));
            return false;
        }
        return true;
    }

    public static boolean minValue(int value, int min, String field, ValidationErrors errors) {
        if (value < min) {
            errors.add(new ValidationError(field, "error.minValue", min));
            return false;
        }
        return true;
    }

    public static boolean maxValue(int value, int max, String field, ValidationErrors errors) {
        if (value > max) {
            errors.add(new ValidationError(field, "error.maxValue", max));
            return false;
        }
        return true;
    }

    public static boolean inRange(int value, int from, int to, String field, ValidationErrors errors) {
        if (value < from || value > to) {
            errors.add(new ValidationError(field, "error.range", from, to));
            return false;
        }
        return true;
    }

    public static boolean integerValue(Object value, String field, ValidationErrors errors) {
        if (value == null) {
            return true;
        }
        if (value instanceof Integer) {
            return true;
        }
        if (value instanceof String) {
            Integer integer = ConversionUtils.getInteger((String) value, null);
            if (integer != null) {
                return true;
            }
        }

        errors.add(new ValidationError(field, "error.not.integer"));
        return false;
    }

    public static boolean longValue(Object value, String field, ValidationErrors errors) {
        if (value == null) {
            return true;
        }
        if (value instanceof Long) {
            return true;
        }
        if (value instanceof String) {
            Long longValue = ConversionUtils.getLong((String) value, null);
            if (longValue != null) {
                return true;
            }
        }

        errors.add(new ValidationError(field, "error.not.long"));
        return false;
    }

    public static boolean doubleValue(Object value, String field, ValidationErrors errors) {
        if (value == null) {
            return true;
        }
        if (value instanceof Double) {
            return true;
        }
        if (value instanceof String) {
            Double doubleValue = ConversionUtils.getDouble((String) value, null);
            if (doubleValue != null) {
                return true;
            }
        }

        errors.add(new ValidationError(field, "error.not.double"));
        return false;
    }

    public static boolean booleanValue(Object value, String field, ValidationErrors errors) {
        if (value == null) {
            return true;
        }
        if (value instanceof Boolean) {
            return true;
        }
        if (value instanceof String) {
            Boolean booleanValue = ConversionUtils.getBoolean((String) value, null);
            if (booleanValue != null) {
                return true;
            }
        }

        errors.add(new ValidationError(field, "error.not.boolean"));
        return false;
    }
}
