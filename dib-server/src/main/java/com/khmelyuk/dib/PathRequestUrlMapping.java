package com.khmelyuk.dib;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Users a patch to map a request url to controller/action name.
 * <p/>
 * For example: /user/authenticate?params => controller=user, action=authenticate
 *
 * @author Ruslan Khmelyuk
 */
@Component
public class PathRequestUrlMapping implements RequestUrlMapping {

    private static final Logger log = LoggerFactory.getLogger(PathRequestUrlMapping.class);

    @Override
    public ActionName map(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = StringUtils.strip(path, "/");

        String[] elems = path.split("/");

        if (elems.length < 2) {
            log.error("Can't map request with path: " + path);
            return null;
        }

        return new ActionName(elems[0], elems[1]);
    }
}
