package com.example.demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsManager userDetailsManager(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("USER").build()
        );
        return manager;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET,"").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin().disable();
        return httpSecurity.build();
    }
}
