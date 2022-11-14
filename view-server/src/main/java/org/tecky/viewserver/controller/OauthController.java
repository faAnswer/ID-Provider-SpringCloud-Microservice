package org.tecky.viewserver.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    @GetMapping("/authorize")
    public ResponseEntity<?> auth(@RequestParam Map<String, String> requestParam HttpServletRequest request, HttpServletResponse response){

        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper mapper = new ObjectMapper();

        mapper.write(requestParam);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);



        HttpEntity<String> request2 = new HttpEntity<>(jsonObject.toString(), headers);

        restTemplate.getForEntity("http://47.92.137.0:9001/api/oauth/authorize",)




    }

}
