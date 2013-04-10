package com.outjected.identity;

/**
 * 
 * @author Cody Lerum
 * 
 */
public interface Authenticator {

    void authenticate();

    IdentityUser getUser();

    AuthenticationStatus getStatus();
}
