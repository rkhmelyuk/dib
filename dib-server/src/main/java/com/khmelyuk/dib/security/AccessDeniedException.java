package com.khmelyuk.dib.security;

import com.khmelyuk.dib.ApiException;

/**
 * Access is denied to specified API action.
 *
 * @author Ruslan Khmelyuk
 */
public class AccessDeniedException extends ApiException {

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
