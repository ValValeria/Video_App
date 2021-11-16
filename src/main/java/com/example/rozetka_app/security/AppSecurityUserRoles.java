package com.example.rozetka_app.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum AppSecurityUserRoles {
    ADMIN(EnumSet.of(AppSecurityUserPermissions.CAN_CREATE_POSTS,
            AppSecurityUserPermissions.CAN_DELETE_POSTS,
            AppSecurityUserPermissions.CAN_VIEW_PROFILES
            )),
    READER(EnumSet.of(AppSecurityUserPermissions.CAN_COMMENT));

    private final Set<AppSecurityUserPermissions> permissionsSet;
    
    AppSecurityUserRoles(Set<AppSecurityUserPermissions> permissionsSet){
        this.permissionsSet = permissionsSet;

        this.permissionsSet.addAll(Set.of(AppSecurityUserPermissions.CAN_VIEW_POSTS, AppSecurityUserPermissions.CAN_VIEW_PROFILE));
    }

    public Set<AppSecurityUserPermissions> getPermissionsSet(){
        return this.permissionsSet;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.permissionsSet.stream().map(v -> new SimpleGrantedAuthority(v.getRole()))
                .collect(Collectors.toList());
    }
}