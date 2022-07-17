package ru.graduation_project_topjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .authorizeRequests()
                .antMatchers( "/user", "/user/**",
                        "/restaurants", "/restaurants/**", "/votes", "/votes/**").access("isAuthenticated()")
                .antMatchers("/admin", "/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/home")
                .and()
                .logout()
                .logoutSuccessUrl("/home")
                .and()
                .build();
    }






}
