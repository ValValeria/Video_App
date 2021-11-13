package com.example.rozetka_app.security;

public enum AppSecurityUserPermissions {
    CAN_CREATE("can:read"),
    CAN_DELETE("can:delete"),
    CAN_COMMENT("can:write");

    private final String role;

    AppSecurityUserPermissions(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
