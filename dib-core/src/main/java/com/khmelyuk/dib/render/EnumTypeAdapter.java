package com.khmelyuk.dib.render;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Adapter to serialize Enums correctly to JSON.
 *
 * @author Ruslan Khmelyuk
 */
public class EnumTypeAdapter implements JsonSerializer<Enum> {

    private static final Logger log = LoggerFactory.getLogger(EnumTypeAdapter.class);

    private final Map<Enum, Map<String, Object>> CACHE = new ConcurrentHashMap<Enum, Map<String, Object>>();

    @Override
    public JsonElement serialize(Enum src, Type typeOfSrc, JsonSerializationContext context) {
        Map<String, Object> value = CACHE.get(src);

        if (value == null) {
            value = extractEnumValues(src);
            CACHE.put(src, value);
        }

        return context.serialize(value);
    }

    private Map<String, Object> extractEnumValues(Enum src) {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        final Field[] fields = src.getClass().getDeclaredFields();

        for (Field field : fields) {
            final int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    result.put(field.getName(), field.get(src));
                }
                catch (IllegalAccessException e) {
                    log.error("Error to extract value for field: " +
                            src.getClass().getName() + "." + field.getName());
                }
            }
        }

        return result;
    }
}
