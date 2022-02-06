package com.example.rozetka_app.aop;

import com.example.rozetka_app.annotations.SecurityPermissionsContext;
import com.example.rozetka_app.repositories.CommentRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.security.AppSecurityUserRoles;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.rozetka_app.models.Comment;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.security.AppSecurityUserRolesList;

@Aspect
@Component
public class SecurityAspect {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final CommentRepository commentRepository;
    private boolean isPermissionGranted = false;
    private String permission;
    private Class<?> clazz;
    private List<String> authoritiesCollection;
    private final List<String> allowedPermissionsWithoutEntity = new ArrayList<>();
    private Collection<? extends GrantedAuthority> authoritiesGranted;

    @Autowired
    SecurityAspect(
            HttpServletRequest request,
            HttpServletResponse response,
            UserRepository userRepository,
            VideoRepository videoRepository,
            CommentRepository commentRepository
    ) {
        this.request = request;
        this.userRepository = userRepository;
        this.response = response;
        this.videoRepository = videoRepository;
        this.commentRepository = commentRepository;

        allowedPermissionsWithoutEntity.addAll(
                List.of(
                        AppSecurityUserRolesList.CAN_VIEW_VIDEO_COMMENTS,
                        AppSecurityUserRolesList.CAN_CREATE_COMMENT,
                        AppSecurityUserRolesList.CAN_ADD_LIKES
                )
        );
    }

    @Pointcut("@annotation(com.example.rozetka_app.annotations.SecurityPermissionsContext)")
    private void pointcut() {}

    private void setUpData(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SecurityPermissionsContext securityPermissionsContext = method.getAnnotation(SecurityPermissionsContext.class);
        this.permission = securityPermissionsContext.permission();
        this.clazz = securityPermissionsContext.className();

        this.authoritiesGranted = getAuthentication().getAuthorities();
        this.authoritiesCollection = this.authoritiesGranted
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        if(request.getUserPrincipal() == null) {
            this.response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            this.response.sendRedirect("/login");
        }
    }

    @Around("pointcut() && args(entityId, ..)")
    private Object handlePermissionCheckById(ProceedingJoinPoint joinPoint, Long entityId) throws Throwable {
        this.setUpData(joinPoint);

        Object retVal = null;
        List<Class<? extends Object>> classList = List.of(AppUser.class, Video.class, Comment.class);

        if(classList.contains(this.clazz)){
            Object object = null;

            if(this.clazz.isAssignableFrom(AppUser.class)){
                object = this.userRepository.findUserById(entityId);
            }
            if(this.clazz.isAssignableFrom(Video.class)){
                object = this.videoRepository.findVideoById(entityId);
            }
            if(this.clazz.isAssignableFrom(AppUser.class)){
                object = this.commentRepository.findById(entityId);
            }

            if(object != null) {
                this.isPermissionGranted = this.checkPermission(object, this.permission);
            }
        }

        if(this.isPermissionGranted){
            retVal = joinPoint.proceed();
            this.isPermissionGranted = false;
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return retVal;
    }

    @Around("pointcut() &amp;&amp; args(Long entityId, ..)")
    private Object handlePermissionCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        this.setUpData(joinPoint);

        Object retVal = null;
        String perm = AppSecurityUserRolesList.getRoleWithPrefix(permission);
        List<Class<?>> classList = List.of(AppUser.class, Video.class, Comment.class);

        if (classList.contains(this.clazz)){
            if((authoritiesCollection.contains(perm)
                    && allowedPermissionsWithoutEntity.contains(perm))
                    || authoritiesGranted.containsAll(AppSecurityUserRoles.ADMIN.getAuthorities())
            ){
                this.isPermissionGranted = true;
            }
        }

        if(this.isPermissionGranted){
            retVal = joinPoint.proceed();

            this.isPermissionGranted = false;
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return retVal;
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
        final AppUser authUser = this.userRepository.findByUsername(getAuthentication().getName());

        if (o instanceof Comment) {
            Comment comment = (Comment) o;

            int ENTITY_COUNT_ALLOWED = 50;
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
                    return authUser.getComments().size() < ENTITY_COUNT_ALLOWED;
            }
        }

        if (o instanceof AppUser) {
            AppUser user = (AppUser) o;

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