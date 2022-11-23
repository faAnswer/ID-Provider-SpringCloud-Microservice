package org.tecky.viewserver.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.faAnswer.web.util.Url2ParamMap;
import org.springframework.beans.BeanUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/api/oauth")
public class OauthController {


    @GetMapping("/authorize")
    public ResponseEntity<?> auth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String referer = request.getHeader("referer");
        referer = URLDecoder.decode(referer, "UTF-8");

        Map<String, String> paraMap = Url2ParamMap.getMap(referer);

        String refererPara = Url2ParamMap.getUrlPara(paraMap);

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.COOKIE, request.getHeader(HttpHeaders.COOKIE));
        HttpEntity<String> request2UAA = new HttpEntity<>(mapper.writeValueAsString(paraMap), headers);

        //Why exchange?
        ResponseEntity<?> responseEntityFromUAA = restTemplate.exchange("http://47.92.137.0:9001/api/oauth/authorize" + refererPara ,HttpMethod.GET, request2UAA, String.class);

        //System.out.println(responseEntityFromUAA.getBody());


        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> mapRes = objectMapper.readValue(responseEntityFromUAA.getBody().toString(), new TypeReference<>() {});

        String token = mapRes.get("access");



        responseEntityFromUAA.getHeaders().get((HttpHeaders.SET_COOKIE));

        HttpHeaders header2Client = new HttpHeaders();

        header2Client.addAll(HttpHeaders.SET_COOKIE, responseEntityFromUAA.getHeaders().get((HttpHeaders.SET_COOKIE)));

        String redirect_uri = paraMap.get("redirect_uri");
        URI uri = URI.create(redirect_uri +"?AccessToken=" + token);
        header2Client.setLocation(uri);


//        Map<String, Object> res = new HashMap<>();
//
//        res.put("AccessToken", responseEntityFromUAA.getHeaders().get((HttpHeaders.SET_COOKIE)));
//
//        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<?> responseEntityToClient = new ResponseEntity<>(header2Client, HttpStatus.PERMANENT_REDIRECT);

        return responseEntityToClient;
    }

    @GetMapping("/v2/authorize")
    public ResponseEntity<?> authv2(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String referer = request.getHeader("referer");
        referer = URLDecoder.decode(referer, "UTF-8");

        Map<String, String> paraMap = Url2ParamMap.getMap(referer);

        String refererPara = Url2ParamMap.getUrlPara(paraMap);

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.COOKIE, request.getHeader(HttpHeaders.COOKIE));
        HttpEntity<String> request2UAA = new HttpEntity<>(mapper.writeValueAsString(paraMap), headers);

        //Why exchange?
        ResponseEntity<?> responseEntityFromUAA = restTemplate.exchange("http://47.92.137.0:9001/api/oauth/authorize" + refererPara ,HttpMethod.GET, request2UAA, String.class);

        responseEntityFromUAA.getHeaders().get((HttpHeaders.SET_COOKIE));

        HttpHeaders header2Client = new HttpHeaders();

        header2Client.addAll(HttpHeaders.SET_COOKIE, responseEntityFromUAA.getHeaders().get((HttpHeaders.SET_COOKIE)));

        String redirect_uri = paraMap.get("redirect_uri");
        URI uri = URI.create(redirect_uri);
        header2Client.setLocation(uri);

        Map<String, Object> res = new HashMap<>();

        res.put("AccessToken", responseEntityFromUAA.getHeaders().get((HttpHeaders.SET_COOKIE)));

        ObjectMapper objectMapper = new ObjectMapper();


        ResponseEntity<?> responseEntityToClient = new ResponseEntity<>(objectMapper.writeValueAsString(res), header2Client, HttpStatus.PERMANENT_REDIRECT);

        return responseEntityToClient;
    }

}
