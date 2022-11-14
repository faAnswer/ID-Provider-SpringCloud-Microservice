package org.tecky.userresource.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tecky.userresource.services.intf.IUserInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/res/user")
@CrossOrigin("*")
public class ResourceController {

    @Autowired
    IUserInfoService iUserInfoService;

    private final Integer USER_DETAIL_SCOPEID = 1;


    @GetMapping(value = "/profile", produces = "application/json")
    public String getProfile(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws JsonProcessingException {

//        String username = (String) authentication.getCredentials();

        return iUserInfoService.getInfo("Admin");
    }

    @GetMapping("/profile2")
    public Map getProfile2(HttpServletRequest request, HttpServletResponse response){

        Map<String, Object> map = new HashMap<>();

        map.put("ANC", "ANC");

        return map;
    }

}
