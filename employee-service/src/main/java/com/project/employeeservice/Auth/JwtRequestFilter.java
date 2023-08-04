package com.project.employeeservice.Auth;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.extractToken(request);
        if (StringUtils.hasText(token)) {
            try {
                String username = jwtUtil.extractUsername(token);

                if (username != null && !jwtUtil.isTokenExpired(token)) {
                    request.setAttribute("email", username);

                }
            } catch (Exception e) {
                // Token is invalid or expired
                logger.error(e.getMessage());
                // Return an error response
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getMessage());

                return;

            }
        }
        filterChain.doFilter(request, response);
    }


}
