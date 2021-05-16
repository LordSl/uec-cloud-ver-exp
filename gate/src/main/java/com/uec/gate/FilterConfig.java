package com.uec.gate;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;

import java.util.Objects;
import java.util.logging.Logger;

//@Configuration
//这种集中配置的写法在当前版本无效，似乎是Ordered注释的变化所致
public class FilterConfig {

    static Logger logger = Logger.getLogger("logger");

//    当请求到来时，Filtering Web Handler 处理器会添加所有 GlobalFilter 实例和匹配的 GatewayFilter 实例到过滤器链中。//
//    过滤器链会使用 org.springframework.core.Ordered 注解所指定的顺序，进行排序。
//    Spring Cloud Gateway区分了过滤器逻辑执行的”pre”和”post”阶段，所以优先级高的过滤器将会在pre阶段最先执行，优先级最低的过滤器则在post阶段最后执行。

    @Bean
    public GlobalFilter IpLogger() {
        // java箭头函数的语法糖
        return (exchange, chain) -> {
            logger.info(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).toString());
            return chain.filter(exchange.mutate().build());
        };
    }
}
