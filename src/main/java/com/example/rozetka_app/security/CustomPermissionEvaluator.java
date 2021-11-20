package com.example.rozetka_app.security;

import com.example.rozetka_app.models.Comment;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;
    private final int ENTITY_COUNT_ALLOWED = 50;

    @Autowired
    public CustomPermissionEvaluator(
            UserRepository userRepository,
            HttpServletRequest servletRequest
    ){
        this.userRepository = userRepository;
        this.httpServletRequest = servletRequest;
    }

    /**
     *
     * @param authentication
     * @param o the domain object for which permissions should be checked.
     * @param o1 a representation of the permission object as supplied by the expression system. Not null.
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        boolean isPermissionGranted = false;

        if (o != null) {
            Collection<? extends GrantedAuthority> authoritiesCollection = authentication.getAuthorities();
            User authUser = this.userRepository.findByUsername(authentication.getName());

            if (o instanceof Comment) {
                Comment comment = (Comment) o;
                String permission = (String) o1;

                switch (permission) {
                    case AppSecurityUserRolesList.CAN_DELETE_COMMENTS:
                        String role = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_DELETE_OWN_COMMENT);
                        String adminRole = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_DELETE_COMMENTS);

                        if (Objects.equals(authUser.getId(), comment.getUser().getId()) && authoritiesCollection.contains(role)) {
                            isPermissionGranted = true;
                        }

                        if (authoritiesCollection.contains(adminRole)) {
                            isPermissionGranted = true;
                        }

                        break;
                    case AppSecurityUserRolesList.CAN_VIEW_VIDEO_COMMENTS:
                        isPermissionGranted = true;
                        break;
                    case AppSecurityUserRolesList.CAN_VIEW_USER_COMMENTS:
                        String canViewOwnComments = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_VIEW_OWN_COMMENTS);
                        String canViewUserComments = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_VIEW_USER_COMMENTS);

                        if (Objects.equals(authUser.getId(), comment.getUser().getId()) && authoritiesCollection.contains(canViewOwnComments)) {
                            isPermissionGranted = true;
                        }

                        if (authoritiesCollection.contains(canViewUserComments)) {
                            isPermissionGranted = true;
                        }

                        break;
                    case AppSecurityUserRolesList.CAN_CREATE_COMMENT:
                        return authUser.getCommentList().size() < ENTITY_COUNT_ALLOWED;
                }
            }

            if (o instanceof User) {
                User user = (User) o;

                String permission = (String) o1;

                switch (permission) {
                    case AppSecurityUserRolesList.CAN_DELETE_PROFILES:
                        String role = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_DELETE_OWN_COMMENT);
                        String adminRole = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_DELETE_COMMENTS);

                        if (Objects.equals(authUser.getId(), user.getId()) && authoritiesCollection.contains(role)) {
                            isPermissionGranted = true;
                        }

                        if (authoritiesCollection.contains(adminRole)) {
                            isPermissionGranted = true;
                        }

                        break;
                    case AppSecurityUserRolesList.CAN_VIEW_PROFILES:
                        String canViewOwnProfile = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_VIEW_PROFILE);
                        String canViewUserProfile = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_VIEW_PROFILES);

                        if (Objects.equals(authUser.getId(), user.getId()) && authoritiesCollection.contains(canViewOwnProfile)) {
                            isPermissionGranted = true;
                        }

                        if (authoritiesCollection.contains(canViewUserProfile)) {
                            isPermissionGranted = true;
                        }

                        break;
                }

                if (o instanceof Video) {
                    Video video = (Video) o;
                }
            }
        }
        return isPermissionGranted;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable serializable,
                                 String s,
                                 Object o) {
        return false;
    }
}
