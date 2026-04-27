package com.example.api_gateway.configuration;

import com.example.api_gateway.filters.HeaderAddFilter;
import com.example.api_gateway.filters.TokenValidationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final TokenValidationFilter tokenValidationFilter;
    private final HeaderAddFilter headerAddFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .uri("http://localhost:8081")
                )
                .route("app-service", r -> r
                        .path("/api/tasks/**")
                        .filters(f -> f
                                .filter(tokenValidationFilter)
                                .filter(headerAddFilter))
                        .uri("http://localhost:8080")
                )
                .build();
    }
}
