package com.example.api_gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoResponse {
    private String sub;
    private String email;
    @JsonProperty("realm_access")
    private RealmAccess realmAccess;

    @Data
    public static class RealmAccess {
        private List<String> roles;
    }
}
