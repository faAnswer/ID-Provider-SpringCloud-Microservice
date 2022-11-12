package org.tecky.userresource.security.filter;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.faAnswer.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tecky.userresource.services.ClientSecretService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class AccessTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    ClientSecretService clientSecretService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String[] requestToken = new String[1];

        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(element -> element.getName().equals("AccessToken"))
                .findFirst();

        if(!cookie.isPresent()){

            log.info("Access Token Not Found");
            chain.doFilter(request, response);
            return;
        }

        String clienId = request.getParameter("client_id");
        String clientSecret = clientSecretService.secretMap.get(clienId);

        if(clientSecret == null){

            log.info("Invaild Client ID");
            chain.doFilter(request, response);
            return;
        }

        String coreJWT = cookie.get().getValue();
        JwtToken coreTokenJwtToken = new JwtToken(this.secret, coreJWT);

        if (!coreTokenJwtToken.valid()) {

            log.info("Invaild Core JWT");

            chain.doFilter(request, response);
            return;
        }

        String clientJWT = (String) coreTokenJwtToken.getPayload(clienId);

        if(clientJWT == null){

            log.info("Client Access Token Not Found");
            chain.doFilter(request, response);
            return;
        }

        JwtToken clientTokenJwtToken = new JwtToken(clientSecret, clientJWT);

        if (!clientTokenJwtToken.valid()) {

            log.info("Invaild Client JWT");

            chain.doFilter(request, response);
            return;
        }

        String username = null;

        List<String> authorizeList;
        try {

            username = (String) clientTokenJwtToken.getPayload("username");
            authorizeList = (List<String>) clientTokenJwtToken.getPayload("scope");

        } catch (MalformedJwtException e) {

            log.info("Invaild JWT Token");
            chain.doFilter(request, response);
            return;
        } catch (IllegalArgumentException e) {

            log.info("Unable to get JWT Token");
            chain.doFilter(request, response);
            return;
        } catch (ExpiredJwtException e) {

            log.info("JWT Token has expired");
            chain.doFilter(request, response);
            return;
        } catch (NullPointerException e) {

            log.info("Missing Data in JWT Token");
            chain.doFilter(request, response);
            return;
        }

        if (username == null || authorizeList.isEmpty()) {

            log.info("Missing Data in JWT Token");
            chain.doFilter(request, response);
            return;

        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {

            chain.doFilter(request, response);
            return;
        }


        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String scope : authorizeList) {

            authorities.add(new SimpleGrantedAuthority("ROLE_" + scope));
        }

//        // if token is valid configure Spring Security to manually set
//        // authentication

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(clienId, username, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        chain.doFilter(request, response);
    }
}