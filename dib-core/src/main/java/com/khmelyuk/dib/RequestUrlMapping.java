package com.khmelyuk.dib;

import javax.servlet.http.HttpServletRequest;

/**
 * Fetches the controller and action name from the request url.
 *
 * @author Ruslan Khmelyuk
 */
public interface RequestUrlMapping {

    /**
     * Maps a request url to controller/action name.
     *
     * @param request the servlet request.
     * @return the action name, if can't parse correctly returns NULL.
     */
    ActionName map(HttpServletRequest request);

}
