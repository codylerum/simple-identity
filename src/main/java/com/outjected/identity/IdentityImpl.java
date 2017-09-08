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

    @Override
    public boolean isLoggedIn() {
        return user != null;
    }

    @Override
    public IdentityUser getUser() {
        return user;
    }

    @Override
    public void setAuthenticatorClass(Class<? extends Authenticator> authenticatorClass) {
        this.authenticatorClass = authenticatorClass;
    }

    @Override
    public String login() {
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

    @Override
    public void logout() {
        if (isLoggedIn()) {
            PostLoggedOutEvent postLoggedOutEvent = new PostLoggedOutEvent(user);
            user = null;
            beanManager.fireEvent(postLoggedOutEvent);
        }
    }

    @Override
    public boolean hasRole(SimpleRole role) {
        return roles.contains(role);
    }

    @Override
    public void addRole(SimpleRole role) {
        roles.add(role);
        for (SimplePermission p : role.getPermissions()) {
            permissions.add(p);
        }
    }

    @Override
    public boolean hasPermission(SimplePermission permission) {
        return permissions.contains(permission);
    }

    @Override
    public void addPermission(SimplePermission permission) {
        permissions.add(permission);
    }
}
