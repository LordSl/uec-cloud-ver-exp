package com.uec.pro1;

import com.alibaba.fastjson.JSONArray;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient discoveryClient;

    @GetMapping("/select")
    UserPo select(@RequestParam String username){
        return userMapper.select(username);
    }

    @GetMapping("/check")
    String check(){
        JSONArray ja = new JSONArray();
        for(Object o: discoveryClient.getApplications().getRegisteredApplications()){
            ja.add(o.toString());
        }
        return ja.toJSONString();
    }
}
