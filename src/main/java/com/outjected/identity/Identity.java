package com.outjected.identity;

/**
 * 
 * @author Cody Lerum
 * 
 */
public interface Identity {

    public static final String RESPONSE_LOGIN_SUCCESS = "success";
    public static final String RESPONSE_LOGIN_FAILED = "failed";
    public static final String RESPONSE_LOGIN_EXCEPTION = "exception";

    boolean isLoggedIn();

    IdentityUser getUser();

    void setAuthenticatorClass(Class<? extends Authenticator> authenticatorClass);

    String login();

    void logout();

    boolean hasRole(SimpleRole role);

    void addRole(SimpleRole role);

    boolean hasPermission(SimplePermission permission);

    void addPermission(SimplePermission permission);
}
