package com.project.housingservice.Config;

import com.project.housingservice.Auth.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter) {

        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//               // .authorizeHttpRequests(auth -> auth
//                       // .antMatchers("/eureka/**").permitAll()
//                       // .anyRequest().authenticated())
//                //.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtRequestFilter, FilterSecurityInterceptor.class);
        //http.csrf().disable()
//                http.csrf(csrf -> csrf.disable())
//               .authorizeHttpRequests(auth -> auth
//                       .anyRequest().permitAll());
        http.csrf().disable().addFilterBefore(jwtRequestFilter, FilterSecurityInterceptor.class);
        return http.build();
    }


}
