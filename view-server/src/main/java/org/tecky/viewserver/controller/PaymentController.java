package org.tecky.viewserver.controller;

import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
public class PaymentController {

    @GetMapping("/api/payment")
    public String payment(@RequestParam Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

//        String invoice_id = map.get("invoice_id");
//
//        URI callback = URI.create("http://47.92.137.0:9001/api/payment/callback?invoice_id=" + invoice_id);
//
//        Optional<Cookie> authToken = Arrays.stream(request.getCookies()).filter(element -> element.getName().equals("Authentication")).findFirst();
//
//        String authAuth;
////        if(authToken.isPresent()){
////
////            authAuth = authToken.get().getValue();
////        } else{
////
////            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
////        }
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.add("Authorization", authAuth);
//
//
//        HttpEntity httpEntity = new HttpEntity(headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<?> responseEntityFromAPI = restTemplate.exchange(callback, HttpMethod.GET, httpEntity, String.class);
//


        return "S";
    }
}
