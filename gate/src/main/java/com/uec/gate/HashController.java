package com.uec.gate;

import com.uec.gate.hash.HashCircle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/hash")
public class HashController {

    @Autowired
    HashCircle hashCircle;

    @Autowired
    void addInitEndPoint() throws URISyntaxException {
        hashCircle.addEndPoint(new URI("ws://localhost:8083"));
//        hashCircle.addEndPoint(new URI("http://localhost:8083"));
        //这里填ws或http都无所谓

    }

    @GetMapping("/addEndPoint")
    void add(@RequestParam String uriStr) throws URISyntaxException {
        URI uri = new URI(uriStr);
        hashCircle.addEndPoint(uri);
    }

    @GetMapping("/delEndPoint")
    void del(@RequestParam String uriStr) throws URISyntaxException {
        URI uri = new URI(uriStr);
        hashCircle.delEndPoint(uri);
    }

}
