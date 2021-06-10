package com.uec.pro1;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/select")
    UserPo select(HttpServletRequest httpRequest, @RequestParam String username) {
        logger.info("/select");
        return userMapper.select(username);
    }
}
