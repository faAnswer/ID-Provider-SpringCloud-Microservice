package org.tecky.userresource.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.tecky.userresource.security.filter.AccessTokenFilter;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig {

    @Autowired
    AccessTokenFilter accessTokenFilter;


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("FilterChain");

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .cors(withDefaults())
                .csrf()
                .disable()

                .authorizeRequests()

                .antMatchers("/api/res/user/profile").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                //.antMatchers("/api/oauth/**").permitAll()
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

        http.addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class);

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