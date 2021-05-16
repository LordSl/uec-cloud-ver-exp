package com.uec.gate;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.logging.Logger;

@Component
public class IPLogger implements GlobalFilter, Ordered {

    static Logger logger = Logger.getLogger("logger");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).toString());
        return chain.filter(exchange.mutate().build());
    }

    @Override
    public int getOrder() {
        return -199;
    }
}
