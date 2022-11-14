package org.tecky.uaaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.faAnswer.jwt.JwtToken;
import org.faAnswer.web.util.json.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import org.tecky.uaaservice.entities.UserEntity;
import org.tecky.uaaservice.security.services.JwtResponseImpl;
import org.tecky.uaaservice.services.impl.UserDetailsServiceImpl;
import org.tecky.uaaservice.services.intf.IRegService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private IRegService iRegService;

    @Value("${jwt.secret}")
    private String secret;


    @GetMapping(value = "/hello")
    public String hello() throws Exception{

        return "hello";
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userInfo, HttpServletRequest request, HttpServletResponse response) throws Exception{

        log.info("register");

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userInfo.get("username"));
        userEntity.setShapassword(userInfo.get("password"));
        userEntity.setEmail(userInfo.get("email"));

        iRegService.regNewUser(userEntity);

        return login(userInfo, request, response);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userInfo, HttpServletRequest request, HttpServletResponse response) throws Exception{

        log.info("authLogin");

        authenticate(userInfo.get("username"), userInfo.get("password"), request);

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userInfo.get("username"));

        JwtToken jwtToken = new JwtToken(this.secret);

        jwtToken.setPayload("username", userDetails.getUsername());
        jwtToken.setPayload("authorize", userDetails.getAuthorities());

        JwtResponseImpl token = new JwtResponseImpl(jwtToken.generateToken());

        Cookie cookie = new Cookie("Authorization", token.getToken());

        cookie.setMaxAge(7*24*60*60*60);
        cookie.setPath("/");
        response.addCookie(cookie);

        return JSONResponse.ok("username", userInfo.get("username"));
    }

    private void authenticate(String username, String password, HttpServletRequest request) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
