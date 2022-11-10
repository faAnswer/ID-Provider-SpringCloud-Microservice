package org.tecky.viewserver.controller;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello(){


        return "hello";
    }
    @PostMapping(value = "/api/user/login", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public ResponseEntity<?> login(@RequestParam Map<String, String> userInfo, HttpServletRequest request, HttpServletResponse response){

        String uri = "http://localhost:9001/api/auth/login";

        RestTemplate restTemplate = new RestTemplate();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username", userInfo.get("username"));
        jsonObject.put("password", userInfo.get("password"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request2 = new HttpEntity<>(jsonObject.toString(), headers);

        //String results = restTemplate.getForObject(uri2, String.class);
        ResponseEntity<?> result = restTemplate.postForEntity(uri, request2, String.class);

        return result;
    }
}
