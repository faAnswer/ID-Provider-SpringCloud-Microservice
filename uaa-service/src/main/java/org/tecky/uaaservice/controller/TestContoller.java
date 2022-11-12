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
import org.tecky.uaaservice.entities.OauthClientEntity;
import org.tecky.uaaservice.mapper.OauthClientEntityRepository;
import org.tecky.uaaservice.services.FetchClientScrect;

import java.util.Map;

@RestController
public class TestContoller {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    FetchClientScrect fetchClientScrect;


    @Autowired
    OauthClientEntityRepository oauthClientEntityRepository;


    @GetMapping("/test")
    public String gotest() {

        OauthClientEntity oauthClientEntity = new OauthClientEntity();

        oauthClientEntity.setName("TEST");
        oauthClientEntity.setClientid("TESTTTTT");
        oauthClientEntity.setStatus("alive");
        oauthClientEntity.setClientsecret("HGHJKGVBKJVBJK");
        oauthClientEntity.setRedirecturi("GHJFVHJVJKHVJKH");

        oauthClientEntityRepository.saveAndFlush(oauthClientEntity);

        return "test";
    }

    @GetMapping("/fetch")
    public String gotest2() throws Exception {

        fetchClientScrect.fetch();

        return "fetch success";
    }
}
