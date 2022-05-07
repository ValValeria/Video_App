package com.example.rozetka_app.services;

import java.util.ArrayList;
import java.util.Collection;

import com.example.rozetka_app.security.SecurityAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.security.AppSecurityUserRoles;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
       this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser user = userRepository.findAppUserByUsername(s);
        Collection<GrantedAuthority> collection = new ArrayList<>();

        if(user == null){
            throw new UsernameNotFoundException("Username is not found");
        }

        if(user.getRole().equals("admin")){
            collection.addAll(AppSecurityUserRoles.ADMIN.getAuthorities());
        } else if(user.getRole().equals("user")){
            collection.addAll(AppSecurityUserRoles.READER.getAuthorities());
        }

        return new SecurityAppUser(
                user.getUsername(),
                user.getPassword(),
                collection
        );
    }
}
