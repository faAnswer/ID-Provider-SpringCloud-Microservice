package org.tecky.viewserver.controller;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.tecky.viewserver.security.config.impl.RedirectRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    RedirectRequestCache redirectRequestCache;

    @GetMapping("/hello")
    public String hello(){


        return "hello";
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @PostMapping(value = "/api/user/login", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public ResponseEntity<?> login(@RequestParam Map<String, String> userInfo, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = "http://47.92.137.0:9001/api/auth/login";

        RestTemplate restTemplate = new RestTemplate();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username", userInfo.get("username"));
        jsonObject.put("password", userInfo.get("password"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request2 = new HttpEntity<>(jsonObject.toString(), headers);

        //String results = restTemplate.getForObject(uri2, String.class);
        ResponseEntity<?> result = restTemplate.postForEntity(uri, request2, String.class);


        // Get target URL
        List<DefaultSavedRequest> savedRequestList = (List<DefaultSavedRequest>) request.getSession().getAttribute(RedirectRequestCache.SAVED_REQUEST);

        if(savedRequestList.get(0).getRequestURI().equals("/login")){

            redirectRequestCache.removeSavedRequest();
            response.addHeader("redirect_uri", "/index");

        } else {

            String tarRequest = savedRequestList.get(0).getRedirectUrl();
            redirectRequestCache.removeSavedRequest();
            response.addHeader("redirect_uri", tarRequest);
        }

        // Redirect
        return result;
    }
}
