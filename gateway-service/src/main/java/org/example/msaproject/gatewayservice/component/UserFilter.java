package org.example.msaproject.gatewayservice.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.msaproject.gatewayservice.token.TokenStorage;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserFilter extends AbstractGatewayFilterFactory<UserFilter.Config> {
    private final TokenStorage tokenStorage;
    UserFilter(TokenStorage tokenStorage) {
        super(Config.class);
        this.tokenStorage = tokenStorage;
    }


    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            System.out.println("pre local filter 1");

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        String token = exchange.getResponse().getHeaders().getFirst("Authorization");

                        if (token != null) {
                            String accessToken = token.startsWith("Bearer ") ? token.substring(7) : token;
                            tokenStorage.setAccessToken(accessToken);
                            System.out.println("Token: " + token);
                            System.out.println("post local filter 1");
                        }
                    }));
        };
    }

    @Data
    public static class Config {
    }
}
