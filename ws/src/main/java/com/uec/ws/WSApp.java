package com.uec.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WSApp {

    public static void main(String[] args) {
        SpringApplication.run(WSApp.class, args);
    }

}
