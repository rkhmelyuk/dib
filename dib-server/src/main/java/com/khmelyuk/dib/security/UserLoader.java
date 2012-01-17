package com.khmelyuk.dib.security;

/**
 * Responsible to load the API request user.
 *
 * @author Ruslan Khmelyuk
 */
public interface UserLoader {

    /**
     * Gets the api user by id.
     *
     * @param id the user id.
     * @return the found user or null.
     * @throws Exception error to get user by id.
     */
    ApiUser getApiUser(Integer id) throws Exception;
}
