package com.outjected.identity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;

import com.outjected.identity.events.PostLoggedOutEvent;

/**
 * 
 * @author Cody Lerum
 * 
 */
@SessionScoped
@Named("identity")
public class IdentityImpl implements Serializable, Identity {

    private static final long serialVersionUID = 1L;

    @Inject
    @Any
    private Instance<Authenticator> authenticators;
    
    @Inject
    private BeanManager beanManager;

    // Data
    private Class<? extends Authenticator> authenticatorClass;
    private IdentityUser user;
    private Set<SimpleRole> roles = new HashSet<SimpleRole>();
    private Set<SimplePermission> permissions = new HashSet<SimplePermission>();

    public boolean isLoggedIn() {
        return user != null;
    }

    public IdentityUser getUser() {
        return user;
    }

    public void setAuthenticatorClass(Class<? extends Authenticator> authenticatorClass) {
        this.authenticatorClass = authenticatorClass;
    }

    public String login() {
        try {
            Authenticator auth = authenticators.select(authenticatorClass).get();
            auth.authenticate();
            user = auth.getUser();
            if (isLoggedIn()) {
                return RESPONSE_LOGIN_SUCCESS;
            }
            else {
                return RESPONSE_LOGIN_FAILED;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return RESPONSE_LOGIN_EXCEPTION;
        }
    }

    public void logout() {
        if (isLoggedIn()) {
            PostLoggedOutEvent postLoggedOutEvent = new PostLoggedOutEvent(user);
            user = null;
            beanManager.fireEvent(postLoggedOutEvent);
        }
    }

    public boolean hasRole(SimpleRole role) {
        return roles.contains(role);
    }

    public void addRole(SimpleRole role) {
        roles.add(role);
        for (SimplePermission p : role.getPermissions()) {
            permissions.add(p);
        }
    }

    public boolean hasPermission(SimplePermission permission) {
        return permissions.contains(permission);
    }

    public void addPermission(SimplePermission permission) {
        permissions.add(permission);
    }
}
