package com.uec.ws1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Ws1App {

    public static void main(String[] args) {
        SpringApplication.run(Ws1App.class, args);
    }

}
