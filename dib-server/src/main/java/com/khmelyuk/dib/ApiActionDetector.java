package com.khmelyuk.dib;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.khmelyuk.dib.action.*;
import com.khmelyuk.dib.model.Model;
import com.khmelyuk.dib.security.Secured;
import com.khmelyuk.dib.security.SecurityInfo;
import com.khmelyuk.dib.validation.ModelValidator;
import com.khmelyuk.dib.validation.ParamsValidator;
import com.khmelyuk.dib.validation.SimpleRequestParamsValidator;
import com.prutsoft.core.utils.StringUtils;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Scans application context for api controllers,
 * scan controllers for actions and add the to the api action holder.
 *
 * @author Ruslan Khmelyuk
 */
@SuppressWarnings("unchecked")
public class ApiActionDetector {

    private final ApplicationContext context;

    private Map<Class<Model>, ModelParam> paramsCache = Maps.newHashMap();
    private Map<Class<? extends ModelValidator>, ModelValidator> modelValidatorsMap = Maps.newHashMap();
    private Map<Class<? extends ParamsValidator>, ParamsValidator> paramsValidatorsMap = Maps.newHashMap();

    public ApiActionDetector(ApplicationContext context) {
        this.context = context;
    }

    public void scanAndPopulate(ApiActionHolder holder) throws ApiException {
        Map<String, Object> beans = context.getBeansWithAnnotation(ApiController.class);

        for (Object bean : beans.values()) {
            ApiController annotation = bean.getClass().getAnnotation(ApiController.class);
            final String controllerName = annotation.value();

            if (StringUtils.isEmpty(controllerName)) {
                throw new ApiException("Controller name not found on bean " + bean.getClass().getName());
            }

            final List<Method> actions = extractActions(bean);
            if (actions.size() > 0) {
                buildAndAddActions(bean, controllerName, actions, holder);
            }
        }
    }

    private void buildAndAddActions(Object controller, String controllerName,
                                    List<Method> actions, ApiActionHolder holder) throws ApiException {

        for (Method method : actions) {
            final Action annotation = method.getAnnotation(Action.class);
            final String actionName = annotation.name();

            final RequestMethod requestMethod = getRequestMethod(annotation);
            final ActionName name = new ActionName(controllerName, actionName);
            final MethodRef methodRef = new MethodRef(controller, method);
            final ApiAction action = new ApiAction(name, methodRef, requestMethod);

            fillParamsMetadata(action, methodRef);
            fillValidatorMetadata(action, method);
            fillSecurityMetadata(action, method);

            holder.add(action);
        }
    }

    private RequestMethod getRequestMethod(Action annotation) {
        RequestMethod requestMethod = annotation.method();
        if (requestMethod == null) {
            requestMethod = RequestMethod.ANY;
        }
        return requestMethod;
    }

    private void fillParamsMetadata(ApiAction action, MethodRef methodRef) throws ApiException {
        final Class[] params = methodRef.getMethod().getParameterTypes();

        for (final Class paramType : params) {
            if (paramType.equals(RequestContext.class)) {
                methodRef.addParam(RequestObjectParam.getInstance());
            }
            else if (paramType.equals(HttpServletRequest.class)) {
                methodRef.addParam(HttpServletRequestParam.getInstance());
            }
            else if (paramType.equals(HttpServletResponse.class)) {
                methodRef.addParam(HttpServletResponseParam.getInstance());
            }
            else if (Model.class.isAssignableFrom(paramType) && action.getModelType() == null) {
                methodRef.addParam(buildModelParam(paramType));
                action.setModelType(paramType);
            }
            else {
                throw new ApiException("Unexpected action parameter " +
                        paramType.getName() + " for action " + action);
            }
        }
    }

    private ModelParam buildModelParam(Class type) throws ApiException {
        ModelParam param = paramsCache.get(type);
        if (param != null) {
            return param;
        }

        Class<Model> modelType = (Class<Model>) type;
        param = new ModelParam(modelType);
        Validator annotation = modelType.getAnnotation(Validator.class);
        if (annotation != null) {
            final Class<? extends ModelValidator> validatorClass = annotation.value();
            if (validatorClass == null) {
                throw new ApiException("Validator is not specified for model " + type.getName());
            }

            ModelValidator validator = getModelValidator(validatorClass);
            if (validator != null) {
                param.setValidator(validator);
            }
        }

        return param;
    }

    private ModelValidator getModelValidator(Class<? extends ModelValidator> validatorClass) throws ApiException {
        ModelValidator validator = modelValidatorsMap.get(validatorClass);
        if (validator != null) {
            return validator;
        }

        try {
            validator = validatorClass.newInstance();
            modelValidatorsMap.put(validatorClass, validator);
        }
        catch (Exception e) {
            throw new ApiException("Failed to instantiate a validator: " + validatorClass.getName());
        }

        return validator;
    }

    private void fillSecurityMetadata(ApiAction action, Method method) {
        Secured securedAnnotation = method.getAnnotation(Secured.class);
        if (securedAnnotation != null) {
            SecurityInfo info = new SecurityInfo();
            info.setDetectUser(securedAnnotation.detectUser());
            info.setRoles(securedAnnotation.roles());
            action.setSecurityInfo(info);
        }
    }

    private void fillValidatorMetadata(ApiAction action, Method method) throws ApiException {
        RequestValidator requestValidatorAnnotation = method.getAnnotation(RequestValidator.class);
        if (requestValidatorAnnotation != null) {
            final Class<? extends ParamsValidator>[] validatorClasses = requestValidatorAnnotation.value();
            if (validatorClasses.length == 0) {
                throw new ApiException("Validator is not specified for " + action);
            }

            addValidators(action, requestValidatorAnnotation, validatorClasses);
        }

        RequestParamsValidator paramsValidatorAnnotation = method.getAnnotation(RequestParamsValidator.class);
        if (paramsValidatorAnnotation != null) {
            final String[] required = paramsValidatorAnnotation.required();
            action.addRequestValidator(new SimpleRequestParamsValidator(required));
        }
    }

    private void addValidators(ApiAction action, RequestValidator requestValidatorAnnotation,
                               Class<? extends ParamsValidator>[] validatorTypes) throws ApiException {
        final String[] customParams = requestValidatorAnnotation.params();
        for (Class<? extends ParamsValidator> each : validatorTypes) {
            final ParamsValidator validator = getParamsValidator(each, customParams);

            if (validator != null) {
                action.addRequestValidator(validator);
            }
        }
    }

    private ParamsValidator getParamsValidator(Class<? extends ParamsValidator> validatorClass,
                                               String[] customParams) throws ApiException {

        // use cache only if there is no custom parameters
        boolean useCache = customParams == null || customParams.length == 0;

        if (useCache) {
            ParamsValidator validator = paramsValidatorsMap.get(validatorClass);
            if (validator != null) {
                return validator;
            }
        }

        try {
            ParamsValidator validator = validatorClass.newInstance();
            validator.setCustomParams(customParams);

            if (useCache) {
                paramsValidatorsMap.put(validatorClass, validator);
            }
            return validator;
        }
        catch (Exception e) {
            throw new ApiException("Failed to instantiate a validator: " + validatorClass.getName());
        }
    }

    private List<Method> extractActions(Object object) throws ApiException {
        List<Method> result = Lists.newArrayList();
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            Action annotation = method.getAnnotation(Action.class);
            if (annotation != null) {
                final String actionName = annotation.name();
                if (StringUtils.isEmpty(actionName)) {
                    throw new ApiException("Action name is not found on " +
                            object.getClass().getName() + "." + method.getName());
                }

                result.add(method);
            }
        }

        return result;
    }
}
