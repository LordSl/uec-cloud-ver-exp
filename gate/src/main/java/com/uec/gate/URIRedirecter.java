package com.uec.gate;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
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
    private final String GATEWAY_ROUTE = "org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRoute";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest req = exchange.getRequest();

        if (req.getPath().toString().contains("/test")) return chain.filter(exchange.mutate().build());

        String toLog = "\n";

        toLog += "uri: " + req.getURI().toString() + "\n";

        URI newUri = getNewUri(req.getURI());

        Route route = exchange.getAttribute(GATEWAY_ROUTE);

        if (route != null) {
            Route newRoute = Route.async().asyncPredicate((route.getPredicate()))
                    .filters(route.getFilters())
                    .id(route.getId())
                    .order(route.getOrder())
                    .uri(newUri)
                    .build();

            exchange.getAttributes().put(GATEWAY_ROUTE, newRoute);
        }

        //其实在这里更新req没什么用，因为并不会根据它来进行路由
        req = req.mutate().uri(newUri).build();

        toLog += "redirect to: " + req.getURI().toString() + "\n";
        logger.info(toLog);

        return chain.filter(exchange.mutate().request(req).build());
    }


    //这里有两种思路
    //一，路由给自己(8089->8089)，改变请求中的一些参数或header，再进行二次匹配，进而转发，这本质上依然是静态的，不予采用
    //二，改变route的uri，也就是目前采用的这种

    private URI getNewUri(URI uri) {
        //在这里配置具体路由规则
        if (!uri.getScheme().equals("http")) return uri;
//        String newUriStr = uri.getScheme()+"://"+"localhost:8082"+uri.getPath()+"/test"+uri.getRawQuery();
        String newUriStr = uri.getScheme() + "://" + "localhost:8082" + uri.getPath() + "?" + uri.getQuery();
        URI newUri = null;
        try {
            newUri = new URI(newUriStr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return newUri;
    }

    @Override
    public int getOrder() {
        return -198;
    }


}
