package com.khmelyuk.dib;

import com.prutsoft.core.utils.ConversionUtils;

import java.util.*;

/**
 * This is a simple wrapper over request parameters map.
 * Add some useful functionality to work with request parameters.
 *
 * @author Ruslan Khmelyuk
 */
public class ParamsMap {

    private Map<String, String[]> params;

    @SuppressWarnings("unchecked")
    public ParamsMap(Map params) {
        this.params = (Map<String, String[]>) params;
    }

    /**
     * Check if contains a parameter with specified key.
     *
     * @param key the key value.
     * @return true if contains, otherwise false.
     */
    public boolean contains(String key) {
        return params.containsKey(key);
    }

    /**
     * Gets the string value by key.
     *
     * @param key the key.
     * @return the found value in params by key.
     */
    public String get(String key) {
        String[] value = params.get(key);
        if (value != null && value.length > 0) {
            return value[0];
        }
        return null;
    }

    /**
     * Gets the string value by key.
     *
     * @param key          the key of string value.
     * @param defaultValue the default value.
     * @return the string value or default value.
     */
    public String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Gets the string if not empty.
     *
     * @param key          the key of value to get.
     * @param defaultValue the default value.
     * @return the not empty string by key or default value.
     */
    public String getNotEmpty(String key, String defaultValue) {
        String value = get(key);
        if (value != null) {
            value = value.trim();
            return value.length() > 0 ? value : defaultValue;
        }

        return defaultValue;
    }

    /**
     * Gets the integer value from map.
     *
     * @param key the key of value to get.
     * @return the parse integer from params or null.
     */
    public Integer getInteger(String key) {
        return getInteger(key, null);
    }

    /**
     * Gets the integer value from map.
     *
     * @param key          the key of value to get.
     * @param defaultValue the default value.
     * @return the parse integer from params or default value.
     */
    public Integer getInteger(String key, Integer defaultValue) {
        String value = get(key);
        if (value != null) {
            return ConversionUtils.getInteger(value, defaultValue);
        }

        return defaultValue;
    }

    /**
     * Gets the long value from map.
     *
     * @param key the key of value to get.
     * @return the parse long from params or null.
     */
    public Long getLong(String key) {
        return getLong(key, null);
    }

    /**
     * Gets the long value from map.
     *
     * @param key          the key of value to get.
     * @param defaultValue the default value.
     * @return the parse long from params or default value.
     */
    public Long getLong(String key, Long defaultValue) {
        String value = get(key);
        if (value != null) {
            return ConversionUtils.getLong(value, defaultValue);
        }

        return defaultValue;
    }

    /**
     * Gets the boolean value from map.
     *
     * @param key the key of value to get.
     * @return the boolean long from params or null.
     */
    public Boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * Gets the boolean value from map.
     *
     * @param key          the key of value to get.
     * @param defaultValue the default value.
     * @return the boolean long from params or default value.
     */
    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = get(key);
        if (value != null) {
            return ConversionUtils.getBoolean(value, defaultValue);
        }

        return defaultValue;
    }

    /**
     * Gets the float value from map.
     *
     * @param key the key of value to get.
     * @return the float float from params or null.
     */
    public Float getFloat(String key) {
        return getFloat(key, null);
    }

    /**
     * Gets the float value from map.
     *
     * @param key          the key of value to get.
     * @param defaultValue the default value.
     * @return the float float from params or default value.
     */
    public Float getFloat(String key, Float defaultValue) {
        String value = get(key);
        if (value != null) {
            return ConversionUtils.getFloat(value, defaultValue);
        }

        return defaultValue;
    }

    /**
     * Gets the double value from map.
     *
     * @param key the key of value to get.
     * @return the double float from params or null.
     */
    public Double getDouble(String key) {
        return getDouble(key, null);
    }

    /**
     * Gets the double value from map.
     *
     * @param key          the key of value to get.
     * @param defaultValue the default value.
     * @return the double float from params or default value.
     */
    public Double getDouble(String key, Double defaultValue) {
        String value = get(key);
        if (value != null) {
            return ConversionUtils.getDouble(value, defaultValue);
        }

        return defaultValue;
    }

    /**
     * Gets the date from map.
     *
     * @param key the key of value to get.
     * @return the date value from params or null.
     */
    public Date getDate(String key) {
        return getDate(key, null);
    }

    /**
     * Gets the date from map.
     *
     * @param key          the key of value to get.
     * @param defaultValue the default value.
     * @return the date value from params or default value.
     */
    public Date getDate(String key, Date defaultValue) {
        String value = get(key);
        if (value != null) {
            return ConversionUtils.getDate(value, AppConstant.DATE_FORMAT, defaultValue);
        }

        return defaultValue;
    }

    /**
     * Gets the list of string values by key.
     * If nothing is found, then empty list is returned.
     *
     * @param key the key to get the list of strings.
     * @return the list of strings or empty collection
     */
    public List<String> getList(String key) {
        String[] strings = params.get(key);
        if (strings.length == 0) {
            return Collections.emptyList();
        }

        if (strings.length == 1) {
            // if a single string, then split by comma
            strings = strings[0].split(",");
        }

        List<String> result = new ArrayList<String>(strings.length);
        Collections.addAll(result, strings);
        return result;
    }

    /**
     * Gets the list of string values by key.
     * If nothing is found, then empty list is returned.
     *
     * @param key the key to get the list of strings.
     * @return the list of strings or empty collection
     */
    public Set<String> getSet(String key) {
        String[] strings = params.get(key);
        if (strings.length == 0) {
            return Collections.emptySet();
        }

        if (strings.length == 1) {
            // if a single string, then split by comma
            strings = strings[0].split(",");
        }

        Set<String> result = new HashSet<String>(strings.length);
        Collections.addAll(result, strings);
        return result;
    }

    /**
     * Gets the array with key values.
     *
     * @param key the key.
     * @return the array with values.
     */
    public String[] getArray(String key) {
        return params.get(key);
    }

    /**
     * Gets the list of integer values. It first get the list of strings,
     * but then convert each into integer value.
     *
     * @param key the key of value.
     * @return the list with integers.
     */
    public List<Integer> getListOfIntegers(String key) {
        List<String> strings = getList(key);
        if (strings.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<Integer>();
        for (String each : strings) {
            Integer value = ConversionUtils.getInteger(each.trim(), null);
            if (value != null) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * Gets the list of integer values. It first get the list of strings,
     * but then convert each into integer value.
     *
     * @param key the key of value.
     * @return the list with integers.
     */
    public Set<Integer> getSetOfIntegers(String key) {
        Set<String> strings = getSet(key);
        if (strings.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Integer> result = new HashSet<Integer>();
        for (String each : strings) {
            Integer value = ConversionUtils.getInteger(each.trim(), null);
            if (value != null) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * Gets the page parameter value.
     *
     * @return the page parameter value.
     */
    public int getPage() {
        return getInteger("page", 1);
    }

    /**
     * Gets the limit parameter value.
     *
     * @return the limit parameter value.
     */
    public int getLimit() {
        return getInteger("limit", AppConstant.DEFAULT_LIMIT);
    }

    /**
     * Gets the id parameter value.
     *
     * @return the id parameter value.
     */
    public Integer getId() {
        return getInteger("id", null);
    }

    /**
     * Gets the id of the current user id, if specified.
     *
     * @return the id of the current user id, if not specified then null.
     */
    public Integer getUserId() {
        return getInteger(AppConstant.CURRENT_USER_ID, null);
    }
}
