package com.uec.con1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserConController {
    private final String gateUrl = "http://localhost:8082";
    @Autowired
    private RestTemplate restTemplate;
    Logger logger = Logger.getLogger("logger");

    @GetMapping("/select")
    public UserVo select(@RequestParam String username) {
        logger.info("/select");
        return restTemplate.getForObject(gateUrl + "/user/select?username={1}", UserVo.class, username);
        //注意一下这里的占位符{1}，它和后面的可变参数一一对应
    }

}
