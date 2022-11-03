package org.tecky.uaaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestContoller {

    @Autowired
    UserDetailsService userDetailsService;


    @PostMapping("/test")
    public String gotest(@RequestBody Map<String, String> regInfo){

        UserDetails user = userDetailsService.loadUserByUsername(regInfo.get("username"));

        return "test";
    }


}
