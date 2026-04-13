package com.example.auth_service.security;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Set;

@Getter
public class CustomPasswordGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final String username;
    private final String password;

    public CustomPasswordGrantAuthenticationToken(
            String username,
            String password,
            Authentication clientPrincipal,
            Set<String> scopes
    ) {
        super(new AuthorizationGrantType("password"), clientPrincipal, null);
        this.username = username;
        this.password = password;
    }
}
