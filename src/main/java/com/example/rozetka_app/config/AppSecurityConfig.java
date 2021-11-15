package com.example.rozetka_app.config;

import com.example.rozetka_app.security.AppSecurityUserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final String[] paths = new String[]{"/static/*", "/"};
    private final String[] adminPaths = new String[]{"/management/*"};

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http
                .csrf()
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
