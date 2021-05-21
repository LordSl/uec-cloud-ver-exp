package com.uec.gate;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

@Component
public class URIRedirecter implements GlobalFilter, Ordered {

    static Logger logger = Logger.getLogger("logger");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();

        String toLog = "\n";

        toLog += "uri: "+req.getURI().toString() +"\n";

        URI uri = req.getURI();
        String newUriStr = uri.getScheme()+"://"+uri.getAuthority()+uri.getPath()+"/test"+"?"+uri.getQuery();

        try {
            uri = new URI(newUriStr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        req = req.mutate().uri(uri).build();

        toLog += "redirect to: "+req.getURI().toString() +"\n";
        logger.info(toLog);

        return chain.filter(exchange.mutate().request(req).build());
    }

    @Override
    public int getOrder() {
        return -198;
    }
}
