package com.example.rozetka_app.security;

public interface AppSecurityUserRolesList {
    String CAN_CREATE_POSTS = "can:create_posts";
    String CAN_DELETE_POSTS = "can:delete_posts";
    String CAN_VIEW_POSTS = "can:view_posts";
    String CAN_VIEW_PROFILES = "can:view_profiles";
    String CAN_VIEW_PROFILE = "can:view_profile";
    String CAN_DELETE_PROFILE = "can:delete_profile";
    String CAN_DELETE_PROFILES = "can:delete_profiles";
    String CAN_VIEW_OWN_COMMENTS = "can:view_own_comments";
    String CAN_VIEW_USER_COMMENTS = "can:view_user_comments";
    String CAN_VIEW_VIDEO_COMMENTS = "can:view_video_comments";
    String CAN_CREATE_COMMENT = "can:create_comments";
    String CAN_DELETE_OWN_COMMENT = "can:delete_own_video_comment";
    String CAN_DELETE_COMMENTS = "can:delete_video_comments";

    static String getRoleWithPrefix(String role) {
        return "ROLE_" + role;
    }
}
