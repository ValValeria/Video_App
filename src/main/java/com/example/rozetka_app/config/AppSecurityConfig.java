package com.example.rozetka_app.config;

import com.example.rozetka_app.security.AppSecurityUserRoles;
import com.example.rozetka_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private final String[] paths = new String[]{"/static/*", "/"};
    private final String[] adminPaths = new String[]{"/management/*"};
    private final UserService userService;

    @Autowired
    public AppSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return this.userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(paths)
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(adminPaths).hasAnyRole(AppSecurityUserRoles.ADMIN.name())
            .antMatchers(HttpMethod.DELETE, "/videos/*").hasAnyRole(AppSecurityUserRoles.ADMIN.name())
            .antMatchers(HttpMethod.PUT, "/videos/*").hasAnyRole(AppSecurityUserRoles.ADMIN.name())
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/create-ad")
            .and()
            .logout()
            .invalidateHttpSession(true)
            .logoutSuccessUrl("/");
        ;
    }
}
