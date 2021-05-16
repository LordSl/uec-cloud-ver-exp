package com.uec.gate.vo;

import java.util.ArrayList;
import java.util.List;

//路由模型
public class GatewayRouteDefinition {
    //路由的Id
    public String id;
    //路由断言集合配置
    public List<GatewayPredicateDefinition> predicates = new ArrayList<>();
    //路由过滤器集合配置
    public List<GatewayFilterDefinition> filters = new ArrayList<>();
    //路由规则转发的目标uri
    public String uri;
    //路由执行的顺序
    public int order = 0;
    //此处省略get和set方法
}
