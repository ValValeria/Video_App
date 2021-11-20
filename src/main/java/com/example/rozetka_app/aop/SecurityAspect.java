package com.example.rozetka_app.aop;

import com.example.rozetka_app.annotations.SecurityPermissionsContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.rozetka_app.models.Comment;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.security.AppSecurityUserRolesList;

@Aspect
@Component
public class SecurityAspect {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final UserRepository userRepository;
    private final int ENTITY_COUNT_ALLOWED = 50;

    @Autowired
    SecurityAspect(
            HttpServletRequest request,
            HttpServletResponse response,
            UserRepository userRepository
    ) {
        this.request = request;
        this.userRepository = userRepository;
        this.response = response;
    }

    @Around("@annotation(com.example.rozetka_app.annotations.SecurityPermissionsContext)")
    private Object handlePermissionCheck(ProceedingJoinPoint joinPoint) throws Throwable
    {
        SecurityPermissionsContext securityPermissionsContext = joinPoint.getThis()
                .getClass().getAnnotation(SecurityPermissionsContext.class);
        String permission = securityPermissionsContext.permission();
        Class<?> clazz = securityPermissionsContext.className();
        boolean isAllowed = false;

        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            Object object = joinPoint.getArgs()[i];

            if(object.getClass().isAssignableFrom(clazz)){
                isAllowed = checkPermission(object, permission);
                break;
            }
        }

        if(isAllowed){
            return joinPoint.proceed();
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        return null;
    }

    /**
     * Check if the authenticated user has the specified permission
     * @param o
     * @param permission
     * @return
     */
    private boolean checkPermission(Object o, String permission) {
        boolean isPermissionGranted = false;
        final Collection<String> authoritiesCollection = getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        final User authUser = this.userRepository.findByUsername(getAuthentication().getName());

        if (o instanceof Comment) {
            Comment comment = (Comment) o;

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
                case AppSecurityUserRolesList.CAN_ADD_LIKES:
                    String canAddLikes = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_ADD_LIKES);

                    if(authoritiesCollection.contains(canAddLikes)){
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
        }

        if (o instanceof Video) {
            Video video = (Video) o;

            switch(permission){
                case AppSecurityUserRolesList.CAN_CREATE_POSTS:
                    break;
                case AppSecurityUserRolesList.CAN_DELETE_POSTS:
                    String canDeletePosts = AppSecurityUserRolesList.getRoleWithPrefix(AppSecurityUserRolesList.CAN_DELETE_POSTS);

                    if(authoritiesCollection.contains(canDeletePosts)) {
                        isPermissionGranted = true;
                    }

                    break;
            }
        }

        return isPermissionGranted;
    }

    public static Authentication getAuthentication(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }
}
