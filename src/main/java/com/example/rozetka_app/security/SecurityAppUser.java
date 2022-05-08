package com.example.rozetka_app.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityAppUser implements UserDetails {
    private final String password;
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;
    private String role;

    public SecurityAppUser(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;

        this.init();
    }

    private void init() {
        if (this.authorities.containsAll(AppSecurityUserRoles.ADMIN.getAuthorities())) {
            this.role = "admin";
        } else {
            this.role = "user";
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRole() {
        return role;
    }
}
