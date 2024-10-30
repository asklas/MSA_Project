package org.example.msaproject.gatewayservice.token;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TokenStorage {
    private String accessToken;
}
