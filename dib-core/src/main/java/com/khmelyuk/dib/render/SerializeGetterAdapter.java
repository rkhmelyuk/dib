package com.khmelyuk.dib.render;

import com.google.gson.*;
import com.khmelyuk.dib.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * The adapter to serialize getter values.
 *
 * @author Ruslan Khmelyuk
 */
public class SerializeGetterAdapter implements JsonSerializer<CustomSerializable> {

    private static final Logger log = LoggerFactory.getLogger(SerializeGetterAdapter.class);

    private final Gson gson;

    public SerializeGetterAdapter() {
        this.gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat(AppConstant.DATE_FORMAT)
                .registerTypeHierarchyAdapter(Enum.class, new EnumTypeAdapter())
                .create();
    }

    @Override
    public JsonElement serialize(CustomSerializable src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = (JsonObject) gson.toJsonTree(src, typeOfSrc);
        Method[] methods = src.getClass().getMethods();
        for (Method each : methods) {
            SerializeGetter annotation = each.getAnnotation(SerializeGetter.class);
            if (annotation != null && each.getParameterTypes().length == 0) {
                // include field
                Object value = invokeMethod(src, each);
                obj.add(annotation.value(), context.serialize(value));
            }
        }

        return obj;
    }

    private static Object invokeMethod(Object src, Method each) {
        try {
            return each.invoke(src);
        }
        catch (Exception e) {
            log.error("Error to get method " + each.getName()
                    + " value to serialize" + e.getMessage());
            return null;
        }
    }
}
