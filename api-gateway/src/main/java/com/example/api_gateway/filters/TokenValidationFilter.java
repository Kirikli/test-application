package com.example.api_gateway.filters;

import com.example.api_gateway.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenValidationFilter implements GatewayFilter, Ordered {

    @Value("${keycloak.userinfo-uri}")
    private String userInfoUri;

    private final WebClient webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        return webClient.get()
                .uri(userInfoUri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(UserInfoResponse.class)
                .flatMap(userInfo -> {
                    exchange.getAttributes().put("userInfo", userInfo);
                    return chain.filter(exchange);
                })
                .onErrorResume(e -> {
                    log.error("KeyCloak error: {}", e.getMessage(), e);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
