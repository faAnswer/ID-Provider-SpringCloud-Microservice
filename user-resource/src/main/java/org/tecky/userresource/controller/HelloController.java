package org.tecky.userresource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tecky.userresource.services.ClientSecretService;

@RestController
public class HelloController {

    @Autowired
    ClientSecretService clientSecretService;


    @GetMapping("/hello")
    public String hello(){


        return "Hello";
    }
}
