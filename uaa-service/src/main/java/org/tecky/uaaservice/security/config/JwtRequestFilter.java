package org.tecky.uaaservice.security.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.faAnswer.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.filter.OrderedFormContentFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tecky.uaaservice.security.services.UserDetailsServiceImpl;
import org.tecky.uaaservice.util.JwtTokenUtil;

import javax.persistence.PostLoad;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
//@Slf4j
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String requestTokenHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwtToken = null;
//
//        // JWT Token is in the form "Bearer token". Remove Bearer word and get
//        // only the Token
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
//
//            jwtToken = requestTokenHeader.substring(7);
//            try {
//
//                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//            } catch (IllegalArgumentException e) {
//
//                log.info("Unable to get JWT Token");
//            } catch (ExpiredJwtException e) {
//
//                log.info("JWT Token has expired");
//            }
//        } else {
//            log.info("JWT Token does not begin with Bearer String");
//
//            logger.warn("JWT Token does not begin with Bearer String");
//        }
//        // Once we get the token validate it.
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//
//            // if token is valid configure Spring Security to manually set
//            // authentication
//            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
//
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//
//                // After setting the Authentication in the context, we specify
//                // that the current user is authenticated. So it passes the
//                // Spring Security Configurations successfully.
//
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        JwtToken jwtTokenUtil = new JwtToken(this.secret);

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if(requestTokenHeader == null){
            chain.doFilter(request, response);
            return;
        }
        if (!requestTokenHeader.startsWith("Bearer")) {

            log.info("JWT Token does not begin with Bearer String");
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = requestTokenHeader.substring(7);
        String username = null;

        if (jwtTokenUtil.valid(jwtToken)) {

            log.info("Invaild JWT Token");
            chain.doFilter(request, response);
            return;

        }

        try {

            username = (String) jwtTokenUtil.getPayload(jwtToken, "username");
        } catch (MalformedJwtException | SignatureException e) {

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
        }

        if(username == null){

            log.info("Missing Username in JWT Token");
            chain.doFilter(request, response);
            return;

        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {

            chain.doFilter(request, response);
            return;
        }

        // Once we get the token validate it.
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        // if token is valid configure Spring Security to manually set
        // authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        chain.doFilter(request, response);
    }
}
