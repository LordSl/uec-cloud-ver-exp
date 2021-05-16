package com.uec.ws.config;

import com.uec.ws.util.GlobalJedis;
import com.uec.ws.util.GlobalLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {
    @Autowired
    GlobalJedis jedis;
    @Autowired
    GlobalLogger logger;

    @Bean
    public void init() {
        logger.log("config finish");
    }
}
