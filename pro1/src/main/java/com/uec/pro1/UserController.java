package com.uec.pro1;

import com.alibaba.fastjson.JSONArray;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;


@RestController
@RequestMapping("/user")
public class UserController {

    static Logger logger = Logger.getLogger("logger");

    @Autowired
    UserMapper userMapper;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient discoveryClient;

    @GetMapping("/select")
    UserPo select(HttpServletRequest httpRequest, @RequestParam String username) {
        logger.info("/select");
        return userMapper.select(username);
    }

    @GetMapping("/select/test")
    UserPo selectTest(HttpServletRequest httpRequest, @RequestParam String username) {
        logger.info("/select/test");
        return userMapper.select(username);
    }


    @GetMapping("/check")
    String check() {
        JSONArray ja = new JSONArray();
        for (Object o : discoveryClient.getApplications().getRegisteredApplications()) {
            ja.add(o.toString());
        }
        return ja.toJSONString();
    }
}
