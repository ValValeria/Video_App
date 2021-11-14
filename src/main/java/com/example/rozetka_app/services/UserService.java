package com.example.rozetka_app.services;

import com.example.rozetka_app.models.User;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.security.AppSecurityUserPermissions;
import com.example.rozetka_app.security.AppSecurityUserRoles;
import com.example.rozetka_app.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
       this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        Collection<GrantedAuthority> collection = new ArrayList<>();

        if(user == null){
            throw new UsernameNotFoundException("Username is not found");
        }

        if(user.getRole().equals("admin")){
            collection.addAll(AppSecurityUserRoles.ADMIN.getAuthorities());
        } else if(user.getRole().equals("user")){
            collection.addAll(AppSecurityUserRoles.READER.getAuthorities());
        }

        return new AppUser(
                user.getUsername(),
                user.getPassword(),
                collection
        );
    }
}
