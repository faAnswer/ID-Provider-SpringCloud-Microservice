package org.tecky.viewserver.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.faAnswer.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String[] requestToken = new String[1];

        Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("Authorization"))
                .forEach(cookie ->
                    requestToken[0] = cookie.getValue()
                );

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestToken[0]== null) {
            log.info("JWT Token Not Found");
            chain.doFilter(request, response);
            return;
        }
        String jwtToken = requestToken[0];
        JwtToken jwtTokenUtil = new JwtToken(this.secret);

        String username = null;

        if (!jwtTokenUtil.valid(jwtToken)) {


            chain.doFilter(request, response);
            return;

        }

        List<LinkedHashMap<String, String>> authorizeList;
        try {

            username = (String) jwtTokenUtil.getPayload(jwtToken, "username");
            authorizeList = (List<LinkedHashMap<String, String>>) jwtTokenUtil.getPayload(jwtToken, "authorize");

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

        if (username == null || authorizeList == null) {

            log.info("Missing Data in JWT Token");
            chain.doFilter(request, response);
            return;

        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {

            chain.doFilter(request, response);
            return;
        }

        LinkedHashMap<String, String> linkedHashMap = authorizeList.get(0);

        Set<String> keys = linkedHashMap.keySet();

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String key : keys) {

            authorities.add(new SimpleGrantedAuthority(linkedHashMap.get(key)));
        }

//        // if token is valid configure Spring Security to manually set
//        // authentication

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        chain.doFilter(request, response);
    }
}