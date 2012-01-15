package com.khmelyuk.dib;

/**
 * The action name, which consists of controller and method name.
 *
 * @author Ruslan Khmelyuk
 */
public class ActionName {

    private final String actionName;
    private final String controllerName;

    public ActionName(String controllerName, String actionName) {
        this.actionName = actionName;
        this.controllerName = controllerName;
    }

    public int hashCode() {
        int result = actionName.hashCode();
        result = 31 * result + controllerName.hashCode();
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof ActionName) {
            ActionName that = (ActionName) o;

            return actionName.equals(that.actionName) &&
                    controllerName.equals(that.controllerName);
        }

        return false;
    }

    @Override
    public String toString() {
        return controllerName + "/" + actionName;
    }
}
