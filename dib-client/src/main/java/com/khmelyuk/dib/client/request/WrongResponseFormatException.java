package com.khmelyuk.dib.client.request;

/**
 * Response formation is incorrect.
 *
 * @author Ruslan Khmelyuk
 */
public class WrongResponseFormatException extends ApiException {

    public WrongResponseFormatException(String message) {
        super(message);
    }
}
