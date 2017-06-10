package com.springframework.cinema.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * Created by Patryk on 2017-05-01.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/", "/favicon.ico").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/employee/**").hasRole("EMPLOYEE")
                    .antMatchers("/users/**","/account/**").hasRole("USER")
                    .antMatchers("/login","/register").hasRole("ANONYMOUS")
                    .anyRequest().denyAll()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .and()
                .logout()
                	.permitAll()
                	.and()
                .httpBasic();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
        auth
        	.userDetailsService(userDetailsService)
        	.passwordEncoder(new BCryptPasswordEncoder());
    }
}
