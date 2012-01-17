package com.khmelyuk.dib.client.request;

import com.khmelyuk.dib.client.ApiClientConstant;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Used to convert value to string.
 *
 * @author Ruslan Khmelyuk
 */
public class ParamsConverter {

    private static final String NONE = "";

    /**
     * Converts object to string.
     * If value is null, then returns an empty string.
     *
     * @param value the value to convert.
     * @return the result string.
     */
    public static String convert(Object value) {
        if (value == null) {
            return NONE;
        }
        if (value instanceof String) {
            return (String) value;
        }
        if (value instanceof Integer) {
            return Integer.toString((Integer) value);
        }
        if (value instanceof Long) {
            return Long.toString((Long) value);
        }
        if (value instanceof Boolean) {
            return Boolean.toString((Boolean) value);
        }
        if (value instanceof Float) {
            return Float.toString((Float) value);
        }
        if (value instanceof Double) {
            return Double.toString((Double) value);
        }
        if (value instanceof Short) {
            return Short.toString((Short) value);
        }
        if (value instanceof Byte) {
            return Byte.toString((Byte) value);
        }
        if (value instanceof Date) {
            return new SimpleDateFormat(ApiClientConstant.DATE_FORMAT).format((Date) value);
        }
        if (value instanceof Object[]) {
            return convert((Object[]) value);
        }
        if (value instanceof Collection) {
            return convert((Collection) value);
        }
        if (value instanceof Map) {
            return convert((Map) value);
        }

        return value.toString();
    }

    public static String convert(Object[] values) {
        if (values.length == 0) return NONE;
        if (values.length == 1) {
            return convert(values[0]);
        }

        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Object each : values) {
            if (first) {
                first = false;
            }
            else {
                builder.append(",");
            }

            builder.append(convert(each));
        }

        return builder.toString();
    }

    public static String convert(Collection values) {
        if (values.size() == 0) return NONE;

        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Object each : values) {
            if (first) {
                first = false;
            }
            else {
                builder.append(",");
            }

            builder.append(convert(each));
        }

        return builder.toString();
    }

    public static String convert(Map values) {
        if (values.size() == 0) return NONE;

        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Object each : values.entrySet()) {
            Map.Entry entry = (Map.Entry) each;

            if (first) {
                first = false;
            }
            else {
                builder.append(",");
            }

            builder // {
                    .append(convert(entry.getKey()))
                    .append(":")
                    .append(convert(entry.getValue()));
            // }
        }

        return builder.toString();
    }
}
