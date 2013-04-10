package com.outjected.identity.events;

import com.outjected.identity.IdentityUser;

public class PostLoggedOutEvent {

    private IdentityUser user;

    public PostLoggedOutEvent(IdentityUser user) {
        this.user = user;
    }

    public IdentityUser getUser() {
        return user;
    }
}
