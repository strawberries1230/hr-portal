package com.proj.authservice.Config;

import com.proj.authservice.Auth.JwtRequestFilter;
import com.proj.authservice.Auth.UserDetailsServiceImpl;
import com.proj.authservice.Exception.InvalidCredentialsException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/role/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws InvalidCredentialsException {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider() {
            @Override
            protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws InvalidCredentialsException {
                try {
                    super.additionalAuthenticationChecks(userDetails, authentication);
                } catch (BadCredentialsException e) {
                    throw new InvalidCredentialsException("Invalid credentials provided. Please try again later.");
                }
            }
        };
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

}
