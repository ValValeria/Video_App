package com.example.rozetka_app.security;

public enum AppSecurityUserPermissions {
    CAN_CREATE_POSTS("can:read_posts"),
    CAN_DELETE_POSTS("can:delete_posts"),
    CAN_VIEW_POSTS("can:view_posts"),
    CAN_VIEW_PROFILES("can:view_profiles"),
    CAN_VIEW_PROFILE("can:view_profile"),
    CAN_COMMENT("can:write");

    private final String role;

    AppSecurityUserPermissions(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
