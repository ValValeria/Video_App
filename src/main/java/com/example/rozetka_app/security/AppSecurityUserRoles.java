package com.example.rozetka_app.security;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum AppSecurityUserRoles {
    ADMIN(EnumSet.of(AppSecurityUserPermissions.CAN_CREATE, AppSecurityUserPermissions.CAN_DELETE)),
    READER(EnumSet.of(AppSecurityUserPermissions.CAN_COMMENT));

    private final Set<AppSecurityUserPermissions> permissionsSet;
    
    AppSecurityUserRoles(Set<AppSecurityUserPermissions> permissionsSet){
        this.permissionsSet = permissionsSet;
    }

    public Set<AppSecurityUserPermissions> getPermissionsSet(){
        return this.permissionsSet;
    }
}
