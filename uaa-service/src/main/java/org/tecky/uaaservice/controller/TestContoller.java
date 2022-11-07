package org.tecky.uaaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestContoller {

    @Autowired
    UserDetailsService userDetailsService;


    @GetMapping("/test")
    public String gotest(Authentication authentication) {

        //UserDetails user = userDetailsService.loadUserByUsername(regInfo.get("username"));

        return "test";
    }


}
