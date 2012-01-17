package com.khmelyuk.dib.client;

import com.khmelyuk.dib.client.request.ApiGetRequest;
import com.khmelyuk.dib.client.request.ApiPostRequest;
import com.prutsoft.core.asserts.ArgumentAssert;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The api client method, that allows to access to the API functionality
 * and retrieve the result. Supports both sync and async methods.
 *
 * @author Ruslan Khmelyuk
 */
public final class ApiClient implements InitializingBean {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private HttpClient httpClient;
    private String apiActionFormat;

    private String userAgent;
    private int getMaxRetry;
    private int postMaxRetry;
    private boolean profile;

    private HttpMethodRetryHandler retryHandler;
    private UserInfoExtractor userInfoExtractor;

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setApiActionFormat(String apiActionFormat) {
        this.apiActionFormat = apiActionFormat;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setGetMaxRetry(int getMaxRetry) {
        this.getMaxRetry = getMaxRetry;
    }

    public void setPostMaxRetry(int postMaxRetry) {
        this.postMaxRetry = postMaxRetry;
    }

    public void setProfile(boolean profile) {
        this.profile = profile;
    }

    public void setUserInfoExtractor(UserInfoExtractor userInfoExtractor) {
        this.userInfoExtractor = userInfoExtractor;
    }

    public void afterPropertiesSet() throws Exception {
        retryHandler = new HttpMethodRetryHandler() {
            public boolean retryMethod(HttpMethod method, IOException exception, int executionCount) {
                if (method instanceof GetMethod && executionCount > getMaxRetry) {
                    return false;
                }
                else if (method instanceof PostMethod && executionCount > postMaxRetry) {
                    return false;
                }
                else if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                return !method.isRequestSent();
            }
        };
    }

    // -----------------------------------------------------

    public ApiGetRequest get(String controller, String action) {
        GetMethod method = new GetMethod(makeRequestUrl(controller, action));
        setupMethod(method);

        ApiGetRequest request = new ApiGetRequest(
                httpClient, method, executor,
                controller + "." + action);

        request.setProfile(profile);

        final Integer id = getUserId();
        if (id != null) {
            request.addParam(ApiClientConstant.CURRENT_USER_ID, id);
        }

        return request;
    }

    public ApiPostRequest post(String controller, String action) {
        final PostMethod method = new PostMethod(makeRequestUrl(controller, action));
        setupMethod(method);

        ApiPostRequest request = new ApiPostRequest(
                httpClient, method, executor,
                controller + "." + action);

        request.setProfile(profile);

        final Integer id = getUserId();
        if (id != null) {
            request.addParam(ApiClientConstant.CURRENT_USER_ID, id);
        }

        return request;
    }

    private Integer getUserId() {
        if (userInfoExtractor != null) {
            return userInfoExtractor.getUserId();
        }
        return null;
    }

    private void setupMethod(HttpMethod method) {
        method.getParams().setUriCharset(ApiClientConstant.CHARSET);
        method.getParams().setContentCharset(ApiClientConstant.CHARSET);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);
        method.getParams().setParameter(HttpMethodParams.USER_AGENT, userAgent);
    }

    /**
     * Returns the request url for specified action.
     *
     * @param controller the controller name; required.
     * @param action     the action name; required.
     * @return the prepared request url.
     */
    private String makeRequestUrl(String controller, String action) {
        ArgumentAssert.isNotEmpty(controller, "Controller name is required");
        ArgumentAssert.isNotEmpty(action, "Action name is required");

        return MessageFormat.format(apiActionFormat, controller, action);
    }

}
