package com.uec.gate;

import com.uec.gate.vo.GatewayFilterDefinition;
import com.uec.gate.vo.GatewayPredicateDefinition;
import com.uec.gate.vo.GatewayRouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


//@RestController
//@RequestMapping("/route")
//public class DynamicRouteController {
//
//    @Autowired
//    DynamicRouteServiceImpl dynamicRouteService;
//
//    //增加路由
//    @PostMapping("/add")
//    public String add(@RequestBody GatewayRouteDefinition grDefinition) {
//        String flag = "fail";
//        try {
//            RouteDefinition definition = assembleRouteDefinition(grDefinition);
//            flag = this.dynamicRouteService.add(definition);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//
//    //删除路由
//    @GetMapping("/del")
//    public Mono<ResponseEntity<Object>> delete(@RequestParam String id) {
//        try {
//            return this.dynamicRouteService.delete(id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    //更新路由
//    @PostMapping("/update")
//    public String update(@RequestBody GatewayRouteDefinition grDefinition) {
//        RouteDefinition definition = assembleRouteDefinition(grDefinition);
//        return this.dynamicRouteService.update(definition);
//    }
//
//    //把传递进来的参数转换成路由对象
//    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition grDefinition) {
//        RouteDefinition definition = new RouteDefinition();
//        definition.setId(grDefinition.id);
//        definition.setOrder(grDefinition.order);
//
//        //设置断言
//        List<PredicateDefinition> pdList = new ArrayList<>();
//        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList = grDefinition.predicates;
//        for (GatewayPredicateDefinition gpDefinition : gatewayPredicateDefinitionList) {
//            PredicateDefinition predicate = new PredicateDefinition();
//            predicate.setArgs(gpDefinition.args);
//            predicate.setName(gpDefinition.name);
//            pdList.add(predicate);
//        }
//        definition.setPredicates(pdList);
//
//        //设置过滤器
//        List<FilterDefinition> filters = new ArrayList<>();
//        List<GatewayFilterDefinition> gatewayFilters = grDefinition.filters;
//        for (GatewayFilterDefinition filterDefinition : gatewayFilters) {
//            FilterDefinition filter = new FilterDefinition();
//            filter.setName(filterDefinition.name);
//            filter.setArgs(filterDefinition.args);
//            filters.add(filter);
//        }
//        definition.setFilters(filters);
//
//        URI uri;
//        if (grDefinition.uri.startsWith("http")) {
//            uri = UriComponentsBuilder.fromHttpUrl(grDefinition.uri).build().toUri();
//        } else {
//            // uri为 lb://consumer-service 时使用下面的方法
//            uri = URI.create(grDefinition.uri);
//        }
//        definition.setUri(uri);
//        return definition;
//    }
//}

