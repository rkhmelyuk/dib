package com.khmelyuk.dib;

import com.khmelyuk.dib.action.ApiAction;
import com.khmelyuk.dib.model.Model;
import com.khmelyuk.dib.response.ResponseObject;
import com.khmelyuk.dib.security.ApiUser;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * The object that incorporates an API request context.
 *
 * @author Ruslan Khmelyuk
 */
public class RequestContext {

    private final ActionName actionName;

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    private ParamsMap params;
    private List<FileItem> files = new ArrayList<FileItem>();

    private ApiUser user;
    private Integer userId;

    private Model model;
    private ApiAction action;
    private ResponseObject actionResponse;

    public RequestContext(ActionName actionName, HttpServletRequest request, HttpServletResponse response) {
        this.actionName = actionName;
        this.request = request;
        this.response = response;

        this.params = new ParamsMap(request.getParameterMap());
    }

    public RequestContext(ActionName actionName, HttpServletRequest request,
                          HttpServletResponse response, ParamsMap params) {
        this.actionName = actionName;
        this.request = request;
        this.response = response;

        this.params = params;
    }

    public ActionName getActionName() {
        return actionName;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public ParamsMap getParams() {
        return params;
    }

    public void setParams(ParamsMap params) {
        this.params = params;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ApiAction getAction() {
        return action;
    }

    public void setAction(ApiAction action) {
        this.action = action;
    }

    public ResponseObject getActionResponse() {
        return actionResponse;
    }

    public void setActionResponse(ResponseObject actionResponse) {
        this.actionResponse = actionResponse;
    }

    public ApiUser getUser() {
        return user;
    }

    public void setUser(ApiUser user) {
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<FileItem> getFiles() {
        return files;
    }

    public void addFiles(List<FileItem> files) {
        this.files.addAll(files);
    }
}
