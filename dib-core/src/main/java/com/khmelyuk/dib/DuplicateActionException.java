package com.khmelyuk.dib;

/**
 * Exception is thrown when duplicate action is found.
 * Action is duplicate if the same controller and action name is used twice.
 *
 * @author Ruslan Khmelyuk
 */
public class DuplicateActionException extends ApiException {

    private final ActionName name;

    public DuplicateActionException(ActionName name) {
        super("Duplicate action: " + name.toString());
        this.name = name;
    }

    public ActionName getName() {
        return name;
    }
}
