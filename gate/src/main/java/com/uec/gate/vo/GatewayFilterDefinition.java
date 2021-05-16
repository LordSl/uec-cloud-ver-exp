package com.uec.gate.vo;

import java.util.LinkedHashMap;
import java.util.Map;

//过滤器模型
public class GatewayFilterDefinition {
    //Filter Name
    public String name;
    //对应的路由规则
    public Map<String, String> args = new LinkedHashMap<>();
    //此处省略Get和Set方法
}
