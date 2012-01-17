package com.khmelyuk.dib.client.request;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Responsible to build and execute a GET request.
 *
 * @author Ruslan Khmelyuk
 */
public class ApiGetRequest extends BaseApiRequest {

    public ApiGetRequest(HttpClient client, GetMethod get, ExecutorService executor, String name) {
        super(client, get, executor, name);
    }

    @Override
    protected Map<String, String> fillParameters(HttpMethod method) {
        GetMethod get = (GetMethod) method;
        final Map<String, String> parameters = getPreparedParametersMap();
        if (parameters.size() > 0) {
            int index = 0;
            final NameValuePair[] params = new NameValuePair[parameters.size()];
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                params[index++] = new NameValuePair(entry.getKey(), entry.getValue());
            }
            get.setQueryString(params);
        }

        return parameters;
    }
}
