package com.uec.gate.vo;

import java.util.LinkedHashMap;
import java.util.Map;

//断言模型
public class GatewayPredicateDefinition {
    //断言对应的Name
    public String name;
    //配置的断言规则
    public Map<String, String> args = new LinkedHashMap<>();
    //此处省略Get和Set方法
}
