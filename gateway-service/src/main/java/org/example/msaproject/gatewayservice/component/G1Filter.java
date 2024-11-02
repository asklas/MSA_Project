package org.example.msaproject.gatewayservice.component;

import lombok.RequiredArgsConstructor;
import org.example.msaproject.gatewayservice.token.TokenStorage;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class G1Filter implements GlobalFilter, Ordered {
    private TokenStorage tokenStorage;
    G1Filter(TokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String accessToken = tokenStorage.getAccessToken();
        if (accessToken != null) {
            exchange.getRequest().mutate()
                    .header("AccessToken", accessToken)
                    .build();
        }

        System.out.println("pre global filter order -1");
        System.out.println(accessToken);

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> System.out.println("post global filter order -1")));
    }

    @Override
    public int getOrder() {

        return -1;
    }
}
