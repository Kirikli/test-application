package com.example.auth_service.keycloak;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;
    private final KeycloakProperties properties;

    public void createUser(String email, String password, String name) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(email);
        user.setEmail(email);
        user.setFirstName(name);
        user.setEmailVerified(true);
        user.setEnabled(true);

        Response response = keycloak.realm(properties.getRealm()).users().create(user);
        log.info("Create user status: {}", response.getStatus());

        String userId = response.getLocation().getPath().replaceAll(".*/", "");

        UserResource userResource = keycloak.realm(properties.getRealm())
                .users()
                .get(userId);

        UserRepresentation createdUser = userResource.toRepresentation();
        createdUser.setRequiredActions(Collections.emptyList());
        userResource.update(createdUser);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        userResource.resetPassword(credential);
    }
}

