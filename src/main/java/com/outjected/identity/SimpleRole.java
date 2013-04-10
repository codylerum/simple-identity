package com.outjected.identity;

import java.util.Set;

/**
 * 
 * @author Cody Lerum
 * 
 */
public interface SimpleRole {
    Set<SimplePermission> getPermissions();
    SimplePermissionType getType();
}
