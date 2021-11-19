package com.example.rozetka_app.security;

public enum AppSecurityUserPermissions {
    CAN_CREATE_POSTS(AppSecurityUserRolesList.CAN_CREATE_POSTS),
    CAN_DELETE_POSTS(AppSecurityUserRolesList.CAN_DELETE_POSTS),
    CAN_VIEW_POSTS(AppSecurityUserRolesList.CAN_VIEW_POSTS),
    CAN_VIEW_PROFILES(AppSecurityUserRolesList.CAN_VIEW_PROFILES),
    CAN_VIEW_PROFILE(AppSecurityUserRolesList.CAN_VIEW_PROFILE),
    CAN_CREATE_COMMENT(AppSecurityUserRolesList.CAN_CREATE_COMMENT),
    CAN_DELETE_OWN_COMMENT(AppSecurityUserRolesList.CAN_DELETE_OWN_COMMENT),
    CAN_DELETE_COMMENTS(AppSecurityUserRolesList.CAN_DELETE_COMMENTS);

    private final String role;

    AppSecurityUserPermissions(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
