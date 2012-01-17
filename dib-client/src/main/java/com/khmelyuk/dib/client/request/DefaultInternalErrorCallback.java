package com.khmelyuk.dib.client.request;

import com.khmelyuk.dib.client.callback.InternalErrorCallback;
import com.khmelyuk.dib.client.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This internal error callback just logs the errors, and used by default.
 *
 * @author Ruslan Khmelyuk
 */
class DefaultInternalErrorCallback implements InternalErrorCallback {

    private static final Logger log = LoggerFactory.getLogger(DefaultInternalErrorCallback.class);

    private static final DefaultInternalErrorCallback instance = new DefaultInternalErrorCallback();

    public static DefaultInternalErrorCallback getInstance() {
        return instance;
    }

    private DefaultInternalErrorCallback() {
    }

    public void internalError(ApiResponse response, String message) throws Exception {
        log.error("Server API internal error: " + message);
        throw new ApiInternalErrorException(message);
    }

}
