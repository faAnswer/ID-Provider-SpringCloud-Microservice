package org.tecky.authservice.nacosdiscovery.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {


    @PostMapping("/test")
    public String gotest(@RequestBody Map<String, String> regInfo){

        return "test";
    }

}
