package com.example.api_gateway.filters;

import com.example.api_gateway.dto.UserInfoResponse;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class HeaderAddFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        UserInfoResponse userInfo = exchange.getAttribute("userInfo");

        if (userInfo == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String roles = Optional.ofNullable(userInfo.getRealmAccess())
                .map(UserInfoResponse.RealmAccess::getRoles)
                .map(r -> String.join(",", r))
                .orElse("");

        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("user-id", userInfo.getSub())
                .header("user-roles", roles)
                .headers(headers -> headers.remove("Authorization"))
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}