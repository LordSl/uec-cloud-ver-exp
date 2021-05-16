package com.uec.gate;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    //手动配置方式
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        // routes.route("host_route",r->r.path("/tell/{words}","/shout/{words}").uri("http://localhost:8080")).build();
        // localhost:8089/tell/{words} -> localhost:8080/tell/{words}
        // localhost:8089/shout/{words} -> localhost:8080/shout/{words}
        //也可以通过yml注入
        routes.route("host_route",r->r.path("/chat/{roomName}/{userName}").uri("ws://localhost:8080")).build();

        return routes.build();
    }
}
