package com.outjected.identity;

public abstract class BaseAuthenticator {

    private AuthenticationStatus status;
    private IdentityUser user;

    public AuthenticationStatus getStatus() {
        return status;
    }

    protected void setStatus(AuthenticationStatus status) {
        this.status = status;
    }

    public IdentityUser getUser() {
        return user;
    }

    protected void setUser(IdentityUser user) {
        this.user = user;
    }
}
