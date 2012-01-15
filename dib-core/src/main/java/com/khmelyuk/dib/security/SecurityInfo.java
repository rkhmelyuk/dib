package com.khmelyuk.dib.security;

/**
 * Represents a security information of specified api action.
 *
 * @author Ruslan Khmelyuk
 */
public class SecurityInfo {

    private boolean detectUser;
    private String[] roles;

    public boolean isDetectUser() {
        return detectUser;
    }

    public void setDetectUser(boolean detectUser) {
        this.detectUser = detectUser;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
