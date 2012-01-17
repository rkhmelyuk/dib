package com.khmelyuk.dib.client.request;

import com.khmelyuk.core.Pair;
import com.khmelyuk.dib.client.ApiClientConstant;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Responsible to build and execute a POST request.
 *
 * @author Ruslan Khmelyuk
 */
public class ApiPostRequest extends BaseApiRequest {

    private final Map<String, Pair<String, File>> files = new HashMap<String, Pair<String, File>>();

    public ApiPostRequest(HttpClient client, PostMethod post, ExecutorService executor, String name) {
        super(client, post, executor, name);
    }

    public ApiPostRequest addFile(String param, String name, File stream) {
        if (name != null && stream != null) {
            files.put(param, new Pair<String, File>(name, stream));
        }
        return this;
    }

    @Override
    protected Map<String, String> fillParameters(HttpMethod method) {
        PostMethod post = (PostMethod) method;
        final Map<String, String> parameters = getPreparedParametersMap();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            post.addParameter(entry.getKey(), entry.getValue());
        }

        return parameters;
    }

    @Override
    protected void prepareMethodForRequest(HttpMethod method) {
        super.prepareMethodForRequest(method);
        if (files.size() > 0) {
            PostMethod post = (PostMethod) method;

            Part[] parts = new Part[files.size() + post.getParameters().length];

            int index = addParameters(post, parts, 0);
            addFiles(parts, index);


            post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
        }
    }

    private int addParameters(PostMethod post, Part[] parts, int index) {
        for (NameValuePair each : post.getParameters()) {
            parts[index++] = new StringPart(each.getName(), each.getValue(), ApiClientConstant.CHARSET);
        }
        return index;
    }

    private void addFiles(Part[] parts, int index) {
        // add file parameters
        for (Map.Entry<String, Pair<String, File>> each : files.entrySet()) {
            String name = each.getKey();
            String fileName = each.getValue().getFirst();
            File file = each.getValue().getSecond();
            FilePart part = buildFilePart(name, fileName, file);
            if (part != null) {
                parts[index++] = part;
            }
            buildFilePart(name, fileName, file);
        }
    }

    private FilePart buildFilePart(String name, String fileName, File file) {
        try {
            return new FilePart(name, fileName, file);
        }
        catch (Exception e) {
            log.error("Error to append a file param " + file);
            return null;
        }
    }
}
