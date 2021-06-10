package com.uec.gate;

import com.uec.gate.hash.HashCircle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.logging.Logger;

@Component
public class URIRedirecter implements GlobalFilter, Ordered {

    @Autowired
    HashCircle hashCircle;
    static Logger logger = Logger.getLogger("logger");
    private final String GATEWAY_ROUTE = "org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRoute";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(hashCircle.domainInclude(exchange.getRequest().getURI()))
            doRedirect(exchange);

        return chain.filter(exchange.mutate().build());
    }


    //这里有两种思路
    //一，路由给自己(8089->8089)，改变请求中的一些参数或header，再进行二次匹配，进而转发，这本质上依然是静态的，不予采用
    //二，改变route的uri，也就是目前采用的这种

    private void doRedirect(ServerWebExchange exchange) {
        Route route = exchange.getAttribute(GATEWAY_ROUTE);
        if (route != null) {
            logger.info("original uri: " + exchange.getRequest().getURI().toString() + "\n");

            URI newUri = hashCircle.getURI();

            Route newRoute = Route.async().asyncPredicate((route.getPredicate()))
                    .filters(route.getFilters())
                    .id(route.getId())
                    .order(route.getOrder())
                    .uri(newUri)
                    .build();

            exchange.getAttributes().put(GATEWAY_ROUTE, newRoute);
            logger.info("redirect to: " + newUri.toString() + "\n");
        }
    }


    @Override
    public int getOrder() {
        return -198;
    }

}
