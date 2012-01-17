package com.khmelyuk.dib.action;

import com.khmelyuk.dib.*;
import com.khmelyuk.dib.model.Model;
import com.khmelyuk.dib.security.SecurityInfo;
import com.khmelyuk.dib.validation.ParamsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * The metadata of api action.
 *
 * @author Ruslan Khmelyuk
 */
public class ApiAction {

    private static final Logger log = LoggerFactory.getLogger(ApiAction.class);

    private final ActionName name;
    private final MethodRef method;
    private final RequestMethod requestMethod;

    private Class<Model> modelType;
    private List<ParamsValidator> requestValidators = new ArrayList<ParamsValidator>(1);

    private SecurityInfo securityInfo;

    public ApiAction(ActionName name, MethodRef method, RequestMethod requestMethod) {
        this.name = name;
        this.method = method;
        this.requestMethod = requestMethod;
    }

    public ActionName getName() {
        return name;
    }

    public MethodRef getMethod() {
        return method;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public List<ParamsValidator> getRequestValidators() {
        return requestValidators;
    }

    public void addRequestValidator(ParamsValidator requestValidator) {
        this.requestValidators.add(requestValidator);
    }

    public Class<Model> getModelType() {
        return modelType;
    }

    public void setModelType(Class<Model> modelType) {
        this.modelType = modelType;
    }

    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(SecurityInfo securityInfo) {
        this.securityInfo = securityInfo;
    }

    /**
     * Invocates the action context method using specified context context.
     *
     * @param context the context context.
     * @return the result of execution.
     * @throws ApiException error to execute an action.
     */
    public Object invoke(RequestContext context) throws ApiException {
        try {
            return method.invoke(populateParameters(context));
        }
        catch (InvocationTargetException e) {
            log.error("Error to invoke an api action: " + this +
                    ". Please check method parameters are defined correctly.", e);
            throw new ActionInvocationException(this);
        }
        catch (IllegalAccessException e) {
            log.error("Illegal call of api action action: " + this +
                    ". Please check method is public and not static.", e);
            throw new ActionInvocationException(this);
        }
        catch (Exception e) {
            log.error("Error while invoking action: " + this, e);
            throw new ApiException("Error to invoke action.", e);
        }
    }

    /**
     * Populate each action parameter with value.
     *
     * @param context the context object.
     * @return the array with actual parameter values.
     */
    private Object[] populateParameters(RequestContext context) {
        final List<Param> params = method.getParams();
        final Object[] result = new Object[params.size()];

        int index = 0;
        for (Param each : params) {
            result[index++] = each.tryToExtractValue(context);
        }

        return result;
    }

    public String toString() {
        return "{ " + name.toString() + " [ " + method.toString() + " ]" + " }";
    }
}
