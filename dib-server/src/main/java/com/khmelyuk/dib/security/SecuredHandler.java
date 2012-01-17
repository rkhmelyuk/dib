package com.khmelyuk.dib.security;

import com.khmelyuk.dib.ApiException;
import com.khmelyuk.dib.RequestContext;
import com.khmelyuk.dib.handler.ApiRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handler responsible to get user from request, check access etc.
 *
 * @author Ruslan Khmelyuk
 */
@Component
public class SecuredHandler implements ApiRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(SecuredHandler.class);

    @Autowired(required = false)
    private UserLoader userLoader;

    @Override
    public void handle(RequestContext context) throws ApiException {
        if (userLoader != null) {
            detectUser(context);
            checkAccess(context);
        }
    }

    private void detectUser(RequestContext context) {
        Integer id = context.getParams().getUserId();
        if (id != null) {
            context.setUserId(id);

            SecurityInfo info = context.getAction().getSecurityInfo();
            if (info != null) {
                if (info.isDetectUser() || info.getRoles().length > 0) {
                    try {
                        ApiUser user = userLoader.getApiUser(id);
                        context.setUser(user);
                    }
                    catch (Exception e) {
                        log.error("Error to find user by id " + id, e);
                    }
                }
            }
        }
    }

    private void checkAccess(RequestContext context) throws AccessDeniedException {
        SecurityInfo info = context.getAction().getSecurityInfo();
        if (info != null) {
            if (context.getUserId() == null) {
                throw new AccessDeniedException("Current user ID is not found in API request, but required.");
            }
            else {
                if (context.getUser() == null && (info.isDetectUser() || info.getRoles().length > 0)) {
                    throw new AccessDeniedException("Specified user is not found.");
                }
                else if (context.getUser() != null) {
                    boolean contains = false;
                    String role = context.getUser().getRole();
                    for (String each : info.getRoles()) {
                        if (role.equals(each)) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        throw new AccessDeniedException("User is not allowed to run specified operation.");
                    }
                }
            }
        }
    }
}
