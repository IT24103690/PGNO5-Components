package com.inventory.inventorymanagement.config;

import com.inventory.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/register", "/users/login", "/css/**").permitAll()
                        .requestMatchers("/users/dashboard").hasAnyRole("ADMIN", "STAFF", "SUPPLIER")  // Allow all roles for dashboard
                        .requestMatchers("/users/**", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/products/**", "/orders/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/orders/supply").hasAnyRole("ADMIN", "SUPPLIER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .defaultSuccessUrl("/users/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessUrl("/users/login")
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            try {
                com.inventory.inventorymanagement.model.User user = userService.getUserByName(username);
                if (user == null) {
                    throw new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found: " + username);
                }
                return new org.springframework.security.core.userdetails.User(
                        user.getName(),
                        user.getPassword(),
                        java.util.Collections.singletonList(
                                new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase())
                        )
                );
            } catch (Exception e) {
                throw new org.springframework.security.core.userdetails.UsernameNotFoundException("Error loading user: " + e.getMessage());
            }
        };
    }
}