package com.project.apigateway;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtValidationFilter implements GlobalFilter {
    private final JwtUtil jwtUtil;

    public JwtValidationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!exchange.getRequest().getHeaders().containsKey("Authorization")) {
            // invalid Jwt, rejected
            return unauthorizedResponse(exchange);
        }

        String jwtToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (jwtToken == null && !jwtToken.startsWith("Bearer ")) {
            // invalid Jwt, rejected
            return unauthorizedResponse(exchange);
        }
        jwtToken = jwtToken.substring(7);
        try {
            Claims claims = jwtUtil.extractClaims(jwtToken);
        } catch (Exception e) {
            return unauthorizedResponse(exchange);
        }

        if (jwtToken == null || !validateJwt(jwtToken)) {
            // invalid Jwt, rejected
            return unauthorizedResponse(exchange);
        }

        // extract roles from jwt
        List<String> roles = jwtUtil.extractRolesFromJwt(jwtToken);
        String rolesString = String.join(",", roles);
        String email = jwtUtil.extractUsername(jwtToken);
        // add roles to the request
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-User-Roles", rolesString)
                .header("X-User-Email", email)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private boolean validateJwt(String jwtToken) {
        String username = jwtUtil.extractUsername(jwtToken);
        if (username != null && !jwtUtil.isTokenExpired(jwtToken)) {
            return true;
        }
        return false;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap("Unauthorized: Invalid Credentials".getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }


}
