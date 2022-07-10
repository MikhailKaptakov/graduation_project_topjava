package ru.graduation_project_topjava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.graduation_project_topjava.service.UserService;

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
                .antMatchers( "/user", "/user/**").access("isAuthenticated()")
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
