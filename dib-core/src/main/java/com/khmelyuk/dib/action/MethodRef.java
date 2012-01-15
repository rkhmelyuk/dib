package com.khmelyuk.dib.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A reference to the object method.
 *
 * @author Ruslan Khmelyuk
 */
public class MethodRef {

    private final Object object;
    private final Method method;
    private final List<Param> params = new ArrayList<Param>(3);

    public MethodRef(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    public Object invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(object, args);
    }

    public void addParam(Param param) {
        params.add(param);
    }

    public List<Param> getParams() {
        return params;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(100);
        builder.append(method.getName()).append("(");
        for (Param each : params) {
            builder.append(each).append(";");
        }
        return builder.append(")").toString();
    }
}
