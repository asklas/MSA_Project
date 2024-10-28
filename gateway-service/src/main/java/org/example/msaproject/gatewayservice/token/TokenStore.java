package org.example.msaproject.gatewayservice.token;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenStore {
    private final Map<String, String> tokenStore = new HashMap<>();

    public void saveToken(String username, String token) {
        tokenStore.put(username, token);
    }

    public String getToken(String username) {
        return tokenStore.get(username);
    }

    public void removeToken(String username) {
        tokenStore.remove(username);
    }
}
