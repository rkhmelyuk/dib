package com.khmelyuk.dib.client;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A wrapper over JSONObject to make it usable.
 *
 * @author Ruslan Khmelyuk
 */
public class JsonObject {

    private final JSONObject json;

    public JsonObject(JSONObject json) {
        this.json = json;
    }

    public JSONObject getOriginal() {
        return json;
    }

    public JsonObject getObject(String key) {
        JSONObject obj = getOriginalObject(key);
        return (obj != null ? new JsonObject(obj) : null);
    }

    public Map getMap(String key) {
        return getOriginalObject(key);
    }

    public JSONObject getOriginalObject(String key) {
        JSONObject obj = getJSONObject(key);
        if (obj == null) {
            return null;
        }
        final String valueKey = getValueKey(key);
        return obj.optJSONObject(valueKey);
    }

    public JSONArray getArray(String key) {
        JSONObject obj = getJSONObject(key);
        if (obj == null) {
            return null;
        }
        final String valueKey = getValueKey(key);
        return obj.optJSONArray(valueKey);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        JSONObject obj = getJSONObject(key);
        if (obj == null) {
            return defaultValue;
        }
        final String valueKey = getValueKey(key);
        if (!obj.has(valueKey) || obj.get(valueKey) == null) {
            return defaultValue;
        }
        try {
            return obj.getString(valueKey);
        }
        catch (JSONException e) {
            return defaultValue;
        }
    }

    public Integer getInt(String key) {
        return getInt(key, null);
    }

    public Integer getInt(String key, Integer defaultValue) {
        JSONObject obj = getJSONObject(key);
        if (obj == null) {
            return defaultValue;
        }
        final String valueKey = getValueKey(key);
        if (!obj.has(valueKey) || obj.get(valueKey) == null) {
            return defaultValue;
        }
        try {
            return obj.getInt(valueKey);
        }
        catch (JSONException e) {
            return defaultValue;
        }
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        JSONObject obj = getJSONObject(key);
        if (obj == null) {
            return defaultValue;
        }
        final String valueKey = getValueKey(key);
        if (!obj.has(valueKey) || obj.get(valueKey) == null) {
            return defaultValue;
        }
        try {
            return obj.getBoolean(key);
        }
        catch (JSONException e) {
            return defaultValue;
        }
    }

    public Date getDate(String key) {
        return getDate(key, null);
    }

    public Date getDate(String key, Date defaultValue) {
        JSONObject obj = getJSONObject(key);
        if (obj == null) {
            return defaultValue;
        }
        final String valueKey = getValueKey(key);
        if (!obj.has(valueKey) || obj.get(valueKey) == null) {
            return defaultValue;
        }
        try {
            String string = obj.getString(key);
            return new SimpleDateFormat(ApiClientConstant.DATE_FORMAT).parse(string);
        }
        catch (JSONException e) {
            return defaultValue;
        }
        catch (ParseException e) {
            return defaultValue;
        }
    }

    public List<String> getStrings(String key) {
        return getStrings(key, null);
    }

    public List<String> getStrings(String key, List<String> defaultValue) {
        JSONObject obj = getJSONObject(key);
        if (obj == null) {
            return defaultValue;
        }
        final String valueKey = getValueKey(key);
        if (!obj.has(valueKey) || obj.get(valueKey) == null) {
            return defaultValue;
        }
        try {
            final JSONArray array = obj.optJSONArray(valueKey);
            if (array != null) {
                final List<String> result = new ArrayList<String>(array.size());
                for (int i = 0; i < array.size(); i++) {
                    result.add(array.getString(i));
                }

                return result;
            }

            return defaultValue;
        }
        catch (JSONException e) {
            return defaultValue;
        }
    }

    /**
     * Get the json object by it's key.
     * <p/>
     * For example, for name "user.name.firstName" it will return object "user->name".
     *
     * @param key the key to get object for.
     * @return the found object or null.
     */
    private JSONObject getJSONObject(String key) {
        if (key.contains(".")) {
            String[] items = key.split("\\.");
            JSONObject obj = json;
            for (int i = 0; i < items.length - 1; i++) {
                final String each = items[i];
                obj = obj.optJSONObject(each);
                if (obj == null) {
                    break;
                }
            }

            return obj;
        }
        return json;
    }

    /**
     * Gets the value key.
     *
     * @param key the key to get value key from.
     * @return the value key.
     */
    private String getValueKey(String key) {
        if (key.contains(".")) {
            // split by '.' and return the last element
            String[] items = key.split("\\.");
            return items[items.length - 1];
        }
        return key;
    }
}
