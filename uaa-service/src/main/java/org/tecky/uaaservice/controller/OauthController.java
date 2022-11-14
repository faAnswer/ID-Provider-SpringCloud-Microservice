package org.tecky.uaaservice.controller;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import lombok.extern.slf4j.Slf4j;
import org.faAnswer.web.util.json.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.tecky.uaaservice.security.services.JwtResponseImpl;
import org.tecky.uaaservice.services.impl.AccessTokenServiceImpl;
import org.tecky.uaaservice.services.impl.UserDetailsServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/oauth")
@CrossOrigin("*")
public class OauthController {

    @Autowired
    private AccessTokenServiceImpl accessTokenServiceImpl;

    @GetMapping(value = "/authorize")
    public ResponseEntity<?> auth(@RequestParam Map<String, String> oauthInfo, HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception{

        final String[] checkToken = new String[1];

        try{
            Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("AccessToken"))
                    .forEach(cookie ->
                            checkToken[0] = cookie.getValue()
                    );

        } catch (NullPointerException e) {

            log.info("Access token Not Found");
        }

        String accessToken;

        if (checkToken[0] == null) {


            accessToken = accessTokenServiceImpl.getAccessToken(authentication, oauthInfo, "");

        } else {

            accessToken = accessTokenServiceImpl.getAccessToken(authentication, oauthInfo, checkToken[0]);
        }

        JwtResponseImpl token = new JwtResponseImpl(accessToken);

        Cookie cookie = new Cookie("AccessToken", token.getToken());

        cookie.setMaxAge(7*24*60*60*60);
        cookie.setPath("/");
        response.addCookie(cookie);

        return JSONResponse.ok("access", token.getToken());
    }

//    @PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded")
//    public ResponseEntity<?> login(@RequestParam Map<String, String> userInfo, Authentication authentication) throws Exception{
//
//        log.info("authLogin");
//
//        authenticate(userInfo.get("username"), userInfo.get("password"));
//
//        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userInfo.get("username"));
//
//        JwtResponseImpl token = new JwtResponseImpl(jwtTokenUtil.generateToken(userDetails));
//
//        return ResponseEntity.ok().header("Authorization", token.getToken()).body(token.getToken());
//
//    }

//    @PostMapping(value = "/token", consumes = "multipart/form-data")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String, String> userInfo) throws Exception {
//
//        log.info("Auth");
//        authenticate(userInfo.get("username"), userInfo.get("password"));
//
//        final UserDetails userDetails = userDetailsServiceImpl
//                .loadUserByUsername(userInfo.get("username"));
//
//
//        JwtResponseImpl token = new JwtResponseImpl(jwtTokenUtil.generateToken(userDetails));
//
//        final String tokenS = jwtTokenUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok().header("Authorization", token.getToken()).body(token.getToken());
//    }
}
