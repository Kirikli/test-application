package com.example.auth_service.keycloak;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String url;
    private String realm;
    private Admin admin;

    @Data
    public static class Admin {
        private String username;
        private String password;
    }
}
