package org.tecky.uaaservice.security.config;

import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.concurrent.SucceededFuture;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.tecky.uaaservice.security.config.impl.RedirectRequestCache;
import org.tecky.uaaservice.security.handler.AuthSuccessRedirectHandler;
import org.tecky.uaaservice.security.services.UserDetailsServiceImpl;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    AuthSuccessRedirectHandler authSuccessRedirectHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(){

        var authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authProvider);
    }


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("FilterChain");

         http
                 .cors(withDefaults())
                 .csrf()
                    .disable()
//                 .formLogin()
//                    .loginPage("/login.html")
//                    .loginProcessingUrl("/api/auth/login")
//                    .successHandler(authSuccessRedirectHandler)
//                    .and()
                 .authorizeRequests()

                    .antMatchers("/index.html").permitAll()
                    .antMatchers("/index").permitAll()
                    .antMatchers("/login.html").permitAll()
                    .antMatchers("/api/user/login").permitAll()

                     .antMatchers("/api/auth/**").permitAll()
                     .antMatchers("/api/oauth/**").permitAll()

                    .antMatchers("/*.js").permitAll()
                    .antMatchers("/**/*.js").permitAll()
                    .antMatchers("/*.css").permitAll()
                    .antMatchers("/**/*.css").permitAll()
                    .antMatchers("/*.ico").permitAll()
                    .antMatchers("/**/*.ico").permitAll()
                    .antMatchers("/*.jpg").permitAll()
                    .antMatchers("/**/*.jpg").permitAll()
                    .anyRequest().permitAll()
                    .and();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET","POST"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }



}