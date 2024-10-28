package org.example.msaproject.gatewayservice.service;

import org.example.msaproject.gatewayservice.token.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenManagementService {
    @Autowired
    private TokenStore tokenStore;

    public void saveToken(String username, String token) {
        tokenStore.saveToken(username, token);
    }

    public String getToken(String username) {
        return tokenStore.getToken(username);
    }

    public void removeToken(String username) {
        tokenStore.removeToken(username);
    }
}
