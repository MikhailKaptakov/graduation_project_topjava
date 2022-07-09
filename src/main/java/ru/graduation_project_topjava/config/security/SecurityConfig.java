package ru.graduation_project_topjava.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .authorizeRequests()
                .antMatchers( "/user").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("email")
                .and()
                .build();
    }




}
