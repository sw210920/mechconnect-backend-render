package com.mechconnect.backend.config;

/**
 * SecurityConfig
 *
 * Part of the MechConnect backend application.
 * Responsible for handling config related logic.
 */


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
 

   

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // ✅ ENABLE CORS
                .authorizeHttpRequests(auth -> auth
                
                    .requestMatchers("/api/**",
                                     "/ping",
                    		"/api/sendRequest",
                    		 "/api/mechanic/update",
                            "/api/customers/**",
                            "/api/mechanic/**"
                    		).permitAll()
                    .anyRequest().authenticated()
                );

            return http.build();
        }
    }

    
    
