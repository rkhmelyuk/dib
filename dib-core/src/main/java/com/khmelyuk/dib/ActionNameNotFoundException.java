package com.khmelyuk.dib;

/**
 * Error to detect action name.
 *
 * @author Ruslan Khmelyuk
 */
public class ActionNameNotFoundException extends ApiException {

    public ActionNameNotFoundException() {
        super("Can't detect action name.");
    }
}
