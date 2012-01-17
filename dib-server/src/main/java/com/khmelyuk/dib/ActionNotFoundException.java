package com.khmelyuk.dib;

/**
 * Action with specified name is not found.
 *
 * @author Ruslan Khmelyuk
 */
public class ActionNotFoundException extends ApiException {

    private final ActionName actionName;

    public ActionNotFoundException(ActionName name) {
        super("Action " + name + " is not found.");
        this.actionName = name;
    }

    public ActionName getActionName() {
        return actionName;
    }
}
