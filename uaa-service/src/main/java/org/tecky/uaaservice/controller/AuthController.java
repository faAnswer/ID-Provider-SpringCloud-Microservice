package org.tecky.uaaservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.tecky.uaaservice.security.services.JwtResponseImpl;
import org.tecky.uaaservice.security.services.UserDetailsServiceImpl;
import org.tecky.uaaservice.util.JwtTokenUtil;

import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping(value = "/login")
    public String login(@RequestBody Map<String, String> userInfo) throws Exception{

        authenticate(userInfo.get("username"), userInfo.get("password"));

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userInfo.get("username"));





        return "hi";
    }



    @PostMapping(value = "/token")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String, String> userInfo) throws Exception {

        log.info("Auth");
        authenticate(userInfo.get("username"), userInfo.get("password"));

        final UserDetails userDetails = userDetailsServiceImpl
                .loadUserByUsername(userInfo.get("username"));


        JwtResponseImpl token = new JwtResponseImpl(jwtTokenUtil.generateToken(userDetails));


        final String tokenS = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok().header("Authorization", token.getToken()).body(token.getToken());
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
