package com.example.rozetka_app.security;

import com.example.rozetka_app.models.User;
import com.example.rozetka_app.repositories.UserRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private final UserRepository userRepository;

    public CustomPermissionEvaluator(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        return false;
    }

    /**
     * Alternative method for evaluating a permission where only
     * the identifier of the target object is available, rather than the target instance itself.
     * @param authentication
     * @param serializable
     * @param s String representing the target's type (usually a Java classname). Not null
     * @param o a representation of the permission object as supplied by the expression system. Not null.
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        Long id = Long.valueOf(serializable.toString());
        Collection<? extends GrantedAuthority> grantedAuthorityCollection = authentication.getAuthorities();
        List<String> allPermissionsSet = EnumSet.allOf(AppSecurityUserPermissions.class)
                                                .stream()
                                                .map(v -> v.getRole()).collect(Collectors.toList());
        String key = "ROLE_" + o;

        try {
            Class<?> clazz = Class.forName(s);

            if(clazz.isAssignableFrom(User.class)){
               Optional<User> optionalUser = userRepository.findById(id);

               if(optionalUser.isEmpty()) return false;

               User user = optionalUser.get();

               if(grantedAuthorityCollection.contains(key) || Objects.equals(user.getId(), id)){
                   return true;
               }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
